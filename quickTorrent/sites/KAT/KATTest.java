package sites.KAT;
import java.util.Scanner;
public class KATTest {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.print("Music/Movie: ");
		String mediaType = input.nextLine().toLowerCase();
		System.out.print("Title: ");
		String sTitle = input.nextLine();
		System.out.print("Filter out bad results (Y/n): ");
		boolean qualityCheck = false;
		if(input.next().toLowerCase().equals("y")){
			System.out.println("> Filter ON.");
			qualityCheck = true;
		}
		sites.KAT.KATRating mySearch = new sites.KAT.KATRating(sTitle, mediaType, qualityCheck);
		mySearch.ConvertToArrays(mySearch.GenerateQueryResults());
		String bestLink = mySearch.GetBestLink(mySearch.seedArray, mySearch.leechArray, mediaType);
		System.out.println("\nResults: " + mySearch.GetDataCache());
		System.out.println("\nBest Link: " + bestLink);
		System.out.println("\nDownload Link: "+ mySearch.GrepMagnetLink(bestLink));
	}
}
