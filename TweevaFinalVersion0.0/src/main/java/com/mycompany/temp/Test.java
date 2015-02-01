/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.temp;

import Cleaning.Translator;
import java.util.Scanner;

/**
 *
 * @author RestyLouis
 */
public class Test {
    
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        //FixGrammar fix = new FixGrammar();
        Translator trans = new Translator();
        while(true){
            String text = in.nextLine();
            System.out.println(trans.translate(text));
          //  System.out.println(fix.fixEnglish(text));
           // System.out.println(fix.fixFilipino(text));
        }
    }
}
