package tests;
import sites.pirate.*;
import sites.kat.*;
import connect.*;
import cache.BuildGenericCache;

public class BuildGenericCacheTest {
	
	public static void main(String args[]){
		GetHTTP getHTML = new GetHTTP();
		GetGzippedHTTP getGzippedHTML = new GetGzippedHTTP();
		String searchTerm = "linkin park";
		
		/*PirateGrep pirateSearchPages = new PirateGrep();
		String pirateSearchURI = pirateSearchPages.createParsedURI(searchTerm, "music");
		String[] pirateDetailsURIs = pirateSearchPages.grepDetailsURI(getHTML.getWebPageHTTP(pirateSearchURI));
		BuildGenericCache pirateCache = new BuildGenericCache();
		System.out.println(pirateCache.buildCache(pirateDetailsURIs, "pirate"));
		*/
		
		KATGrep KATSearchPages = new KATGrep();
		String KATSearchURI = KATSearchPages.createParsedURI(searchTerm, "music");
		String[] KATDetailsURIs = KATSearchPages.grepDetailsURI(getGzippedHTML.getWebPageGzipHTTP(KATSearchURI));
		BuildGenericCache KATCache = new BuildGenericCache();
		System.out.println(KATCache.buildCache(KATDetailsURIs, "KAT"));
	
		
		
		
	}
	

}
