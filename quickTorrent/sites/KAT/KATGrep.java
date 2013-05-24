package sites.KAT;

import java.util.ArrayList;

public class KATGrep extends KATPageBase {
	/*
	 *Provides the methods to search URIs it extends the basic functionality of the PiratePageBase class
	 *Finds # of seeds, # of leeches, and detailPage links (these are the pages that hold the torrent download links not the .torrent itself)
	 *Can determine based on comments whether a torrent link contains a decent quality download
	 *Can find the size of a file.
	 */
	ArrayList<String> dataCache = new ArrayList<String>(); //stores the data detailsPage data (seeders, leechers, size, links)
	
	public String CreateParsedURI (String searchTerm){ 
			/*
			 * Given a search term return the KAT URI.
			 * example @param: "linkin park in the end/"
			 */
			String baseURI = "https://kat.ph/usearch/";
			String parsedQuery = "";
			String audioURI = "%20category:music/";
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
			return baseURI + parsedQuery + audioURI;				
		}

	public String[] GrepDetailsPage(String searchPage){ 
			/*
			 * Given a plain HTML page as a string it will parse out the torrent links and return an array of all of them
			 * This method will take direct input from the KATParseURI method
			 * example @param: "https://kat.ph/usearch/linkinpark%20in%20the%20end/"
			 */
			String p = searchPage;
			int result = 0;
			String[] KATTorrentPage = new String[26]; //holds the top 30 results
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
							KATTorrentPage[result] = ("https://kat.ph/"+p.substring(linkStart, linkEnd));
						result++;
					}
					
				}
				System.out.println("Found: " + result);
				return KATTorrentPage;
			}catch(Exception e){
				return null;
			}
		}
	
	public ArrayList<String> BuildDataCache(String[] detailsPage){
		/*
		 *  Given a list of KAT torrent address returns the seeds, leeches and links in an ArrayList
		 *  example @param: GrepDetailsPage("https://kat.ph/usearch/linkinpark%20in%20the%20end/")
		 */
		dataCache = new ArrayList<String>();
		int pageNumber = 0;
		while (detailsPage[pageNumber] != null){
			String URI = detailsPage[pageNumber]; //store the URI in a local variable for convenience
			String pageHTML = super.GetWebPageGzipHTTP(URI); //pull down the html of the page
			String size = null; 
			String seed = null; 
			String leech = null;
			for ( int i = 0; i <pageHTML.length() ; i++){
				
				if  (pageHTML.charAt(i) == '('  && pageHTML.charAt(i+1) == 'S' && pageHTML.charAt(i+2) == 'i' && pageHTML.charAt(i+3) == 'z' && pageHTML.charAt(i+4) == 'e' && pageHTML.charAt(i+5) == ':'){
					int sizeStart = i + 7;
					int sizeEnd = i + 7;
					while (pageHTML.charAt(sizeEnd) != ' ')
						sizeEnd++;
					size = (pageHTML.substring(sizeStart, sizeEnd));
				}
		
				if  (pageHTML.charAt(i) == 's'  && pageHTML.charAt(i+1) == 'e' && pageHTML.charAt(i+2) == 'e' && pageHTML.charAt(i+3) == 'd' && pageHTML.charAt(i+4) == 'e' && pageHTML.charAt(i+5) == 'r'  && pageHTML.charAt(i+6) == 's'  && pageHTML.charAt(i+8) == '>'){
					int seedStart = i + 9;
					int seedEnd = i + 9;
					while (pageHTML.charAt(seedEnd) != '<')
						seedEnd++;
					seed = (pageHTML.substring(seedStart, seedEnd));
				}
				
				if  (pageHTML.charAt(i) == 'l' && pageHTML.charAt(i+1) == 'e' && pageHTML.charAt(i+2) == 'e' && pageHTML.charAt(i+3) == 'c' && pageHTML.charAt(i+4) == 'h' && pageHTML.charAt(i+5) == 'e' && pageHTML.charAt(i+6) == 'r' && pageHTML.charAt(i+7) == 's' && pageHTML.charAt(i+8) == '"'){
					int leechStart = i + 10;
					int leechEnd = i + 10;
					while (pageHTML.charAt(leechEnd) != '<')
						leechEnd++;
					leech = (pageHTML.substring(leechStart, leechEnd));
				}
			}
			dataCache.add(size);
			dataCache.add(seed);
			dataCache.add(leech);
		
			dataCache.add(detailsPage[pageNumber]); // add the page number to the first location of our arrayList
			pageNumber++;
		}
		System.out.print("#");
		return dataCache;
	}
	
	public ArrayList <String> QualityFilter(ArrayList <String> dataCache){
		/*
		 * Quality Check Based on positive/negative rating takes the original cache and filters out bad results
		 * example @param: GetDataCache() || BuildDataCache("https://kat.ph/usearch/linkinpark%20in%20the%20end/")
		 */
		ArrayList<String> qualityList = dataCache; //use the original dataCache array list
		int pageNumber = 0;
		try{
			String[] detailsPage = DataCacheToArray(dataCache, "link");
			while (detailsPage[pageNumber] != null){
				String URI = detailsPage[pageNumber]; //store the URI in a local variable for convenience
				String pageHTML = super.GetWebPageGzipHTTP(URI); //pull down the html of the page
				int fakeCount = 0;
				int goodCount = 0;
				for ( int i = 0; i <pageHTML.length() ; i++){
					
					if  (pageHTML.charAt(i) == 'f'  && pageHTML.charAt(i+1) == 'a' && pageHTML.charAt(i+2) == 'k' && pageHTML.charAt(i+3) == 'e' && pageHTML.charAt(i+8) == 't' && pageHTML.charAt(i+16) == '>'){
						int fakeCountStart = i + 17;
						int fakeCountEnd = i + 18;
						while (pageHTML.charAt(fakeCountEnd) != '<')
							fakeCountEnd++;
						fakeCount = Integer.parseInt((pageHTML.substring(fakeCountStart, fakeCountEnd))); // get the integer value of the fakeCount (thumbs down)
					}
					if  (pageHTML.charAt(i) == 't'  && pageHTML.charAt(i+1) == 'h' && pageHTML.charAt(i+2) == 'n' && pageHTML.charAt(i+3) == 'x' && pageHTML.charAt(i+8) == 't' && pageHTML.charAt(i+16) == '>'){
						int goodCountStart = i + 17;
						int goodCountEnd = i + 18;
						while (pageHTML.charAt(goodCountEnd) != '<')
							goodCountEnd++;
						if (pageHTML.substring(goodCountStart, goodCountEnd).charAt(0) == '+') //parse out the + sign so these can be evaluated to integers
							goodCount = Integer.parseInt(pageHTML.substring(goodCountStart + 1, goodCountEnd));
						else
							goodCount = Integer.parseInt(pageHTML.substring(goodCountStart, goodCountEnd));
					}
					
			}
				if(fakeCount*10 >= goodCount || pageHTML.contains(".m4p")){
					
					int badStart =  qualityList.indexOf(URI) - 3; //shift back 3 since we want to delete from size which is the first entry
					//System.out.println("Bad Quality " + badStart+" -- removing"+"\n");
					qualityList.remove(badStart); //remove size
					qualityList.remove(badStart); //remove seeder
					qualityList.remove(badStart); //remove leecher
					qualityList.remove(badStart); //remove links
				}
				
					
			pageNumber++;
			//System.out.print("#");
			}
			return qualityList;
		}catch(Exception e){
			return null;
		}
	}
	
	public String GrepMagnetLink(String detailsPage){
		/*
		 * Given a link this method will find the download link and return it.
		 * example @param: "https://kat.ph/usearch/linkinpark%20in%20the%20end/"
		 */
		String torrentDownloadLink = null;
		boolean firstResult = false;
		try {
			String pageHTML = super.GetWebPageGzipHTTP(detailsPage);
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
	
	public ArrayList <String> GetDataCache(){
		/* 
		 * returns the current dataCache
		 * the BuildDataCache method must be called before this.
		 */
		return dataCache;
	}
	
	
//********************************************Cache Manipulation METHODS**********************************************************\\


	public String[] DataCacheToArray(ArrayList <String> dataCache, String dataField){
		/*
		 * @param: (ArrayList <String> dataCache, "link" || "seed" || "leech" || "size")
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
	
	public float[] SizeToFloat(ArrayList <String> queryResults){
		/*
		 * Conversion method used to convert <String> Size value to integer
		 */
		try{ 
			float[] sizeArray = new float[queryResults.size()/4];
			for (int i = 0; i < queryResults.size()/4; i++){
				sizeArray[i] = Float.parseFloat(DataCacheToArray(queryResults, "size")[i]);
			}
			System.out.print("#");
			return sizeArray;
		}catch (Exception e){
			return null;
		}
	}
	
	public int[] SeedToInt(ArrayList <String> queryResults){
		/*
		 * Conversion method used to convert <String> seeder value to integer
		 */
		try{
			int[] seedArray = new int[queryResults.size()/4];
			for (int i = 0; i < queryResults.size()/4; i++){
				seedArray[i] = Integer.parseInt(DataCacheToArray(queryResults, "seed")[i]);
			}
			System.out.print("#");
			return seedArray;
		}catch (Exception e){
			return null;
		}
		
	}
	
	public int[] LeechToInt(ArrayList <String> dataCache){
		/*
		 * Conversion method used to convert <String> leecher value to integer
		 */
		try{
			int[] leechArray = new int[dataCache.size()/4];
			for (int i = 0; i < dataCache.size()/4; i++){
				leechArray[i] = Integer.parseInt(DataCacheToArray(dataCache, "leech")[i]);
			}
			System.out.print("#");
			return leechArray;
		}catch (Exception e) {
			return null;
		}
	}
}
