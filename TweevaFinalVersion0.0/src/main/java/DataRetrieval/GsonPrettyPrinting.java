/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataRetrieval;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Janine Alexis
 */
public class GsonPrettyPrinting 
{

	public String formatJson(Tweet tweet) {

		Gson prettyGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                
                
               
                String pretJson = gson.toJson(tweet);

		
        return pretJson;

	}

}
