/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cleaning;

/**
 *
 * @author RestyLouis
 */
public class HashtagBean {    
    String hashtag;
    int count;
    
    
    public HashtagBean(){
    
    }
    
    public HashtagBean(String hashtag, int count){
        this.hashtag = hashtag;
        this.count = count;    
    }

    public int getCount() {
        return count;
    }

    public String getHashtag() {
        return hashtag;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
           
}
