package com.jwl.presentation.components.widget;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.forms.UploadedFile;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.WikiURLParser;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Lukas Rychtecky
 */
public class WidgetPresenter extends AbstractPresenter {
	private WidgetRenderer renderer;

	public WidgetPresenter() {
		super();
		this.renderer = new WidgetRenderer(super.linker, getFacade().getIdentity() ,renderParams);
	}

	@Override
	public void renderDefault() throws IOException {
		
		HtmlAppForm form = this.createFormMujForm();
		super.container.add(form);
		this.renderer.renderDefault();
	}

	public void renderDetail() throws IOException {
		HtmlAppForm form = this.createFormMujForm();
		super.container.add(form);
		this.renderer.renderDetail();
	}

	public void decodeMujForm() {
		HtmlAppForm form = super.getForm("MujForm");

		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setColumns(2);

		HtmlOutputText text = new HtmlOutputText();
		text.setValue("Text");
		table.getChildren().add(text);
		text = new HtmlOutputText();
		UploadedFile file = (UploadedFile) form.get("file").getValue();
		text.setValue(file.getTempPath());
		
		table.getChildren().add(text);
		text = new HtmlOutputText();
		text.setValue("Text");
		table.getChildren().add(text);
		text = new HtmlOutputText();
		text.setValue(form.get("blah").getValue());
		table.getChildren().add(text);

		super.container.add(table);

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.buildLink("default"));
		link.setText("ja jsem AJAXovy odkaz na default");
		link.setIsAjax(Boolean.TRUE);
		super.container.add(link);
		
	}

	public HtmlAppForm createFormMujForm() {
		HtmlAppForm form = new HtmlAppForm("MujForm");
		form.addFile("file", "File");
		form.addText("blah", "Blah", "BLAH");
		form.addSubmit("submit", "Odesli", null);
		
		WikiURLParser parser = new WikiURLParser(context);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, "default");
//		params.put(JWLURLParams.REDIRECT_TARGET, parser.getCurrentPage());
//		params.put(JWLURLParams.DO, JWLActions.FILE_UPLOAD.id);
		params.put("jwl-fu", "fu");
		
		form.setAction(this.linker.buildForm("mujForm", null));
		return form;
	}

}
