
package cache;

import java.util.List;

public class AccessCache {
    
    private List<String> filter;
    private List<String> size;
    private List<String> seeds;
    private List<String> leeches;
    private List<String> links;
    private List<String> magnetLinks;
    
    public List<String> getFilter() {
        return filter;
    }
    public void setFilter(List<String> filter) {
        this.filter = filter;
    }
    public List<String> getSize() {
        return size;
    }
    public void setSize(List<String> size) {
        this.size = size;
    }
    public List<String> getSeeds() {
        return seeds;
    }
    public void setSeeds(List<String> seeds) {
        this.seeds = seeds;
    }
    public List<String> getLeeches() {
        return leeches;
    }
    public void setLeeches(List<String> leeches) {
        this.leeches = leeches;
    }
    public List<String> getLinks() {
        return links;
    }
    public void setLinks(List<String> links) {
        this.links = links;
    }
    public List<String> getMagnetLinks() {
        return magnetLinks;
    }
    public void setMagnetLinks(List<String> magnetLinks) {
        this.magnetLinks = magnetLinks;
    }
}
