/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataRetrieval;

import Cleaning.DataPreProcessing;
import Cleaning.TweetBean;
import Cleaning.TweetUser;
import Cleaning.UserBean;
import Upload.Upload;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Level;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Janine Alexis
 */
public class SearchTweet2 {

    private static final String CONSUMER_KEY1 = "6hkevUeN8nMdYYrMOssoqTeG9";
    private static final String CONSUMER_SECRET1 = "Z0V1jT9259JBmRvnDXGKdKL2SZJyS1C0AWBi9ooo7Q9iiUZhHL";
    private static final String ACCESS_TOKEN1 = "986408252-uWHerWaQBc6W466BJ04rpuCJ9aUgwp11dxmMECod";
    private static final String ACCESS_TOKEN_SECRET1 = "K5kFM6l9aA9aMYEe2745Og60STwFueNbvGBdhB48AigDW";
    
    
    private static final GeoLocation CEBU_LOCATION = new GeoLocation(10.315, 123.885);
    private static final GeoLocation MANILA_LOCATION = new GeoLocation(14.5833, 120.9667);
    private static final GeoLocation USA_LOCATION = new GeoLocation(40.0000, 100.0000);
    private static final GeoLocation UK_LOCATION = new GeoLocation(53.5500, 2.4333);
    private static final GeoLocation PHILIPPINES_LOCATION = new GeoLocation(13.0000, 122.0000);
        
    
    private static final int TWEETS_PER_QUERY = 100;
    private static final int MAX_QUERIES = 50;
    
    
    private static int count = 0;
    private static RateLimitStatus searchTweetsRateLimit;
    private static long recentId = -1;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static long maxID = -1;
    private static int totalTweets = 0;
    private static String sinceDate;
    private static String untilDate;
   private int topicID;
   private Upload upload;
    
    private DataPreProcessing preProc;
    StreamSearch ss;
    /**
     * @param args the command line arguments
     */
    public static String cleanText(String text) {
        text = text.replace("\n", " ");
        text = text.replace("\t", " ");
        return text;
    }
    
    public void endSearch(){
         ss.terminate();
    }
    
    public SearchTweet2(long mostRecentTweetID, String keyword,int topicID)throws TwitterException, InterruptedException, IOException {
       upload = new Upload();
        preProc = new DataPreProcessing();
        this.topicID = topicID;
       final String keywordStatic = keyword;
       System.out.println("Keyword: " + keyword);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        untilDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        sinceDate = sdf.format(calendar.getTime());

        recentId = mostRecentTweetID;

        retrieveTweets(null, null,keyword);
        final int tempTopicID = topicID;
        new Thread(new Runnable(){
            public void run(){
                ss= new StreamSearch();
                ss.searchingStream(recentId, keywordStatic, PHILIPPINES_LOCATION,tempTopicID);
            }                
        }).start();
        System.out.println("sinceDate " + sinceDate);
        
       if(mostRecentTweetID==-1) {
            new Thread(new Runnable(){
                 public void run(){
                    try {
                        retrieveTweets(sinceDate, untilDate,keywordStatic);
                    } catch (TwitterException ex) {
                        java.util.logging.Logger.getLogger(SearchTweet2.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(SearchTweet2.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(SearchTweet2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }

             }).start();
       }
    }
    
    
    public String getLimit(Twitter twitter) {
        Map<String, RateLimitStatus> rateLimitStatus = null;
        try {
            rateLimitStatus = twitter.getRateLimitStatus("search");
        } catch (TwitterException ex) {
            java.util.logging.Logger.getLogger(SearchTweet2.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");
        return "You have " + searchTweetsRateLimit.getRemaining() + " calls remaining out of " + searchTweetsRateLimit.getLimit() + ", Limit resets in " + searchTweetsRateLimit.getSecondsUntilReset() + " seconds\n";
    }

    

    public void retrieveTweets(String sinceDate, String untilDate,String keyword) throws TwitterException, InterruptedException, IOException {
        GsonPrettyPrinting gformat = new GsonPrettyPrinting();
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY1)
                .setOAuthConsumerSecret(CONSUMER_SECRET1)
                .setOAuthAccessToken(ACCESS_TOKEN1)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET1);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        System.out.println(getLimit(twitter));
        
        ArrayList<TweetUser> toUpload = new ArrayList();
        
        for (int queryNumber = 0; queryNumber < MAX_QUERIES; queryNumber++) {
            if (searchTweetsRateLimit.getRemaining() < 20) {
                System.out.println("Sleeping for " + searchTweetsRateLimit.getSecondsUntilReset());
                Thread.sleep(searchTweetsRateLimit.getSecondsUntilReset() * 1000);
            }
            
            Query q = new Query(keyword);
            q.setCount(TWEETS_PER_QUERY);
            q.setGeoCode(PHILIPPINES_LOCATION, 20000, Query.Unit.mi);
             if (sinceDate == null && untilDate == null) {
            q.setSince(sinceDate);
            q.setUntil(untilDate);
            }
             
            q.resultType(Query.ResultType.recent);

            if (maxID != -1) {
                q.setMaxId(maxID - 1);
            }

            QueryResult r = twitter.search(q);

            if (r.getTweets().isEmpty()) {
                break;
            }
            
            recentId = r.getTweets().get(0).getId();           //MOST RECENT ID
            for (Status s : r.getTweets()) {

                totalTweets = totalTweets + 1;
                if (maxID == -1 || s.getId() < maxID) {
                    maxID = s.getId();
                }

                Tweet tweet = new Tweet();
                tweet.setTweetId(s.getId());
                tweet.setTweetMsg(cleanText(s.getText()));
                tweet.setTweetDate(s.getCreatedAt().toString());
                tweet.setTweetLang(s.getLang());

                tweet.setUserId(s.getUser().getId());
                tweet.setUserLoc(s.getUser().getLocation());
                tweet.setUserName(s.getUser().getName());
                tweet.setUserGender(tweet.getNamePart(tweet.getUserName(), 1), tweet.getNamePart(tweet.getUserName(), 2));
                tweet.setUserScreenName(s.getUser().getScreenName());

                String tweetLoc;
                if (s.getPlace() != null) {
                    tweetLoc = s.getGeoLocation().toString();

                } else {
                    tweetLoc = "null";
                }
                tweet.setTweetLoc(tweetLoc);
                final Tweet tweetBean = tweet;
                
                if(toUpload.size()<5){
                    toUpload.add(preProc.preProcess(tweetBean, topicID));
                }else{
                    upload.upload(toUpload);
                    toUpload = new ArrayList();
                }
                
                
                //System.out.println(gformat.formatJson(tweet));
            }

        }
    }
}
