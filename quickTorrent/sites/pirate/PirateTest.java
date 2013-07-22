package sites.pirate;
import java.util.Scanner;
public class PirateTest {

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
		sites.pirate.PirateRating mySearch = new sites.pirate.PirateRating(sTitle, mediaType, qualityCheck);
		mySearch.convertToArrays(mySearch.generateQueryResults());
		String bestLink = mySearch.getBestLink(mySearch.seedArray, mySearch.leechArray, mediaType);
		System.out.println("\nResults: " + mySearch.getDataCache());
		System.out.println("\nBest Link: " + bestLink);
		System.out.println("\nDownload Link: "+ mySearch.grepMagnetLink(bestLink));
	}
}
