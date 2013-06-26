package quickSearch.pirate;

import globals.Variables;
import quickSearch.pirate.PirateSimpleSearch;

public class PirateTest {
	public static void main(String[] args) {
		PirateSimpleSearch mySearch = new PirateSimpleSearch("In the end", "music", true);
		System.out.println(mySearch.findBestDownload());
		System.out.println("All links searched: " + Variables.lastSearch);
	}

}
