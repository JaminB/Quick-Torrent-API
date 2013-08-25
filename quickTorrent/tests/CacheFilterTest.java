package tests;

import java.io.IOException;
//toggle BuildCache.filter to public to test this class

import cache.BuildCache;

public class CacheFilterTest {

	public static void main(String[] args) throws IOException {
		BuildCache filter = new BuildCache();
		System.out.println(filter.filter("bad_words.txt", "virus"));

	}

}

