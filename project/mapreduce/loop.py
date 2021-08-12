import os
import argparse

from bfs_iter import MRBFSIteration

parser = argparse.ArgumentParser()
parser.add_argument('--local', action='store_true', help='running in local mode; disabled default, so using server by deafult')
parser.add_argument('--prefix', type=str,
                    help=('prefix. In local mode, it is the prefix of file name;'
                          'however in server mode, it stands for path and'
                          'prefix can or cannot contain trailing slash in server mode'
                          'e.g. mapreduce/ or mapreduce are both ok'
                         ))

args = parser.parse_args()

local_mode = args.local
prefix = args.prefix
if local_mode == False:
    prefix = prefix.rstrip('/') + '/'

hdfs_home_path = 'hdfs:///user/pgroup1/'

def single_job(input_file, output_file):

    if local_mode:
        bfs_args = [input_file]
    else:
        bfs_args = ['-r', 'hadoop', '-o', output_file, input_file]

    mr_job = MRBFSIteration(bfs_args)

    with mr_job.make_runner() as runner:
        runner.run()

        counters = runner.counters()
        # print(len(counters))
        # print(counters[0]['remained']['Number of remaining nodes'])
        print(counters)
        remained = counters[0].get('remained', None)
        hasCounter = remained != None
        if hasCounter:
            num_remaining_nodes = remained['Number of remaining nodes']
        else:
            num_remaining_nodes = -1

        if local_mode:
            with open(output_file, 'w') as file:
                for _, value in mr_job.parse_output(runner.cat_output()):
                    print(str(value).rstrip('\n'))
                    file.write(value)

        return hasCounter, num_remaining_nodes


if local_mode == False:
    # remove potential output directory before running first iteration
    os.system('hdfs dfs -rm -r ' + hdfs_home_path + prefix + 'output/')

for i in range(0, 10):
    if local_mode:
        # prefix here acts for its file name
        input_file = 'output/' + prefix + str(i) + '.txt'
        output_file = 'output/' + prefix + str(i+1) + '.txt'
    else:
        # upon server, path is under hdfs:// protocol prefix
        # storage of mapreduce output is also under hdfs
        # therefore, we might `cp part-00000` to another place
        # for coding simplicity as we would iterate multiple times
        #
        # also, remove <...>/output directory before storing
        # mapreduce's output

        # prefix should contain trailing slash for server mode
        # e.g. mapreduce/
        if i != 0:
            input_file = hdfs_home_path + prefix + 'file' + str(i) + '.txt'
        else:
            # for the first iteration, use name `0file.txt`
            input_file = hdfs_home_path + prefix + '0file.txt'
        # it is not file, but output directory
        output_file = hdfs_home_path + prefix + 'output/'
        # if no error occurs, the output of mapreduce will be stored under
        # <output_file> directory, named `part-00000`
        # it will be then moved to its parent folder naming similar to
        # <input_file>, this step does not take much time

    hasCounter, num_remaining_nodes = single_job(input_file, output_file)

    if local_mode == False:
        # server mode
        # as mentioned before, move output of mapreduce to its parent
        # folder and rename it properly
        # the file name is called file0.txt for instance
        # os.system('hdfs dfs -cat ' + hdfs_home_path + prefix + 'output/part-00000')
        cml = 'hdfs dfs -mv ' + hdfs_home_path + prefix + 'output/part-00000 ' + prefix + 'file' + str(i+1) + '.txt'
        os.system(cml)
        os.system('echo ' + '"' + cml + '"')

        # remove output/ directory
        cml = 'hdfs dfs -rm -r ' + hdfs_home_path + prefix + 'output/'
        os.system(cml)
        os.system('echo ' + '"' + cml + '"')

    print(i, hasCounter, num_remaining_nodes)

    if hasCounter == False:
        break
