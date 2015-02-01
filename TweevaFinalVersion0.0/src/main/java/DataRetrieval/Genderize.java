/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataRetrieval;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import twitter4j.JSONObject;

/**
 *
 * @author Janine Alexis
 */
class Genderize {
     public static String genderize(String firstName, String lastName) {
        try {

            String s = "http://api.namsor.com/onomastics/api/json/gendre/"
                    + URLEncoder.encode(firstName, "UTF-8") + "/"
                    + URLEncoder.encode(lastName, "UTF-8");
            URL url = new URL(s);

            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();

            JSONObject obj = new JSONObject(str);


            String gend = obj.getString("gender");
            return gend;
        } catch (Exception e) {
            return null;
        }
}
}