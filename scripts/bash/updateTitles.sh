#!/bin/sh

set -v

hdfs dfs -rm  /user/ubuntu/reviews/MovieTitles.txt

hdfs dfs -copyFromLocal  /home/ubuntu/reviews/MovieTitles.txt /user/ubuntu/reviews/

set +v