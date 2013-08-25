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
	private int totalWords;
	private int unImportantWords;
	private int correctlySpelled;
	private List<String> queryList = new LinkedList<String>();
	private List<String> dictionaryList = new LinkedList<String>();
	
	private double h = 0; //heuristic used to find the best link
	
	private void setQueryList(String searchTerm) {
		int startPosition = 0;
		int endPosition = 0;
		for (int i = 0; i < searchTerm.length(); i++) {
		   if (searchTerm.charAt(i) == ' '){
			   endPosition = i;
			   queryList.add(searchTerm.substring(startPosition, endPosition).replace(" ", "").toLowerCase());
			   startPosition = endPosition;
			   
		   }
		}
		queryList.add(searchTerm.substring(startPosition).replace(" ", "").toLowerCase());
	}
	
	private boolean isInsignificantWord(String someWord){
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
	
	private boolean isInDictionary(String someWord){
		if (dictionaryList.indexOf(someWord) != -1)
			return true;
		return false;
		
	}
	
	private void setDictionaryList(String dictionaryFile) throws IOException{
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(dictionaryFile));
		String line = in.readLine();
		dictionaryList.clear();
		while(line != null){ // loop till you have no more lines
		    dictionaryList.add(line);
		    line = in.readLine(); // try to read another line
		}	
	}
	
	public void setQuerySize(String searchTerm){
		setQueryList(searchTerm);
		this.totalWords = queryList.size();
		queryList.clear();
	}
	
		
	
	public void setUnimportantWords(String searchTerm){
		setQueryList(searchTerm);
		int unImportantWords = 0;
		for(int i = 0; i < queryList.size(); i++)
			if(isInsignificantWord(queryList.get(i)))
				unImportantWords++;
		this.unImportantWords = unImportantWords;
		queryList.clear();
	}
	
	
	public void setCorrectlySpelled(String searchTerm) throws IOException {
		setQueryList(searchTerm);
		int correctlySpelled = 0;
		setDictionaryList("DictionaryList.txt");
		for (int i = 0; i < queryList.size(); i++){
			if (isInDictionary(queryList.get(i)))
					correctlySpelled++;
		}
		this.correctlySpelled = correctlySpelled;
		queryList.clear();
		dictionaryList.clear();
	}
	
	public void setH(String searchTerm) throws IOException{
		setUnimportantWords(searchTerm);
		setQuerySize(searchTerm);
		setCorrectlySpelled(searchTerm);
		double totalWord = (double) getQuerySize();
		double unImportantWords = (double) getUnimportantWords();
		double correctlySpelled = (double) getCorrectlySpelled();
		if (totalWords <= 7 && totalWords >= 2){
			this.h = (correctlySpelled - (.5 * unImportantWords) )/totalWords;
		}
		else if (totalWords > 7)
			this.h = ((correctlySpelled - (.5 * unImportantWords))/(totalWords-7))/totalWords;
		
		
		else{
			this.h = 0;
		}
	}
	
	public int getQuerySize(){
		return this.totalWords;
	}
	public int getUnimportantWords(){
		return this.unImportantWords;
	}
	
	public int getCorrectlySpelled(){
		return this.correctlySpelled;
	}
	
	public double getH(){
		return 100*this.h;
	}
	
	

}
