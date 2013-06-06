package sites.KAT;

import java.util.ArrayList;


public class KATRating extends KATGrep {
	/*
	 * Finds the best Piratebay torrent based on data gleaned using the PirateGrep Class
	 */
	public float[] sizeArray;
	public int[] seedArray;
	public int[] leechArray;
	public String[] linkArray;
	
	private String[] torrentPages;
	private boolean qc = true;
	
	public KATRating(String query, String mediaType, boolean qualityCheck){
		/*
		 * Constructor, two arguments the search query and a boolean, true == scan comments, false == do not scan comments
		 */
		String currentSearch = CreateParsedURI(query, mediaType); //returns the parsed URI query
		String searchResultHTML = super.GetWebPageGzipHTTP(currentSearch); //pulls down the html from the URI given
		torrentPages = super.GrepDetailsPage(searchResultHTML); //finds each torrent page link
		qc = qualityCheck;
	}
	
	public ArrayList <String> GenerateQueryResults(){ 
		/*
		 * Uses methods from PageGrep and PageFunc to generate a list of candidate torrents
		 * This method must be called after the constructor
		 */
		boolean qualityCheck = this.qc;
		super.BuildDataCache(this.torrentPages);
		if (qualityCheck) return super.QualityFilter(super.GetDataCache()); //run it through a quality check (takes a bit longer)
		else return super.GetDataCache(); //skip quality check
	}
	
	public void ConvertToArrays(ArrayList <String> queryResults){
		/*
		 * Converts the ArrayList <String> into the separate Arrays of the correct datatype
		 * The easiest way to implement this method is passing the GenerateQueryResults() directly to it.
		 */
		sizeArray = super.SizeToFloat(queryResults);
		seedArray = super.SeedToInt(queryResults);
		leechArray = super.LeechToInt(queryResults);
		linkArray = super.DataCacheToArray(queryResults, "link");
	}
	
	public String GetBestLink(int[] seedArray, int[] leechArray, String mediaType){
		/*
		 * Determines the best link to pull the torrent from.
		 */
		int sizeMinimum;
		int sizeMaximum;
		if (mediaType.toLowerCase().equals("movie") || mediaType.toLowerCase().equals("movies")){
			sizeMinimum = 300;
			sizeMaximum = 999;
		}
		else{
			sizeMinimum = 3;
			sizeMaximum = 11;
		}
		System.out.println("\nSize Minimum: " + sizeMinimum);
			
		try{
		String bestChoice = null;
		int greatestDifference = 0;
			for (int i = 0; i < seedArray.length; i++){
				if (seedArray[i] - leechArray[i] > greatestDifference)
					if (sizeArray[i] > sizeMinimum && sizeArray[i] < sizeMaximum){
						greatestDifference = seedArray[i] -leechArray[i];
						bestChoice = linkArray[i];
					}
			}
			return bestChoice;
		}catch (Exception e){
			return null;
		}
	}
}
	

