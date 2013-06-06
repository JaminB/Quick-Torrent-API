package quickSearch.KAT;

public class KATSimpleSearch extends sites.KAT.KATRating {
/*
 * Takes a search term and a boolean (true == run qualityFilter)
 */
	String mediaType;
	public KATSimpleSearch(String query, String mediaType, boolean qualityCheck) {
		/*
		 * Set up your search.
		 * example @param "Stairway to heaven", true
		 */
		super(query, mediaType, qualityCheck);
		this.mediaType = mediaType;
	}	
	
	public String FindBestDownload(){
		/*
		 * Use Returns the best magnet link based on your query passed to the Constructor
		 */
		super.ConvertToArrays(super.GenerateQueryResults());
		String bestLink = super.GetBestLink(super.seedArray, super.leechArray, this.mediaType);
		return super.GrepMagnetLink(bestLink);
	}
}
