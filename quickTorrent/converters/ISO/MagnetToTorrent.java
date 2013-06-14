package converters.ISO;
import connect.GetHTTP;
public class MagnetToTorrent extends GetHTTP {
	/*
	 * Depreciated Magnet to Torrent Conversion Class
	 * The ISO.MagnetToTorrent methods should only be used if Torcache.MagnetToTorrent yields no results
	 */
	String torrentFile;
	String magnetLink;
	
	public MagnetToTorrent(String magnetLink){
		this.magnetLink = magnetLink;
	}
	public String GetHash(){
		/*
		 * Strips the hash value from the magnetLink
		 */
		try{ 
			int hashStart = magnetLink.indexOf("btih:")+5;
			int hashEnd = magnetLink.indexOf("&");
			return magnetLink.substring(hashStart, hashEnd);
			
		}catch (Exception e){
			return null;
		}
	}
	
	public String CreateISOParsedURI(String hash){
		/*
		 * Generates a search link based on a hash query
		 */
		String searchLink = null;
		if(hash != null){
			searchLink = "http://isohunt.com/torrents/?ihq="+hash;
			return searchLink;
		}
		else 
			return null;	
	}
	
	
	public String GrepUniqueID(String searchLink){
		/*
		 * Gets the unique file ID (used to generate a .torrent location)
		 * This method also take server load/latency into account
		 */
		int searchLinkStart = 0;
		int searchLinkEnd = 0;
		int attempts = 0;
		String uniqueID = "0000000000000000"; //loop atleast once.
		String searchPage; 
		try { 
			while(uniqueID.length() > 15 ){
				attempts++;
				if (attempts >= 5)
					return null; //exit if we are in this loop for over 5 iterations
				searchPage = super.GetWebPageHTTP(searchLink);
				searchLinkStart = searchPage.indexOf("torrent_details") +16 ;
				int i = 0;
				searchLinkEnd = searchLinkStart;
				while(searchPage.charAt(searchLinkEnd) + i != '/' )
					searchLinkEnd++;
				uniqueID = searchPage.substring(searchLinkStart, searchLinkEnd);
			}
		return uniqueID;
		}catch(Exception e){
			return null;
		}
	}
	
	public String GetTorrentLink(String hash, String uniqueID){
		String base = "http://isohunt.com/download/";
		if(hash == null || uniqueID == null)
			return null;
		return base + uniqueID + "/" + hash +".torrent";
		
	}
	
	public String GetTorrentFilePreview(String torrentURI){
		/*
		 * Returns the file as a string
		 */
		try{
			return super.GetWebPageHTTP(torrentURI);
		}catch(Exception e){
			return null;
		}
			
	}
}
