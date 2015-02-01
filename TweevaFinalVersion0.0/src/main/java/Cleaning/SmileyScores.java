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


public class SmileyScores {
    private String smiley;
    private float  score;

    public SmileyScores(String smiley, float score){
        this.smiley = smiley;
        this.score = score;
    }
    
    
    public float getScore() {
        return score;
    }

    public String getSmiley() {
        return smiley;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setSmiley(String smiley) {
        this.smiley = smiley;
    }
    
    
}
