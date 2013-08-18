package tests;
import sites.pirate.*;
import sites.kat.*;
import connect.*;
import cache.BuildGenericCache;

public class BuildGenericCacheTest {
	
	public static void main(String args[]){
		GetHTTP getHTML = new GetHTTP();
		String searchTerm = "linkin park";
		PirateGrep searchPages = new PirateGrep();
		String searchURI = searchPages.createParsedURI(searchTerm, "music");
		System.out.println(searchURI);
		String[] detailsURIs = searchPages.grepDetailsURI(getHTML.getWebPageHTTP(searchURI));
		System.out.println(detailsURIs[0]);
		BuildGenericCache cache = new BuildGenericCache();
		System.out.println(cache.buildCache(detailsURIs, "pirate"));
	}
	

}
