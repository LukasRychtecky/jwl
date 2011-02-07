package com.jwl.integration.cache;

import java.util.List;

import com.jwl.integration.cache.EntityHome;
import com.jwl.integration.entity.Attachment;

/**
 * 
 * @author Petr Janouch
 *
 */
public class AttachmentHome extends EntityHome<Attachment>{

	private static final long serialVersionUID = -267973769121992288L;
	private String byTitleQuery = "Attachment.getByTitle";
	
	public Attachment getAttachmentFromTitle(String title) {
		String[] params = new String[]{title};		
		List<?> results = this.entityManagerDao.doNamedQuery(byTitleQuery, params);
		if (results.size() == 0) {
			return null;
		}
		Attachment attachment = (Attachment) results.get(0);
		if (attachment != null) {
			this.setInstance(attachment);
			return attachment;
		} else {
			return null;
		}
	}
}
