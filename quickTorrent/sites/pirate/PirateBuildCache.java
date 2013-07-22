package sites.pirate;

import globals.Variables;
import connect.GetHTTP;
import java.util.ArrayList;

public class PirateBuildCache extends PirateGrep {
	ArrayList<String> dataCache = new ArrayList<String>(); //stores the data detailsURI data (seeders, leechers, size, links)
	GetHTTP conn = new GetHTTP();
	public ArrayList<String> buildDataCache(String[] detailsURI){
		/*
		 *  Given a string array of Detail Pages will return the stats of each page in an array list
		 *  example @param GrepdetailsURI("http://thepiratebay.sx/search/linkin%20park%20in%20the%20end/0/99/100")
		 */
		dataCache = new ArrayList<String>(); //rebuild cache
		int pageNumber = 0;
		while (detailsURI[pageNumber] != null){
			String URI = detailsURI[pageNumber]; //store the URI in a local variable for convenience
			String pageHTML = conn.getWebPageHTTP(URI); //pull down the html of the page
			dataCache.add(grepSize(pageHTML));
			dataCache.add(grepSeeds(pageHTML));
			dataCache.add(grepLeeches(pageHTML));
			dataCache.add(detailsURI[pageNumber]); // add the page number to the first location of our arrayList
			pageNumber++;
		}
		System.out.print("#");
		Variables.cache = dataCache;
		return dataCache;
	}
	
	public ArrayList <String> qualityFilter(ArrayList <String> dataCache ){ 
		/*
		 * Quality Check Based on comments takes the original cache and filters out bad results
		 * example @param getDataCache() || BuildDataCache("http://thepiratebay.sx/search/linkin%20park%20in%20the%20end/0/99/100")
		 */
		ArrayList<String> qualityList = dataCache; //use the original dataCache array list
		int pageNumber = 0;
		try{
			String[] detailsPage = dataCacheToArray(dataCache, "link");
				while (detailsPage[pageNumber] != null){
					String URI = detailsPage[pageNumber]; //store the URI in a local variable for convenience
					String pageHTML = conn.getWebPageHTTP(URI);
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
	
	public ArrayList <String> getDataCache(){
		/* 
		 * returns the current dataCache
		 * the BuildDataCache method must be called before this.
		 */
		return dataCache;
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
		//********************************************Conversion METHODS**********************************************************\\
		
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
