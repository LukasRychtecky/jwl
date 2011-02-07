package com.jwl.integration.convertor;

import java.util.HashSet;
import java.util.Set;
import com.jwl.integration.entity.Tag;
import java.util.Collection;

public class TagConvertor {

	public static Set<String> toStringSet(Collection<Tag> tags){
		Set<String> stringTags = new HashSet<String>();
		for (Tag tag : tags) {
			stringTags.add(tag.getName());
		}
		return stringTags;
	}	
	
	public static Set<Tag> toTagSet(Set<String> stringTags){
		Set<Tag> tags = new HashSet<Tag>();
		for (String tagName : stringTags) {
			tags.add(new Tag(tagName));
		}
		return tags;
	}
}
