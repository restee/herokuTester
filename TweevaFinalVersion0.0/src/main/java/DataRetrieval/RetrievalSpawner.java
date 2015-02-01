/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataRetrieval;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author RestyLouis
 */
public class RetrievalSpawner {
    static ArrayList<TopicBean> topics;
    static DatabaseConnector database;
    static ArrayList<TopicRetrievalSpawn> spawn;
    
    public static void main(String[] args){
        database= new DatabaseConnector();            
        spawn = new ArrayList();
        topics = getTopics();              
        
        
        
        for(int init=0;init<topics.size();init++){
            System.out.println(topics.get(init).getTopicID() + "\t" + topics.get(init).getKeyword() + "\t" + topics.get(init).getMostRecentID());
            spawn.add(new TopicRetrievalSpawn(topics.get(init)));
        }
        
        for(int init=0;init<topics.size();init++){
            System.out.println(spawn.get(init).getTopic().getKeyword());
            spawn.get(init).startThread();
           // System.out.println("Shit!!!");
        }
        
        ArrayList<TopicBean> tempTopics;
        while(true){
            try {
                Thread.sleep(600000);
                tempTopics = getTopics();
                if(tempTopics.size()>topics.size()){
                    for(int init = tempTopics.size()-1;init>=topics.size();init--){
                        spawn.add(new TopicRetrievalSpawn(tempTopics.get(init)));
                        spawn.get(init).startThread();
                    }
                }                
            } catch (InterruptedException ex) {
                Logger.getLogger(RetrievalSpawner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }  
        
    public static ArrayList<TopicBean>  getTopics(){
        ArrayList<TopicBean> flag = new ArrayList();                
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://tweeva.zz.mu/getTopics");
        HttpResponse response;
        String outputLine = "";
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader
                (new InputStreamReader(response.getEntity().getContent()));

              String line = "";
              while ((line = rd.readLine()) != null) {
                outputLine+= line;
              } 
              
              JSONArray jsonResult = new JSONArray(outputLine);
              JSONObject tempObj;
              for(int init=0;init<jsonResult.length();init++){
                  tempObj = jsonResult.getJSONObject(init);
                  flag.add(new TopicBean(tempObj.getInt("topicID"),tempObj.getString("keyword"),database.getLatestTweet(tempObj.getInt("topicID"))));                  
              }                            
        } catch (Exception ex) {
           System.out.println(ex.getMessage());
        }

        // Get the response                        
        return flag;
    }
    
    
    
}
