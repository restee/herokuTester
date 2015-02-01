/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataRetrieval;

import Cleaning.TweetBean;
import Cleaning.TweetUser;
import Cleaning.UserBean;
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
    private Statement stmt;
     public DatabaseConnector(){
        try{
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Tweeva", "tweeva","tweeva123");
            stmt = con.createStatement();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
     
    
     
     public void addUserEntry(UserBean user){
         PreparedStatement statement = null;
          
          String SQL_INSERT = "insert into user_table (userid,screenname,fullname,gender,address) values (?,?,?,?,?)";
          try{
              statement = con.prepareStatement(SQL_INSERT);
                            
                  
                statement.setString(1,user.getUserid());                
                statement.setString(2,user.getScreenName());
                statement.setString(3,user.getFullName());                
                statement.setString(4,Character.toString(user.getGender()));
                statement.setString(5,user.getAddress());               

                statement.execute();               
          
              
         }catch(Exception e){    
             System.out.println("User Insert Error: \n" + e.getMessage());
         }
     }
     
     public long getLatestTweet(int topicID){
         long flag = -1;
         
          String sql = "select tweetid from tweets_table where topicID = " + topicID + " order by date desc" ;
            try {                                                        
                 
                ResultSet rs = stmt.executeQuery(sql);            
                rs.next();
                flag = Long.parseLong(rs.getString("tweetid"));
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }     
         
         
         
         return flag;     
     }
     public void addTweetEntry(TweetBean tweet){
            PreparedStatement statement = null;
          
          String SQL_INSERT = "insert into tweets_table (tweetid,userid,topicid, tweet,location,date,language) values (?,?,?,?,?,?,?)";
          try{
                statement = con.prepareStatement(SQL_INSERT);
                                                      
                statement.setString(1, tweet.getTweetID());
                statement.setString(2,tweet.getUserID());
                statement.setInt(3,tweet.getTopicID());
                statement.setString(4, tweet.getTweetTXT());
                statement.setString(5,tweet.getLocation());
                statement.setString(6,tweet.getDate());               
                statement.setString(7,tweet.getLanguage());                
                
                statement.execute();
         }catch(Exception e){                
            System.out.println("Tweet Insert Error: \n" + e.getMessage());
         }
     }
     public void addUserEntries(ArrayList<UserBean> tweets){
          PreparedStatement statement = null;
          
          String SQL_INSERT = "insert into user_table (userid,screenname,fullname,gender,address) values (?,?,?,?,?)";
          try{
              statement = con.prepareStatement(SQL_INSERT);
                            
              for(int init=0;init<tweets.size();init++){       
                statement.setString(1,tweets.get(init).getUserid());                
                statement.setString(2,tweets.get(init).getScreenName());
                statement.setString(3, tweets.get(init).getFullName());                
                statement.setString(4,Character.toString(tweets.get(init).getGender()));
                statement.setString(5,tweets.get(init).getAddress());               

                statement.addBatch();                
              }            
              statement.executeBatch();
              
         }catch(Exception e){         
         }
     }
     
     
     
     public ArrayList<TweetUser> getAllTweetsOn(int topicID){
         ArrayList<TweetUser> flag = new ArrayList();
          try {            
            String sql = "Select * from user_table u,tweets_table t where u.userid=t.userid AND t.topicid=" + topicID;
            ResultSet rs = stmt.executeQuery( sql);
            TweetUser tuBean;
            TweetBean tBean;
            UserBean uBean;
           while(rs.next()){
                tuBean = new TweetUser();
                tBean = new TweetBean();
                uBean = new UserBean();
                tBean.setTweetID(rs.getString("tweetid"));
                tBean.setUserID(rs.getString("userid"));
                tBean.setTweetTXT(rs.getString("tweet"));
                tBean.setDate(rs.getString("date"));
                tBean.setLanguage(rs.getString("language"));
                tBean.setSentimentScore(rs.getFloat("sentimentscore"));
                tBean.setTopicID(topicID);
                tBean.setLocation(rs.getString("location"));
                
                uBean.setUserid(rs.getString("tweetid"));
                uBean.setScreenName(rs.getString("screenname"));
                uBean.setFullName(rs.getString("fullname"));
                uBean.setGender(rs.getString("gender").charAt(0));
                uBean.setAddress(rs.getString("address"));
                
                tuBean.setTweet(tBean);
                tuBean.setUser(uBean);
                flag.add(tuBean);
            }            
        } catch (SQLException ex) {
            //Logger.getLogger(LoginUI.class.getName()).log(Level.SEVERE, null, ex);
        } 
         
         return flag;    
     }
     
     
     
     public void addTweetEntries(ArrayList<TweetBean> tweets){
          PreparedStatement statement = null;
          
          String SQL_INSERT = "insert into tweets_table (tweetid,userid,topicid, tweet,location,date,language) values (?,?,?,?,?,?,?)";
          try{
              statement = con.prepareStatement(SQL_INSERT);
                            
              for(int init=0;init<tweets.size();init++){                
                statement.setString(1, tweets.get(init).getTweetID());
                statement.setString(2,tweets.get(init).getUserID());
                statement.setInt(3,tweets.get(init).getTopicID());
                statement.setString(4, tweets.get(init).getTweetTXT());
                statement.setString(5,tweets.get(init).getLocation());
                statement.setString(6,tweets.get(init).getDate());               
                statement.setString(7,tweets.get(init).getLanguage());                
                
                statement.addBatch();                
              }            
              statement.executeBatch();              
         }catch(Exception e){                
             System.out.println("ERRRORRRRRR\n" + e.getMessage() );
             SQLException error = (SQLException) e;
             do{
                 System.out.println(error);
             }while((error = error.getNextException())!=null);
         }
     }
          
          
      
      public void setSentimentScore(String tweetID, float score){
          String sql = "update tweets_table set sentimentscore=" + Float.toString(score) + " where tweetid=" + tweetID;
            try {                                                        
                stmt.executeUpdate(sql);            
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }           
     }
      
      
      public void addTopics(ArrayList<TopicBean>  topics ){
            PreparedStatement statement = null;
          
          String SQL_INSERT = "insert into topics(topicid,keyword) values (?,?,?,?)";
          try{
              statement = con.prepareStatement(SQL_INSERT);
                            
              for(int init=0;init<topics.size();init++){                
                statement.setInt(1, topics.get(init).getTopicID());
                statement.setString(2,topics.get(init).getKeyword());                             
                statement.addBatch();                
              }
              
              statement.executeBatch();              
         }catch(Exception e){                
             System.out.println("ERRRORRRRRR\n" + e.getMessage() );
             SQLException error = (SQLException) e;
             do{
                 System.out.println(error);
             }while((error = error.getNextException())!=null);
         }
      }
      
      public int getLatestTopicID(){
          int flag=-1;
           String sql = "select topicid from topics order by topicid desc";
            try {                                                        
                ResultSet rs = stmt.executeQuery(sql);            
                rs.next();
                flag = rs.getInt("topicid");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }     
            
            return flag;
      }
                             
}
