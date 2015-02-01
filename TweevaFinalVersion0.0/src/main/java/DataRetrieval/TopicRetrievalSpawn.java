/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataRetrieval;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.TwitterException;

/**
 *
 * @author RestyLouis
 */
public class TopicRetrievalSpawn {
    private Thread runningThread;
    private TopicBean topic;
    
    static SearchTweet2 search ;
    
    public TopicRetrievalSpawn() {
    }

    public TopicRetrievalSpawn(TopicBean topic) {
        
        this.topic = topic;
        final TopicBean topicStatic = topic;
        
        this.runningThread = new Thread(new Runnable() {

            public void run() {
                      try { 
                    search= new SearchTweet2(topicStatic.getMostRecentID(),topicStatic.getKeyword(),topicStatic.getTopicID());
                } catch (TwitterException ex) {
                    Logger.getLogger(TopicRetrievalSpawn.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TopicRetrievalSpawn.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TopicRetrievalSpawn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public Thread getRunningThread() {
        return runningThread;
    }

    public TopicBean getTopic() {
        return topic;
    }

    public void startThread(){
        this.runningThread.start();
    }
    
    
    public void stopThread() throws InterruptedException{        
        this.runningThread.join();
        search.endSearch();        
    }
    public void setRunningThread(Thread runningThread) {
        this.runningThread = runningThread;
    }

    public void setTopic(TopicBean topic) {
        this.topic = topic;
    }
    
    
    
}
