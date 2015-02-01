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
public class TweetUser {
    private TweetBean tweet;
    private UserBean user;
   
    public TweetUser(){
    }
    public TweetUser(TweetBean tweet, UserBean user){
        this.tweet = tweet;
        this.user = user;                
    }

    public TweetBean getTweet() {
        return tweet;
    }

    public UserBean getUser() {
        return user;
    }

    public void setTweet(TweetBean tweet) {
        this.tweet = tweet;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
    
    
}
