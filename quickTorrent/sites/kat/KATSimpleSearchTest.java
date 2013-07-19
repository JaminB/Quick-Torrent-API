package sites.kat;

import globals.Variables;

public class KATSimpleSearchTest {
	public static void main(String[] args) {
		KATSimpleSearch mySearch = new KATSimpleSearch("Bad Teacher (english)", "movie", true);
		System.out.println(mySearch.findBestDownload());
		System.out.println("All links searched: " + Variables.lastSearch);

	}
}
