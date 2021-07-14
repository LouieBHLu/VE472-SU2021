import glob
from unicodedata import name
import pandas as pd
from tqdm import tqdm
import sys
import os
import time

files = glob.glob("flight_data/*.bz2")

####### Read and Store the data in RAM #######
def readset(num):
    data = pd.DataFrame()
    topn = files[:num]
    for file in tqdm(topn):    
        one_file = pd.read_csv(file, compression='bz2', header=0, encoding='latin-1', usecols=[1,2,3,4,5,6,16,25])    
        data = data.append(one_file)
    return data

####### Processing precedures for CPU and RAM test #######
def process(data):
    p2 = data[['Origin','WeatherDelay']]
    p2 = p2.dropna()
    p2 = p2[p2['WeatherDelay'] > 0]
    top3 = p2.groupby('Origin').size()
    top3.sort_values(ascending=False).head(3)
    return top3


pid = os.getpid()
print("Pid: " + str(pid))
num = int(sys.argv[1])
print("Total " + str(num) + " file(s).")

df = readset(num)
print("Data is stored!")
time.sleep(1)
print("Start Processing!")
while True:
    result = process(df)


    