package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.core.Linker;

/**
 * Class for editing article.
 * 
 * @author ostatnickyjiri
 */
public class EncodeEdit extends AbstractEncodeEdit {

	protected ArticleId id;
	private ArticleTO article;

	public EncodeEdit(ArticleId id) {
		this.id = id;
	}

	public EncodeEdit(ArticleTO article, Linker linker) {
		this.article = article;
		this.id = article.getId();
		super.linker = linker;
	}

	@Override
	protected void encodeContent(List<UIComponent> formData) throws IOException, ModelException {
		if (article == null) {
			article = facade.getArticle(id);
		}
		super.encodeEdit(formData, article);
		formData.add(this.encodeHiddenArticleId());
	}

	private UIComponent encodeHiddenArticleId() {
		HtmlInputHidden hiddenArticleId = new HtmlInputHidden();
		hiddenArticleId.setId(JWLElements.EDIT_ID.id);
		hiddenArticleId.setValue(id.getId());
		return hiddenArticleId;
	}
}
