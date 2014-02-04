package com.coalmine.urimatcher;

import static org.junit.Assert.*;

import org.junit.Test;

import android.net.Uri;

public class EnumUriMatcherTest {
	private static final String AUTHORITY = "authority";


	@Test(expected=IllegalStateException.class)
	public void testAddUriWithoutAuthority() {
		EnumUriMatcher<MatchType> matcher = new EnumUriMatcher<EnumUriMatcherTest.MatchType>(MatchType.class);

		matcher.addUri("path", MatchType.FOO);

		fail("Adding a URI without an authority should throw an exception unless a default has been specified.");
	}

	@Test
	public void testMatch() {
		EnumUriMatcher<MatchType> matcher = new EnumUriMatcher<MatchType>(MatchType.class, AUTHORITY);
		matcher.addUri("foos", MatchType.FOOS);
		matcher.addUri("foos/#", MatchType.FOO);
		matcher.addUri("bars", MatchType.BARS);

		assertEquals(MatchType.FOOS,
				matcher.match(Uri.parse("content://"+AUTHORITY+"/foos")));

		assertEquals(MatchType.FOO,
				matcher.match(Uri.parse("content://"+AUTHORITY+"/foos/1")));

		assertEquals(MatchType.BARS,
				matcher.match(Uri.parse("content://"+AUTHORITY+"/bars")));
	}


	private enum MatchType {
		FOOS, FOO,
		BARS;
	}
}


