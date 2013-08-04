package sites.kat;

import globals.Variables;

public class KATSimpleSearchTest {
	public static void main(String[] args) {
		KATSimpleSearch mySearch = new KATSimpleSearch("Oblivion", "movie", true);
		System.out.print("Processing: ");
		System.out.println("\nBest Magnet Link: " + mySearch.findBestDownload());
		System.out.println("Download Size Range (music): " + globals.Variables.musicSizeMin + " , " + globals.Variables.musicSizeMax);
		System.out.println("Download Size Range (movie): " + globals.Variables.movieSizeMin + " , " + globals.Variables.movieSizeMax);
		System.out.println("All links searched: " + Variables.lastSearch);
		System.out.println("Cache: " + Variables.cache);
	}
}
