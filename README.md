# TweetsMeetReviews
An application to bind tweets with reviews.

### Introduction

This idea is to get tweets from Twitter Stream and possibly associate them with the reviews in the system.

User tweet about a product(Movie), tweet get streamed by Twitter Streaming API.
Tweets are received on the cluster using Apache Flume and stored in Hadoop HDFS.
The stored tweets are partitioned hourly and analyzed for possible association 
with a review by a MapReduce job. The processed tweets are then stored in HBase.

Reviews are loaded in to HBase as well, at the bootstrapping of the application.

REST APIs are built for consuming the results and reviews.

  
## Data Source



## Volume, Velocity, and Variety


## REST API

    Get Tweets (Limited by 100)

    Get Tweets from ID (Limited by 100)

    Get Reviews for Movie
    

