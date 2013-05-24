package sites.Pirate;

import java.util.ArrayList;


public class PirateRating extends PirateGrep {
	/*
	 * Finds the best Piratebay torrent based on data gleaned using the PirateGrep Class
	 */
	public float[] sizeArray;
	public int[] seedArray;
	public int[] leechArray;
	public String[] linkArray;
	
	private String[] torrentPages;
	private boolean qc = true;
	
	public PirateRating(String query, boolean qualityCheck){
		/*
		 * Constructor, two arguments the search query and a boolean, true == scan comments, false == do not scan comments
		 */
		String currentSearch = CreateParsedURI(query); //returns the parsed URI query
		String searchResultHTML = super.GetWebPageHTTP(currentSearch); //pulls down the html from the URI given
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
	
	public String GetBestLink(int[] seedArray, int[] leechArray){
		/*
		 * Determines the best link to pull the torrent from.
		 */
		try{
		String bestChoice = null;
		int greatestDifference = 0;
			for (int i = 0; i < seedArray.length; i++){
				if (seedArray[i] - leechArray[i] > greatestDifference)
					if (sizeArray[i] < 10){
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
	

