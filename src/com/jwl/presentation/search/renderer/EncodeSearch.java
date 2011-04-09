package com.jwl.presentation.search.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.model.SelectItem;

import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.renderer.AbstractEncodeListing;
import com.jwl.presentation.search.enumerations.SearchCategories;
import com.jwl.util.html.component.HtmlActionForm;

public class EncodeSearch extends AbstractEncodeListing {

	@Override
	protected void encodeResponse() {
		try {
			renderSearchInput();
			renderSearchResult();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void renderSearchInput() throws IOException {
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.SEARCH_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.getFormAction());

		form.getChildren().add(encodeSearchComponents());

		form.encodeAll(context);
	}

	private UIComponent encodeSearchComponents() {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setColumns(2);
		table.setBorder(0);
		List<UIComponent> children = table.getChildren();
		children.add(this.getHtmlInputComponent("", JWLElements.SEARCH_INPUT,
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
				SearchCategories.TITLE.where, "true", false));
		children.add(this.getCheckbox(JWLElements.SEARCH_WHERE_KEY_WORDS.id,
				SearchCategories.KEY_WORDS.where, "true", false));
		children.add(this.getCheckbox(JWLElements.SEARCH_WHERE_EDITORS.id,
				SearchCategories.EDITORS.where, "true", false));
		children.add(this.getCheckbox(JWLElements.SEARCH_WHERE_TAGS.id,
				SearchCategories.TAGS.where, "true", false));
		return table;
	}

	/*
	 * private HtmlSelectBooleanCheckbox getCheckbox(String id, String label,
	 * String value, boolean checked) { HtmlSelectBooleanCheckbox chbx = new
	 * HtmlSelectBooleanCheckbox(); chbx.setLabel(label); chbx. chbx.setId(id);
	 * return chbx; }
	 */

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

	private void renderSearchResult() throws IOException {
		IPaginator<ArticleTO> paginator = this.facade.getSearchPaginator();
		if (paginator != null) {
			super.encodeListing(paginator, super.getHeaderNames(),
					this.getOrderableColumns());
		}
	}

	private Map<Integer, String> getOrderableColumns() {
		Map<Integer, String> oc = new HashMap<Integer, String>();
		// oc.put(1, ListColumns.TITLE);
		// oc.put(3, ListColumns.EDITOR);
		// oc.put(5, ListColumns.CREATED);
		return oc;
	}
}
