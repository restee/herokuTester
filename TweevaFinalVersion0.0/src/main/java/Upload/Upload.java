/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Upload;

import Cleaning.TweetUser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 *
 * @author RestyLouis
 */
public class Upload {
    
    public Upload(){
    
    }
    
    
    public String putDataToServer(String url,JSONObject returnedJObject) throws Throwable
    {

    HttpPost request = new HttpPost(url);
    JSONStringer json = new JSONStringer();
    StringBuilder sb=new StringBuilder();


    if (returnedJObject!=null) 
    {
        Iterator<String> itKeys = returnedJObject.keys();
        if(itKeys.hasNext())
            json.object();
        while (itKeys.hasNext()) 
        {
            String k=itKeys.next();
            json.key(k).value(returnedJObject.get(k));
          //  Log.e("keys "+k,"value "+returnedJObject.get(k).toString());
        }             
    }
    json.endObject();
    


    StringEntity entity = new StringEntity(json.toString());
                         entity.setContentType("application/json;charset=UTF-8");
                         entity.setContentEncoding(new BasicHeader("Content-Type","application/json;charset=UTF-8"));
                         request.setHeader("Accept", "application/json");
                         request.setEntity(entity); 

                         HttpResponse response =null;
                         DefaultHttpClient httpClient = new DefaultHttpClient();

                         
                         try{

                         response = httpClient.execute(request); 
                         }
                         catch(SocketException se)
                         {
                   //          Log.e("SocketException", se+"");
                             throw se;
                         }




    InputStream in = response.getEntity().getContent();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line = null;
    while((line = reader.readLine()) != null){
        sb.append(line);
    }

    return sb.toString();
    }
    
    
    public void upload(List<TweetUser> tweets){
        List<TweetUser> allTweetList =tweets;
        
        JSONObject toSend = new JSONObject();
        JSONArray tweetUserArray = new JSONArray();
        JSONObject tuObj;
        
        for(int init=0;init<allTweetList.size();init++){
            tweetUserArray = new JSONArray();
            for(int innerCounter=0;innerCounter<6;innerCounter++,init++){
                tuObj = new JSONObject();
                try{                    
                    tuObj.put("tweetid", allTweetList.get(init).getTweet().getTweetID());
                    tuObj.put("userid", allTweetList.get(init).getUser().getUserid());
                    tuObj.put("topicid", allTweetList.get(init).getTweet().getTopicID());
                    tuObj.put("tweet", allTweetList.get(init).getTweet().getTweetTXT());
                    tuObj.put("language", allTweetList.get(init).getTweet().getLanguage());
                    tuObj.put("location", allTweetList.get(init).getTweet().getLocation());
                    tuObj.put("date", allTweetList.get(init).getTweet().getDate());
                    tuObj.put("sentimentscore",  allTweetList.get(init).getTweet().getSentimentScore());
                    tuObj.put("screenname", allTweetList.get(init).getUser().getScreenName());
                    tuObj.put("fullname", allTweetList.get(init).getUser().getFullName());                                        
                    tuObj.put("gender",allTweetList.get(init).getUser().getGender());
                    tuObj.put("address", allTweetList.get(init).getUser().getAddress());
                    tweetUserArray.put(tuObj);
                }catch(Exception e){
                
               }
                }
              try {                                       
                    toSend.put("data", tweetUserArray);
                    System.out.println(toSend.toString());
                    String putDataToServer = putDataToServer("http://tweeva.zz.mu/getJson",toSend);
                     
                    System.out.println(putDataToServer);
               } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                } catch (Throwable ex) {
                    System.out.println(ex.getMessage());
                }
            }
    }
}
