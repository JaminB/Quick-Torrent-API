package quickSearch.pirate;

import quickSearch.pirate.PirateSimpleSearch;

public class PirateTest {
	public static void main(String[] args) {
		PirateSimpleSearch mySearch = new PirateSimpleSearch("Lord Of The Rings III", "movie", true);
		System.out.println(mySearch.findBestDownload());

	}

}
