AWKPATH="createcsv.awk"	# awk script
ROWNUM=$1               # number of rows
FILENUM=$2              # number of files

for((i=1; i<=$FILENUM; i++))
do
    awk -v row_num=$ROWNUM -f $AWKPATH > "grade_$i.csv"
    echo "sleep 1 second to generate different rand seed."
    sleep 1s

done

mkdir small_files
mv *.csv small_files
exit 0
