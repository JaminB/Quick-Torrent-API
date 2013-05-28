package fileIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFile {
	String name = "temp";
	String downloadURI;
	
	public DownloadFile(String downloadURI, String name){
		this.name = name;
		this.downloadURI = downloadURI;
	}
	public void GetFileDownload() throws IOException{ 
		/*
		 * Given a URI this method will grab the binary data from the server using http protocol
		 */
		URL inputURI;
		HttpURLConnection connect;
			inputURI = new URL(this.downloadURI);
			connect = (HttpURLConnection) inputURI.openConnection();
		    connect.setReadTimeout(10000);
			connect.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
			connect.setRequestMethod("GET");
			String fileName = name + ".torrent";
		    HttpURLConnection http = (HttpURLConnection)inputURI.openConnection();
		    InputStream input  = http.getInputStream();
		    byte[] buffer = new byte[2048];
		    int n = -1;
		    OutputStream output = new FileOutputStream( new File( fileName ));
		    while ((n = input.read(buffer)) != -1) { 
		       output.write( buffer, 0, n );
		    }
		    output.close();
	}
}