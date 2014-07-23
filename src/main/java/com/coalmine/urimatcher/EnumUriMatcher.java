package com.coalmine.urimatcher;

import android.content.UriMatcher;
import android.net.Uri;

/** Wraps an Android UriMatcher in order to allow users to associate URI matches with enumeration
 * values, rather than arbitrary integer constants.  This reduces the amount of boilerplate code
 * needed to do URI matching, provides type safety, allows for useful compile-time warnings (e.g.,
 * missing cases in a switch statement) and eliminates the potential for errors while
 * manually defining "code" values. */
public class EnumUriMatcher<MatchEnumeration extends Enum<?>> {
	private UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	private String defaultAuthority;
	private MatchEnumeration[] enumerationValues;
	private MatchEnumeration noMatchValue;


	/** Creates a matcher for the given enumeration type, without a default authority.  When adding
	 * matches, use {@link #addMatch(String,String,Enum)} or call {@link #setDefaultAuthority(String)}
	 * prior to calling {@link #addMatch(String,Enum)}. */
	public EnumUriMatcher(Class<MatchEnumeration> enumerationClass) {
		enumerationValues = enumerationClass.getEnumConstants();
	}

	/** Creates a matcher for the given enumeration type, providing a default value returned by
	 * {@link #match(Uri)} when no match is found, and without a default authority.  When adding
	 * matches, use {@link #addMatch(String,String,Enum)} or call
	 * {@link #setDefaultAuthority(String)} prior to calling {@link #addMatch(String,Enum)}. */
	public EnumUriMatcher(Class<MatchEnumeration> enumerationClass, MatchEnumeration noMatchValue) {
		this(enumerationClass);

		this.noMatchValue = noMatchValue;
	}

	/** Creates a matcher for the given enumeration type, also specifying the authority to use when
	 * adding a match without specifying the authority. */
	public EnumUriMatcher(Class<MatchEnumeration> enumerationClass, String defaultAuthority) {
		this(enumerationClass);

		this.defaultAuthority = defaultAuthority;
	}

	/** Creates a matcher for the given enumeration type, also specifying the authority to
	 * use when adding a match without specifying the authority, and the value returned by
	 * {@link #match(Uri)} when no match is found. */
	public EnumUriMatcher(Class<MatchEnumeration> enumerationClass, String defaultAuthority, MatchEnumeration noMatchValue) {
		this(enumerationClass);

		this.defaultAuthority = defaultAuthority;
		this.noMatchValue = noMatchValue;
	}

	/** Adds a URI match for the given authority and path, which will return the given enumeration
	 * value when matched. */
	public void addUri(String authority, String uriPath, MatchEnumeration matchEnum) {
		matcher.addURI(authority, uriPath, matchEnum.ordinal());
	}

	/**
	 * Adds a URI match for the given path and the default authority, which will return the given enumeration
	 * value when matched.
	 * @throws IllegalStateException thrown if called with no default authority set.
	 */
	public void addUri(String uriPath, MatchEnumeration matchEnumeration) {
		if(defaultAuthority==null) {
			throw new IllegalStateException("A default authority is needed before calling addMatch(String,EnumType).");
		}

		addUri(defaultAuthority, uriPath, matchEnumeration);
	}

	/** @return The enumeration that was associated with the given URI's pattern, as determined by Android's {@link UriMatcher} class. */
	public MatchEnumeration match(Uri uri) {
		if(uri == null) {
			return null;
		}

		int matchValue = matcher.match(uri);

		return matchValue == UriMatcher.NO_MATCH?
				noMatchValue :
				enumerationValues[matchValue];
	}

	/** Sets the authority used when adding a URI match using {@link #addMatch(String, Enum)}. */
	public void setDefaultAuthority(String defaultAuthority) {
		this.defaultAuthority = defaultAuthority;
	}

	/** Sets the value returned by {@link #match(Uri)} when no match is found for a URI. */
	public void setNoMatchValue(MatchEnumeration noMatchValue) {
		this.noMatchValue = noMatchValue;
	}
}


