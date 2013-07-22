package sites.kat;

import globals.*;


public class KATGrep {
	/*
	 *Provides the methods to search URIs it extends the basic functionality of the PiratePageBase class
	 *Finds # of seeds, # of leeches, and detailPage links (these are the pages that hold the torrent download links not the .torrent itself)
	 *Can determine based on comments whether a torrent link contains a decent quality download
	 *Can find the size of a file.
	 */
	
	
	public String createParsedURI (String searchTerm, String mediaType){ 
			/*
			 * Given a search term return the KAT URI.
			 * example @param: "linkin park in the end/", "music"
			 */
			String baseURI = Constants.KAT_SEARCH_BASE;
			String parsedQuery = "";
			String URI;
			if (mediaType.toLowerCase().equals("movie") || mediaType.toLowerCase().equals("movies"))
				URI = Constants.KAT_SEARCH_MOVIE_SUFFIX;
			else
				URI = Constants.KAT_SEARCH_MUSIC_SUFFIX;
			
			for (int i = 0; i < searchTerm.length(); i++){
				if (searchTerm.charAt(i) == ' ')
					parsedQuery = parsedQuery + "%20";
				else if ( (int) searchTerm.charAt(i) == 39
						||	(int) searchTerm.charAt(i) == 145 
						||  (int) searchTerm.charAt(i) == 146 ){
					parsedQuery = parsedQuery + "%27";
				}
					
				else
					parsedQuery = parsedQuery + searchTerm.charAt(i);
			}
			return baseURI + parsedQuery + URI; //returns the search page				
		}

	public String[] grepDetailsPage(String searchPage){ 
			/*
			 * Given a plain HTML page as a string it will parse out the torrent links and return an array of all of them
			 * This method will take direct input from the KATParseURI method
			 * example @param: "https://kickass.to/usearch/linkinpark%20in%20the%20end/"
			 */
			String p = searchPage;
			int result = 0;
			String[] detailsPage = new String[26]; //holds the top 30 results
			try {
				for (int i = 0; i<p.length(); i++){
					if(p.charAt(i) == 't'
							&& p.charAt(i+1) == 'o' 
							&& p.charAt(i+2) == 'r'
							&& p.charAt(i+3) == 'T'
							&& p.charAt(i+4) == 'y'
							&& p.charAt(i+5) == 'p'
							&& p.charAt(i+6) == 'e'){
						int linkStart = i;
						while (p.charAt(linkStart) != '<') //find the link from the backwards index of where the keyword "torType" is found
							linkStart--;
						linkStart+=9;
						int linkEnd = linkStart+2;
						while (p.charAt(linkEnd) != '"')
							linkEnd++;
							detailsPage[result] = (Constants.KAT_BASE+p.substring(linkStart, linkEnd));
							Variables.lastSearch.add(detailsPage[result]);
						result++;
						
					}
					
				}
				System.out.println("Found: " + result);
				//adds the query to the global last search list
				return detailsPage;
			}catch(Exception e){
				return null;
			}
		}
	public String grepSize (String detailsPage){
		String size = null;
		for ( int i = 0; i <detailsPage.length() ; i++){
			
			if  (detailsPage.charAt(i) == '('  && detailsPage.charAt(i+1) == 'S' && detailsPage.charAt(i+2) == 'i' && detailsPage.charAt(i+3) == 'z' && detailsPage.charAt(i+4) == 'e' && detailsPage.charAt(i+5) == ':'){
				int sizeStart = i + 7;
				int sizeEnd = i + 7;
				while (detailsPage.charAt(sizeEnd) != ' ')
					sizeEnd++;
				size = (detailsPage.substring(sizeStart, sizeEnd));
			}
		}
		Variables.sizeCount = size;
		return size;
	}
	
	public String grepSeeds(String detailsPage){
		String seed = null;
		for ( int i = 0; i <detailsPage.length() ; i++){
			if  (detailsPage.charAt(i) == 's'  && detailsPage.charAt(i+1) == 'e' && detailsPage.charAt(i+2) == 'e' && detailsPage.charAt(i+3) == 'd' && detailsPage.charAt(i+4) == 'e' && detailsPage.charAt(i+5) == 'r'  && detailsPage.charAt(i+6) == 's'  && detailsPage.charAt(i+8) == '>'){
				int seedStart = i + 9;
				int seedEnd = i + 9;
				while (detailsPage.charAt(seedEnd) != '<')
					seedEnd++;
				seed = (detailsPage.substring(seedStart, seedEnd));
			}
		}
		Variables.seedCount = seed;
		return seed;
	}
	public String grepLeeches(String detailsPage){
		String leech = null;
		for ( int i = 0; i <detailsPage.length() ; i++){
			if  (detailsPage.charAt(i) == 'l' && detailsPage.charAt(i+1) == 'e' && detailsPage.charAt(i+2) == 'e' && detailsPage.charAt(i+3) == 'c' && detailsPage.charAt(i+4) == 'h' && detailsPage.charAt(i+5) == 'e' && detailsPage.charAt(i+6) == 'r' && detailsPage.charAt(i+7) == 's' && detailsPage.charAt(i+8) == '"'){
				int leechStart = i + 10;
				int leechEnd = i + 10;
				while (detailsPage.charAt(leechEnd) != '<')
					leechEnd++;
				leech = (detailsPage.substring(leechStart, leechEnd));
			}
		}
		Variables.leechCount = leech;
		return leech;
	}
	
	public String grepMagnetLink(String detailsPage){
		/*
		 * Given a link this method will find the download link and return it.
		 * example @param: "https://kickass.to/usearch/linkinpark%20in%20the%20end/"
		 */
		String torrentDownloadLink = null;
		boolean firstResult = false;
		try {
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
					torrentDownloadLink = (detailsPage.substring(i,j));
				}
			}
			return torrentDownloadLink;
		}catch (Exception e){
			return null;
		}
	}
	

	
	
	
	/*
	 * Conversion methods are here to make life easier. Otherwise you're going to be parsing ArrayLists. Use them!
	 */
	
}
