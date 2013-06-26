package globals;

public class Variables {
	/*
	 * Holds all the global variables
	 */
	public static String currentURI; //URI currently being searched
	public static String currentWebPage; //Webpage html content currently being searched
	
	public static String magnetLink; //Most recent magnetLink
	public static String torrentPreview; //Preview of content of most recent .torrent file
	
	public static String downloadUri; //URI location of most recent torrent download
	public static String downloadName; //Name of File and download location.
	
	public static String query; //Most recent search query
	public static String mediaType; //Movie/Music/TV/Other (movie and music are currently the only available fields)
	public static boolean qualityCheck; //Safe-search enabled
	
	public static int seedCount; //Number of seeds of the most recently searched torrent
	public static int leechCount; //Number of leeches for the most recently searched torrent
	
	

	
	
	
}
