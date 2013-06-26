package quickSearch.pirate;

import quickSearch.pirate.PirateSimpleSearch;

public class PirateTest {
	public static void main(String[] args) {
		PirateSimpleSearch mySearch = new PirateSimpleSearch("In the end", "music", true);
		System.out.println(mySearch.findBestDownload());

	}

}
