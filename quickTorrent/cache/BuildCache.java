package cache;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import sites.pirate.PirateGrep;
import sites.kat.KATGrep;
import connect.GetGzippedHTTP;
import connect.GetHTTP;

public class BuildCache {
	 PirateGrep pirateSearch = new PirateGrep();
	 KATGrep katSearch = new KATGrep();
	 GetHTTP HTTPConnect = new GetHTTP();
	 GetGzippedHTTP gzippedHTTPConnect = new GetGzippedHTTP();
	
	private ArrayList<ArrayList<String>> fullCache(ArrayList<String> filter,  ArrayList<String> size, ArrayList<String> seeds, ArrayList<String> leeches, ArrayList<String> links, ArrayList<String> magnetLinks){
		ArrayList<ArrayList<String>> fullCache = new ArrayList<ArrayList<String>>() ;
		fullCache.add(filter);
		fullCache.add(size);
		fullCache.add(seeds);
		fullCache.add(leeches);
		fullCache.add(links);
		fullCache.add(magnetLinks);
		
		return fullCache ;
	}
	
	private String filter(String wordList, String detailsPage ) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(wordList));
		List<String> lines = new LinkedList<String>(); // create a new list
		String line = in.readLine(); // read a line at a time
		while(line != null){ // loop till you have no more lines
		    //System.out.println(line);
		    lines.add(line);
		    line = in.readLine(); // try to read another line
		}
		in.close();
		//System.out.println(lines);
		//System.out.println(detailsPage);
		for (int i = 0; i < lines.size(); i++)
			if(detailsPage.toLowerCase().contains(lines.get(i)))
				return "true";
		return "false";
		
	
		
	}
	
	 
	public ArrayList<ArrayList<String>> buildCache(String[] detailsURIs, String cacheType) throws IOException{
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<String> filter =  new ArrayList<String>();
		ArrayList<String> magnetLinks =  new ArrayList<String>();
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
				filter.add(filter("bad_words.txt",pageHTML));
				links.add(detailsURIs[pageNumber]);
				magnetLinks.add(katSearch.grepMagnetLink(pageHTML));
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
				filter.add(filter("bad_words.txt",pageHTML));
				links.add(detailsURIs[pageNumber]);
				magnetLinks.add(katSearch.grepMagnetLink(pageHTML));
				size.add(katSearch.grepSize(pageHTML));
				seeds.add(katSearch.grepSeeds(pageHTML));
				leeches.add(katSearch.grepLeeches(pageHTML));
				pageNumber++;
			}
			
		}
		else
			return null;	
		return fullCache(filter, size, seeds, leeches, links, magnetLinks);
	}
	
}
