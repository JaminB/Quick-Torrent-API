package sites.pirate;
import converters.generic.DataConverters;
import globals.*;

public class PirateGrep{
	/*
	 *Provides the methods to search URIs it extends the basic functionality of the PiratePageBase class
	 *Finds # of seeds, # of leeches, and detailsPage links (these are the pages that hold the torrent download links not the .torrent itself)
	 *Can determine based on comments whether a torrent link contains a decent quality download
	 *Can find the size of a file.
	 */
	
	public String createParsedURI (String searchTerm, String mediaType){ 
		/*
		 * Given a search term return the Piratebay URI.
		 * example @param "linkin park in the end", "music"
		 */
		String baseURI = Constants.PIRATE_SEARCH_BASE;
		String parsedQuery = "";
		
		
		String URI;
		if (mediaType.toLowerCase().equals("movie") || mediaType.toLowerCase().equals("movies"))
			URI = Constants.PIRATE_SEARCH_MOVIE_SUFFIX;
		else
			URI = Constants.PIRATE_SEARCH_MUSIC_SUFFIX;
		        
		for (int i = 0; i < searchTerm.length(); i++){
			if (searchTerm.charAt(i) != ' ')
				parsedQuery = parsedQuery + searchTerm.charAt(i);
			else
				parsedQuery = parsedQuery + "%20";
		}
		return baseURI + parsedQuery + URI; //returns the search page
	}
	
	public String[] grepDetailsURI(String searchPage){ 
		/*
		 * Given a plain HTML page as a string it will parse out the detail page links and return an array of all of them
		 */
		String p = searchPage;
		int result = 0;
		String[] detailsPage = new String[62]; //holds the top 30 results with error buffer
		for (int i = 0; i<p.length(); i++){
			if(p.charAt(i) == 'h' && p.charAt(i+1) == 'r' && p.charAt(i+2) == 'e' && p.charAt(i+3) == 'f'  && p.charAt(i+7) == 't' && p.charAt(i+8) == 'o' 
					&& p.charAt(i+9) == 'r' && p.charAt(i+10) == 'r' && p.charAt(i+11) == 'e' && p.charAt(i+12) == 'n' && p.charAt(i+13) == 't' && p.charAt(i+14) == '/'){
					int j = i+15; //j is a substring iterator
					while ((int) p.charAt(j) != 47){
						j++;
						
					}
					int linkStart = i +7;
					int linkEnd = j;
					detailsPage[result] = (Constants.PIRATE_BASE+p.substring(linkStart, linkEnd));
					Variables.lastSearch.add(detailsPage[result]); //adds the list of traversed links to the global last search list
					if((detailsPage[result].contains("Ä") || detailsPage[result].contains("™") || detailsPage[result].contains("‡"))) //remove some unicode characters that break the search
						result--;
					result++;
					detailsPage[result] = Constants.PIRATE_BASE+p.substring(linkStart, linkEnd);
			}
		}
		System.out.println("Found " + result);
		return detailsPage;
	}
	
	public String grepSize(String detailsPage){
		/*
		 *Given a detailsPage this method will return the size associated with a torrent
		 *example @param: "https://kickass.to/usearch/linkinpark%20in%20the%20end/" 
		 */
		String size = null;
		for ( int i = 0; i <detailsPage.length() ; i++){
			if(detailsPage.charAt(i) == 'S' && detailsPage.charAt(i+1) == 'i' && detailsPage.charAt(i+2) == 'z' && detailsPage.charAt(i+3) == 'e'&& detailsPage.charAt(i+4) == ':' && detailsPage.charAt(i+5) == '<' && detailsPage.charAt(i+6) == '/') {
					int j = i + 16;
					while (detailsPage.charAt(j) != '&')
						j++;
					int sizeStart = i + 16;
					int sizeEnd = j;
					if (detailsPage.charAt(sizeEnd + 6) == 'G'){
						size = Float.toString(DataConverters.GBToMB(Float.parseFloat(((detailsPage.substring(sizeStart, sizeEnd)))))); //Parses out the the correct part of the string and converts it from GB to MB
					}
					
					else{
						size = (detailsPage.substring(sizeStart, sizeEnd));
					}
					
				}
		}
		Variables.sizeCount = size;
		return size;
	}
	
	public String grepSeeds(String detailsPage){
		/*
		 * Given a detailsPage this method will return the seeds associated with a torrent
		 * example @param: "https://kickass.to/usearch/linkinpark%20in%20the%20end/" 
		 */
		String seed = null;
		for ( int i = 0; i <detailsPage.length() ; i++){
			if  (detailsPage.charAt(i) == 'S'  && detailsPage.charAt(i+1) == 'e' && detailsPage.charAt(i+2) == 'e' && detailsPage.charAt(i+3) == 'd' && detailsPage.charAt(i+4) == 'e' && detailsPage.charAt(i+5) == 'r' && detailsPage.charAt(i+6) == 's' && detailsPage.charAt(i+7) == ':'){
					int j = i + 19;
					while (detailsPage.charAt(j) != '<')
						j++;
					int seedStart = i + 19;
					int seedEnd = j;
					seed = detailsPage.substring(seedStart, seedEnd);
				}
		}
		Variables.seedCount = seed;
		return seed;
		
	}
	
	public String grepLeeches(String detailsPage){
		/*
		 * Given a detailsPage this method will return the leeches associated with a torrent
		 * example @param: "https://kickass.to/usearch/linkinpark%20in%20the%20end/"
		 */

		String leech = null;
		for ( int i = 0; i <detailsPage.length() ; i++){
			
			if  (detailsPage.charAt(i) == 'L' && detailsPage.charAt(i+1) == 'e' && detailsPage.charAt(i+2) == 'e' && detailsPage.charAt(i+3) == 'c' && detailsPage.charAt(i+4) == 'h' && detailsPage.charAt(i+5) == 'e' && detailsPage.charAt(i+6) == 'r' && detailsPage.charAt(i+7) == 's' && detailsPage.charAt(i+8) == ':'){
					int j = i + 19;
					while (detailsPage.charAt(j) != '<')
						j++;
					int leechStart = i + 20;
					int leechEnd = j;
					leech = detailsPage.substring(leechStart, leechEnd);
				}
		}
		Variables.leechCount = leech;
		return leech;
	}
		
	public String grepMagnetLink(String detailsPage){
		/*
		 * Given a detailsPage this method will return the magnetLink associated with a torrent
		 * example @param: "https://kickass.to/usearch/linkinpark%20in%20the%20end/"
		 */
		boolean firstResult = false;
		try {
			String magnetLink = null;
			for (int i = 0; i < detailsPage.length(); i++){
				if (detailsPage.charAt(i) == 'm'
					&& detailsPage.charAt(i+1) == 'a'
					&& detailsPage.charAt(i+2) == 'g'
					&& detailsPage.charAt(i+3) == 'n'
					&& detailsPage.charAt(i+6) == ':'
					&& detailsPage.charAt(i+7) == '?'
					&& firstResult == false){
					int j = i;
					while(detailsPage.charAt(j) != '"')
						j++;
					firstResult = true; // these pages contain more then one download link. This ensures you are only getting one
					magnetLink = (detailsPage.substring(i,j));
					Variables.magnetLink = magnetLink; //store magnet link in global library
				}
			}
			return Variables.magnetLink;
		}catch (Exception e){
			return null;
		}
	}
}
