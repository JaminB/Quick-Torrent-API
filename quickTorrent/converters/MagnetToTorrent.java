package converters;

public class MagnetToTorrent {
	String magnetLink;
	String torrentFile;
	public MagnetToTorrent(String magnetLink){
		this.magnetLink = magnetLink;
	}
	public String GrepHash(){
		
		try{ 
			int hashStart = magnetLink.indexOf("btih:")+5;
			int hashEnd = magnetLink.indexOf("&");
			return magnetLink.substring(hashStart, hashEnd);
			
		}catch (Exception e){
			return null;
		}
	}

}
