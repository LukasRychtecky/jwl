package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.Map;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.jwl.business.IFacade;
import com.jwl.business.permissions.IIdentity;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.global.Global;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.component.HtmlDivCommandButton;
import com.jwl.util.html.component.HtmlDivInputText;
import com.jwl.util.html.component.HtmlDivOutputLabel;
import com.jwl.util.html.component.HtmlDivOutputText;
import com.jwl.util.html.component.HtmlLinkProperties;
import com.jwl.util.html.component.HtmlOutputPropertyLink;
import com.jwl.util.html.url.URLBuilder;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public abstract class JWLEncoder {

	public static final char HTML_ID_SEPARATOR = JWLStyleClass.HTML_ID_SEPARATOR;
	protected IFacade facade;
	protected FacesContext context;
	protected ResponseWriter writer;
	protected List<FlashMessage> messages;

	public JWLEncoder(IFacade facade) {
		this.facade = facade;
		this.context = FacesContext.getCurrentInstance();
		this.writer = context.getResponseWriter();
		this.context.getAttributes().put("javax.faces.SEPARATOR_CHAR",
				HTML_ID_SEPARATOR);
		this.messages = new ArrayList<FlashMessage>();
	}

	public void addFlashMessage(String message, FlashMessageType type,
			Boolean hide) {
		this.messages.add(new FlashMessage(message, type, hide));
	}

	public void addFlashMessage(String message, FlashMessageType type) {
		this.messages.add(new FlashMessage(message, type));
	}

	public void addFlashMessage(String message) {
		this.messages.add(new FlashMessage(message));
	}

	public void addFlashMessage(FlashMessage message) {
		this.messages.add(message);
	}

	public void addImplicitErrorFlashMessage() {
		this.messages.add(new FlashMessage("Service is unavailable, sorry.",
				FlashMessageType.ERROR, Boolean.FALSE));
	}

	public void encode() throws IOException {
		encodeDivIdStart(JWLElements.JWL_DIV.id);
		encodeResponse();
		encodeDivEnd();
	}

	private void encodeFlashMessage(FlashMessage message) throws IOException {
		String flash = " " + JWLStyleClass.NO_HIDE;
		if (message.isHide()) {
			flash = "";
		}
		Writer writer = this.writer;
		if (writer == null) {
			writer = this.context.getExternalContext()
					.getResponseOutputWriter();
		}
		writer.write("<div class=\"" + JWLStyleClass.FLASH_MESSAGE + " "
				+ JWLStyleClass.FLASH_PREFIX + message.getType().getType()
				+ flash + "\">" + message.getMessage() + "</div>");
	}

	protected void encodeFlashMessages() throws IOException {
		for (FlashMessage message : this.messages) {
			this.encodeFlashMessage(message);
		}
	}

	protected void encodeCriticalFlashMessages() throws IOException {
		for (FlashMessage message : this.messages) {
			if (!message.getType().equals(FlashMessageType.INFO)) {
				this.encodeFlashMessage(message);
			}
		}
	}

	protected abstract void encodeResponse();

	protected void encodeDivIdStart(String id) throws IOException {
		writer.startElement("div", null);
		writer.writeAttribute("id", id, null);
	}

	protected void encodeDivClassStart(String styleClass) throws IOException {
		writer.startElement("div", null);
		writer.writeAttribute("class", styleClass, null);
	}

	protected void encodeDivEnd() throws IOException {
		writer.write("</div>");
	}

	public void encodePlainText(String text) throws IOException {
		writer.write(text);
	}

	protected void encodeH1Text(String title) throws IOException {
		writer.write("<h1>");
		writer.append(title);
		writer.write("</h1>");
	}

	protected void encodeH1Text(String title, String styleClass)
			throws IOException {
		writer.write("<h1 class=\"" + styleClass + "\">");
		writer.append(title);
		writer.write("</h1>");
	}

	protected void encodeHorizontalLine() throws IOException {
		writer.write("<hr width=\"90%\" size=\"2\" align=\"left\" />");
	}

	protected HtmlOutputText getHtmlTextComponent(Object value) {
		HtmlOutputText component = new HtmlOutputText();
		component.setValue(value);
		return component;
	}

	protected HtmlDivOutputText getHtmlTextComponent(Object value,
			String styleClass) {
		HtmlDivOutputText text = new HtmlDivOutputText();
		text.setDivStyleClass(styleClass);
		text.setValue(value);
		return text;
	}

	protected HtmlDivInputText getHtmlInputComponent(String value,
			JWLElements elm, String styleClass) {
		HtmlDivInputText inputText = new HtmlDivInputText();
		inputText.setDivStyleClass(styleClass);
		inputText.setValue(value);
		inputText.setId(elm.id);
		return inputText;
	}

	/**
	 * Create submit button for ending work on article.
	 * 
	 * @param element element for determine if article is editing or creating
	 * @throws IOException
	 */
	protected HtmlDivCommandButton getHtmlSubmitComponent(JWLElements element,
			String styleClass) {
		HtmlDivCommandButton submit = new HtmlDivCommandButton();
		submit.setDivStyleClass(styleClass);
		submit.setType("submit");
		submit.setId(element.id);
		submit.setValue(element.value);
		return submit;
	}

	protected HtmlDivOutputLabel getHtmlLabelComponent(String label,
			String _for, String styleClass) {
		HtmlDivOutputLabel labelForFileName = new HtmlDivOutputLabel();
		labelForFileName.setDivStyleClass(styleClass);
		labelForFileName.setFor(_for);
		labelForFileName.setValue(label);
		return labelForFileName;
	}

	protected HtmlOutputLink getHtmlLinkComponent(HtmlLinkProperties properties) {
		WikiURLParser parser = new WikiURLParser();
		properties.setHref(parser.getCurrentURL());
		properties.addParameters(parser
				.getURLParametersMinusArticleParameters());
		return new HtmlOutputPropertyLink(properties);
	}

	protected boolean hasPermission(String permission, ArticleId id) {
		IIdentity identity = Global.getInstance().getFacade().getIdentity();
		try {
			identity.checkPermission(permission, id);
			return true;
		} catch (PermissionDeniedException e) {
		}
		return false;
	}

	protected String getFormAction() {
		WikiURLParser parser = new WikiURLParser();
		String context = parser.getCurrentContext();
		String target = parser.getCurrentPage();
		Map<String, String> params = parser.getURLParametersAndArticleTitle();
		params.put(JWLURLParameters.ACTION, ArticleActions.VIEW);
		return getFormActionString(context, target, params);
	}

	protected String getFormAction(String target) {
		WikiURLParser parser = new WikiURLParser();
		String context = parser.getCurrentContext();
		String currentPage = parser.getCurrentPage();
		Map<String, String> params = parser.getURLParametersAndArticleTitle();
		params.put(JWLURLParameters.REDIRECT_TARGET, currentPage);
		params.put(JWLURLParameters.ACTION, ArticleActions.VIEW);
		return getFormActionString(context, target, params);
	}

	private String getFormActionString(String context, String target,
			Map<String, String> params) {
		StringBuilder href = new StringBuilder();
		if (context.length() != 0) {
			href.append("/");
			href.append(context);
		}
		href.append("/");
		href.append(target);
		return URLBuilder.buildURL(href.toString(), params);
	}

}
