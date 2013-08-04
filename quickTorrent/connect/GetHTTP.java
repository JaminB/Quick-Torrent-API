package connect;

import java.io.*;
import java.net.*;
import globals.*;

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
		String renderedPage = "";
		Variables.currentURI = URI; //store the current URI in our globals library
		try{
			BufferedReader read;
			inputURI = new URL(URI);
			connect = (HttpURLConnection) inputURI.openConnection();
		    connect.setReadTimeout(Constants.READ_TIMEOUT);
			connect.setRequestProperty("User-Agent", Constants.USER_AGENT);
			connect.setRequestMethod("GET");
			read = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String inputLine;
			while ((inputLine = read.readLine()) != null)
				renderedPage += inputLine;
			read.close();
			Variables.currentWebPage = renderedPage; //store the current webpage in our globals library
			System.out.println(Variables.currentURI);
		return Variables.currentWebPage;
		}catch (Exception e){
			return null;
		}
	}
}

