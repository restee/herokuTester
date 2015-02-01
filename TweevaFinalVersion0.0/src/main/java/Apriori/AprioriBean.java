/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Apriori;

import java.util.ArrayList;

/**
 *
 * @author RestyLouis
 */
public class AprioriBean {
    
    private ArrayList<String> items;    
    private ArrayList<String> tweetsInvolved;
    private float confidence;
    
    
    public AprioriBean(){
        
    }
    
    public AprioriBean(ArrayList<String> items,ArrayList<String> tweetsInvolved){
        this.items = items;        
        this.tweetsInvolved = tweetsInvolved;
    }
    
    public AprioriBean(ArrayList<String> items,ArrayList<String> tweetsInvolved,float confidence){
        this.items = items;        
        this.tweetsInvolved = tweetsInvolved;
        this.confidence = confidence;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }
    
    
    public ArrayList<String> getItems(){
        return this.items;
    }

    public ArrayList<String> getTweetsInvolved() {
        return tweetsInvolved;
    }

    public void setTweetsInvolved(ArrayList<String> tweetsInvolved) {
        this.tweetsInvolved = tweetsInvolved;
    }
    
    
    public int getCount(){
        return this.tweetsInvolved.size();
    }
    public void setItems(ArrayList<String> items){
        this.items = items;        
    }
    
    public void addTweet(String tweetID){
        boolean alreadyInList = false;
        for(int init= 0 ;init<tweetsInvolved.size(); init++){
            if(tweetID.compareTo(tweetsInvolved.get(init))==0){
                alreadyInList = true; 
                break;
            }
        }
        if(!alreadyInList)
            this.tweetsInvolved.add(tweetID);
    }
}
