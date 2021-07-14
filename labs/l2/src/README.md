# VE472 - Lab 2

## Introduction

This project is to execute a MapReduce program on a Hadoop cluster. 

## MapReduce program
*   `createcsv.awk`, `createcsv.sh`
    
    First, we use the lists of first-names and last-names in `l2-names` to create a csv file `grade.csv` where the first column contains a list of students, the second a ten digit random student ID, and the third one a random grade in the range 0 to 100. Each students appears a random number of times all along the file with different grades.

*   `mapper.sh`

    Then, we extract the grades from `grade.csv` and for each line outputs on the standard output a pair of values constructed as follows: `studentID<TAB>grade`, e.g.`1234567890 34`.

*   `reducer.sh`

    Now, we read pairs from the standard input and return the max grade for each student on the standard output.

## Execution 
After the cluster is set up, we make directories in HDFS.
``` bash
hdfs dfs -mkdir /user
hdfs dfs -mkdir /user/hadoop
hdfs dfs -mkdir input
```

Put `grade.csv` into HDFS.
``` bash
hdfs dfs -put grade.csv input
```

Execute the program on HDFS.
``` bash
hadoop \
    jar /usr/local/hadoop/share/hadoop/tools/lib/hadoop-streaming-3.2.2.jar \
    -D stream.non.zero.exit.is.failure=false \
    -files mapper.sh,reducer.sh \
    -input input/grade.csv \
    -output output \
    -mapper "mapper.sh" \
    -reducer "reducer.sh"
```

To change the size of `grade.csv`, we modify the rows generated in `createcsv.awk` and rerun the program for several times. To be convenient, we collect the instructions in `hdfs_task.sh` and run by the following command. 
``` bash
./hdfs_task.sh
```