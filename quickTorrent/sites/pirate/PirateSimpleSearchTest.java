package sites.pirate;

import globals.Variables;

public class PirateSimpleSearchTest {
	public static void main(String[] args) {
		PirateSimpleSearch mySearch = new PirateSimpleSearch("Prometheus", "movie", true);
		System.out.print("Processing: ");
		System.out.println("Best Magnet Link: " + mySearch.findBestDownload());
		System.out.println("All links searched: " + Variables.lastSearch);
		System.out.println("Cache: " + Variables.cache);
	}
}
