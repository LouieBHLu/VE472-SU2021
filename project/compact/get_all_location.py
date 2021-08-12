import os
import glob
from utils import get_all_files

h5_list = get_all_files("../millionsongsubset")

# write to file
with open('../flist.csv', 'w') as writeFile:
    for i in h5_list:
        writeFile.write(i)
        writeFile.write('\n')