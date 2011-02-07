package com.jwl.integration.cache;

import java.util.List;

import com.jwl.integration.entity.Tag;

public class TagHome extends EntityHome<Tag> {

	private static final long serialVersionUID = -7352404533083573829L;
	private String byName = "Tag.getByName";

	public Tag getTagFromName(String name) {
		String[] params = new String[]{name};
		List<?> results = this.entityManagerDao.doNamedQuery(byName, params);
		if (results.isEmpty()) {
			return null;
		}
		Tag tag = (Tag) results.get(0);
		if (tag != null) {
			this.setInstance(tag);
			return tag;
		} else {
			return null;
		}
	}
}
