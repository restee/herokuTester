/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cleaning;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author RestyLouis
 */
public class MostCommon {
    
    
    public MostCommon(){
    
    }
    
    public ArrayList<HashtagBean> getMostUsedHashtags(ArrayList<TweetText> ttList){
        int totalSize = ttList.size(),hashtags,tempIndex;
        ArrayList<HashtagBean> flag = new ArrayList();
        StringTokenizer hashtagTokens;
        String tempHashtag;
        
        for(int init=0;init<totalSize;init++){
            hashtagTokens = new StringTokenizer(ttList.get(init).getHashtag());
            hashtags = hashtagTokens.countTokens();
            
            for(int hashtagCounters = 0;hashtagCounters<hashtags;hashtagCounters++){
                tempHashtag = hashtagTokens.nextToken();
                tempIndex = inList(flag,tempHashtag);
                if(tempIndex>=0){
                    flag.get(tempIndex).setCount(flag.get(tempIndex).getCount() + 1);
                }else{
                    flag.add(new HashtagBean(tempHashtag,1));
                }
            }
            
        }
        
        
        return flag;
    }
    
    public int inList(ArrayList<HashtagBean> hashtags,String hashtag){
        int flag = -1;
        
        for(int init=0;init<hashtags.size();init++){
            if(hashtags.get(init).getHashtag().compareToIgnoreCase(hashtag)==0){
                return init;
            }
        }
        return flag;
    }
}
