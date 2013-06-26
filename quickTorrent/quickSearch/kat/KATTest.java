package quickSearch.kat;

import globals.Variables;

public class KATTest {
	public static void main(String[] args) {
		KATSimpleSearch mySearch = new KATSimpleSearch("Lord of the rings", "movies", true);
		System.out.println(mySearch.findBestDownload());
		System.out.println("All links searched: " + Variables.lastSearch);

	}
}
