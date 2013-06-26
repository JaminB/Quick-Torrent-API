package connect;

import java.io.*;
import java.net.*;
import globals.Constants;

public class GetHTTP { 
	/*
	 * Basic HTTP methods needed for the piratebay.sx
	 */	
	public String getWebPageHTTP(String URI){ 
		/*
		 * Given a URI this method will grab the html from that page using http protocol
		 */
		URL inputURI;
		HttpURLConnection connect;
		BufferedReader read;
		String line;
		String renderedPage = "";
		try{
			inputURI = new URL(URI);
			connect = (HttpURLConnection) inputURI.openConnection();
		    connect.setReadTimeout(Constants.READ_TIMEOUT);
			connect.setRequestProperty("User-Agent", Constants.USER_AGENT);
			connect.setRequestMethod("GET");
			read = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			while ((line = read.readLine()) != null)
				renderedPage += line;
			read.close();
		return renderedPage;
		}catch (Exception e){
			return null;
		}
	}
}

