function gen_rand_int(first, last) {
    choices = last - first + 1
	return int(first + rand() * (choices))
}

BEGIN { 
    first=1000000000			    
    last=9999999999				
    row_num=100000			# row number
    lastname="l2-names/lastnames.txt"
    firstname="l2-names/firstnames.txt"

	# printf "%d\n", row_num;

    # Read the firstnames and store
    num = 0;
	while (getline < firstname){
		name[num] = $0;
		num++;
	}
	close(firstname);

    # Rand seed to generate random student ID
    srand(); 
	ind = 0;
	while (getline < lastname && ind < num){
		name[ind] = sprintf("%s %s,%010d", name[ind], $0, gen_rand_int(first, last));
		ind++;
	}

    # Random pick students and generate grades and write in the csv file
	row = 0;
	while (row++ < row_num){
		printf "%s,%d\n", name[gen_rand_int(0, num-1)], gen_rand_int(0, 99);
	}
}
