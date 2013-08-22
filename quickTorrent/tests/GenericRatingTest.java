package tests;
import rating.*;

public class GenericRatingTest {

	public static void main(String[] args) {
		QueryRating rating = new QueryRating();
		String searchPhrase = "in the end linkin park";
		System.out.println(rating.getQuerySize(searchPhrase));
		System.out.println(rating.getQueryImportantWords(searchPhrase));
	}
	
	

}