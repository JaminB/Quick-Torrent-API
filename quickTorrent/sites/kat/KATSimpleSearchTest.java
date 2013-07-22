package sites.kat;

import globals.Variables;

public class KATSimpleSearchTest {
	public static void main(String[] args) {
		KATSimpleSearch mySearch = new KATSimpleSearch("Prometheus", "movie", true);
		System.out.print("Processing: ");
		System.out.println("Best Magnet Link: " + mySearch.findBestDownload());
		System.out.println("All links searched: " + Variables.lastSearch);
		System.out.println("Cache: " + Variables.cache);
	}
}
