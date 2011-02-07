package com.jwl.presentation.component.renderer;

import com.jwl.business.exceptions.ModelException;
import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.component.enumerations.JWLElements;

/**
 * Class for editing article.
 * 
 * @author ostatnickyjiri
 */
public class EncodeEdit extends AbstractEncodeEdit {

	protected ArticleId id;

	public EncodeEdit(IFacade facade, ArticleId id, boolean existUserName) {
		super(facade, existUserName);
		this.id = id;
	}

	@Override
	protected void encodeContent(List<UIComponent> formData) throws IOException, ModelException {
		ArticleTO article = null;
		article = facade.getArticle(id);
		encodeCommonContent(formData, article, existUserName,
				JWLElements.EDIT_SAVE);
		formData.add(this.encodeHiddenArticleId());
	}

	private UIComponent encodeHiddenArticleId() {
		HtmlInputHidden hiddenArticleId = new HtmlInputHidden();
		hiddenArticleId.setId(JWLElements.EDIT_ID.id);
		hiddenArticleId.setValue(id.getId());
		return hiddenArticleId;
	}
}
