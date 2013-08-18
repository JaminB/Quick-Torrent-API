package tests;

import java.io.IOException;

import connect.DownloadFile;


public class DownloadTest {

	public static void main(String[] args) throws IOException {
		DownloadFile myTorrentLink = new DownloadFile("http://torcache.net/torrent/fd6b56d0a5deb520823265948a5378fde0b9840a.torrent", "c:\\users\\jamin\\Desktop\\LordOfTheRings");
		myTorrentLink.getFileDownload();

	}

}
