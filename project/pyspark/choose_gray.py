import os
import glob
import re
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--input', type=str, help='input directory path')
parser.add_argument('--output', type=str,help=('output file path'))
args = parser.parse_args()

if __name__ == '__main__':
    input_file = args.input
    output_file = args.output
    # input_file = '/home/pgroup1/project/pyspark/output'
    # output_file = '/home/pgroup1/project/pyspark/gray'

    with open(output_file, 'w') as f2:
        with open(input_file, 'r') as f1:
            line = f1.readline()

            while line:
                line = line.split('|')
                color = line[-1].rstrip('\n').rstrip('\t')
                ID = line[0]
                if color == 'GRAY':
                    f2.write(ID)
                    f2.write('\n')
                line = f1.readline()

        f1.close()
    f2.close()