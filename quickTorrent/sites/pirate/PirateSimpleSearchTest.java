package sites.pirate;

import globals.Variables;

public class PirateSimpleSearchTest {
	public static void main(String[] args) {
		PirateSimpleSearch mySearch = new PirateSimpleSearch("The Unforgiven Metallica II", "music", true);
		System.out.println(mySearch.findBestDownload());
		System.out.println("All links searched: " + Variables.lastSearch);
	}

}
