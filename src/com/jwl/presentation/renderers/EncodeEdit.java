package com.jwl.presentation.renderers;

import static com.jwl.presentation.enumerations.JWLElements.EDIT_TITLE;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLContextKey;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.html.HtmlDiv;

/**
 * Class for editing article.
 * 
 * @author ostatnickyjiri
 */
public class EncodeEdit extends AbstractEncodeEdit {

	private ArticleTO article;
	
	public EncodeEdit() {
		super();
		this.article = (ArticleTO) context.getAttributes().get(JWLContextKey.ARTICLE);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(this.encodedArticleForm(JWLActions.ARTICLE_UPDATE));
		return components;
	}
	
	@Override
	protected List<UIComponent> encodedFormContent() {
		List<UIComponent> components = new ArrayList<UIComponent>();
		
		components.add(this.encodedLabelForTitle());
		components.add(this.encodedTitle(article.getTitle()));
		components.add(this.encodedHiddenTitle(article.getTitle()));
		
		components.addAll(this.encodedCommonContent(article));
		
		components.add(this.getHtmlSubmitComponent(JWLElements.EDIT_SAVE,
				JWLStyleClass.EDIT_INPUT_SUBMIT));
		return components;
	}

	private UIComponent encodedLabelForTitle() {
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass(JWLStyleClass.EDIT_LABEL_OF_TITLE);
		div.addChildren(getHtmlText(EDIT_TITLE.value));
		return div;
	}
	
	private UIComponent encodedTitle(String title) {
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass(JWLStyleClass.EDIT_TITLE);
		div.addChildren(getHtmlText(title));
		return div;
	}
	
	private UIComponent encodedHiddenTitle(String title) {
		HtmlInputHidden articleTitle = new HtmlInputHidden();
		articleTitle.setId(JWLElements.EDIT_TITLE.id);
		articleTitle.setValue(title);
		return articleTitle;
	}
	
}
