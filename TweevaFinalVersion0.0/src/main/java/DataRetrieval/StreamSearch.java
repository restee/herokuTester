/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataRetrieval;

import Cleaning.DataPreProcessing;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.GeoLocation;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Janine Alexis
 */
public class StreamSearch {

    private static final String CONSUMER_KEY2 = "vi8LvLClEYaeMCIbOsGDz0iyG";
    private static final String CONSUMER_SECRET2 = "AOV8a7ihzDNkufwK0yLDoe4hgS2O54CHn5aXslzeUtgOHVi81G";
    private static final String ACCESS_TOKEN2 = "2888212786-PYumAjT8BmCBo7sR39utJeykSgNH71zuuNyNSLo";
    private static final String ACCESS_TOKEN_SECRET2 = "HCGkQC1DvFxAfezIti4RzXwyzIMVRzaP05slH8NzAgffJ";
    private static RateLimitStatus searchTweetsRateLimit;
   
    private long recentId;
    private String keyword;
    private GeoLocation location;
    private boolean terminator = false;
    private int topicID;

    private DataPreProcessing preProc;
    public StreamSearch() {
        preProc = new DataPreProcessing();
    }
    
    public void terminate(){
        terminator = true;
    }
    public StreamSearch(long recentId, String keyword, GeoLocation location,int topicID) {
        this.recentId = recentId;
        this.keyword = keyword;
        this.location = location;
        this.topicID = topicID;
    }

    public static String cleanText(String text) {
        text = text.replace("\n", " ");
        text = text.replace("\t", " ");
        
        return text;
    }

    public static int getLimit(Twitter twitter) {
        Map<String, RateLimitStatus> rateLimitStatus = null;
        try {
            rateLimitStatus = twitter.getRateLimitStatus("search");
        } catch (TwitterException ex) {
            java.util.logging.Logger.getLogger(SearchTweet2.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");
        return searchTweetsRateLimit.getRemaining();
    }

   
    public void searchingStream(long recentId, String keyword, GeoLocation location,int topicID) {
        try {
            GsonPrettyPrinting gformat = new GsonPrettyPrinting();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(CONSUMER_KEY2)
                    .setOAuthConsumerSecret(CONSUMER_SECRET2)
                    .setOAuthAccessToken(ACCESS_TOKEN2)
                    .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET2);
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

           // System.out.printf("%s Continue? ...", getLimit(twitter) + "\n");
            //String r = in.readLine();

            Query q = new Query(keyword);

            q.setGeoCode(location, 20000, Query.Unit.mi);
            q.resultType(Query.ResultType.mixed);
            q.setSinceId(recentId);

            for (;;) {
                if(terminator){
                   break; 
                }
                if (getLimit(twitter) <20) {
                System.out.println("Sleeping for " + searchTweetsRateLimit.getSecondsUntilReset());
                Thread.sleep(searchTweetsRateLimit.getSecondsUntilReset() * 1000);
            }
                QueryResult queryResult = twitter.search(q);
                for (Status s : queryResult.getTweets()) {
                    if (s.getId() > recentId) {
                        recentId = s.getId();

                        String fullname = s.getUser().getName();
                        String[] splitname = fullname.split(" ");
                        String firstName = splitname[0];
                        String lastName = " ";
                        if (splitname.length > 1) {
                            lastName = splitname[splitname.length - 1];
                        }
                        if (splitname.length > 2) {
                            for (int i = 1; i <= splitname.length - 2; i++) {
                                firstName = firstName + " " + splitname[i];
                            }
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
                    //System.out.println(gformat.formatJson(tweet));

                
                    preProc.preProcess(tweet, topicID);
                    }
                    
                    
                }
                
                
                
                //Thread.sleep(300000);    //5 mins
                Thread.sleep(18000);   //6 ms
                //Thread.sleep(900000);   //15 mins
            }
       
        } catch (InterruptedException ex) {
            Logger.getLogger(StreamSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TwitterException ex) {
            Logger.getLogger(StreamSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
