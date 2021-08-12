SOURCE=SOWFLUH12AB018C135
H5PATH=/home/pgroup1/project/tmp/subset_10.h5
OUTPUT_FILE=/home/pgroup1/demo/graph/graph.txt

GEN_PYTHON=/home/pgroup1/project/graph/gen_graph.py

python $GEN_PYTHON --source $SOURCE --path $H5PATH --output $OUTPUT_FILE
