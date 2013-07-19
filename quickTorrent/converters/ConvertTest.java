package converters;

import converters.torcache.MagnetToTorrent;
import sites.kat.KATSimpleSearch;
import sites.pirate.PirateSimpleSearch;
public class ConvertTest {
	
	public static void main(String[] args) {
		PirateSimpleSearch Pirate = new PirateSimpleSearch("Return of the king", "movie", true);
		KATSimpleSearch KAT = new KATSimpleSearch("Return of the king", "movie", true);
		MagnetToTorrent myPirateConversion = new MagnetToTorrent(Pirate.findBestDownload());
		MagnetToTorrent myKATConversion = new MagnetToTorrent(KAT.findBestDownload());
		String PirateHash = myPirateConversion.getHash();
		String PiratePage = (myPirateConversion.getTorrentLink(PirateHash));
		String KATHash = myKATConversion.getHash();
		String KATPage = myKATConversion.getTorrentLink(KATHash);
		System.out.println(PirateHash+"\n"+PiratePage+"\n"+KATHash+"\n"+KATPage);
		
	}
}
