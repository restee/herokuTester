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
public class DataProcessing {
    DatabaseConnector database;
    SentimentAnalysis systemAn = new SentimentAnalysis();
    
    public  DataProcessing(){
        database = new DatabaseConnector();
    }
    
    public TweetUser process(TweetUser tweet){
        String output = "";
        tweet.getTweet().setSentimentScore(systemAn.calculateSentiment(tweet.getTweet().getTweetTXT()));
        
        
        
        output +="\n" + tweet.getTweet().getTweetID() + "\n";
        output+=tweet.getTweet().getTweetTXT()+"\n";
        output+= tweet.getTweet().getDate()+"\n";
            output+= tweet.getTweet().getTopicID()+"\n";
            output+= tweet.getTweet().getLanguage()+"\n";
            output+= tweet.getTweet().getLocation()+"\n";
            output+= tweet.getTweet().getSentimentScore()+"\n";
            output+= tweet.getUser().getUserid()+"\n";
            output+= tweet.getUser().getScreenName()+"\n";
            output+= tweet.getUser().getFullName()+"\n";
            output+= tweet.getUser().getAddress()+ "\n";
            output+= tweet.getUser().getGender()+"\n";
            
        database.addTweetEntry(tweet.getTweet());
        database.addUserEntry(tweet.getUser());
        System.out.println(output);
        
        return tweet;
    }
}
