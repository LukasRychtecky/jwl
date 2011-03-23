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
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.article.enumerations.ListColumns;
import com.jwl.presentation.component.controller.JWLComponent;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.renderer.AbstractEncodeListing;
import com.jwl.util.html.component.HtmlActionForm;
import com.jwl.util.html.component.HtmlDivInputFile;
import com.jwl.util.html.component.HtmlInputFile;
import javax.faces.component.html.HtmlCommandButton;

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
			this.encodeACLUploader();
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

	protected void encodeACLUploader() throws IOException {
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FILE_FORM.id);
		form.setEnctype("multipart/form-data");
		form.setAction(
				this.getFormAction(
					JWLComponent.JWL_UPLOAD_FILE_PAGE,
					ArticleActions.LIST,
					AdministrationActions.IMPORT_ACL.toString()
				)
		);

		List<UIComponent> formData = form.getChildren();
		formData.add(this.encodeLabelForFileInput());
		formData.add(this.encodeFileInput());
		formData.add(this.encodeSubmit());
		form.encodeAll(context);
	}

	private UIComponent encodeLabelForFileInput() {
		return getHtmlLabelComponent("ACL import:", JWLElements.FILE_ITEM.id,
				JWLStyleClass.ATTACH_LABEL_FOR_FILE);
	}

	private HtmlInputFile encodeFileInput() throws IOException {
		HtmlDivInputFile fileInput = new HtmlDivInputFile();
		fileInput.setDivStyleClass(JWLStyleClass.ATTACH_FILE);
		fileInput.setId(JWLElements.FILE_ITEM.id);
		return fileInput;
	}

	private HtmlCommandButton encodeSubmit() throws IOException {
		return getHtmlSubmitComponent(JWLElements.FILE_SAVE,
				JWLStyleClass.ACTION_BUTTON);
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
