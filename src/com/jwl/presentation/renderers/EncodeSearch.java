package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.model.SelectItem;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.security.IIdentity;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlActionForm;
import com.jwl.presentation.url.Linker;

public class EncodeSearch extends EncodeListing {

	public static final String TITLE = "Title";
	public static final String KEY_WORDS = "KeyWords";
	public static final String TAGS = "Tags";
	public static final String EDITORS = "Editors";
	
	public EncodeSearch(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(this.encodedSearchInput());
		
		List<ArticleTO> currentPageContent = Collections.emptyList();
		if (paginator != null) {
			currentPageContent = paginator.getCurrentPageContent();
		}
		if (!currentPageContent.isEmpty()) {
			components.add(this.encodedSearchResult());
		}
		return components;
	}

	private HtmlActionForm encodedSearchInput() {
		Map<String, String> params = parser.getURLParametersAndArticleTitle();
		params.put(JWLURLParams.STATE, JWLStates.SEARCH.id);
		params.put(JWLURLParams.DO, JWLActions.SEARCH_ACTION.id);
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.SEARCH_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.linker.buildLink(params));

		form.getChildren().add(encodeSearchComponents());

		return form;
	}

	private UIComponent encodeSearchComponents() {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setColumns(2);
		table.setBorder(0);
		List<UIComponent> children = table.getChildren();
		children.add(this.getHtmlInputComponent(JWLElements.SEARCH_INPUT, "", 
				JWLStyleClass.SEARCH_INPUT));
		children.add(this.getHtmlSubmitComponent(JWLElements.SEARCH_BUTTON,
				JWLStyleClass.SEARCH_INPUT_SUBMIT));
		children.add(this.encodeWhere());
		return table;
	}

	private UIComponent encodeWhere() {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setColumns(5);
		table.setBorder(0);
		List<UIComponent> children = table.getChildren();
		children.add(this.getCheckbox(JWLElements.SEARCH_WHERE_TITLE.id,
				TITLE, "true", false));
		children.add(this.getCheckbox(JWLElements.SEARCH_WHERE_KEY_WORDS.id,
				KEY_WORDS, "true", false));
		children.add(this.getCheckbox(JWLElements.SEARCH_WHERE_EDITORS.id,
				EDITORS, "true", false));
		children.add(this.getCheckbox(JWLElements.SEARCH_WHERE_TAGS.id,
				TAGS, "true", false));
		return table;
	}

	private HtmlSelectManyCheckbox getCheckbox(String id, String label,
			String value, boolean checked) {
		SelectItem option = new SelectItem(value, label);
		List<SelectItem> optionsList = new ArrayList<SelectItem>();
		optionsList.add(option);
		UISelectItems optionsGroup = new UISelectItems();
		optionsGroup.setValue(optionsList);
		HtmlSelectManyCheckbox checkbox = new HtmlSelectManyCheckbox();
		checkbox.getChildren().add(optionsGroup);
		checkbox.setId(id);
		return checkbox;
	}

	private HtmlPanelGrid encodedSearchResult() {
		return super.encodedListing();
	}
}
