package converters.torcache;
import connect.GetHTTP;
public class MagnetToTorrent extends GetHTTP {
/*
 * Recommended Magnet to Torrent Conversion Class
 * The ISO.MagnetToTorrent methods should only be used if this class yields no results
 */
	String torrentFile;
	String magnetLink;
	
	public MagnetToTorrent(String magnetLink){
		this.magnetLink = magnetLink;
	}
	public String getHash(){
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
	
	
	public String getTorrentLink(String hash){
		String base = "http://torcache.net/torrent/";
		if(hash == null)
			return null;
		return base + hash + ".torrent";
		
	}
	
	public String getTorrentFilePreview(String torrentURI){
		/*
		 * Returns the file as a string
		 */
		try{
			return super.getWebPageHTTP(torrentURI);
		}catch(Exception e){
			return null;
		}
			
	}
}
