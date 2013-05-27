package converters;

import quickSearch.KAT.KATSimpleSearch;
import quickSearch.Pirate.PirateSimpleSearch;

public class ConvertTest {
	
	public static void main(String[] args) {
		PirateSimpleSearch Pirate = new PirateSimpleSearch("In the end Linkin Park", false);
		KATSimpleSearch KAT = new KATSimpleSearch("In the end Linkin Park", true);
		MagnetToTorrent myPirateSearch = new MagnetToTorrent(Pirate.FindBestDownload());
		System.out.println(myPirateSearch.GrepHash());
		MagnetToTorrent myKATSearch = new MagnetToTorrent(KAT.FindBestDownload());
		System.out.println(myKATSearch.GrepHash());

	}

}
