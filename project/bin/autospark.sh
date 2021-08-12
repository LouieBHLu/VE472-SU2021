SPARK_PYTHON=/home/pgroup1/project/pyspark/bfs_spark.py
OUTPUT_DIR=hdfs:///user/pgroup1/demo/output_spark
INPUT_FILE=/home/pgroup1/demo/graph/graph.txt
LOCAL_FILE=/home/pgroup1/demo/spark/output.txt

hdfs dfs -rm -r $OUTPUT_DIR

python $SPARK_PYTHON -r spark -o $OUTPUT_DIR $INPUT_FILE

rm $LOCAL_FILE
LOCAL_OUTPUT_DIR=/home/pgroup1/demo/spark/output_dir
hdfs dfs -get $OUTPUT_DIR $LOCAL_OUTPUT_DIR
python /home/pgroup1/project/pyspark/combine.py --input $LOCAL_OUTPUT_DIR --output $LOCAL_FILE

echo "extract GRAY nodes"


CHOOSE_GRAY_PYTHON=/home/pgroup1/project/pyspark/choose_gray.py
GRAY_FILE=/home/pgroup1/demo/spark/gray.txt
SPARK_DEMO_DIR=/home/pgroup1/demo/spark

mkdir $SPARK_DEMO_DIR
python $CHOOSE_GRAY_PYTHON --input $LOCAL_FILE --output $GRAY_FILE

echo "number of gray nodes: "
cat $GRAY_FILE | wc -l

SAMPLE_N_PYTHON=/home/pgroup1/project/pyspark/sample_n.py
TOPN=/home/pgroup1/demo/spark/topn

python $SAMPLE_N_PYTHON --input $GRAY_FILE --output $TOPN --num 3

echo "Top N Gray points have been selected, see:"
echo $TOPN

cat $TOPN