package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.component.enumerations.JWLElements;

/**
 * Class for encoding create state
 * 
 * @author ostatnickyjiri
 */
public class EncodeCreate extends AbstractEncodeEdit {

	public EncodeCreate(IFacade facade, boolean existUserName) {
		super(facade, existUserName);
	}

	@Override
	protected void encodeContent(List<UIComponent> formData) throws IOException {
		ArticleTO article = new ArticleTO();
		encodeCommonContent(formData, article, existUserName,
				JWLElements.CREATE_SAVE);
	}

}
