package tests;
import java.io.IOException;

import sites.pirate.*;
import sites.kat.*;
import connect.*;
import cache.*;

public class BuildGenericCacheTest {
	
	public static void main(String args[]) throws IOException{
		GetHTTP getHTML = new GetHTTP();
		GetGzippedHTTP getGzippedHTML = new GetGzippedHTTP();
		String searchTerm = "linkin park in the end";
	
		PirateGrep pirateSearchPages = new PirateGrep();
		String pirateSearchURI = pirateSearchPages.createParsedURI(searchTerm, "music");
		String[] pirateDetailsURIs = pirateSearchPages.grepDetailsURI(getHTML.getWebPageHTTP(pirateSearchURI));
		BuildCache pirateCache = new BuildCache();
		AccessCache cache = new AccessCache();
		//System.out.println(pirateCache.buildCache(pirateDetailsURIs, "pirate"));
		System.out.println(cache.getFilter(pirateCache.buildCache(pirateDetailsURIs, "Pirate")));
		
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
