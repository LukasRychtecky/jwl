package com.jwl.integration.convertor;

import java.util.HashSet;
import java.util.Set;

import com.jwl.business.article.AttachmentTO;
import com.jwl.integration.entity.Attachment;

public class AttachmentConvertor {

	public static Set<AttachmentTO> convertFromEntity(
			Set<Attachment> attachments) {
		Set<AttachmentTO> attachmentsTO = new HashSet<AttachmentTO>();
		for (Attachment att : attachments) {
			AttachmentTO attTO = new AttachmentTO(att.getTitle(), att
					.getOriginalFileName(), att.getUniqueFileName(), att
					.getDescription());
			attachmentsTO.add(attTO);
		}
		return attachmentsTO;
	}

}
