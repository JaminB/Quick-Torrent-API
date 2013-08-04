package converters.torcache;

public class MagnetToTorrentTest {
	public static void main(String[] args) {
		MagnetToTorrent myConversion = new MagnetToTorrent("magnet:?xt=urn:btih:A89E0A483C1E8C9720CA7BD12D03631039670DEB&dn=oblivion+2013+720p+brrip+x264+ac3+jyk&tr=udp%3A%2F%2Ffr33domtracker.h33t.com%3A3310%2Fannounce&tr=udp%3A%2F%2Fopen.demonii.com%3A1337");
		System.out.println(myConversion.getTorrentLink(myConversion.getHash()));
		System.out.println(myConversion.getTorrentFilePreview(myConversion.getTorrentLink(myConversion.getHash())));

	}

}
