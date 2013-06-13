package quickSearch.KAT;

public class KATTest {
	public static void main(String[] args) {
		KATSimpleSearch mySearch = new KATSimpleSearch("Pirates of the caribbean", "music", true);
		System.out.println(mySearch.FindBestDownload());

	}

}
