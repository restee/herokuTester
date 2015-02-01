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
public class CommonWordsBean {
    private int wordsID;
    private String words;
    private int topicID;
    
    public CommonWordsBean(int wordsID, String words, int topicID){
        this.wordsID = wordsID;
        this.words = words;
        this.topicID = topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public void setWordsID(int wordsID) {
        this.wordsID = wordsID;
    }

    public int getTopicID() {
        return topicID;
    }

    public String getWords() {
        return words;
    }

    public int getWordsID() {
        return wordsID;
    }
    
    
}
