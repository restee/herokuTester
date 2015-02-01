/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.temp;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.language.BritishEnglish;
import org.languagetool.language.Tagalog;
import org.languagetool.rules.RuleMatch;

/**
 *
 * @author RestyLouis
 */
public class FixGrammar {
    
    JLanguageTool langToolEnglish,langToolFilipino;
    Language tagalog;
    Language english;
    
    public FixGrammar(){        
         try{
             tagalog  = new Tagalog();
             english = new BritishEnglish();
             langToolEnglish  = new JLanguageTool(english);
             langToolEnglish.activateDefaultPatternRules();
             langToolFilipino  = new JLanguageTool(tagalog);
             langToolFilipino.activateDefaultPatternRules();
         }catch(Exception e){}
    }
    
   
    
    public String fixEnglish(String text){
        String flag="";
        List<RuleMatch> matches ;
        try{    
            matches = langToolEnglish.check(text);           
            int prev = 0;
            for (RuleMatch match : matches) {            
              if(match.getSuggestedReplacements().size()>0){
                if(prev>(match.getColumn()-1)){
                    flag = flag.substring(0, (match.getColumn()-1));
                    prev = match.getColumn()-1; }                
                flag += text.substring(prev,match.getColumn()-1) + match.getSuggestedReplacements().get(0);
                prev = match.getEndColumn()-1; }       
            }
            flag+= text.substring(prev,text.length());                                
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            flag = text;
        }finally{
            return flag;    
        }                
    }
    
    
    public String fixFilipino(String text){
        String flag="";
        List<RuleMatch> matches ;
            try{
            Scanner in = new Scanner(System.in);
            
            
            matches = langToolFilipino.check(text);
                        
            int prev = 0;
            for (RuleMatch match : matches) {            
//
//              System.out.println("Potential error at line " +
//                  match.getLine() + ", column (start = " +
//                  match.getColumn() +", end = " + match.getEndColumn() + "): " + match.getMessage());
//              System.out.println("Suggested correction: " +
//                  match.getSuggestedReplacements());

              if(match.getSuggestedReplacements().size()>0){
                   if(prev>(match.getColumn()-1)){
                    flag = flag.substring(0, (match.getColumn()-1));
                    prev = match.getColumn()-1;
                }
                flag += text.substring(prev,match.getColumn()-1) + match.getSuggestedReplacements().get(0);
                prev = match.getEndColumn()-1;          
              }
            }
            flag+= text.substring(prev,text.length());
                    
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return flag;
    }
}
