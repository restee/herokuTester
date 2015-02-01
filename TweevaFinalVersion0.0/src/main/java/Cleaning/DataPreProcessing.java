/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cleaning;

import DataRetrieval.Tweet;
import Upload.Upload;
import com.mycompany.temp.FixGrammar;
import java.util.ArrayList;

/**
 *
 * @author RestyLouis
 */
public class DataPreProcessing {
    DataProcessing proc;
    TweetCleaner cleanTweet;
     LanguageTranslator translate;
     FixGrammar fix;
    
     public DataPreProcessing(){    
         proc = new DataProcessing();
         cleanTweet = new TweetCleaner();
         translate = new LanguageTranslator();
         fix = new FixGrammar();
    }
     
     
    public TweetUser preProcess(Tweet tweetBean,int topicID){
        String output="";
        TweetBean tb = new TweetBean();
                tb.setTweetID(Long.toString(tweetBean.getTweetId()));
                tb.setTweetTXT(tweetBean.getTweetMsg());
                tb.setLanguage(tweetBean.getTweetLang());
                tb.setDate(tweetBean.getTweetDate());
                tb.setLocation(tweetBean.getTweetLoc());
                tb.setTopicID(topicID);
                tb.setUserID(Long.toString(tweetBean.getUserId()));
                
                UserBean ub = new UserBean();
                ub.setUserid(Long.toString(tweetBean.getUserId()));
                ub.setAddress(tweetBean.getUserLoc());
                ub.setFullName(tweetBean.getUserName());
                ub.setScreenName(tweetBean.getUserScreenName());
                if(tweetBean.getUserGender()==null)
                    ub.setGender('u');
                else
                    ub.setGender(tweetBean.getUserGender().charAt(0));
                
        TweetUser tweet = new TweetUser(tb,ub);
        
        
        
           //  ReadingInput rd = new ReadingInput();         
         //SentimentAnalysis systemAn = new SentimentAnalysis();
         
         
         
        
         
         
                           
         tb = new TweetBean();
         ub = new UserBean();
         
         TweetText tt = new TweetText();
            output +="\n" + tweet.getTweet().getTweetID() + "\n";
            //System.out.println("\n" + tweet.getTweet().getTweetID());
           // System.out.println(tweet.getTweet().getTweetTXT());
          //  System.out.println(tweet.getTweet().getTweetTXT());
                         
             tweet.getTweet().setTweetTXT(cleanTweet.removeImageLinks(cleanTweet.removeSpecialChars(tweet.getTweet().getTweetTXT())));
            //tweet.getTweet().setTweetTXT(cleanTweet.cleanTweetText(tweet.getTweet().getTweetTXT()));
             
            tt = cleanTweet.separate(tweet.getTweet().getTweetTXT());
            
            if(tweet.getTweet().getLanguage().compareToIgnoreCase("tl")==0){                                 
                tt.setText(fix.fixFilipino(tt.getText()));
                
                tt.setText(translate.translate(tt.getText()));
                //tweet.getTweet().setTweetTXT(fix.fixFilipino(tt.getText()));
                //tweet.getTweet().setTweetTXT(translate.translate(tweet.getTweet().getTweetTXT()));                
            }
            
            tt.setText(fix.fixEnglish(tt.getText()));
            //tweet.getTweet().setTweetTXT(fix.fixEnglish(tweet.getTweet().getTweetTXT()));
            tweet.getTweet().setTopicID(topicID);
            
            tweet.getTweet().setTweetTXT(tt.getFullTweet());
            output+=tweet.getTweet().getTweetTXT()+"\n";
            //System.out.println(tweet.getTweet().getTweetTXT());
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
            
           
                     return proc.process(tweet);
             
        
    }
   

    
}
