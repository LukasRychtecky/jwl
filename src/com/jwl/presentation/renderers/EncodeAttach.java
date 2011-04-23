package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.components.core.AbstractComponent;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLContextKey;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlActionForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlInputFile;
import com.jwl.presentation.url.WikiURLParser;

public class EncodeAttach extends AbstractEncoder {

	private ArticleTO article;
	
	public EncodeAttach() {
		super();
		this.article = (ArticleTO) context.getAttributes().get(JWLContextKey.ARTICLE);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(this.encodedForm());
		return components;
	}

	private HtmlActionForm encodedForm() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
		params.put(JWLURLParams.ARTICLE_TITLE, article.getTitle());
		params.put(JWLURLParams.REDIRECT_TARGET, parser.getCurrentPage());
		params.put(JWLURLParams.DO, JWLActions.FILE_UPLOAD.id);
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FILE_FORM.id);
		form.setEnctype("multipart/form-data");
		
		form.setAction(this.linker.buildLink(
				AbstractComponent.JWL_UPLOAD_FILE_PAGE, params));

		List<UIComponent> formData = form.getChildren();

		formData.add(this.encodedLabelForFileName());
		formData.add(this.encodedFileName());

		formData.add(this.encodedLabelForFileInput());
		formData.add(this.encodedFileInput());

		formData.add(this.encodedLabelForFileDescription());
		formData.add(this.encodedFileDescription());

		formData.add(this.encodedSubmit());
		formData.add(this.encodedHiddenArticleTitle());
		
		return form;
	}

	private UIComponent encodedLabelForFileName() {
		return getHtmlLabelComponent(JWLElements.FILE_TITLE,
				JWLStyleClass.ATTACH_LABEL_FOR_FILE_NAME);
	}

	private HtmlDiv encodedFileName() {
		return getHtmlInputComponent(JWLElements.FILE_TITLE, "", 
				JWLStyleClass.ATTACH_FILE_NAME);
	}

	private UIComponent encodedLabelForFileInput() {
		return getHtmlLabelComponent(JWLElements.FILE_ITEM,
				JWLStyleClass.ATTACH_LABEL_FOR_FILE);
	}

	private HtmlDiv encodedFileInput() {
		HtmlDiv div = new HtmlDiv();
		div.setStyleClass(JWLStyleClass.ATTACH_FILE);
		
		HtmlInputFile fileInput = new HtmlInputFile();
		fileInput.setId(JWLElements.FILE_ITEM.id);
		
		div.addChildren(fileInput);
		return div;
	}

	private UIComponent encodedLabelForFileDescription() {
		return getHtmlLabelComponent(JWLElements.FILE_DESCRIPTION,
				JWLStyleClass.ATTACH_LABEL_FOR_FILE_DESCRIPTION);
	}

	private HtmlDiv encodedFileDescription() {
		return getHtmlInputComponent(JWLElements.FILE_DESCRIPTION, "", 
				JWLStyleClass.ATTACH_FILE_DESCRIPTION);
	}

	private HtmlDiv encodedSubmit() {
		return getHtmlSubmitComponent(JWLElements.FILE_SAVE,
				JWLStyleClass.ATTACH_INPUT_SUBMIT);
	}

	private HtmlInputHidden encodedHiddenArticleTitle() {
		HtmlInputHidden articleTitle = new HtmlInputHidden();
		articleTitle.setId(JWLElements.FILE_ARTICLE_TITLE.id);
		articleTitle.setValue(this.getArticleTitle());
		return articleTitle;
	}

	private String getArticleTitle() {
		WikiURLParser parser = new WikiURLParser();
		return parser.getArticleTitle();
	}

}