package com.coalmine.urimatcher;

import android.content.UriMatcher;
import android.net.Uri;

/** Wraps an Android UriMatcher so as to allow users to associate URI matches with enumeration
 * values, rather than arbitrary integer constants.  This reduces the amount of boilerplate code
 * needed to do URI matching, provides type safety, allows for useful compile-time warnings (e.g.,
 * missing cases in a switch statement) and eliminates the potential for errors while
 * manually defining "code" values. */
public class EnumUriMatcher<EnumType extends Enum<?>> {
	private UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	private String defaultAuthority;
	private EnumType[] enumValues;


	/** Creates a matcher for the given enumeration type, without a default authority.  When adding
	 * matches, use {@link #addMatch(String,String,Enum)} or call {@link #setDefaultAuthority(String)}
	 * prior to calling {@link #addMatch(String,Enum)}. */
	public EnumUriMatcher(Class<EnumType> clazz) {
		enumValues = clazz.getEnumConstants();
	}

	/** Creates a matcher for the given enumeration type, also specifying the authority to use when
	 * adding a match without specifying the authority. */
	public EnumUriMatcher(Class<EnumType> clazz, String defaultAuthority) {
		this(clazz);

		this.defaultAuthority = defaultAuthority;
	}

	/** Adds a URI match for the given authority and path, which will return the given enumeration
	 * value when matched. */
	public void addUri(String authority, String uriPath, EnumType matchEnum) {
		matcher.addURI(authority, uriPath, matchEnum.ordinal());
	}

	/**
	 * Adds a URI match for the given path and the default authority, which will return the given enumeration
	 * value when matched.
	 * @throws IllegalStateException thrown if called with no default authority set.
	 */
	public void addUri(String uriPath, EnumType matchEnum) {
		if(defaultAuthority==null) {
			throw new IllegalStateException("A default authority is needed before calling addMatch(String,EnumType).");
		}

		addUri(defaultAuthority, uriPath, matchEnum);
	}

	public EnumType match(Uri uri) {
		if(uri == null) {
			return null;
		}
		
		int matchValue = matcher.match(uri);
		
		return matchValue == UriMatcher.NO_MATCH?
				null :
					enumValues[matchValue];
	}

	/** Sets the authority used when adding a URI match using {@link #addMatch(String, Enum)}. */
	public void setDefaultAuthority(String defaultAuthority) {
		this.defaultAuthority = defaultAuthority;
	}
}


