INPUT_FILE=$1
INPUT_DIR=$2
OUTPUT_FILE=$3

time python /home/pgroup1/project/pyspark/bfs_spark.py -r spark $INPUT_FILE -o $INPUT_DIR
echo "Spark Done!"
time python /home/pgroup1/project/pyspark/combine.py --input $INPUT_DIR --output $OUTPUT_FILE

