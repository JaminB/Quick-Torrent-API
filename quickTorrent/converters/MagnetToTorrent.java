package converters;

import connect.GetHTTP;
public class MagnetToTorrent extends GetHTTP {
	String magnetLink;
	String torrentFile;
	
	public MagnetToTorrent(String magnetLink){
		this.magnetLink = magnetLink;
	}
	public String GrepHash(){
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
	
	public String CreateParsedURI(String hash){
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
	
	public String GrepUniqueID(String detailsLink){
		/*
		 * Gets the unique file ID (used to generate a .torrent location)
		 * This method also take server load/latency into account
		 */
		int detailsLinkStart = 0;
		int detailsLinkEnd = 0;
		int attempts = 0;
		String uniqueID = "0000000000000000"; //loop atleast once.
		String searchPage; 
		try { 
			while(uniqueID.length() > 15 ){
				attempts++;
				if (attempts >= 5)
					return null; //exit if we are in this loop for over 5 iterations
				searchPage = super.GetWebPageHTTP(detailsLink);
				detailsLinkStart = searchPage.indexOf("torrent_details") +16 ;
				int i = 0;
				detailsLinkEnd = detailsLinkStart;
				while(searchPage.charAt(detailsLinkEnd) + i != '/' )
					detailsLinkEnd++;
				uniqueID = searchPage.substring(detailsLinkStart, detailsLinkEnd);
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
