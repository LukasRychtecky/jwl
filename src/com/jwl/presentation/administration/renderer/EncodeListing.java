package com.jwl.presentation.administration.renderer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.context.ResponseWriter;

import com.jwl.business.IFacade;
import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.administration.enumerations.AdministrationActions;
import com.jwl.presentation.article.enumerations.ListColumns;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.component.renderer.AbstractEncodeListing;
import com.jwl.util.html.component.HtmlLinkProperties;

/**
 * @author Lukas Rychtecky
 */
public class EncodeListing extends AbstractEncodeListing {

	public EncodeListing(IFacade facade) {
		super(facade);
	}

	@Override
	protected void encodeResponse() {
		try {
			super.encodeFlashMessages();
			super.encodeLinkToCreateNewArticle();
			encodeArticles();
			this.encodeKnowledgeLinks();
		} catch (IOException e) {
			Logger.getLogger(EncodeListing.class.getName()).log(Level.SEVERE,
					null, e);
			super.addImplicitErrorFlashMessage();
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeListing.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	protected void encodeArticles() throws IOException {
		IPaginator<ArticleTO> paginator = this.facade.getPaginator();
		super.encodeListing(paginator, super.getHeaderNames(), this
				.getOrderableColumns());
	}

	@Override
	protected List<UIComponent> encodeAdditionalRowData(ArticleTO article,
			List<UIComponent> articlesTableData) {
		List<UIComponent> actions = super.encodeAdditionalRowData(article,
				articlesTableData);

		if (article.isLocked()) {
			actions.add(super.encodeActionLink(article.getId().getId(),
					"Unlock", AdministrationActions.UNLOCK.action,
					JWLStyleClass.ACTION_LINK));
		} else {

			actions.add(super.encodeActionLink(article.getId().getId(), "Lock",
					AdministrationActions.LOCK.action,
					JWLStyleClass.ACTION_LINK));
			actions.add(super.encodeActionLink(article.getId().getId(),
					"Delete", AdministrationActions.DELETE.action,
					JWLStyleClass.ACTION_LINK));
		}
		return actions;
	}

	private Map<Integer, String> getOrderableColumns() {
		Map<Integer, String> oc = new HashMap<Integer, String>();
		oc.put(1, ListColumns.TITLE);
		oc.put(3, ListColumns.EDITOR);
		oc.put(5, ListColumns.CREATED);
		return oc;
	}
	private void encodeKnowledgeLinks() throws IOException{
		this.encodeDivIdStart(JWLElements.ADMINISTRATION_KM_DIV.id);
		ResponseWriter writer = this.context.getResponseWriter();
		writer.write("Knowledge manageent");
		encodeMergeSuggestionsLink();
		encodeDeadArticlesLink();
		this.encodeKeyWordLink();
		this.encodeDivEnd();
	}
	private void encodeKeyWordLink() throws IOException{
		ResponseWriter writer = this.context.getResponseWriter();
		writer.write("<a id=\""+JWLElements.ADMINISTRATION_KW_LINK.id+"\" href=\"#\">");
		writer.write("create key words");
		writer.write("</a>");
	}
	private void encodeMergeSuggestionsLink() throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Merge suggestions");
		properties.addParameter(JWLURLParameters.ACTION, AdministrationActions.MERGE_SUGGESTION_LIST.action);
		properties.addClass(JWLStyleClass.ACTION_BUTTON);

		HtmlOutputLink link = this.getHtmlLinkComponent(properties);
		link.encodeAll(this.context);
	}
	
	private void encodeDeadArticlesLink() throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Dead articles suggestions");
		properties.addParameter(JWLURLParameters.ACTION, AdministrationActions.DEAD_ARTICLE_LIST.action);
		properties.addClass(JWLStyleClass.ACTION_BUTTON);

		HtmlOutputLink link = this.getHtmlLinkComponent(properties);
		link.encodeAll(this.context);
	}
}
