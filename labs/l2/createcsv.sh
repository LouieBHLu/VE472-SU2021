AWKPATH="createcsv.awk"	# awk script
CSV="grade.csv"	        # output file

awk -f $AWKPATH > $CSV

exit 0
