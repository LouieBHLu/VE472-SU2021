import argparse

import h5py
import numpy as np

parser = argparse.ArgumentParser()
parser.add_argument('--source', type=str, help='source id')
parser.add_argument('--path', type=str, help='file path')
parser.add_argument('--output', type=str, help='output file name')

args = parser.parse_args()
source = args.source
path = args.path
output_file = args.output

f = h5py.File(path, "r")

m = f['metadata']
songs = m['songs']
song_ids = songs.fields("song_id")
hots = songs.fields("artist_hotttnesss")

c = []

for i in range(len(songs)):
    idx = song_ids[i]
    hot = hots[i]
    
    c.append((idx.decode('UTF-8'), hot))

# Sort
c.sort(key=lambda tup: tup[1])

print(c[:5])

n = len(c)
# n = 50
maxSize = 100
minDis = 999999
maxDis = -1
with open(output_file, 'w') as graphFile:
    for i in range(n):
        # neibour = []
        # if i != n - 1:
        #     neibour = [c[i+1][0]]
        neibour = []
        for k in range(maxSize):
            j = i - maxSize // 2 + k
            if j == i:
                continue
            if j < 0 or j >= n:
                continue


            dis = abs(c[i][1] - c[j][1])

            # dis
            minDis = min(minDis, 
                abs(c[i][1] - c[j][1]))
            maxDis = max(maxDis,
                abs(c[i][1] - c[j][1]))

            # if dis > 1:
            #     continue

            neibour.append(c[j][0])


        line = str(c[i][0]) + '|' + \
            ','.join(neibour) + '|'
        if source == c[i][0]:
            line += str(0) + '|' + 'GRAY'
        else:
            line += str(9999) + '|' + 'WHITE'

        graphFile.write(line)
        graphFile.write('\n')

print(minDis)
print(maxDis)
print(c[0][0])
