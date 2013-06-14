package converters;

import converters.Torcache.MagnetToTorrent;
import quickSearch.KAT.KATSimpleSearch;
import quickSearch.Pirate.PirateSimpleSearch;
public class ConvertTest {
	
	public static void main(String[] args) {
		PirateSimpleSearch Pirate = new PirateSimpleSearch("Return of the king", "movie", true);
		KATSimpleSearch KAT = new KATSimpleSearch("Return of the king", "movie", true);
		MagnetToTorrent myPirateConversion = new MagnetToTorrent(Pirate.FindBestDownload());
		MagnetToTorrent myKATConversion = new MagnetToTorrent(KAT.FindBestDownload());
		String PirateHash = myPirateConversion.GetHash();
		String PiratePage = (myPirateConversion.GetTorrentLink(PirateHash));
		String KATHash = myKATConversion.GetHash();
		String KATPage = myKATConversion.GetTorrentLink(KATHash);
		System.out.println(PirateHash+"\n"+PiratePage+"\n"+KATHash+"\n"+KATPage);
		
	}
}
