# awk -F "\t" '{ max = 0; for (i = 2; i < NF+1; i++) {if($i > max){max=$i;}} print max}' #reducer.txt

sort -t $'\t' -k 1 -k 2 -r | awk '!x[$1]++'
exit 0