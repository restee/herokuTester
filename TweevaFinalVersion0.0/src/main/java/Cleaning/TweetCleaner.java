/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cleaning;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 *
 * @author RestyLouis
 */

public class TweetCleaner {
   private ArrayList<Abbreviation> abblist;
   private ArrayList<SmileyScores> smileys;
   
   public TweetCleaner(){
        abblist = getAbbs();
        smileys = getSmileys();
   }
           
   public String removeNonASCII(String tweetText){
            String flag = tweetText.replaceAll("[^\\p{ASCII}]", "");
            return flag;
   }
    
   public String removeSpecialChars(String tweetText){
           String flag = tweetText.replaceAll("[^\\p{ASCII}]", "");
        flag = flag.replaceAll("&#39;", "'");
        flag = flag.replaceAll(Pattern.quote("\\n")," ");
        flag = flag.replaceAll(Pattern.quote("\\t")," ");
        flag = flag.replaceAll(Pattern.quote("w/"),"with");
     
        return flag;
   }
    
    
    public String removeImageLinks(String tweetText){
        String flag=tweetText;
        StringTokenizer tokenize  = new StringTokenizer(flag);
        
        int size= tokenize.countTokens();
        String temp;
        flag="";
        
        for(int init=0;init<size;init++){
            temp = tokenize.nextToken();
            if(!(temp.contains("http://t.co/"))&&!(temp.charAt(0)=='@')&&!(temp.contains("https://t.co/"))){
                flag+= temp + " ";
            }
        }
        
        
        
        return flag;
    }
    
    public String cleanTweetText(String tweetText){        
        String flag = tweetText.replaceAll("[^\\p{ASCII}]", "");
        flag = flag.replaceAll("&#39;", "'");
        flag = flag.replaceAll(Pattern.quote("\\n")," ");
        flag = flag.replaceAll(Pattern.quote("\\t")," ");
        flag = flag.replaceAll(Pattern.quote("w/"),"with");
        StringTokenizer tokenize  = new StringTokenizer(flag);
        int size= tokenize.countTokens();
        String temp;
        flag="";
        for(int init=0;init<size;init++){
            temp = tokenize.nextToken();
            if (temp.charAt(0)=='#'){
                //flag+= temp.substring(1,temp.length()) + " ";
            }else if(!(temp.contains("http://t.co/"))&&!(temp.charAt(0)=='@')&&!(temp.contains("https://t.co/"))){
                flag+= temp + " ";
            }
        }
        
        flag = fixAbb(flag);
        return flag;
    }
    
    
    
    private ArrayList<Abbreviation> getAbbs(){
        ArrayList<Abbreviation> flag = new ArrayList();
        try{
            FileReader fr = new FileReader("DataSource\\Acronyms.txt");
            BufferedReader read = new BufferedReader(fr);
            
            String word=null;
            do{
                word = read.readLine();                
                if(word!=null){
                    StringTokenizer token = new StringTokenizer(word,"	");
                    flag.add(new Abbreviation(token.nextToken(), token.nextToken()));
                }
            }while(word!=null);
            
        }catch(Exception e){
        
        }
        
        return flag;
    }
    public String fixAbb(String tweet){
        String flag = "";
         WordCleaner wordClean = new WordCleaner();
        StringTokenizer token = new StringTokenizer(tweet," ");
        int size = token.countTokens(),index,start,end;
        String tempWord,word;
        
        for(int counter = 0;counter<size;counter++){
            word = token.nextToken();
            start = wordClean.startLetter(word);
            
            end = wordClean.endLetter(word);
            
            tempWord = word.substring(start,end);
            
            index =isAbbreviation(tempWord);
            if(index>=0){                
               flag+= word.toLowerCase().replace(abblist.get(index).getAbbreviation().toLowerCase(), abblist.get(index).getOriginalForm())+ " ";
            }else{
                flag += word + " ";
            }
        }
        return flag;
    }
    
    
    private int isAbbreviation(String word){
        int flag = -1;
           for(int init=0;init<abblist.size();init++){               
                if(word.toUpperCase().compareToIgnoreCase(abblist.get(init).getAbbreviation())==0){
                    flag = init; 
                    System.out.println(flag);
                    break;
                }
            }
           
         return flag;
    }
    
   
    
    private boolean isSmiley(String word){
         boolean flag = false;
         for(int init=0;init<smileys.size();init++){
            if(word.compareToIgnoreCase(smileys.get(init).getSmiley())==0){
                flag = true;
               break;
            }
         }         
         return flag;
     }
    
     private ArrayList<SmileyScores> getSmileys(){
         ArrayList<SmileyScores> flag=  new ArrayList();
        try {
            FileReader fr = new FileReader("DataSource\\SmileyScores.txt");
            BufferedReader textReader = new BufferedReader(fr);
            String word=null;
            do{
                word = textReader.readLine();                
                if(word!=null){
                    StringTokenizer token = new StringTokenizer(word,",");
                    flag.add(new SmileyScores(token.nextToken(),Float.parseFloat(token.nextToken())));
                }
            }while(word!=null);                        
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }         
         return flag;
     }
     
     
    
    public TweetText separate(String tweetText){
        TweetText flag = new TweetText();
        StringTokenizer tokens = new StringTokenizer(tweetText);
        int size=tokens.countTokens();
        String temp;
        for(int init=0;init<size;init++){
            temp = tokens.nextToken();
            if(isSmiley(temp)){
                flag.setSmileys(flag.getSmileys() + " " + temp);
            }else if (temp.charAt(0)=='#'){
                flag.setHashtag(flag.getHashtag() + " " + temp);
            }else{
                flag.setText(flag.getText() + " " + temp);
            }        
        }
        
        return flag;
    }
        
}
