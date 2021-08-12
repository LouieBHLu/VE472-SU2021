MAPREDUCE_PYTHON=/home/pgroup1/project/mapreduce/steps.py
OUTPUT_DIR=hdfs:///user/pgroup1/demo/output_mr
INPUT_FILE=/home/pgroup1/demo/graph/graph.txt
LOCAL_FILE=/home/pgroup1/demo/mapreduce/output.txt

hdfs dfs -rm -r $OUTPUT_DIR

python $MAPREDUCE_PYTHON -r hadoop -o $OUTPUT_DIR $INPUT_FILE

rm $LOCAL_FILE
hdfs dfs -get $OUTPUT_DIR/part-00000 $LOCAL_FILE

echo "extract GRAY nodes"


CHOOSE_GRAY_PYTHON=/home/pgroup1/project/pyspark/choose_gray.py
GRAY_FILE=/home/pgroup1/demo/mapreduce/gray.txt
MAPREDUCE_DEMO_DIR=/home/pgroup1/demo/mapreduce

mkdir $MAPREDUCE_DEMO_DIR
python $CHOOSE_GRAY_PYTHON --input $LOCAL_FILE --output $GRAY_FILE

echo "number of gray nodes: "
cat $GRAY_FILE | wc -l

SAMPLE_N_PYTHON=/home/pgroup1/project/pyspark/sample_n.py
TOPN=/home/pgroup1/demo/mapreduce/topn

python $SAMPLE_N_PYTHON --input $GRAY_FILE --output $TOPN --num 3

echo "Top N Gray points have been selected, see:"
echo $TOPN

cat $TOPN
