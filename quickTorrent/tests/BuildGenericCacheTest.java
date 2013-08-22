package tests;
import java.io.IOException;

import sites.pirate.PirateGrep;
import cache.AccessCache;
import cache.BuildCache;
import connect.GetGzippedHTTP;
import connect.GetHTTP;

public class BuildGenericCacheTest {
	
	public static void main(String args[]) throws IOException{
		GetHTTP getHTML = new GetHTTP();
		GetGzippedHTTP getGzippedHTML = new GetGzippedHTTP();
		String searchTerm = "linkin park in the end";
	
		PirateGrep pirateSearchPages = new PirateGrep();
		String pirateSearchURI = pirateSearchPages.createParsedURI(searchTerm, "music");
		String[] pirateDetailsURIs = pirateSearchPages.grepDetailsURI(getHTML.getWebPageHTTP(pirateSearchURI));
		BuildCache pirateCache = new BuildCache();
		AccessCache cache = pirateCache.buildCache(pirateDetailsURIs, "Pirate");
		System.out.println(cache.getFilter());
		
		/*
		KATGrep KATSearchPages = new KATGrep();
		String KATSearchURI = KATSearchPages.createParsedURI(searchTerm, "music");
		String[] KATDetailsURIs = KATSearchPages.grepDetailsURI(getGzippedHTML.getWebPageGzipHTTP(KATSearchURI));
		BuildCache KATCache = new BuildCache();
		//System.out.println(KATCache.buildCache(KATDetailsURIs, "KAT"));
		AccessCache cache = new AccessCache();
		System.out.println(cache.getMagnetLinks(KATCache.buildCache(KATDetailsURIs, "KAT")));
		 */
		
		
		
	}
	

}
