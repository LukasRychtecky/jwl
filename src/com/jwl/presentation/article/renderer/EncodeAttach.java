package com.jwl.presentation.article.renderer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputText;

import com.jwl.business.IFacade;
import com.jwl.presentation.component.controller.JWLComponent;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.renderer.JWLEncoder;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.component.HtmlActionForm;
import com.jwl.util.html.component.HtmlDivInputFile;
import com.jwl.util.html.component.HtmlInputFile;

public class EncodeAttach extends JWLEncoder {

	public EncodeAttach(IFacade facade) {
		super(facade);
	}

	@Override
	protected void encodeResponse() {
		try {
			renderCode();
		} catch (IOException ex) {
			Logger.getLogger(EncodeAttach.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	private void renderCode() throws IOException {
		this.encodeForm();
	}

	private void encodeForm() throws IOException {
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FILE_FORM.id);
		form.setEnctype("multipart/form-data");
		form.setAction(this.getFormAction(JWLComponent.JWL_UPLOAD_FILE_PAGE));

		/*
		 * HtmlPanelGrid table = new HtmlPanelGrid(); table.setColumns(2);
		 * table.setBorder(0); List<UIComponent> children = table.getChildren();
		 * 
		 * children.add(this.encodeLabelForFileName());
		 * children.add(this.encodeFileName());
		 * 
		 * children.add(this.encodeLabelForFileInput());
		 * children.add(this.encodeFileInput());
		 * 
		 * children.add(this.encodeLabelForFileDescription());
		 * children.add(this.encodeFileDescription());
		 * 
		 * children.add(this.encodeSubmit());
		 */

		List<UIComponent> formData = form.getChildren();

		formData.add(this.encodeLabelForFileName());
		formData.add(this.encodeFileName());

		formData.add(this.encodeLabelForFileInput());
		formData.add(this.encodeFileInput());

		formData.add(this.encodeLabelForFileDescription());
		formData.add(this.encodeFileDescription());

		formData.add(this.encodeSubmit());
		formData.add(this.encodeHiddenArticleTitle());

		// form.getChildren().add(this.encodeHiddenArticleTitle());
		// form.getChildren().add(table);
		form.encodeAll(context);
	}

	private UIComponent encodeLabelForFileName() {
		return getHtmlLabelComponent("File name:", JWLElements.FILE_TITLE.id,
				JWLStyleClass.ATTACH_LABEL_FOR_FILE_NAME);
	}

	private HtmlInputText encodeFileName() throws IOException {
		return getHtmlInputComponent("", JWLElements.FILE_TITLE,
				JWLStyleClass.ATTACH_FILE_NAME);
	}

	private UIComponent encodeLabelForFileInput() {
		return getHtmlLabelComponent("File:", JWLElements.FILE_ITEM.id,
				JWLStyleClass.ATTACH_LABEL_FOR_FILE);
	}

	private HtmlInputFile encodeFileInput() throws IOException {
		HtmlDivInputFile fileInput = new HtmlDivInputFile();
		fileInput.setDivStyleClass(JWLStyleClass.ATTACH_FILE);
		fileInput.setId(JWLElements.FILE_ITEM.id);
		return fileInput;
	}

	private UIComponent encodeLabelForFileDescription() {
		return getHtmlLabelComponent("Description:",
				JWLElements.FILE_DESCRIPTION.id,
				JWLStyleClass.ATTACH_LABEL_FOR_FILE_DESCRIPTION);
	}

	private HtmlInputText encodeFileDescription() throws IOException {
		return getHtmlInputComponent("", JWLElements.FILE_DESCRIPTION,
				JWLStyleClass.ATTACH_FILE_DESCRIPTION);
	}

	private HtmlCommandButton encodeSubmit() throws IOException {
		return getHtmlSubmitComponent(JWLElements.FILE_SAVE,
				JWLStyleClass.ATTACH_INPUT_SUBMIT);
	}

	private HtmlInputHidden encodeHiddenArticleTitle() throws IOException {
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