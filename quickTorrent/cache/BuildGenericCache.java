package cache;
import java.util.ArrayList;
import sites.pirate.PirateGrep;
import sites.kat.KATGrep;
import connect.GetGzippedHTTP;
import connect.GetHTTP;

public class BuildGenericCache {
	 PirateGrep pirateSearch = new PirateGrep();
	 KATGrep katSearch = new KATGrep();
	 GetHTTP HTTPConnect = new GetHTTP();
	 GetGzippedHTTP gzippedHTTPConnect = new GetGzippedHTTP();
	
	private ArrayList<ArrayList<String>> fullCache(ArrayList<String> detailsPage, ArrayList<String> size, ArrayList<String> links, ArrayList<String> seeds, ArrayList<String> leeches){
		ArrayList<ArrayList<String>> fullCache = new ArrayList<ArrayList<String>>() ;
		fullCache.add(links);
		fullCache.add(detailsPage);
		fullCache.add(size);
		fullCache.add(seeds);
		fullCache.add(leeches);
		return fullCache ;
	}
	 
	public ArrayList<ArrayList<String>> buildCache(String[] detailsURIs, String cacheType){
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<String> detailsPage =  new ArrayList<String>();
		ArrayList<String> size =  new ArrayList<String>();
		ArrayList<String> seeds =  new ArrayList<String>();
		ArrayList<String> leeches =  new ArrayList<String>();
		String URI;
		String pageHTML;
		if (cacheType.toLowerCase().equals("pirate")){
			int pageNumber = 0;
			while(detailsURIs[pageNumber] != null){
				URI = detailsURIs[pageNumber]; //store the URI in a local variable for convenience
				pageHTML = HTTPConnect.getWebPageHTTP(URI); //pull down the html of the page
				links.add(detailsURIs[pageNumber]);
				detailsPage.add(pageHTML);
				size.add(pirateSearch.grepSize(pageHTML));
				seeds.add(pirateSearch.grepSeeds(pageHTML));
				leeches.add(pirateSearch.grepLeeches(pageHTML));
				pageNumber++;
			}
		}
		else if (cacheType.toLowerCase().equals("kat")){
			int pageNumber = 0;
			while(detailsURIs[pageNumber] != null){
				URI = detailsURIs[pageNumber]; //store the URI in a local variable for convenience
				pageHTML = gzippedHTTPConnect.getWebPageGzipHTTP(URI); //pull down the html of the page
				links.add(detailsURIs[pageNumber]);
				detailsPage.add(pageHTML);
				size.add(katSearch.grepSize(pageHTML));
				seeds.add(katSearch.grepSeeds(pageHTML));
				leeches.add(katSearch.grepLeeches(pageHTML));
				pageNumber++;
			}
			
		}
		else
			return null;	
		return fullCache(links, detailsPage, size, seeds, leeches);
	}
	
}
