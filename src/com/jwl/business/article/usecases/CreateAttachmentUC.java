package com.jwl.business.article.usecases;

import java.util.Set;

import com.jwl.integration.cache.ArticleHome;
import com.jwl.integration.cache.AttachmentHome;
import com.jwl.business.article.AttachmentTO;
import com.jwl.business.article.usecases.interfaces.ICreateAttachmentUC;
import com.jwl.business.exceptions.InsufficientArticleDataException;
import com.jwl.integration.entity.Article;
import com.jwl.integration.entity.Attachment;

public class CreateAttachmentUC extends AbstractUC implements ICreateAttachmentUC{
	
	AttachmentHome attachmentHome;
	ArticleHome articleHome;
	
	public CreateAttachmentUC(){
		this.attachmentHome = new AttachmentHome();
		this.articleHome = new ArticleHome();
	}
	
	@Override
	public Integer createAttachment(AttachmentTO attachmentTO)
			throws InsufficientArticleDataException {
		this.assertAttachmentValidInput(attachmentTO);
		
		Article article = getArticle(attachmentTO.getArticleTitle());
		
		Attachment attachment = attachmentHome.getInstance();
		
		attachment.setTitle(attachmentTO.getTitle());
		attachment.setOriginalFileName(attachmentTO.getOriginalName());
		attachment.setUniqueFileName(attachmentTO.getUniqueName());
		attachment.setDescription(attachmentTO.getDescription());

		Set<Article> articles = attachment.getArticles();
		articles.add(article);
		attachment.setArticles(articles);
		
		attachmentHome.save();
		
		return attachment.getId();
	}

	private Article getArticle(String title)
			throws InsufficientArticleDataException {
		Article article = articleHome.getArticleByTitle(title);
		checkIsSet(article, "Article called '" + title + "' does not exist.");
		return article;
	}

	private void assertAttachmentValidInput(AttachmentTO attachmentTO)
			throws InsufficientArticleDataException {
		checkIsSet(attachmentTO, "Attachment is not set.");
		checkIsSet(attachmentTO.getTitle(), "Attachment title is not set.");
		checkIsSet(attachmentTO.getArticleTitle(), "Article is not set.");
		checkIsSet(attachmentTO.getOriginalName(),
				"Attachment original name is not set.");
		checkIsSet(attachmentTO.getUniqueName(),
				"Attachment unique name is not set.");
	}

}
