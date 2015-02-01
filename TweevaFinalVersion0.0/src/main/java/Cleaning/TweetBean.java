/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cleaning;

/**
 *
 * @author RestyLouis
 */
public class TweetBean {
    private String tweetID;
    private String userID;
    private String tweetTXT;
    private int topicID;
    private String date;
    private String location;
    private String language;
    private float sentimentScore;
    
    public TweetBean(){
    }
    public TweetBean(String tweetID, String userID,int topicID, String tweetTXT,String date,String language, String location){
        this.tweetID = tweetID;
        this.location = location;
        this.userID = userID;
        this.date = date;
        this.topicID = topicID;
        this.language = language;
        this.tweetTXT = tweetTXT;    
    }
    public TweetBean(String tweetID, String userID,int topicID, String tweetTXT,String date,String language, String location,float sentimentScore){
        this.tweetID = tweetID;
        this.location = location;
        this.userID = userID;
        this.topicID = topicID;
        this.tweetTXT = tweetTXT;
        this.language = language;
        this.date = date;
        this.sentimentScore = sentimentScore;
    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    
    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    
    public String getTweetID() {
        return tweetID;
    }

    public String getUserID() {
        return userID;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getTweetTXT() {
        return tweetTXT;
    }

    public void setTweetTXT(String tweetTXT) {
        this.tweetTXT = tweetTXT;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(float sentimentScore) {
        this.sentimentScore = sentimentScore;
    }
    
    
}
