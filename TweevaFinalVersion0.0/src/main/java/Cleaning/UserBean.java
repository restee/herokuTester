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
public class UserBean {
    private String userid;
    private String screenName;
    private String fullName;
    private char gender;
    private String address;

    public UserBean(){
    
    }
    public UserBean(String userid, String screenName, String fullName, char gender, String address) {
        this.userid = userid;
        this.screenName = screenName;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public String getFullName() {
        return fullName;
    }

    public char getGender() {
        return gender;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getUserid() {
        return userid;
    }
    
    
}
