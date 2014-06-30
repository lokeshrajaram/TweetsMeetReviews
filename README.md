# TweetsMeetReviews

The idea is to get tweets from Twitter Stream and possibly associate them with the reviews in the system.

User Tweet about a product(e.g Movie), Tweet get streamed by Twitter Streaming API.
Tweets are received on the cluster using Apache Flume and stored in Hadoop HDFS.
The stored Tweets are partitioned hourly and analyzed for possible association 
with a review by a MapReduce job. The processed Tweets are then stored in HBase.

Reviews are loaded in to HBase as well, at the bootstrapping of the application.

REST APIs are built for consuming the results.

  
### Data Source
    1. Twitter Stream (Twitter Streaming API)

    2. Amazon Reviews (Snap Dataset)


### Volume, Velocity, and Variety
    1. Volume: Movie Reviews 1 GB(~800,000 records: ~12,000 Unique Movie Titles)
               Tweets processed (~1 Million tweets per day)
    
    2. Velocity: The rate at which the tweets are received from Twitter Streaming API(~20,000-40,000 tweets per hour)
    
    3. Variety: Two different data sources used (1. Tweets 2. Reviews)  

### REST API

    Get Tweets (Limited by 100)

    Get Tweets from ID (Limited by 100)

    Get Reviews for Movie
    

### Bootstrapping the Application
(Involves: Script to Cleanup data & Importing to HBase)


### Setup the cluster to receive Tweets continously
(Involves: Flume, HDFS)


### Processing Jobs(Involves: Hadoop, HDFS, MapReduce, Hive)


### Storing processed Tweets(Involves: HDFS, Script to retrieve results and store, HBase)


### Serving the processed result
(Involves: Rest API powered by a web server)