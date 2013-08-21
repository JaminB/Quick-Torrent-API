package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class WordListDeDuper {
	//Efficient algorithm for finding the unique words that exist in one list but not another.
	//Used for building large wordlists
	List<String> list1 = new LinkedList<String>();
	List<String> list2 = new LinkedList<String>();
	static List<String> list3 = new LinkedList<String>();
	
	private boolean doesExist(String someWord, List compareList){
		if (compareList.indexOf(someWord) != -1)
			return true;
		return false;
	}
	
	public void wordDeDuper(String wordList1, String wordList2 ) throws IOException{
		BufferedReader read1 = new BufferedReader(new FileReader(wordList1));
		BufferedReader read2 = new BufferedReader(new FileReader(wordList2));
		String line1 = read1.readLine(); 
		while(line1 != null){
		    list1.add(line1);
		    line1 = read1.readLine();
		}
		read1.close();
		String line2 = read2.readLine();
		while(line2 != null){
		    list2.add(line2);
		    line2 = read2.readLine();
		}
		read2.close();
		int uniqueWords = 0;
		for (int i = 0; i <list1.size(); i++){
			if (!doesExist(list1.get(i), list2)){
				list3.add(list1.get(i));
				uniqueWords++;
				if (uniqueWords%1000 == 0)
					System.out.println("Found over " + uniqueWords + " unique words and still working");
					
			}
		}
		System.out.println("Found " + uniqueWords +" that exist in "+ wordList1 + ", but not do not exist in " +wordList2+".");
		System.out.println("To merge copy the words from the unique list to " + wordList2);
	}

	public static void main(String args[]) throws IOException{
		WordListDeDuper deDuplicator  = new WordListDeDuper();
		deDuplicator.wordDeDuper("1.txt", "2.txt");
		PrintWriter output = new PrintWriter("uniqueList.txt");
		for (int i = 0; i < list3.size(); i++){
			output.println(list3.get(i));
		}
		output.close();
		System.out.println("Written to uniqueList.txt." );
		
			
	}

}
