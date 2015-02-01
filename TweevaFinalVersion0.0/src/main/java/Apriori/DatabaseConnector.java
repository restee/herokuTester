/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Apriori;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RestyLouis
 */
public class DatabaseConnector {
    private Connection con;
    private Connection con2;
    private Statement stmt;
     public DatabaseConnector(){
        try{
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Tweeva", "tweeva","tweeva123");
            con2 = DriverManager.getConnection("jdbc:derby://localhost:1527/Tweeva", "tweeva","tweeva123");
            stmt = con.createStatement();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
     
     
    private String connectPhrases(ArrayList<String> phrase){
        String flag = "";
        for(int init = 0 ;init<phrase.size();init++){
            if(init==phrase.size()-1)
                flag += phrase.get(init);
            else
                flag += phrase.get(init) + " ";
        }            
        return flag;
    }
    
    
    public int getLatestID(){
         PreparedStatement statement = null;
        int flag = 1;
         
          String SQL_INSERT = "select WORDSID from COMMONWORDS order by WORDSID desc";        
          try{
              statement = con.prepareStatement(SQL_INSERT);
                                          
              ResultSet rs = statement.executeQuery();
              rs.next();
              flag = rs.getInt("WORDSID");
              System.out.println(flag);
         }catch(Exception e){    
             System.out.println("User Insert Error: \n" + e.getMessage());
         }          
         return flag;
    }    
    
    public void addFromApriori(ArrayList<AprioriBean> aprioriList,int topicID){
        PreparedStatement statement = null;
        PreparedStatement wordTweetStatement = null;
        
        String SQL_INSERT = "insert into COMMONWORDS (words,topicid,sent) values (?,?,?)";
        String SQL_INSERT_WORDTWEET = "insert into COMMONWORDS_TWEET (wordsid,tweetid,sent) values (?,?,?)";
        try{
            statement = con.prepareStatement(SQL_INSERT);
            wordTweetStatement = con.prepareStatement(SQL_INSERT_WORDTWEET);
            int latestID = getLatestID()+1;
            for(int init=0;init<aprioriList.size();init++){
                statement.setString(1,connectPhrases(aprioriList.get(init).getItems()));
                statement.setInt(2,topicID);
                statement.setBoolean(3,false);
                statement.addBatch();
                for(int tweetCounter=0;tweetCounter<aprioriList.get(init).getTweetsInvolved().size();tweetCounter++){
                    wordTweetStatement.setInt(1, init+latestID);
                    wordTweetStatement.setString(2,aprioriList.get(init).getTweetsInvolved().get(tweetCounter));
                    wordTweetStatement.setBoolean(3,false);
                    wordTweetStatement.addBatch();
                }
            }
          statement.executeBatch();
          wordTweetStatement.executeBatch();          
        }catch(Exception e){
             System.out.println("ERRRORRRRRR\n" + e.getMessage() );
             SQLException error = (SQLException) e;
             do{
                 System.out.println(error);
             }while((error = error.getNextException())!=null);
             
        }
    }
    
    
    public ArrayList<CommonWordsBean> getUnsentCommonWords(){
          PreparedStatement statement = null;
        ArrayList<CommonWordsBean> flag = new ArrayList();
         
          String SQL_INSERT = "select WORDSID,WORDS,TOPICID from COMMONWORDS WHERE SENT=FALSE";        
          try{
              statement = con.prepareStatement(SQL_INSERT);
                                          
              ResultSet rs = statement.executeQuery();
              while(rs.next()){
                  flag.add(new CommonWordsBean(rs.getInt("WORDSID"),rs.getString("WORDS"),rs.getInt("TOPICID")));
              }              
              
              
//              for(int init=0;init<flag.size();init++){
//                  System.out.println(flag.get(init).getWordsID() + "\t" + flag.get(init).getWords() + "\t" + flag.get(init).getTopicID());
//              }
              SQL_INSERT = "update COMMONWORDS SET SENT=TRUE WHERE SENT=FALSE";
              statement = con.prepareStatement(SQL_INSERT);
              statement.execute();
              
         }catch(Exception e){    
             System.out.println("User Insert Error: \n" + e.getMessage());
         }          
         return flag;
    }
    
    
    public ArrayList<CommonWords_TweetsBean> getUnsentCommonWords_Tweets(){
          PreparedStatement statement = null;
          ArrayList<CommonWords_TweetsBean> flag = new ArrayList();
         
          String SQL_INSERT = "select WORDSID,TWEETID from COMMONWORDS_TWEET WHERE SENT=FALSE";        
          try{
              statement = con.prepareStatement(SQL_INSERT);
                                          
              ResultSet rs = statement.executeQuery();
              while(rs.next()){
                  flag.add(new CommonWords_TweetsBean(rs.getInt("WORDSID"),rs.getString("TWEETID")));
              }              
              
              
//              for(int init=0;init<flag.size();init++){
//                  System.out.println(flag.get(init).getWordsID() + "\t" + flag.get(init).getTweetID() );
//              }
              
              SQL_INSERT = "update COMMONWORDS_TWEET SET SENT=TRUE WHERE SENT=FALSE";
              statement = con.prepareStatement(SQL_INSERT);
              statement.execute();
         }catch(Exception e){    
             System.out.println("User Insert Error: \n" + e.getMessage());
         }          
         return flag;
    }
}
