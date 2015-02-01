/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Apriori;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author RestyLouis
 */
public class AprioriToJSON {
    public AprioriToJSON(){
    
    }
    
    private String connectPhrases(ArrayList<String> phrase){
        String flag = "";
        for(int init = 0 ;init<phrase.size();init++){
            if(init==phrase.size()-1)
                flag += phrase.get(init);
            else
                flag += phrase.get(init) + " ";
        }            
        return flag;
    }
    
    private String connectTweetIDs(ArrayList<String> tweetIDs){
        String flag = "";
        for(int init = 0 ;init<tweetIDs.size();init++){
            if(init==tweetIDs.size()-1)
                flag += tweetIDs.get(init);
            else
                flag += tweetIDs.get(init) + ",";
        }            
        return flag;
    }
    
    public JSONObject commonWordsToJSON(ArrayList<CommonWordsBean> cwList){
        String flag="";
        
        
        JSONObject toSend = new JSONObject();
        JSONArray tweetUserArray = new JSONArray();
        JSONObject tuObj;
        tweetUserArray = new JSONArray();
        for(int init=0;init<cwList.size();init++){
            //tweetUserArray = new JSONArray();
          //  for(int innerCounter=0;innerCounter<6;innerCounter++,init++){
                tuObj = new JSONObject();
                try{                    
                    tuObj.put("wordID", cwList.get(init).getWordsID());
                    tuObj.put("words", cwList.get(init).getWords());
                    tuObj.put("topicID", cwList.get(init).getTopicID());
                    tweetUserArray.put(tuObj);
                    toSend.put("data", tweetUserArray);
                    }catch(Exception e){
                   }                                    
          //  }
           
        }
         System.out.println(toSend.toString()+"\n\n");              
        
        
        
        return toSend;
    }
    
    public JSONObject commonWordsTweetsToJSON(ArrayList<CommonWords_TweetsBean> cwList){
        String flag="";
        
        
        JSONObject toSend = new JSONObject();
        JSONArray tweetUserArray = new JSONArray();
        JSONObject tuObj;
        tweetUserArray = new JSONArray();
        String tweetIDtemp;
        int wordID;
        for(int init=0;init<cwList.size();init++){
          //  tweetUserArray = new JSONArray();
          //  for(int innerCounter=0;innerCounter<6;innerCounter++,init++){
                tuObj = new JSONObject();                
                tweetIDtemp= "";
                wordID=cwList.get(init).getWordsID();
                
                try{                    
                    tuObj.put("wordID", cwList.get(init).getWordsID());
                    
                    do{
                        tweetIDtemp+= cwList.get(init).getTweetID() + ",";
                        init++;
                    }while(init<cwList.size()&&wordID==cwList.get(init).getWordsID());
                    init--;                                                            
                    tweetIDtemp = tweetIDtemp.substring(0,tweetIDtemp.length()-1);
                   // System.out.println(tweetIDtemp);
                    
                    tuObj.put("tweetsID", tweetIDtemp);                   
                    tweetUserArray.put(tuObj);
                    toSend.put("data", tweetUserArray);
                    }catch(Exception e){
                   }                                    
        //    }
                    
        }
        System.out.println(toSend.toString()+"\n\n");      
        
        
        
        return toSend;
    }
    public String toJSON(ArrayList<AprioriBean> aprioriList){
        String flag = "";
        ArrayList<String> ids = new ArrayList();
        
       for(int init = 0; init<aprioriList.size();init++){
           ids.add(Integer.toString(init));
       }
        
        
        JSONObject toSend = new JSONObject();
        JSONArray tweetUserArray = new JSONArray();
        JSONObject tuObj;
        //tweetUserArray = new JSONArray();
        for(int init=0;init<aprioriList.size();init++){
            tweetUserArray = new JSONArray();
            for(int innerCounter=0;innerCounter<6;innerCounter++,init++){
                tuObj = new JSONObject();
                try{                    
                    tuObj.put("phraseID",ids.get(init));                    
                    tuObj.put("phrase", connectPhrases(aprioriList.get(init).getItems()));
                    tuObj.put("tweetIDs", connectTweetIDs(aprioriList.get(init).getTweetsInvolved()));
                    tweetUserArray.put(tuObj);
                    toSend.put("data", tweetUserArray);
                    }catch(Exception e){
                   }                                    
            }
            System.out.println(toSend.toString()+"\n\n");              
        }
        
        return flag;
    }
}
