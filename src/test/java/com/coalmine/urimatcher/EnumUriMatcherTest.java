package com.coalmine.urimatcher;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.net.Uri;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class EnumUriMatcherTest {
	private static final String AUTHORITY = "authority";


	@Test(expected=IllegalStateException.class)
	public void testAddUriWithoutAuthorityOrDefaultAuthority() {
		EnumUriMatcher<MatchType> matcher = new EnumUriMatcher<EnumUriMatcherTest.MatchType>(MatchType.class);

		matcher.addUri("path", MatchType.FOO);

		fail("Adding a URI without an authority (or default authority) should throw an exception unless a default has been specified.");
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


