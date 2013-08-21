package rating;
import java.util.LinkedList;
import java.util.List;

import cache.*;

public class RateTrackers {
	BuildCache buildCache = new BuildCache();
	AccessCache accessCache = new AccessCache();
	
	int h = 0; //heuristic used to find the best link
	int g = 100; //goal state
	double linkDetailLevel = 0;
	public void setLinkDetailLevel(String searchTerm){
		int index = searchTerm.indexOf(" ");
		float count = 0;
		while (index != -1) {
		    count++;
		    searchTerm = searchTerm.substring(index + 1);
		    index = searchTerm.indexOf(" ");
		}
		linkDetailLevel = ((count + 1)*(count + 1))*1.3; 
		System.out.println(count + 1);
		System.out.println(linkDetailLevel);
	}
	
	private boolean insignificantWord(String someWord){
		String[] ignoreList = {
				"a","an","the",
				"and", "but", "for", "nor", "or", "yet", 
				"am", "is", "are", "was","were", "be", "being", "is", 
				"have", "has","had","do","does","did", "shell", "will", "should","would","may","might","must","can", "could",
				"about", "above", "across", "after", "against", "around", "at", "before", "behind", "below", "beneath", "beside",
				"besides", "between", "beyond", "by", "down", "during", "except", "for", "from", "in","inside","into","like","near","of",
				"off","on","out","outside","over","since","through","throughout","till","to","toward","under","until","up","upon","with",
				"without"
				};
		for(int i = 0; i < ignoreList.length; i++)
			if(someWord.equals(ignoreList[i]))
					return true;
		return false;
		
	}
	public void setSearchLinkAccuracy(String searchTerm, String detailPage){
		//List<String> searchTerms = new LinkedList<String>(); // create a new list
		int insignificantWordCount = 0;
		int significantWordCount = 0;
		int startIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < searchTerm.length(); i++){
			endIndex++;
			if(searchTerm.charAt(i) == ' '){
				String untestedWord = (searchTerm.substring(startIndex, endIndex).replaceAll("\\s", "").toLowerCase());
				if(insignificantWord(untestedWord)){
					insignificantWordCount++;
				}
					//searchTerms.add(untestedWord);
				else
					significantWordCount++;
				startIndex = endIndex;
			}
		}
		String untestedWord = (searchTerm.substring(startIndex, searchTerm.length()).replaceAll("\\s", "").toLowerCase());
		if(insignificantWord(untestedWord)){
			insignificantWordCount++;
		}
			//searchTerms.add(untestedWord);
		else
			significantWordCount++;
		
		System.out.println("Insignificant words: " + insignificantWordCount + "" +"\nSignificant words: "+ significantWordCount );
			
	}


}
