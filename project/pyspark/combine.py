import os
import glob
import re
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--input', type=str, help='input directory path')
parser.add_argument('--output', type=str,help=('output file path'))

args = parser.parse_args()

def get_all_files(basedir,ext=''):
    """
    From a root directory, go through all subdirectories
    and find all files with the given extension.
    Return all absolute paths in a list.
    """
    allfiles = []
    for root, dirs, files in os.walk(basedir):
        files = glob.glob(os.path.join(root,'part*'+ext))
        for f in files :
            allfiles.append(os.path.abspath(f))
    return allfiles

if __name__ == '__main__':
    parts = get_all_files(args.input)
    out_f = args.output

    with open(out_f, 'w') as out:
        for part in parts:
            with open(part,'r') as f:
                line = f.readline()
                while line:
                    result = ''
                    
                    line = line.replace('(', '')
                    line = line.replace(')', '')
                    line = line.replace(']','')
                    line = line.replace('[','')
                    line = line.replace(' ', '')
                    line = line.replace('\'', '')
                    line = line.split(',')

                    result = result + line[0] + '|'

                    connections = ''
                    for i in range(len(line)-4):
                        connections = connections + line[i+1] + ','
                    connections = connections + line[len(line)-3] + '|'
                    result = result + connections + line[-2] + '|' + line[-1]

                    out.write(result)
                    line = f.readline()   
            f.close()
    out.close()