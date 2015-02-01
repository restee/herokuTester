/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataRetrieval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.GeoLocation;
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
public class RetrieveTweet implements Runnable {

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
    private String keyword;
    private static RateLimitStatus searchTweetsRateLimit;
    public long recentId = -1;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static long maxID = -1;
    private static int totalTweets = 0;
    private String sinceDate;
    private String untilDate;

    public long getRecentId() {
        return recentId;
    }

    public RetrieveTweet(String sinceDate, String untilDate, String keyword) {
        this.sinceDate = sinceDate;
        this.untilDate = untilDate;
        this.keyword = keyword;

    }

    @Override
    public synchronized void run() {
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(CONSUMER_KEY1)
                    .setOAuthConsumerSecret(CONSUMER_SECRET1)
                    .setOAuthAccessToken(ACCESS_TOKEN1)
                    .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET1);
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            System.out.println(getLimit(twitter));
            System.out.println("Press any key to continue...");
            String a = in.readLine();

            for (int queryNumber = 0; queryNumber < MAX_QUERIES; queryNumber++) {
                if (searchTweetsRateLimit.getRemaining() == 0) {
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
                q.resultType(Query.ResultType.mixed);

                if (maxID != -1) {
                    q.setMaxId(maxID - 1);
                }

                QueryResult r = twitter.search(q);

                if (r.getTweets().isEmpty()) {
                    break;
                }
                recentId = r.getTweets().get(0).getId();            //MOST RECENT ID
                for (Status s : r.getTweets()) {
                    totalTweets = totalTweets + 1;
                    if (maxID == -1 || s.getId() < maxID) {
                        maxID = s.getId();
                    }

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

                    //FOR TWEETS
                    long tweetId = s.getId();
                    String tweetMsg = cleanText(s.getText());
                    String date = s.getCreatedAt().toString();
                    String lang = s.getLang();
                    String tweetLoc;
                    //FOR USER
                    long userId = s.getUser().getId();
                    String userSN = s.getUser().getScreenName();
                    String username = s.getUser().getName();
                    String userLoc = s.getUser().getLocation();
                    String gender = Genderize.genderize(firstName, lastName);



                    if (s.getPlace() != null) {
                        tweetLoc = s.getGeoLocation().toString();
                        System.out.printf("%-5d At %s, @%-20s @%-20s ; %-2s said: %-150s %s\n", totalTweets, date, userSN, username, gender, tweetMsg, s.getPlace().getFullName());

                    } else {
                        tweetLoc = null;
                        System.out.printf("%-5d At %s, @%-20s @%-20s ; %-2s said: %-150s \n", totalTweets, date, userSN, username, gender, tweetMsg);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RetrieveTweet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RetrieveTweet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TwitterException ex) {
            Logger.getLogger(RetrieveTweet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getLimit(Twitter twitter) {
        Map<String, RateLimitStatus> rateLimitStatus = null;
        try {
            rateLimitStatus = twitter.getRateLimitStatus("search");
        } catch (TwitterException ex) {
            java.util.logging.Logger.getLogger(SearchTweet2.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");
        return "You have " + searchTweetsRateLimit.getRemaining() + " calls remaining out of " + searchTweetsRateLimit.getLimit() + ", Limit resets in " + searchTweetsRateLimit.getSecondsUntilReset() + " seconds\n";
    }

    public static String cleanText(String text) {
        text = text.replace("\n", "\\n");
        text = text.replace("\t", "\\t");
        return text;
    }
}
