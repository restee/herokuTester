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
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author RestyLouis
 */
public class Apriori {
    public static void main(String[] args){
    ReadingInput rd = new ReadingInput();
        ArrayList<TweetUser> tweets = rd.getInputs();
        SentimentAnalysis systemAn = new SentimentAnalysis();
        TweetCleaner cleanTweet = new TweetCleaner();
        FixGrammar fix = new FixGrammar();
        Upload upload = new Upload();
        Translator translate = new Translator();
        MostCommon mc = new MostCommon(); 
        DatabaseConnector connector = new DatabaseConnector();
        TweetBean tb = new TweetBean();
        UserBean ub = new UserBean();
        TweetText tt = new TweetText();
        
        ArrayList<TweetText> ttList = new ArrayList();
        for(int init=0;init<tweets.size();init++){
            tweets.get(init).getTweet().setTweetTXT(cleanTweet.removeImageLinks(cleanTweet.removeSpecialChars(tweets.get(init).getTweet().getTweetTXT())));
            
            tt = cleanTweet.separate(tweets.get(init).getTweet().getTweetTXT());
            System.out.println("\nText: " + tt.getText());
            System.out.println("Hashtags: " + tt.getHashtag());
            System.out.println("Smileys: " + tt.getSmileys());            
            System.out.println("------------------------------------------------");
            ttList.add(tt);
        }                  
        
        ArrayList<HashtagBean> hashtags = mc.getMostUsedHashtags(ttList);
        int min  =(int) ((int) tweets.size()*.01);
        System.out.println("minimum = " + min);
        for(int init= 0 ;init< hashtags.size();init++){
            if(hashtags.get(init).getCount()>min)
                System.out.println(hashtags.get(init).getHashtag() + "\t" + hashtags.get(init).getCount());
        }
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
