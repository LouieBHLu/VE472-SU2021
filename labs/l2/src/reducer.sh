#! /bin/bash
# declare an array var
declare -A score

while read cur_ID cur_score
do
    if [[ -v "score[$cur_ID]" ]]    # check if the id exists
    then
        if (( cur_score > score[$cur_ID] ))
        then
            score[$cur_ID]=$cur_score
        fi
    else
        score[$cur_ID]=$cur_score
    fi
done < "${1:-/dev/stdin}"


for i in ${!score[@]}; do
    echo -e "$i\t${score[$i]}"
done