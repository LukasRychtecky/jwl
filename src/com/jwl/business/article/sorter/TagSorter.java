package com.jwl.business.article.sorter;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import com.jwl.util.TagComparator;

/**
 * Class for sorting of tags.
 * 
 * @author ostatnickyjiri
 */
public class TagSorter {
	
	/**
	 * It sorts set of tags to alphabetic order.
	 * 
	 * @param tags set of 
	 * @return
	 */
	public static Set<String> order(Set<String> tags) {
		if (null == tags)
			return Collections.emptySet();
		
		TreeSet<String> sortedTags = new TreeSet<String>(new TagComparator());
		sortedTags.addAll(tags);
		return sortedTags;
	}
	
}
