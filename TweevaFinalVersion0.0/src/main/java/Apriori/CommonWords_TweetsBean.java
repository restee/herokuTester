/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Apriori;

/**
 *
 * @author RestyLouis
 */
public class CommonWords_TweetsBean {
    private int wordsID;
    private String tweetID;
            
    public CommonWords_TweetsBean(int wordsID, String tweetID){
         this.wordsID = wordsID;
         this.tweetID = tweetID;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public void setWordsID(int wordsID) {
        this.wordsID = wordsID;
    }

    public String getTweetID() {
        return tweetID;
    }

    public int getWordsID() {
        return wordsID;
    }    
    
}
