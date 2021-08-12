import os
import glob
import re
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('--input', type=str, help='input directory path')
parser.add_argument('--output', type=str,help=('output file path'))
parser.add_argument('--num', type=int,help=('sample number'))
args = parser.parse_args()

if __name__ == '__main__':
    input_file = args.input
    output_file = args.output
    # input_file = '/home/pgroup1/project/pyspark/gray'
    # output_file = '/home/pgroup1/project/pyspark/topn'
    n = args.num
    # n = 3
    
    with open(output_file, 'w') as f2:
        with open(input_file, 'r') as f1:
            line = f1.readline()
            i = 0

            while line and i < n:
                f2.write(line)
                line = f1.readline()
                i += 1
        f1.close()
    f2.close()