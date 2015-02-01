/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataRetrieval;

/**
 *
 * @author RestyLouis
 */
public class TopicBean {
    private int topicID;
    private String keyword;
    private long mostRecentID;
    

    
    public TopicBean(int topicID, String keyword,long mostRecentID){
        this.topicID = topicID;
        this.keyword = keyword;    
        this.mostRecentID = mostRecentID;
    }
    
    public String getKeyword() {
        return keyword;
    }

    public void setMostRecentID(long mostRecentID) {
        this.mostRecentID = mostRecentID;
    }

    public long getMostRecentID() {
        return mostRecentID;
    }

  

    public int getTopicID() {
        return topicID;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }
    
    
}
