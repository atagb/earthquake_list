
package earthquakes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


public class Earthquakes {

    /**
     * @param args the command line arguments
     */

    private static HttpURLConnection connection;

    public static void main(String[] args){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try{
            URL url = new URL("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2022-01-01&endtime=2022-01-02");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();
            if(status <= 200){
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            System.out.println(responseContent.toString());
            //parse(responseContent.toString());
        }

        catch (MalformedURLException e){
            e.printStackTrace();
        }   
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static String parse(String responseBody){
        JSONArray geodata = new JSONArray(responseBody);
        for(int i=0; i<geodata.length(); i++){
            JSONObject earthquake = geodata.getJSONObject(i);
            String place = earthquake.getJSONArray("properties").toString();
            int magnitude = earthquake.getInt("mag");
            int date_time = earthquake.getInt("time");
            System.out.println(magnitude +" "+ place +" "+ date_time);
        }
    return null;
    }
    
}
