package com.jwl.business.article.usecases.interfaces;

import com.jwl.business.article.AttachmentTO;
import com.jwl.business.exceptions.InsufficientArticleDataException;

public interface ICreateAttachmentUC {
	
	public Integer createAttachment(AttachmentTO attachment)
			throws InsufficientArticleDataException;
}
