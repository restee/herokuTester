/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cleaning;


import Upload.Upload;
import com.mycompany.temp.FixGrammar;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;



/**
 *
 * @author RestyLouis
 */



public class CharacterReplacement {

    /**
     * @param args the command line arguments
     */
        
    public static void main(String[] args) {
        ReadingInput rd = new ReadingInput();
        ArrayList<TweetUser> tweets = rd.getInputs();
         SentimentAnalysis systemAn = new SentimentAnalysis();
         TweetCleaner cleanTweet = new TweetCleaner();
         FixGrammar fix = new FixGrammar();
         Upload upload = new Upload();
         Translator translate = new Translator();
         
         DatabaseConnector connector = new DatabaseConnector();
         TweetBean tb = new TweetBean();
         UserBean ub = new UserBean();
         
         for(int init=0;init<tweets.size();init++){
            System.out.println("\n" + tweets.get(init).getTweet().getTweetID());
             System.out.println(tweets.get(init).getTweet().getTweetTXT());
            tweets.get(init).getTweet().setTweetTXT(cleanTweet.cleanTweetText(tweets.get(init).getTweet().getTweetTXT()));
            if(tweets.get(init).getTweet().getLanguage().compareToIgnoreCase("tl")==0){                                 
                tweets.get(init).getTweet().setTweetTXT(fix.fixFilipino(tweets.get(init).getTweet().getTweetTXT()));
                tweets.get(init).getTweet().setTweetTXT(translate.translate(tweets.get(init).getTweet().getTweetTXT()));                
            }
            
            tweets.get(init).getTweet().setTweetTXT(fix.fixEnglish(tweets.get(init).getTweet().getTweetTXT()));
            tweets.get(init).getTweet().setTopicID(7);
            
            System.out.println(tweets.get(init).getTweet().getTweetTXT());
            
            System.out.println(tweets.get(init).getTweet().getDate());
            System.out.println(tweets.get(init).getTweet().getTopicID());
            System.out.println(tweets.get(init).getTweet().getLanguage());            
            System.out.println(tweets.get(init).getTweet().getLocation());
            tweets.get(init).getTweet().setSentimentScore(systemAn.calculateSentiment(tweets.get(init).getTweet().getTweetTXT()));
            System.out.println(tweets.get(init).getTweet().getSentimentScore());
            System.out.println(tweets.get(init).getUser().getUserid());
            System.out.println(tweets.get(init).getUser().getScreenName());
            System.out.println(tweets.get(init).getUser().getFullName());
            System.out.println(tweets.get(init).getUser().getAddress());
            System.out.println(tweets.get(init).getUser().getGender());         
        }
       // upload.upload(tweets);
                           
    }
    
    
    
    
    public static String retrieve(String toTranslate){
        String flag = "";
        toTranslate = toTranslate.replaceAll(" ","%20");
         try {
        String url = "http://tweeva.zz.mu/translator/" + URLEncoder.encode(toTranslate,"UTF-8");
        
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;
        String outputLine = "";
       
            response = client.execute(request);
            BufferedReader rd = new BufferedReader
                (new InputStreamReader(response.getEntity().getContent()));

              String line = "";
              while ((line = rd.readLine()) != null) {
                outputLine+= line;
              } 
              
              flag = outputLine;
              //JSONObject resultJSON = new JSONObject(outputLine);
             // flag  = resultJSON.getJSONObject("responseData").getString("translatedText");
        } catch (Exception ex) {
           
        }

        // Get the response
                       
        return flag;
    }
    
    
}
