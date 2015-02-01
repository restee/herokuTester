/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataRetrieval;

/**
 *
 * @author Janine Alexis
 */
public class Tweet {
        private long tweetId;
        private String tweetMsg;
        private String tweetDate;
        private String tweetLang;
        private String tweetLoc;
        
        private long userId;
        private String userScreenName;
        private String userName;
        private String userLoc;
        private String userGender;

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetMsg() {
        return tweetMsg;
    }

    public void setTweetMsg(String tweetMsg) {
        this.tweetMsg = tweetMsg;
    }

    public String getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(String tweetDate) {
        this.tweetDate = tweetDate;
    }

    public String getTweetLang() {
        return tweetLang;
    }

    public void setTweetLang(String tweetLang) {
        this.tweetLang = tweetLang;
    }

    public String getTweetLoc() {
        return tweetLoc;
    }

    public void setTweetLoc(String tweetLoc) {
        this.tweetLoc = tweetLoc;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLoc() {
        return userLoc;
    }

    public void setUserLoc(String userLoc) {
        this.userLoc = userLoc;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String firstName, String lastName) {
        
       this.userGender = Genderize.genderize(firstName, lastName);
    }
        
      
    /* 1 - firstname
     * 2 - lastname
     */
    public String getNamePart(String fullname, int id)
    {
    
                    String[] splitname = fullname.split(" ");
                    String firstName = splitname[0];
                    String lastName = " ";
                    if (splitname.length > 1) {
                        lastName = splitname[splitname.length - 1];
                    }
                    if (splitname.length > 2) {
                        for (int i = 1; i <= splitname.length - 2; i++) {
                            firstName = firstName + " " + splitname[i];
                        }
                    }
                    
                    if(id==1)
                        return firstName;
                    else
                        return lastName;
                    
    }
            
    
}
