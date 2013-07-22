package sites.kat;

import connect.GetGzippedHTTP;
import globals.Variables;

import java.util.ArrayList;

public class KATBuildCache extends KATGrep {
	
	GetGzippedHTTP conn = new GetGzippedHTTP();
	
	ArrayList<String> dataCache = new ArrayList<String>(); //stores the data detailsPage data (seeders, leechers, size, links)
	
	public ArrayList<String> buildDataCache(String[] detailsPage){
		/*
		 *  Given a list of KAT torrent address returns the seeds, leeches and links in an ArrayList
		 *  example @param: GrepDetailsPage("https://kickass.to/usearch/linkinpark%20in%20the%20end/")
		 */
		dataCache = new ArrayList<String>();
		int pageNumber = 0;
		while (detailsPage[pageNumber] != null){
			String URI = detailsPage[pageNumber]; //store the URI in a local variable for convenience
			String pageHTML = conn.getWebPageGzipHTTP(URI); //pull down the html of the page
			
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
	
	public ArrayList <String> qualityFilter(ArrayList <String> dataCache){
		/*
		 * Quality Check Based on positive/negative rating takes the original cache and filters out bad results
		 * example @param: GetDataCache() || BuildDataCache("https://kickass.to/usearch/linkinpark%20in%20the%20end/")
		 */
		ArrayList<String> qualityList = dataCache; //use the original dataCache array list
		int pageNumber = 0;
		try{
			String[] detailsPage = dataCacheToArray(dataCache, "link");
			while (detailsPage[pageNumber] != null){
				String URI = detailsPage[pageNumber]; //store the URI in a local variable for convenience
				String pageHTML = conn.getWebPageGzipHTTP(URI); //pull down the html of the page
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
