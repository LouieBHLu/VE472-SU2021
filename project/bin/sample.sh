#!/bin/bash

filename="../tmp/sample_list"

for line in `cat $filename`
do
   cp $line /home/pgroup1/data
done