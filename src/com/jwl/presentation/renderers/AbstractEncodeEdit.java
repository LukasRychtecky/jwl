package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputTextarea;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlActionForm;
import com.jwl.presentation.html.HtmlDiv;

/**
 * Abstract class for rendering form of article manipulation.
 * 
 * @author ostatnickyjiri
 */
public abstract class AbstractEncodeEdit extends AbstractEncoder {
	
	public AbstractEncodeEdit() {
		super();
	}

	protected HtmlActionForm encodedArticleForm(JWLActions action) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.DO, action.id);
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.EDIT_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.linker.buildLink(params));

		form.getChildren().addAll(this.encodedFormContent());

		return form;
	}

	protected HtmlDiv encodedSimilarArcitlesDiv() {
		HtmlDiv div = new HtmlDiv();
		div.setId(JWLElements.EDIT_SIMILAR_ARTICLE_DIV.id);
		return div;
		
	}
	
	protected abstract List<UIComponent> encodedFormContent();
	
	protected List<UIComponent> encodedCommonContent(ArticleTO article) {
		List<UIComponent> components = new ArrayList<UIComponent>();
		
		components.add(this.encodedLabelForText());
		components.add(this.encodedText(article.getText()));

		components.add(this.encodedLabelForTags());
		StringBuilder tags = new StringBuilder();
		for (String tag : article.getTags()) {
			tags.append(tag).append(", ");
		}

		String tagAsString = "";
		if (tags.length() > 0) {
			tagAsString = tags.substring(0, tags.length() - 1);
		}
		components.add(this.encodedTags(tagAsString));

		components.add(this.encodedLabelForChangeNote());
		components.add(this.encodedChangeNote());

		if (!super.isUserLogged()) {
			components.add(this.encodedUnknownUserMessage());
		}
		return components;
	}
	
	private UIComponent encodedLabelForText() {
		return getHtmlLabelComponent(JWLElements.EDIT_TEXT, JWLStyleClass.EDIT_LABEL_FOR_TEXT);
	}

	private UIComponent encodedText(String text) {
		HtmlInputTextarea textArea = new HtmlInputTextarea();
		textArea.setRows(30);
		textArea.setCols(100);
		textArea.setValue(text);
		textArea.setId(JWLElements.EDIT_TEXT.id);
		return textArea;
	}

	private UIComponent encodedLabelForTags() {
		return getHtmlLabelComponent(JWLElements.EDIT_TAGS, JWLStyleClass.EDIT_LABEL_FOR_TAGS);
	}

	private UIComponent encodedTags(String tags) {
		return getHtmlInputComponent(JWLElements.EDIT_TAGS, tags, 
				JWLStyleClass.EDIT_TAGS_INPUT);
	}

	private UIComponent encodedLabelForChangeNote() {
		return getHtmlLabelComponent(JWLElements.EDIT_CHANGE_NOTE,
				JWLStyleClass.EDIT_LABEL_FOR_CHANGE_NOTE);
	}

	private UIComponent encodedChangeNote() {
		return getHtmlInputComponent(JWLElements.EDIT_CHANGE_NOTE, "",  
				JWLStyleClass.EDIT_CHANGE_NOTE_INPUT);
	}

	private UIComponent encodedUnknownUserMessage() {
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass(JWLStyleClass.EDIT_MESSAGE_WARNING);
		div.getChildren().add(getHtmlText("Your user name is unknown! " +
				"It will record your IP adress."));
		return div;
	}

}
