package tests;
import rating.*;

public class GenericRatingTest {

	public static void main(String[] args) {
		RateTrackers rating = new RateTrackers();
		String searchPhrase = "nickleback";
		rating.setQueryDetailLevel(searchPhrase);
		rating.setSearchQuality(searchPhrase);
	}
	
	

}
