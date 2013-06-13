package converters;

import quickSearch.KAT.KATSimpleSearch;
import quickSearch.Pirate.PirateSimpleSearch;
public class ConvertTest {
	
	public static void main(String[] args) {
		PirateSimpleSearch Pirate = new PirateSimpleSearch("Serenity", "movie", true);
		KATSimpleSearch KAT = new KATSimpleSearch("Serenity", "movie", true);
		
		MagnetToTorrent myPirateSearch = new MagnetToTorrent(Pirate.FindBestDownload());
		String PirateHash = myPirateSearch.GrepHash();
		String PiratePage = (myPirateSearch.CreateParsedURI(PirateHash));
		String PirateUniqueID =  myPirateSearch.GrepUniqueID(PiratePage);
		String PirateTorrentLink = myPirateSearch.GetTorrentLink(PirateHash, PirateUniqueID);
		String PirateTorrentFile = myPirateSearch.GetTorrentFilePreview(PirateTorrentLink);
		System.out.println("\nLocated Unique ID: " + PirateUniqueID);
		System.out.println("Isolated Hash: " + PirateHash);
		System.out.println("Download Link: " + PirateTorrentLink);
		System.out.println("Torrent File: " + PirateTorrentFile);
		 
		MagnetToTorrent myKATSearch = new MagnetToTorrent(KAT.FindBestDownload());
		String KATHash = myKATSearch.GrepHash();
		String KATPage = (myKATSearch.CreateParsedURI(KATHash));
		String KATUniqueID = myKATSearch.GrepUniqueID(KATPage);
		String KATTorrentLink = myPirateSearch.GetTorrentLink(KATHash, KATUniqueID);
		String KATTorrentFile = myPirateSearch.GetTorrentFilePreview(KATTorrentLink);
		System.out.println("\nLocated Unique ID: " + KATUniqueID);
		System.out.println("Isolated Hash: " + KATHash);
		System.out.println("Download Link: " + KATTorrentLink);
		System.out.println("Torrent File: " + KATTorrentFile);
	}
}
