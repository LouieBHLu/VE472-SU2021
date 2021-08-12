import os
import glob

def get_all_files(basedir,ext='.h5',sample=0.01) :
    """
    From a root directory, go through all subdirectories
    and find all files with the given extension.
    Given a sample rate,
    Return all absolute paths in a list.
    """
    allfiles = []
    for root, dirs, files in os.walk(basedir):
        files = glob.glob(os.path.join(root,'*'+ext))
        for f in files :
            allfiles.append( os.path.abspath(f) )
    return allfiles


def get_hdfs_h5_files(basedir, fs):
    """
    From a root directory, go through all subdirectories
    and find all h5 files.
    Return all absolute paths in a list within the hdfs.
    - basedir is base path name
    - fs is HDFS
    write file_list.csv to current dir
    """
    root = fs.ls(basedir)                       # base/
    h5_list = []
    for subdir in root:                         # base/X/
        for subsubdir in fs.ls(subdir):         # base/X/X/
            for h5_dir in fs.ls(subsubdir):     # base/X/X/X/
                h5_list += fs.ls(h5_dir)        # base/X/X/X/xxx.h5

    # if write_to_file is True:
    #     with open('file_list.csv', 'w') as writeFile:
    #         for i in h5_list:
    #             writeFile.write(i)
    #             writeFile.write('\n')
    
    return h5_list

