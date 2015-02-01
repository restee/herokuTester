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
public class TweetText {
    String text;
    String hashtag;
    String smileys;

    public TweetText() {
        this.text="";
        this.hashtag="";
        this.smileys="";
    }

    public TweetText(String text, String hashtag, String smileys) {
        this.text = text;
        this.hashtag = hashtag;
        this.smileys = smileys;
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getSmileys() {
        return smileys;
    }

    public String getText() {
        return text;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public void setSmileys(String smileys) {
        this.smileys = smileys;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public String getFullTweet(){
        return text + "  " + smileys + "  " + hashtag;
    }
}
