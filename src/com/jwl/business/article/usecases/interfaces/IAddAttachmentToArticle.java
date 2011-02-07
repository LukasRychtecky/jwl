package com.jwl.business.article.usecases.interfaces;

import com.jwl.business.article.AttachmentTO;

public interface IAddAttachmentToArticle {
	public void addAttachment(int articleId,AttachmentTO attachment);
}
