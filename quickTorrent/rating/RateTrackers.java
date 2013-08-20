package rating;
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
	
	public void setSearchLinkAccuracy(String searchTerm, String detailPage){
		
	}


}
