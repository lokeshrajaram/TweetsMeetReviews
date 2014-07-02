#!/bin/sh

set -v

HOUR=`date --date='1 hour ago' +"%H"`
DAY=`date --date='1 hour ago' +"%d"`
MONTH=`date --date='1 hour ago' +"%m"`
YEAR=`date --date='1 hour ago' +"%Y"`

FILE_PATH="/user/flume/tweets/$YEAR/$MONTH/$DAY/$HOUR"

PARTITION_KEY=$YEAR$MONTH$DAY$HOUR

echo "FILE_PATH:: $FILE_PATH" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

echo "PARTITION_KEY:: $PARTITION_KEY" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

sudo -u hdfs hive -e "add jar /home/ubuntu/lib/hive-serdes-1.0-SNAPSHOT.jar; LOAD DATA INPATH '$FILE_PATH' INTO TABLE default.tweets PARTITION (datehour='$PARTITION_KEY');" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log 2>&1

echo "Completed Partitioning" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

sudo -u hdfs hive -e "add jar /home/ubuntu/lib/hive-serdes-1.0-SNAPSHOT.jar; INSERT OVERWRITE DIRECTORY '/user/ubuntu/tweets/$PARTITION_KEY' select id, created_at, LOWER(text) from tweets where datehour='$PARTITION_KEY';" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log 2>&1

echo "Completed Parsing" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

USERCP=$(hadoop classpath):$(hbase classpath):/home/ubuntu/jar/project.jar

hadoop jar /home/ubuntu/jar/project.jar map.reduce.twitter.tweets.TweetsApplication /user/ubuntu/tweets/$PARTITION_KEY /user/ubuntu/tweets/processed/$PARTITION_KEY >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log 2>&1

echo "Completed Movie Matching" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

java -cp $USERCP tweets.export.TweetsExport /user/ubuntu/tweets/processed/$PARTITION_KEY/part-m-00000 >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log 2>&1

echo "Completed Exporting to HBase"  >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

hive -e "LOAD DATA INPATH '/user/ubuntu/tweets/processed/$PARTITION_KEY' INTO TABLE default.tweets_stats PARTITION (datehour='$PARTITION_KEY');" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log 2>&1

echo "Completed loading processed Tweets"  >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

echo "Computing counts" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

hive -e "add jar /home/ubuntu/lib/hive-serdes-1.0-SNAPSHOT.jar; INSERT OVERWRITE LOCAL DIRECTORY '/home/ubuntu/stats/$PARTITION_KEY' select tweetcountkey, tweetcount, matchcount from ( select count(id) as tweetcount , '$PARTITION_KEY' as tweetcountkey from tweets_stats where datehour='$PARTITION_KEY') tc join (select count(id) as matchcount,'$PARTITION_KEY' as matchcountkey from tweets_stats where datehour='$PARTITION_KEY' and review_title <> 'NO MATCH') mc on (tc.tweetcountkey=mc.matchcountkey);" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log 2>&1 

echo "Exporting counts" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

java -cp $USERCP stats.export.StatsExport /home/ubuntu/stats/$PARTITION_KEY/000000_0 >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log 2>&1

echo "Completed Exporting counts" >> /home/ubuntu/scripts/log/$PARTITION_KEY.cron.log

set +v