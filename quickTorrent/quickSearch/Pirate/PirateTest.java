package quickSearch.Pirate;

import quickSearch.Pirate.PirateSimpleSearch;

public class PirateTest {
	public static void main(String[] args) {
		PirateSimpleSearch mySearch = new PirateSimpleSearch("Linkin Park In the End", true);
		System.out.println(mySearch.FindBestDownload());

	}

}
