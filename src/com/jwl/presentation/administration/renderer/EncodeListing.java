package com.jwl.presentation.administration.renderer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import com.jwl.business.IFacade;
import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.administration.enumerations.AdministrationActions;
import com.jwl.presentation.article.enumerations.ListColumns;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.renderer.AbstractEncodeListing;

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
		IPaginator paginator = this.facade.getPaginator();
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
}
