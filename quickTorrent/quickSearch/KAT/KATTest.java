package quickSearch.KAT;

public class KATTest {
	public static void main(String[] args) {
		KATSimpleSearch mySearch = new KATSimpleSearch("Linkin Park in the End", "music", false);
		System.out.println(mySearch.FindBestDownload());

	}

}
