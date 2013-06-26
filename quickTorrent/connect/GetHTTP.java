package connect;

import java.io.*;
import java.net.*;

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
		    connect.setReadTimeout(10000);
			connect.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
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

