## Run sample test 
- Output in std
```bash
python bfs_spark.py small_input.txt 
```

## Run on Spark Server
```bash
../bin/combine.sh /home/pgroup1/project/mapreduce/output/big0.txt /home/pgroup1/project/pyspark/big0 /home/pgroup1/project/pyspark/output
```

## Select gray node
```bash
python choose_gray.py --input output --output gray
```