package sites.pirate;

import globals.Variables;
import connect.GetHTTP;

import java.util.ArrayList;

public class PirateBuildCache extends PirateGrep {
	ArrayList<String> dataCache = new ArrayList<String>(); //stores the data detailsPage data (seeders, leechers, size, links)
	GetHTTP conn = new GetHTTP();
	public ArrayList<String> buildDataCache(String[] detailsPage){
		/*
		 *  Given a string array of Detail Pages will return the stats of each page in an array list
		 *  example @param GrepDetailsPage("http://thepiratebay.sx/search/linkin%20park%20in%20the%20end/0/99/100")
		 */
		dataCache = new ArrayList<String>(); //rebuild cache
		int pageNumber = 0;
		while (detailsPage[pageNumber] != null){
			String URI = detailsPage[pageNumber]; //store the URI in a local variable for convenience
			String pageHTML = conn.getWebPageHTTP(URI); //pull down the html of the page
			dataCache.add(grepSize(pageHTML));
			dataCache.add(grepSeeds(pageHTML));
			dataCache.add(grepLeeches(pageHTML));
			dataCache.add(detailsPage[pageNumber]); // add the page number to the first location of our arrayList
			pageNumber++;
		}
		System.out.print("#");
		Variables.cache = dataCache;
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
}
