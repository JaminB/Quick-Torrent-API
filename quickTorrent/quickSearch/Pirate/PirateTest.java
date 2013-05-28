package quickSearch.Pirate;

import quickSearch.Pirate.PirateSimpleSearch;

public class PirateTest {
	public static void main(String[] args) {
		PirateSimpleSearch mySearch = new PirateSimpleSearch("Riverside Agnes", true);
		System.out.println(mySearch.FindBestDownload());

	}

}
