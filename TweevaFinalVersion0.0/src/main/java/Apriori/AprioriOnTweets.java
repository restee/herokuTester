/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Apriori;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RestyLouis
 */
public class AprioriOnTweets {

    /**
     * @param args the command line arguments
     */
    
    static DatabaseConnector connector;
    static Random r;
    static AprioriToJSON apToJSON;    
    static DataSender sender;
    static final float support=.2f;
    static final float confidenceRequirement = .2f;
    static String[] words = {"crazy","stupid","amazing","fucker","mild","awesome","legendary","cool","kind","gentle",
                        "harmful","lively","evil","new","first","long","small","large","giant","huge","high","old",
                        "big","high","different","small","large","next","early","young","important","unimportant",
                        "healhy","political","financial","significant","successful","electrical","energizing",
                        "strong","entire","severe","unusual","consistent","cultural","existing"};
    
    
    public static void main(String[] args) {
        r = new Random();
        int size = 40;
        apToJSON = new AprioriToJSON();
        sender = new DataSender();
        connector = new DatabaseConnector();
        System.out.println("Generated Words: ");
        ArrayList<Tweets> tweets = fakeGenerate(size);
        for(int init = 0;init<size;init++){
            System.out.println("\n" + init + ":");
            for(int hashtagCounter = 0; hashtagCounter<tweets.get(init).getDescriptors().size();hashtagCounter++){
                System.out.print(" " + tweets.get(init).getDescriptors().get(hashtagCounter));
            }            
        }
        properApriori(tweets);
    }   
    
    public static ArrayList<AprioriBean> getItemSet(ArrayList<AprioriBean> itemSet,int itemSetNumber){
        ArrayList<AprioriBean> flag = new ArrayList();
        ArrayList<String> phrase = new ArrayList();
        if(itemSetNumber==2){
                for(int init=0;init<itemSet.size()-1;init++){                    
                    for(int init2 = init+1; init2<itemSet.size();init2++){
                        phrase = new ArrayList();
                        phrase.add(itemSet.get(init).getItems().get(0));
                        phrase.add(itemSet.get(init2).getItems().get(0));
                        flag.add(new AprioriBean(phrase,new ArrayList()));
                    }
                }
        }else{
            for(int init=0;init<itemSet.size();init++){
                for(int init2 = init+1; init2<itemSet.size();init2++){
                    if(match(itemSet.get(init).getItems(),itemSet.get(init2).getItems(),itemSetNumber)){
                        phrase = getNewItemSet(itemSet.get(init).getItems(),itemSet.get(init2).getItems(),itemSetNumber);
                        flag.add(new AprioriBean(phrase,new ArrayList()));
                    }
                }
            }
        }
        
        return flag;
    }
    
    public static ArrayList<String> getNewItemSet(ArrayList<String> itemOne, ArrayList<String> itemTwo, int itemSetNumber){
        ArrayList<String> flag = new ArrayList();
        for(int init=0;init<itemSetNumber-2;init++){
            flag.add(itemOne.get(init));
        }
        flag.add(itemOne.get(itemOne.size()-1));
        flag.add(itemTwo.get(itemOne.size()-1));
                
        return flag;
    }
    
    public static boolean match(ArrayList<String> itemOne, ArrayList<String> itemTwo, int itemSetNumber){
        boolean flag = true;
        
        for(int init=0;init<itemSetNumber-2;init++){
            if(itemOne.get(init).compareToIgnoreCase(itemTwo.get(init))!=0){
                flag = false;
                break;
            }
        }
                
        return flag;
    }
    
    public static float computeConfidence(ArrayList<AprioriBean> result,AprioriBean phrase){
        float flag = .1f;
        int numerator = phrase.getCount();
        int supportFound=0,found;
        int denominator=1;
        ArrayList<String> toFind= new ArrayList();
        
        
        for(int init=0;init<phrase.getItems().size()-1;init++){
            toFind.add(phrase.getItems().get(init));
        }
        
        for(int init=0;init<result.size();init++){
            if(result.get(init).getItems().size()<phrase.getItems().size()){                
                if(isContained(result.get(init).getItems(), toFind)){
                    denominator = result.get(init).getCount();
                }            
            }else{
                break;
            }
        }
       // System.out.println("To Find = " + toFind.toString() + "\t" + numerator + "/" + supportFound + "\t" + result.size());
        flag = (float) numerator/denominator;
        
        return flag;
    }
        
    
    public static void properApriori(ArrayList<Tweets> tweets){
        ArrayList<AprioriBean> aprioriList = new ArrayList();
        ArrayList<AprioriBean> itemSet = getItemSetOne(tweets);
        ArrayList<AprioriBean> result = new ArrayList();
        int itemSetNumber = 1;
        float confidence;
        float supportRequirement = (float)tweets.size()*support;
                
        do{
            
            for(int init = itemSet.size()-1; init>=0;init--){
                itemSet.get(init).setTweetsInvolved(tweetIDofContained(tweets,itemSet.get(init).getItems()));            
                if(supportRequirement>itemSet.get(init).getCount()){
                    itemSet.remove(init);            
                }else{
                    if(itemSet.get(init).getItems().size()>1)
                        confidence = computeConfidence(aprioriList,itemSet.get(init));
                    else
                        confidence = 1;
                    
                    if(confidence>= confidenceRequirement)
                        aprioriList.add(new AprioriBean(itemSet.get(init).getItems(),itemSet.get(init).getTweetsInvolved(),confidence));                                        
                    System.out.println(itemSet.get(init).getItems() + "\t" + itemSet.get(init).getCount() + "\t" + aprioriList.get(aprioriList.size()-1).getConfidence());
                }            
            }            
            System.out.println("ItemSet " + (itemSetNumber) +"    = " + itemSet.size()  );
            itemSetNumber++;            
            itemSet = getItemSet(itemSet, itemSetNumber);                    
        }while(itemSet.size()>0);
        
        /*
        connector.addFromApriori(aprioriList, 10);
        try {
            System.out.println(sender.putDataToServer("http://tweeva.zz.mu/addCommonWord",apToJSON.commonWordsToJSON(connector.getUnsentCommonWords())));
            System.out.println(sender.putDataToServer("http://tweeva.zz.mu/addWordTweet",apToJSON.commonWordsTweetsToJSON(connector.getUnsentCommonWords_Tweets())));
        } catch (Throwable ex) {
            Logger.getLogger(AprioriOnTweets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        */
        
        
    }
    
    
    public static boolean isContained(ArrayList<String> basis, ArrayList<String> phrase){
        boolean flag = false;
        int found = 0;
        for(int init= 0 ;init<basis.size();init++){
            for(int phraseCounter= 0 ;phraseCounter<phrase.size();phraseCounter++){
                if(basis.get(init).compareToIgnoreCase(phrase.get(phraseCounter))==0){
                    found++;
                }
            }
        }
        
        if(found==phrase.size()){
            flag = true;
        }
        
        return flag;   
    }
        
    public static ArrayList<String> tweetIDofContained(ArrayList<Tweets> tweets, ArrayList<String> phrase){
        ArrayList<String> flag = new ArrayList();
        
        for(int init = 0; init<tweets.size();init++){
            if(isContained(tweets.get(init).getDescriptors(),phrase)){
                flag.add(Integer.toString(tweets.get(init).getTweetID()));
            }
        }        
        return flag;
    }
    
    public static ArrayList<AprioriBean> getItemSetOne(ArrayList<Tweets> tweets){
        ArrayList<AprioriBean> flag  = new ArrayList();        
        ArrayList<String> phrase = new ArrayList();
        for(int init= 0 ;init<tweets.size();init++){
            for(int descriptorCounter = 0; descriptorCounter<tweets.get(init).getDescriptors().size();descriptorCounter++){
                phrase = new ArrayList();
                phrase.add(tweets.get(init).getDescriptors().get(descriptorCounter));
                if(descriptorExists2(flag,phrase)<0){
                    flag.add(new AprioriBean(phrase,new ArrayList()));
                }
            }
        }        
        return flag;
    }
    
    
    
    public static void Apriori(ArrayList<Tweets> tweets,float support){
        ArrayList<AprioriBean> aprioriList = new ArrayList();
        
        
        ArrayList<ArrayList<String>> descriptors = new ArrayList();
        ArrayList<Integer> count =new ArrayList();
        ArrayList<String> phrase = new ArrayList();
        int itemSetNumber = 1,index;
        float supportRequirement = (float)tweets.size()*support;
        do{
            for(int init=0;init<tweets.size();init++){            
                    for(int descriptorCounter = 0; descriptorCounter<tweets.get(init).getDescriptors().size();descriptorCounter++){
                        phrase = new ArrayList();
                        if(tweets.get(init).getDescriptors().size()-descriptorCounter>itemSetNumber){
                            
                            for(int phraseCounter=descriptorCounter;phraseCounter<descriptorCounter+itemSetNumber;phraseCounter++){
                                phrase.add(tweets.get(init).getDescriptors().get(phraseCounter));
                            }
                            
                            index = descriptorExists2(aprioriList,phrase);
                            
                            if(index>=0){                                                                
                                //aprioriList.get(index).getTweetsInvolved().add(Integer.toString(tweets.get(init).getTweetID()));
                                aprioriList.get(index).addTweet(Integer.toString(tweets.get(init).getTweetID()));
                            }else{                                
                                aprioriList.add(new AprioriBean(phrase,new ArrayList()));
                                aprioriList.get(aprioriList.size()-1).addTweet(Integer.toString(tweets.get(init).getTweetID()));
                                //aprioriList.get(aprioriList.size()-1).getTweetsInvolved().add(Integer.toString(tweets.get(init).getTweetID()));                                
                            }
                        }
                    }
            }
                        
            for(int init=aprioriList.size()-1;init>=0;init--){
                if(itemSetNumber==aprioriList.get(init).getItems().size()){
                    if(aprioriList.get(init).getTweetsInvolved().size() <supportRequirement){
                        aprioriList.remove(init);
                    }
                }
            }        
                                    
            itemSetNumber++;
            
        }while(hasConfidence(aprioriList,itemSetNumber-1));
        
        for(int init = 0 ;init<aprioriList.size();init++){
           System.out.println("\n" + aprioriList.get(init).getTweetsInvolved().size() + "\t" + aprioriList.get(init).getItems().toString());
           for(int involved=0;involved<aprioriList.get(init).getTweetsInvolved().size();involved++){
               System.out.print(aprioriList.get(init).getTweetsInvolved().get(involved) + " ");
           }
        }
    }
    
    public static boolean hasConfidence(ArrayList<AprioriBean> aprioriList, int itemSetNumber){
        boolean flag = false;
        
        for(int init= 0 ; init<aprioriList.size();init++){
            if(aprioriList.get(init).getItems().size() == itemSetNumber){
                flag = true;
                break;
            }
        }
        return flag;                
    }
    
    public static int descriptorExists2(ArrayList<AprioriBean> aprioriList, ArrayList<String> phrase){
        int flag = -1;
        int wordsFound = 0;
        for(int init = 0; init<aprioriList.size(); init++){            
            wordsFound=0;            
            for(int descriptorCounter = 0 ; descriptorCounter< aprioriList.get(init).getItems().size(); descriptorCounter++){
                for(int phraseCounter=0;phraseCounter<phrase.size();phraseCounter++){
                    if(phrase.get(phraseCounter).compareToIgnoreCase(aprioriList.get(init).getItems().get(descriptorCounter))==0){
                        wordsFound++;
                    }
                }
            }
            if(wordsFound==phrase.size()){
                flag = init;
                break;
            }
        }           
        return flag;
    }
    
    
    
    public static int descriptorExists(ArrayList<ArrayList<String>> words, ArrayList<String> phrase){
        int flag = -1;
        int wordsFound = 0;
        for(int init = 0; init<words.size(); init++){            
            wordsFound=0;
            for(int descriptorCounter = 0 ; descriptorCounter< words.get(init).size(); descriptorCounter++){
                for(int phraseCounter=0;phraseCounter<phrase.size();phraseCounter++){
                    if(phrase.get(phraseCounter).compareToIgnoreCase(words.get(init).get(descriptorCounter))==0){
                        wordsFound++;
                    }
                }
            }
            if(wordsFound==phrase.size()){
                flag = init;
                break;
            }
        }   
        
        return flag;
    }
    
    public static  ArrayList<Tweets> fakeGenerate(int size){
        ArrayList<Tweets> flag = new ArrayList();
        int hashtags, chance; 
        String word;
        ArrayList<String> wordsAssembled;
        for(int init = 0;init<size;init++){
            wordsAssembled = new ArrayList();
            hashtags = r.nextInt(20);
            for(int hashtagCounter = 0; hashtagCounter< hashtags; hashtagCounter++){
                word = words[r.nextInt(words.length)];
                wordsAssembled.add(word);
            }
            flag.add(new Tweets(init, wordsAssembled));
        }        
        return flag;
    }
    
    
}
