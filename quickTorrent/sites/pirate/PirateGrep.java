package sites.pirate;
import globals.Constants;

import java.util.ArrayList;

import connect.GetHTTP;


public class PirateGrep extends GetHTTP {
	/*
	 *Provides the methods to search URIs it extends the basic functionality of the PiratePageBase class
	 *Finds # of seeds, # of leeches, and detailPage links (these are the pages that hold the torrent download links not the .torrent itself)
	 *Can determine based on comments whether a torrent link contains a decent quality download
	 *Can find the size of a file.
	 */
	ArrayList<String> dataCache = new ArrayList<String>(); //stores the data detailsPage data (seeders, leechers, size, links)
	
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
		return baseURI + parsedQuery + URI;				
	}
	
	public String[] grepDetailsPage(String searchPage){ 
		/*
		 * Given a plain HTML page as a string it will parse out the detail page links and return an array of all of them
		 * This method will take direct input from the GetParseURI method
		 * example @param "http://thepiratebay.sx/search/linkin%20park%20in%20the%20end/0/99/100"
		 */
		String p = searchPage;
		int result = 0;
		String[] detailPage = new String[31]; //holds the top 30 results
		for (int i = 0; i<p.length(); i++){
			if(p.charAt(i) == 'h' && p.charAt(i+1) == 'r' && p.charAt(i+2) == 'e' && p.charAt(i+3) == 'f'  && p.charAt(i+7) == 't' && p.charAt(i+8) == 'o' 
					&& p.charAt(i+9) == 'r' && p.charAt(i+10) == 'r' && p.charAt(i+11) == 'e' && p.charAt(i+12) == 'n' && p.charAt(i+13) == 't' && p.charAt(i+14) == '/'){
					int j = i+15; //j is a substring iterator
					while ((int) p.charAt(j) != 47){
						j++;
					}
					int linkStart = i +7;
					int linkEnd = j;
					detailPage[result] = Constants.PIRATE_BASE+p.substring(linkStart, linkEnd);
					result++;
			}
		}
		System.out.println("Found " + result);
		return detailPage;
	}
	
	public ArrayList<String> buildDataCache(String[] detailPage){
		/*
		 *  Given a string array of Detail Pages will return the stats of each page in an array list
		 *  example @param GrepDetailsPage("http://thepiratebay.sx/search/linkin%20park%20in%20the%20end/0/99/100")
		 */
		dataCache = new ArrayList<String>(); //rebuild cache
		int pageNumber = 0;
		while (detailPage[pageNumber] != null){
			String URI = detailPage[pageNumber]; //store the URI in a local variable for convenience
			String pageHTML = super.getWebPageHTTP(URI); //pull down the html of the page
			for ( int i = 0; i <pageHTML.length() ; i++){
				if(pageHTML.charAt(i) == 'S' && pageHTML.charAt(i+1) == 'i' && pageHTML.charAt(i+2) == 'z' && pageHTML.charAt(i+3) == 'e'&& pageHTML.charAt(i+4) == ':' && pageHTML.charAt(i+5) == '<' && pageHTML.charAt(i+6) == '/') {
						int j = i + 16;
						while (pageHTML.charAt(j) != '&')
							j++;
						dataCache.add(pageHTML.substring(i+16, j));
					}
				if  (pageHTML.charAt(i) == 'S'  && pageHTML.charAt(i+1) == 'e' && pageHTML.charAt(i+2) == 'e' && pageHTML.charAt(i+3) == 'd' && pageHTML.charAt(i+4) == 'e' && pageHTML.charAt(i+5) == 'r' && pageHTML.charAt(i+6) == 's' && pageHTML.charAt(i+7) == ':'){
					int j = i + 19;
					while (pageHTML.charAt(j) != '<')
						j++;
					int seedStart = i + 19;
					int seedEnd = j;
					dataCache.add(pageHTML.substring(seedStart, seedEnd));
				}
				if  (pageHTML.charAt(i) == 'L' && pageHTML.charAt(i+1) == 'e' && pageHTML.charAt(i+2) == 'e' && pageHTML.charAt(i+3) == 'c' && pageHTML.charAt(i+4) == 'h' && pageHTML.charAt(i+5) == 'e' && pageHTML.charAt(i+6) == 'r' && pageHTML.charAt(i+7) == 's' && pageHTML.charAt(i+8) == ':'){
					int j = i + 19;
					while (pageHTML.charAt(j) != '<')
						j++;
					int leechStart = i + 20;
					int leechEnd = j;
					dataCache.add(pageHTML.substring(leechStart, leechEnd));
				}
			}
			dataCache.add(detailPage[pageNumber]); // add the page number to the first location of our arrayList
			pageNumber++;
		}
		System.out.print("#");
		return dataCache;
	}
	
	public ArrayList <String> qualityFilter(ArrayList <String> dataCache ){ 
		/*
		 * Quality Check Based on comments takes the original cache and filters out bad results
		 * example @param GetDataCache() || BuildDataCache("http://thepiratebay.sx/search/linkin%20park%20in%20the%20end/0/99/100")
		 */
		ArrayList<String> qualityList = dataCache; //use the original dataCache array list
		int pageNumber = 0;
		try{
			String[] detailsPage = dataCacheToArray(dataCache, "link");
				while (detailsPage[pageNumber] != null){
					String URI = detailsPage[pageNumber]; //store the URI in a local variable for convenience
					String pageHTML = super.getWebPageHTTP(URI);
					//Word List -------------------------------------------------------------------
						if  (pageHTML.toLowerCase().contains("virus") 
							|| pageHTML.toLowerCase().contains("trojan")
							|| pageHTML.toLowerCase().contains("is fake") 
							|| pageHTML.contains("FAKE")
							|| pageHTML.toLowerCase().contains("do not download")
							|| pageHTML.toLowerCase().contains("don't download") 
							|| pageHTML.toLowerCase().contains("poor quality") 
							|| pageHTML.toLowerCase().contains("bad quality")
							|| pageHTML.toLowerCase().contains("horrible quality")
							|| pageHTML.contains("span id=\"NumComments\">0</span")){ //automatically skips if no comments
						//-------------------------------------------------------------------------
							int badStart =  qualityList.indexOf(URI) - 3; //shift back 3 since we want to delete from size which is the first entry
							qualityList.remove(badStart); //remove size
							qualityList.remove(badStart); //remove seeder
							qualityList.remove(badStart); //remove leecher
							qualityList.remove(badStart); //remove links
						}
						pageNumber++;
					}
				System.out.print("#");
				return qualityList;	
			}catch(Exception e){
				return null;
			}
	}
	
	public ArrayList <String> GetDataCache(){
		/* 
		 * returns the current dataCache
		 * the BuildDataCache method must be called before this.
		 */
		return dataCache;
	}
		
	public String grepMagnetLink(String detailsPage){
		/*
		 * Given a link like http://thepiratebay.sx/torrent/4510145 will find the download link and return it.
		 */
		String torrentDownloadLink = null;
		boolean firstResult = false;
		try {
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
					torrentDownloadLink = (pageHTML.substring(i,j));
				}
			}
			return torrentDownloadLink;
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
