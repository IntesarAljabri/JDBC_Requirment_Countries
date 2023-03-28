package src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {

	 public static void main(String[] args) {
	        String apiUrl = "https://https://restcountries.com/v3.1/all";
	        try {
	            URL url = new URL(apiUrl);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Accept", "application/json");
	            
	            if (conn.getResponseCode() != 200) {
	                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
	            }
	            
	            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	            String output;
	            StringBuilder json = new StringBuilder();
	            
	            while ((output = br.readLine()) != null) {
	                json.append(output);
	            }
	            
	            conn.disconnect();
	            
	            Gson gson = new Gson();
	            MyClass myObj = gson.fromJson(json.toString(), Myclass.class);
	            
	            // Use myObj for further processing
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	class Myclass {
	   
	
	}
	//----------------------------------------------------------------------------------------------------------------------//
	
	
	
	
	
	

