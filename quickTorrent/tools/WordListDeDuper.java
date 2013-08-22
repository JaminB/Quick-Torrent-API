package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class WordListDeDuper {
	//Efficient algorithm for finding the unique words that exist in one list but not another.
	//Used for building large wordlists
	public Set<String> wordDeDuper(String wordList1, String wordList2 ) throws IOException{
	    Set<String> sortedSet = new TreeSet<String>();
		BufferedReader read1 = new BufferedReader(new FileReader(wordList1));
		BufferedReader read2 = new BufferedReader(new FileReader(wordList2));
		String line1 = read1.readLine(); 
		while(line1 != null){
		    sortedSet.add(line1);
		    line1 = read1.readLine();
		}
		read1.close();
		String line2 = read2.readLine();
		while(line2 != null){
		    sortedSet.add(line2);
		    line2 = read2.readLine();
		}
		read2.close();
		int uniqueWords = sortedSet.size();
		System.out.println("Found over " + uniqueWords + " unique words");
		return sortedSet;
	}

	public static void main(String args[]) throws IOException{
		WordListDeDuper deDuplicator  = new WordListDeDuper();
		Collection<String> uniqueWords = deDuplicator.wordDeDuper("1.txt", "2.txt");
		PrintWriter output = new PrintWriter("uniqueList.txt");
		for (String word : uniqueWords) {
		    output.println(word);
		}
		output.close();
		System.out.println("Written to uniqueList.txt." );
	}
}
