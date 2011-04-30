package com.jwl.presentation.renderers;

import com.jwl.business.security.IIdentity;
import com.jwl.presentation.url.Linker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlActionForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlInputFile;
import com.jwl.presentation.html.HtmlLink;

public class EncodeAdministrationConsole extends AbstractEncoder {

	public EncodeAdministrationConsole(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(encodedACLUploader());
		components.add(encodedKnowledgeLinks());
		return components;
	}

	protected HtmlActionForm encodedACLUploader() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.REDIRECT_TARGET, parser.getCurrentPage());
		params.put(JWLURLParams.STATE, JWLStates.ADMINISTRATION_CONSOLE.id);
		params.put(JWLURLParams.DO, JWLActions.IMPORT_ACL.id);
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FILE_FORM.id);
		form.setEnctype("multipart/form-data");
		form.setAction(this.linker.buildLink(AbstractComponent.JWL_UPLOAD_FILE_PAGE, params));

		form.getChildren().add(this.encodedLabelForFileInput());
		form.getChildren().add(this.encodedFileInput());
		form.getChildren().add(this.encodedSubmit());
		
		return form;
	}

	private UIComponent encodedLabelForFileInput() {
		return getHtmlLabelComponent(JWLElements.FILE_ITEM,
				JWLStyleClass.ATTACH_LABEL_FOR_FILE);
	}

	private HtmlDiv encodedFileInput() {
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass(JWLStyleClass.ATTACH_FILE);
		
		HtmlInputFile fileInput = new HtmlInputFile();
		fileInput.setId(JWLElements.FILE_ITEM.id);
		
		div.addChildren(fileInput);
		return div;
	}

	private HtmlDiv encodedSubmit() {
		return getHtmlSubmitComponent(JWLElements.FILE_SAVE,
				JWLStyleClass.ACTION_BUTTON);
	}
	
	private HtmlDiv encodedKnowledgeLinks() {
		HtmlDiv div = new HtmlDiv();
		div.setId(JWLElements.ADMINISTRATION_KM_DIV.id);
		
		div.addChildren(this.encodedTitle());
		div.addChildren(this.encodedMergeSuggestionsLink());
		div.addChildren(this.encodedDeadArticlesLink());
		div.addChildren(this.encodedKeyWordLink());
		
		return div;
	}
	
	private HtmlOutputText encodedTitle() {
		return getHtmlText("Knowledge manageent");
	}

	private HtmlLink encodedMergeSuggestionsLink() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.MERGE_SUGGESTION_LIST.id);

		HtmlLink link = this.getHtmlLink("Merge suggestions", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON);
		return link;
	}
	
	private HtmlLink encodedDeadArticlesLink() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.DEAD_ARTICLE_LIST.id);

		HtmlLink link = this.getHtmlLink("Dead articles suggestions", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON);
		return link;
	}

	private HtmlLink encodedKeyWordLink() {
		HtmlLink link = new HtmlLink();
		link.setIsAjax(false);
		link.setText("create key words - do nothing");
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON);
		link.setId(JWLElements.ADMINISTRATION_KW_LINK.id);
		
		return link;
	}
	
}
