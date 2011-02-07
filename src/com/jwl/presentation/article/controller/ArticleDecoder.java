package com.jwl.presentation.article.controller;

import java.util.Map;
import javax.faces.component.UIComponent;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.controller.JWLDecoder;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.global.WikiURLParser;
import javax.naming.NoPermissionException;

public class ArticleDecoder extends JWLDecoder {

	public ArticleDecoder(Map<String, String> map, UIComponent component,
			IFacade facade) {
		super(map, component, facade, JWLElements.EDIT_FORM.id);
	}

	@Override
	public void processDecode() throws ModelException, NoPermissionException {
		if (isArticleComponentRequest()) {
			ArticleTO article = getFilledArticle();

			if (this.isArticleEditRequest()) {
				String stringId = getMapValue(JWLElements.EDIT_ID);
				ArticleId articleId = new ArticleId(Integer.parseInt(stringId));
				article.setId(articleId);
				this.facade.updateArticle(article);

			} else if (this.isArticleSaveRequest()) {
				this.facade.createArticle(article);
			}
		}
	}

	private ArticleTO getFilledArticle() {
		ArticleTO article = new ArticleTO();
		article.setText(getMapValue(JWLElements.EDIT_TEXT));
		article.setTitle(getMapValue(JWLElements.EDIT_TITLE));

		String[] tags = this.getMapValue(JWLElements.EDIT_TAGS).split(",");
		for (int i = 0; i < tags.length; i++) {
			article.addTag(tags[i]);
		}

		article.setChangeNote(getMapValue(JWLElements.EDIT_CHANGE_NOTE));
		article.setEditor(findEditor());
		return article;
	}

	/**
	 * Find name of logged user. If his name is empty, return his IP address.
	 * 
	 * @return user name or IP address
	 */
	private String findEditor() {
		String editor = getLogedUser();
		if (editor.isEmpty()) {
			WikiURLParser parser = new WikiURLParser();
			editor = parser.getUserIP();
		}
		return editor;
	}

	private boolean isArticleComponentRequest() {
		return isArticleEditRequest() || isArticleSaveRequest();
	}

	private boolean isArticleEditRequest() {
		return map.containsKey(this.getFullKey(JWLElements.EDIT_SAVE.id));
	}

	private boolean isArticleSaveRequest() {
		return map.containsKey(this.getFullKey(JWLElements.CREATE_SAVE.id));
	}
}
