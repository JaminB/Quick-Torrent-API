package sites.pirate;
import sites.kat.KATGrep;
public class PirateSimpleSearch extends sites.pirate.PirateRating {

	KATGrep magnetLink = new KATGrep();
	/*
	 * Takes a search term and a boolean (true == run qualityFilter)
	 */
	String mediaType;
	public PirateSimpleSearch(String query, String mediaType, boolean qualityCheck) {
		/*
		 * Set up your search.
		 * example @param "Stairway to heaven", "music", true
		 */
		
		super(query, mediaType, qualityCheck);
		this.mediaType = mediaType;
	}	
	
	public String findBestDownload(){
		/*
		 * Use Returns the best magnet link based on your query passed to the Constructor
		 */
		super.convertToArrays(super.generateQueryResults());
		String bestLink = super.getBestLink(super.seedArray, super.leechArray, this.mediaType);
		String bestLinkHTML = conn.getWebPageHTTP(bestLink);
		return magnetLink.grepMagnetLink(bestLinkHTML);
	}
}
