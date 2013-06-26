package quickSearch.kat;

public class KATTest {
	public static void main(String[] args) {
		KATSimpleSearch mySearch = new KATSimpleSearch("Grave Digger", "music", false);
		System.out.println(mySearch.findBestDownload());

	}

}
