# VE472 Lab 4  Drill

```bash
hdfs dfs -mkdir l4
hdfs dfs -put ./mapper0.csv l4
```

We can check `http://10.119.6.238:8047/storage` for storage plugin configuration

2. 1)student who had the lowest grade:

```sqlite
apache drill> select name, min(score) as min_score from (select columns[0] as name, INTEGER columns[2] as score from hdfs.`/user/pgroup1/l4/mapper0.csv`) group by name order by min_score asc limit 1;
+----------------+-----------+
|      name      | min_score |
+----------------+-----------+
| Dwana Harradon | 0         |
+----------------+-----------+
1 row selected (31.429 seconds)
```

​		2)student who had the highest average grade:

```sqlite
apache drill> select name, AVG(score) as avg_score from (select columns[0] as name, cast(columns[2] as int) as score from hdfs.`/user/pgroup1/l4/mapper0.csv` ) group by name order by avg_score desc limit 1;
+----------------+------------------+
|      name      |    avg_score     |
+----------------+------------------+
| Domonique Mess | 53.2076209086468 |
+----------------+------------------+
1 row selected (48.804 seconds)
```

3. median over all the scores:

```sqlite
apache drill> SELECT COUNT(*) FROM hdfs.`/user/pgroup1/l4/mapper0.csv`;
+-----------+
|  EXPR$0   |
+-----------+
| 200000000 |
+-----------+
1 row selected (22.093 seconds)
apache drill> SELECT AVG(score)
2..semicolon> FROM (SELECT cast(columns[2] as int) as score
3..........)>       FROM hdfs.`/user/pgroup1/l4/mapper0.csv`
4..........)>       ORDER BY score
5..........)>       LIMIT 2    -- odd 1, even 2
6..........)>       OFFSET 99999999)
7..semicolon> ;
+--------+
| EXPR$0 |
+--------+
| 50.0   |
+--------+
1 row selected (85.175 seconds)
```

