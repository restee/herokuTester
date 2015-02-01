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
public class Abbreviation {
    private String abbreviation;
    private String originalForm;
    
    public Abbreviation(String abbreviation, String originalForm){
        this.abbreviation = abbreviation;
        this.originalForm = originalForm;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setOriginalForm(String originalForm) {
        this.originalForm = originalForm;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getOriginalForm() {
        return originalForm;
    }
    
    
}
