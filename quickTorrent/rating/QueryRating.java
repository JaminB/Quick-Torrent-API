package rating;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cache.*;

public class QueryRating {
	BuildCache buildCache = new BuildCache();
	AccessCache accessCache = new AccessCache();
	
	int h = 0; //heuristic used to find the best link
	int g = 100; //goal state
	
	public int getQuerySize(String searchTerm){
		int index = searchTerm.indexOf(" ");
		int count = 0;
		while (index != -1) {
		    count++;
		    searchTerm = searchTerm.substring(index + 1);
		    index = searchTerm.indexOf(" ");
		}
		return count + 1;
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
				"without", "i", "me", "us","we", "him", "her", "it", "she", "you", "they", "them", "he"
				};
		for(int i = 0; i < ignoreList.length; i++)
			if(someWord.equals(ignoreList[i]))
					return true;
		return false;
		
	}
	public int getQueryImportantWords(String searchTerm){
		//List<String> searchTerms = new LinkedList<String>(); // create a new list
		int significantWordCount = 0;
		int startIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < searchTerm.length(); i++){
			endIndex++;
			if(searchTerm.charAt(i) == ' '){
				String untestedWord = (searchTerm.substring(startIndex, endIndex).replaceAll("\\s", "").toLowerCase());
				if(!insignificantWord(untestedWord))
					significantWordCount++;
				
				startIndex = endIndex;
			}
		}
		String untestedWord = (searchTerm.substring(startIndex, searchTerm.length()).replaceAll("\\s", "").toLowerCase());
		if(!insignificantWord(untestedWord))
			significantWordCount++;
		
		return significantWordCount;
	}
	
	public int getCorrectlySpelled(String searchTerm) throws IOException{
		BufferedReader read = new BufferedReader(new FileReader("DictionaryList.txt"));
		List<String> dictionary = new LinkedList<String>();
		String line = read.readLine(); 
		while(line != null){
		    dictionary.add(line);
		    line = read.readLine();
		}
		return g; //not working yet
	}

}
