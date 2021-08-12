import sys
from mrjob.job import MRJob
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--source', type=str, help='input the source node ID')
args = parser.parse_args()

class MRSparkBFS(MRJob):
    def spark(self, input_path, output_path):
        # Spark may not be available where script is launched
        from pyspark import SparkContext

        sc = SparkContext(appName='mrjob select ID')
        # lines = sc.textFile(input_path)
        df = sc.read.format("avro").options(inferSchema='true', header='true').load(input_path)
        df.registerTempTable('t1')

        sourceID = args.source
        q1 = 'select * from t1 where song_id = {}'.format('b\''+sourceID+'\'')
        sourceNode = sc.sql(q1)
        sourceGenre = sourceNode['genre']

        with open(output_path, 'w') as fw:
            with open(input_path,'r') as f:
                line = f.readline()
                while line:
                    q = 'select * from t1 where song_id = {}'.format('b\''+sourceID+'\'')
                    targetSong = sc.sql(q)
                    fw.write(targetSong)
                    fw.write('\n')

                    line = f.readline()
            f.close()
        fw.close()

        sc.stop()

if __name__ == '__main__':
    MRSparkBFS.run()
