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
public class Tweets {
    public int tweetID;
    public ArrayList<String> descriptors;
    
    public Tweets(){
    
    }
    
    public Tweets(int tweetID, ArrayList<String> descriptors){
        this.tweetID = tweetID;
        this.descriptors = descriptors;
    }

    public ArrayList<String> getDescriptors() {
        return descriptors;
    }

    public int getTweetID() {
        return tweetID;
    }

    public void setDescriptors(ArrayList<String> descriptors) {
        this.descriptors = descriptors;
    }

    public void setTweetID(int tweetID) {
        this.tweetID = tweetID;
    }
    
    
}
