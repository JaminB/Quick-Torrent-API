package sites.pirate;
import globals.Constants;
import globals.Variables;

import java.util.ArrayList;

import connect.GetHTTP;


public class PirateGrep extends GetHTTP {
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
	
	public String[] grepDetailsPage(String searchPage){ 
		/*
		 * Given a plain HTML page as a string it will parse out the detail page links and return an array of all of them
		 * This method will take direct input from the GetParseURI method
		 * example @param "http://thepiratebay.sx/search/linkin%20park%20in%20the%20end/0/99/100"
		 */
		String p = searchPage;
		int result = 0;
		String[] detailsPage = new String[31]; //holds the top 30 results
		for (int i = 0; i<p.length(); i++){
			if(p.charAt(i) == 'h' && p.charAt(i+1) == 'r' && p.charAt(i+2) == 'e' && p.charAt(i+3) == 'f'  && p.charAt(i+7) == 't' && p.charAt(i+8) == 'o' 
					&& p.charAt(i+9) == 'r' && p.charAt(i+10) == 'r' && p.charAt(i+11) == 'e' && p.charAt(i+12) == 'n' && p.charAt(i+13) == 't' && p.charAt(i+14) == '/'){
					int j = i+15; //j is a substring iterator
					while ((int) p.charAt(j) != 47){
						j++;
					}
					int linkStart = i +7;
					int linkEnd = j;
					detailsPage[result] = Constants.PIRATE_BASE+p.substring(linkStart, linkEnd);
					Variables.lastSearch.add(detailsPage[result]); //adds the list of traversed links to the global last search list
					result++;
			}
		}
		System.out.println("Found " + result);
		return detailsPage;
	}
	
	public String grepSize(String detailsPage){
		String size = null;
		for ( int i = 0; i <detailsPage.length() ; i++){
			
			if(detailsPage.charAt(i) == 'S' && detailsPage.charAt(i+1) == 'i' && detailsPage.charAt(i+2) == 'z' && detailsPage.charAt(i+3) == 'e'&& detailsPage.charAt(i+4) == ':' && detailsPage.charAt(i+5) == '<' && detailsPage.charAt(i+6) == '/') {
					int j = i + 16;
					while (detailsPage.charAt(j) != '&')
						j++;
					size = detailsPage.substring(i+16, j);
				}
		}
		Variables.sizeCount = size;
		return size;
	}
	
	public String grepSeeds(String detailsPage){
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
		 * Given a link like http://thepiratebay.sx/torrent/4510145 will find the download link and return it.
		 */
		boolean firstResult = false;
		try {
			String magnetLink = null;
			String pageHTML = super.getWebPageHTTP(detailsPage);
			for (int i = 0; i < pageHTML.length(); i++){
				if (pageHTML.charAt(i) == 'm'
					&& pageHTML.charAt(i+1) == 'a'
					&& pageHTML.charAt(i+2) == 'g'
					&& pageHTML.charAt(i+3) == 'n'
					&& pageHTML.charAt(i+6) == ':'
					&& pageHTML.charAt(i+7) == '?'
					&& firstResult == false){
					int j = i;
					while(pageHTML.charAt(j) != '"')
						j++;
					firstResult = true; // these pages contain more then one download link. This ensures you are only getting one
					magnetLink = (pageHTML.substring(i,j));
					Variables.magnetLink = magnetLink; //store magnet link in global library
				}
			}
			return Variables.magnetLink;
		}catch (Exception e){
			return null;
		}
	}
	
	
//********************************************Cache Manipulation METHODS**********************************************************\\

	
	public String[] dataCacheToArray(ArrayList <String> dataCache, String dataField){
		/*
		 * @param (ArrayList <String> dataCache, "link" || "seed" || "leech" || "size")
		 * example (BuildDataCache(String[] foo), "size")) will return all the torrent sizes in a string array.
		 * Parses the SizeStats ArrayList into one dimensional array (seeders, leechers, links, size)
		 */
		try{
			String[] parsedArray = new String[dataCache.size()];
			int listPos = 0;
			if(dataField.equals("link")) listPos = 3;
			if(dataField.equals("leech"))listPos = 2;
			if(dataField.equals("seed")) listPos = 1;
			if(dataField.equals("size")) listPos = 0;
			for (int i = 0; i < dataCache.size()/4; i++){
				parsedArray[i] = dataCache.get(listPos);
				listPos = listPos+4;
			}
			System.out.print("#");
			return parsedArray;
		}catch(Exception e){
			return null;
		}
	}
	
	/*
	 * Conversion methods are here to make life easier. Otherwise you're going to be parsing ArrayLists. Use them!
	 */
	
	public float[] sizeToFloat(ArrayList <String> queryResults){
		/*
		 * Conversion method used to convert <String> Size value to integer
		 */
		try{ 
			float[] sizeArray = new float[queryResults.size()/4];
			for (int i = 0; i < queryResults.size()/4; i++){
				sizeArray[i] = Float.parseFloat(dataCacheToArray(queryResults, "size")[i]);
			}
			System.out.print("#");
			return sizeArray;
		}catch (Exception e){
			return null;
		}
	}
	
	public int[] seedToInt(ArrayList <String> queryResults){
		/*
		 * Conversion method used to convert <String> seeder value to integer
		 */
		try{
			int[] seedArray = new int[queryResults.size()/4];
			for (int i = 0; i < queryResults.size()/4; i++){
				seedArray[i] = Integer.parseInt(dataCacheToArray(queryResults, "seed")[i]);
			}
			System.out.print("#");
			return seedArray;
		}catch (Exception e){
			return null;
		}
		
	}
	
	public int[] leechToInt(ArrayList <String> dataCache){
		/*
		 * Conversion method used to convert <String> leecher value to integer
		 */
		try{
			int[] leechArray = new int[dataCache.size()/4];
			for (int i = 0; i < dataCache.size()/4; i++){
				leechArray[i] = Integer.parseInt(dataCacheToArray(dataCache, "leech")[i]);
			}
			System.out.print("#");
			return leechArray;
		}catch (Exception e) {
			return null;
		}
	}
}
