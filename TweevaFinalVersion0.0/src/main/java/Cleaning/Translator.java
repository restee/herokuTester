/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cleaning;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author RestyLouis
 */
public class Translator {
    private static final String urlHead = "http://tweeva.zz.mu/translator/";
    
    public Translator(){
    
    }        
    
    
    public String translate(String toTranslate){
        String flag="";
                    
        toTranslate = toTranslate.replaceAll(" ","%20");
         try {
        String url = urlHead + URLEncoder.encode(toTranslate,"UTF-8");
        
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;
        String outputLine = "";
       
            response = client.execute(request);
            BufferedReader rd = new BufferedReader
                (new InputStreamReader(response.getEntity().getContent()));

              String line = "";
              while ((line = rd.readLine()) != null) {
                outputLine+= line;
              } 
              
              flag = outputLine;
              //JSONObject resultJSON = new JSONObject(outputLine);
             // flag  = resultJSON.getJSONObject("responseData").getString("translatedText");
        } catch (Exception ex) {
           
        }

        return flag.substring(3, flag.length());
    }
}
