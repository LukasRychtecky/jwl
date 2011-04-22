package com.jwl.business.article.sorter;

import java.util.Comparator;

/**
 * Comparator to sort tags. This class is for TreeSet, which sorts strings by
 * default so that upper case are preferred. And we want no-matter-upper-case
 * sorting.
 * 
 * @author ostatnickyjiri
 */
public class TagComparator implements Comparator<String> {

	@Override
	public int compare(String tag1, String tag2) {

		return tag1.compareToIgnoreCase(tag2);

	}

}
