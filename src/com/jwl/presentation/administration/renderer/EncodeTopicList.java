package com.jwl.presentation.administration.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.renderer.AbstractEncodeTopicList;
import com.jwl.presentation.component.renderer.EncodeHistoryListing;
import com.jwl.util.html.component.HtmlDiv;

public class EncodeTopicList extends AbstractEncodeTopicList {

	public EncodeTopicList(ArticleId articleId) {
		super(articleId);
	}

	@Override
	protected void encodeResponse() {
		try {
			super.encodeFlashMessages();
			IPaginator<TopicTO> paginator = super.facade
					.getArticleForumTopics(articleId);
			super.encodeForm(paginator, getHeaderNames());
		} catch (IOException ex) {
			super.addImplicitErrorFlashMessage();
			Logger.getLogger(EncodeListing.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (ModelException e) {
			super.addImplicitErrorFlashMessage();
			Logger.getLogger(EncodeListing.class.getName()).log(Level.SEVERE,
					null, e);
		} finally {
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeHistoryListing.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	protected List<String> getHeaderNames() {
		List<String> headers = new ArrayList<String>();
		headers.add("");
		headers.addAll(super.getHeaderNames());
		return headers;
	}

	@Override
	protected void encodeRowData(TopicTO topic, List<UIComponent> topicTableData)
			throws IOException {
		HtmlSelectBooleanCheckbox chbx = new HtmlSelectBooleanCheckbox();
		chbx.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + "-"
				+ JWLElements.FORUM_TOPIC_CHBX.id + "-" + topic.getId());
		topicTableData.add(chbx);
		super.encodeRowData(topic, topicTableData);
	}

	@Override
	protected void encodePanelActions(List<UIComponent> formData) {
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		List<UIComponent> panelChildren = buttonsPanel.getChildren();
		panelChildren.add(this.getArticleLinkComponent(articleId));
		panelChildren.add(this.getCreateTopicLinkComponent(articleId));
		if (hasDeleteTopicPermission()) {
			panelChildren.add(getDeleteButton());
		}
		if (hasCloseTopicPermission()) {
			panelChildren.add(getCloseButton());
			panelChildren.add(getOpenButton());
		}
		formData.add(buttonsPanel);
	}

	protected UIComponent getDeleteButton() {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + "-"
				+ JWLElements.FORUM_TOPIC_DELETE.id);
		button.setValue(JWLElements.FORUM_TOPIC_DELETE.value);
		button.setType("submit");
		return button;
	}

	protected UIComponent getCloseButton() {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + "-"
				+ JWLElements.FORUM_TOPIC_CLOSE.id);
		button.setValue(JWLElements.FORUM_TOPIC_CLOSE.value);
		button.setType("submit");
		return button;
	}

	protected UIComponent getOpenButton() {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + "-"
				+ JWLElements.FORUM_TOPIC_OPEN.id);
		button.setValue(JWLElements.FORUM_TOPIC_OPEN.value);
		button.setType("submit");
		return button;
	}

	private boolean hasDeleteTopicPermission() {
		return this.hasPermission(AccessPermissions.FORUM_DELETE_TOPIC);
	}

	private boolean hasCloseTopicPermission() {
		return this.hasPermission(AccessPermissions.FORUM_CLOSE_TOPIC);
	}

}
