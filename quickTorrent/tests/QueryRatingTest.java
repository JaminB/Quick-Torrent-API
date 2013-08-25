package tests;
import java.io.IOException;

import rating.*;

public class QueryRatingTest {

	public static void main(String[] args) throws IOException {
		QueryRating rating = new QueryRating();
		String searchPhrase = "The Wall Pink floyd";
	rating.setUnimportantWords(searchPhrase);
	rating.setQuerySize(searchPhrase);
	rating.setCorrectlySpelled(searchPhrase);
	rating.setH(searchPhrase);
	
	System.out.println("Total Words: "+rating.getQuerySize());
	System.out.println("Known Words: "+rating.getCorrectlySpelled());
	System.out.println("Unimportant Words: "+rating.getUnimportantWords());
	System.out.println("Query Rating (1-100): " +rating.getH());
	
	}
}