/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Apriori;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 *
 * @author RestyLouis
 */
public class DataSender {
      int minutes = 10;
    
    public DataSender(){
        
    }
    
    
    public String putDataToServer(String url,JSONObject returnedJObject) throws Throwable
    {

    HttpPost request = new HttpPost(url);
    JSONStringer json = new JSONStringer();
    StringBuilder sb=new StringBuilder();


    if (returnedJObject!=null) 
    {
        Iterator<String> itKeys = returnedJObject.keys();
        if(itKeys.hasNext())
            json.object();
        while (itKeys.hasNext()) 
        {
            String k=itKeys.next();
            json.key(k).value(returnedJObject.get(k));
          //  Log.e("keys "+k,"value "+returnedJObject.get(k).toString());
        }             
    }
    json.endObject();
    


    StringEntity entity = new StringEntity(json.toString());
                         entity.setContentType("application/json;charset=UTF-8");
                         entity.setContentEncoding(new BasicHeader("Content-Type","application/json;charset=UTF-8"));
                         request.setHeader("Accept", "application/json");
                         request.setEntity(entity); 

                         HttpResponse response =null;
                         DefaultHttpClient httpClient = new DefaultHttpClient();

                         HttpConnectionParams.setSoTimeout(httpClient.getParams(), minutes*60*1000); 
                        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),minutes*60*1000); 
                         try{

                         response = httpClient.execute(request); 
                         }
                         catch(SocketException se)
                         {                   
                             throw se;
                         }




    InputStream in = response.getEntity().getContent();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line = null;
    while((line = reader.readLine()) != null){
        sb.append(line);
    }

    return sb.toString();
    }
}
