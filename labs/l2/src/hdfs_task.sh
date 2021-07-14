./createcsv.sh
hdfs dfs -rm input/grade.csv
hdfs dfs -put grade.csv input
hdfs dfs -rm -r output

time hadoop jar /usr/local/hadoop/share/hadoop/tools/lib/hadoop-streaming-3.2.2.jar -D stream.non.zero.exit.is.failure=false -files mapper.sh,reducer.sh -input input/grade.csv -output output -mapper "mapper.sh" -reducer "reducer.sh"


# START=`date +%s%N`;
# cat grade.csv | ./mapper.sh | ./reducer.sh
# END=`date +%s%N`;
# time=$((END-START))
# time=`expr $time / 1000000`
# echo $time


# time hadoop jar /usr/local/hadoop/share/hadoop/tools/lib/hadoop-streaming-3.2.1.jar  -D stream.non.zero.exit.is.failure=false -files mapper.sh,reducer.sh -input input/grade.csv -output output -mapper "mapper.sh" -reducer "reducer.sh"

