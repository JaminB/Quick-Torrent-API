
package cache;

import java.util.ArrayList;

public class AccessCache {

	
	public ArrayList<String> getFilter(ArrayList<ArrayList<String>> fullCache){
		return fullCache.get(0);
	}
	public ArrayList<String> getSize(ArrayList<ArrayList<String>> fullCache){
		return fullCache.get(1);
	}
	
	public ArrayList<String> getSeeds(ArrayList<ArrayList<String>> fullCache){
		return fullCache.get(2);
	}
	
	public ArrayList<String> getLeeches(ArrayList<ArrayList<String>> fullCache){
		return fullCache.get(3);
	}
	
	public ArrayList<String> getLinks(ArrayList<ArrayList<String>> fullCache){
		return fullCache.get(4);
	}
	
	public ArrayList<String> getMagnetLinks(ArrayList<ArrayList<String>> fullCache){
		return fullCache.get(5);
	}

}
