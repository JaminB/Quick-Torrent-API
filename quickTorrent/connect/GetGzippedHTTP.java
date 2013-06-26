package connect;
import java.io.*;
import java.net.*;
import java.util.zip.GZIPInputStream;
import globals.Constants;

public class GetGzippedHTTP { 
	/*
	 * Given a URI this method will grab the html from that page using http protocol 
	 * kickass.to pages are gzipped. This method takes that into account
	 */
	public String getWebPageGzipHTTP(String URI){ 
		String html = "";
		try {
		    URLConnection connect = new URL(URI).openConnection();                        
		    BufferedReader in = null;
		    connect.setReadTimeout(Constants.READ_TIMEOUT);
		    connect.setRequestProperty("User-Agent", Constants.USER_AGENT);
		    if (connect.getHeaderField("Content-Encoding")!=null && connect.getHeaderField("Content-Encoding").equals("gzip")){
		        in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connect.getInputStream())));            
		    } else {
		        in = new BufferedReader(new InputStreamReader(connect.getInputStream()));            
		    }          
		    String inputLine;
		    while ((inputLine = in.readLine()) != null){
		    html+=inputLine;
		    }
		in.close();
			return html;
		} catch (Exception e) {
			return html;
		}
	}
}

