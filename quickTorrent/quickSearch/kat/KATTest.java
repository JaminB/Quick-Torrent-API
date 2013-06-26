package quickSearch.kat;

public class KATTest {
	public static void main(String[] args) {
		KATSimpleSearch mySearch = new KATSimpleSearch("The Lord Of the Rings fellowship of the rings", "movie", true);
		System.out.println(mySearch.findBestDownload());

	}

}
