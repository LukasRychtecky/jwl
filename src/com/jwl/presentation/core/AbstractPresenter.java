package com.jwl.presentation.core;

// <editor-fold defaultstate="collapsed">
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.StateManager;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.jwl.business.Facade;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.components.article.ArticlePresenter;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLContextKey;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.global.ExceptionLogger;
import com.jwl.presentation.html.AppForm;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlInputExtended;
import com.jwl.presentation.renderers.units.FlashMessage;
import com.jwl.presentation.renderers.units.FlashMessage.FlashMessageType;
import com.jwl.presentation.url.Linker;
import com.jwl.presentation.url.RequestMapDecoder;
import com.jwl.presentation.url.WikiURLParser;
import java.util.HashMap;

// </editor-fold>
/**
 *
 * @author Lukas Rychtecky
 */
abstract public class AbstractPresenter {

	public static final String SERIALIZED_STATE_KEY = "com.jwl.presentation.core.view.serializedstate";
	public static final String COMPONENT_CLASS = "jwl-component";
	public static final String COMPONENT_ID = JWLElements.JWL_DIV.id;
	public static final String CREATE_FORM = "createForm";
	
	public static final String HTML_ELEMENT = "html";
	public static final String BODY_ELEMENT = "body";
	public static final String LANG_ATTRIBUTE = "lang";

	public static final String CONTENT_TYPE = "text/html";
	public static final String ENCODING = "utf-8";

	protected FacesContext context;
	protected Linker linker;
	protected WikiURLParser urlParser;
	private IFacade facade = null;
	protected List<UIComponent> container;
	protected List<FlashMessage> messages;
	protected Map<String, HtmlAppForm> forms;

	public AbstractPresenter() {
		this.context = FacesContext.getCurrentInstance();
		this.urlParser = new WikiURLParser();
		this.messages = new ArrayList<FlashMessage>();
		this.container = new ArrayList<UIComponent>();
		this.forms = new HashMap<String, HtmlAppForm>();

		if (isAjax()) {
			this.linker = new Linker(getPresenterName(), getRequestParam(JWLURLParams.URI));
		} else {
			this.linker = new Linker(getPresenterName());
		}
		
		try {
			this.prepareFormFromRequest();
		} catch (IOException ex) {
			ExceptionLogger.severe(getClass(), ex);
		}
		
		this.initializeFacesContext();
		
	}
	
	
	private String getPresenterName() {
		String className = this.getClass().getSimpleName();
		Integer lastIndex = className.lastIndexOf("Presenter");
		String presenterName = className;
		if (lastIndex > -1) {
			presenterName = className.substring(0, lastIndex);
		}
		return presenterName;
	}
	
	private HtmlAppForm buildForm(String formName) {
		Method method;
		HtmlAppForm form = null;
		try {
			method = this.getClass().getMethod(CREATE_FORM + formName);
			form = (HtmlAppForm) method.invoke(this);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return form;
	}

	private void prepareFormFromRequest() throws IOException {
		String formName = getRequestParam(AppForm.FORM_NAME);
		if (formName != null && !formName.isEmpty()) {
			Method method;
			try {
				method = this.getClass().getMethod(CREATE_FORM + formName);
				HtmlAppForm form = (HtmlAppForm) method.invoke(this);

				for (String key : getRequestParamMap().keySet()) {
					if (!key.startsWith(AppForm.PREFIX)) {
						continue;
					}

					HtmlInputExtended input = form.get(key.substring(AppForm.PREFIX.length()));
					if (input == null) {
						continue;
					}

					Object value = getRequestParam(key);
					if (input.getComponent() instanceof HtmlSelectBooleanCheckbox) {
						value = (value.toString().equals("on") ? Boolean.TRUE : Boolean.FALSE);
					}
					input.setValue(value);
				}

				this.forms.put(formName, form);
			} catch (NoSuchMethodException ex) {
				ExceptionLogger.severe(getClass(), new RuntimeException(
						"No such method found " + this.getClass().toString()
								+ "." + CREATE_FORM + formName, ex));
				this.render404();
			} catch (SecurityException ex) {
				ExceptionLogger.severe(getClass(), new RuntimeException(
						"Method " + this.getClass().toString() + "."
								+ CREATE_FORM + formName
								+ " must be declarated as public.", ex));
				this.render500();
			} catch (IllegalAccessException ex) {
				ExceptionLogger.severe(getClass(), ex);
				this.render500();
			} catch (IllegalArgumentException ex) {
				ExceptionLogger.severe(getClass(), ex);
				this.render500();
			} catch (InvocationTargetException ex) {
				ExceptionLogger.severe(getClass(), ex);
				this.render500();
			}
		}
	}

	protected IFacade getFacade() {
		if (this.facade == null) {
			this.facade = new Facade();
		}
		return this.facade;
	}

	protected final Boolean isAjax() {
		String method = getRequestParam(JWLURLParams.METHOD);
		if (method != null && method.equals("ajax")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	protected RequestMapDecoder getRequestMapDecoder(JWLElements root) {
		return new RequestMapDecoder(getRequestParamMap(), root);		
	}
	
	protected String getRequestParam(String key) {
		return this.getRequestParamMap().get(key);
	}

	protected Map<String, String> getRequestParamMap() {
		return this.context.getExternalContext().getRequestParameterMap();
	}
	
	protected void redirect(String state) {
		this.context.getAttributes().put(JWLContextKey.STATE, state);
	}

	public void renderDefault() throws IOException {
	}

	public void render404() throws IOException {
		AbstractRenderer renderer = new AbstractRenderer(this.context, this.linker, this.container);
		renderer.render404(this.getRequestParam(JWLURLParams.STATE).toString());
		this.sendResponse();
	}

	public void render500() throws IOException {
		AbstractRenderer renderer = new AbstractRenderer(this.context, this.linker, this.container);
		renderer.render500();
		this.sendResponse();
	}
	
	public void defaultProcessException(Exception ex, String redirectState) {
		ExceptionLogger.severe(this.getClass(), ex);
		this.messages.add(new FlashMessage("Service is unavailable, sorry.",
				FlashMessageType.ERROR, Boolean.FALSE));
		this.redirect(redirectState);
	}
	
	public void defaultProcessException(Exception ex) {
		this.defaultProcessException(ex, "default");
	}

	public void encodeAjaxBegin(FacesContext context) throws IOException {
		UIViewRoot viewRoot = context.getViewRoot();
		ResponseWriter out = context.getResponseWriter();
		out.startElement(HTML_ELEMENT, viewRoot);
		Locale locale = viewRoot.getLocale();
		out.writeAttribute(LANG_ATTRIBUTE, locale.toString(), LANG_ATTRIBUTE);
		out.startElement(BODY_ELEMENT, viewRoot);
	}

	public void encodeAjaxEnd(FacesContext context)	throws IOException {
		ResponseWriter out = context.getResponseWriter();
		out.endElement(BODY_ELEMENT);
		out.endElement(HTML_ELEMENT);
	}

	private void ajaxResponse(UIComponent componentCover) throws IOException {
		String encoding = null;
		ExternalContext externalContext = this.context.getExternalContext();

		if (externalContext.getRequest() instanceof ServletRequest) {
			ServletRequest request = (ServletRequest) externalContext.getRequest();
			ServletResponse response = (ServletResponse) externalContext.getResponse();
			String contentType = CONTENT_TYPE;
			encoding = request.getCharacterEncoding();
			if (encoding == null) {
				encoding = ENCODING;
			}
			response.setContentType(contentType + ";charset=" + encoding);
		} else {
			encoding = ENCODING;
		}

		RenderKit kit = this.context.getRenderKit();
		PrintWriter servletWriter = (PrintWriter) this.context.getExternalContext().getResponseOutputWriter();
		ResponseWriter writer = kit.createResponseWriter(servletWriter, null, encoding);
		this.context.setResponseWriter(writer);
		writer.startDocument();
		this.encodeAjaxBegin(this.context);
		
		this.context.getViewRoot().getChildren().add(componentCover);
		this.context.getViewRoot().encodeAll(this.context);

		this.saveViewState(this.context);
		this.encodeAjaxEnd(this.context);
		writer.endDocument();
		writer.flush();
		writer.close();
		servletWriter.close();

		this.context.responseComplete();
	}

	public void saveViewState(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		StateManager stateManager = context.getApplication().getStateManager();
		Object serializedView = stateManager.saveView(context);
		if (null != serializedView && null != writer) {
			StringWriter bufWriter = new StringWriter();
			ResponseWriter tempWriter;
			tempWriter = writer.cloneWithWriter(bufWriter);
			context.setResponseWriter(tempWriter);
			stateManager.writeState(context, serializedView);
			tempWriter.flush();
			if (bufWriter.getBuffer().length() > 0) {
				context.getExternalContext().getRequestMap().put(
						SERIALIZED_STATE_KEY,
						bufWriter.toString());
			}
			context.setResponseWriter(writer);
		}
	}

	protected UIComponent renderMessages() {
		HtmlDiv messagesContainer = new HtmlDiv();
		messagesContainer.setId("jwl-messages");

		for (FlashMessage flashMessage : this.messages) {
			if (flashMessage == null) {
				continue;
			}
			HtmlDiv messageWidget = new HtmlDiv();
			messageWidget.addStyleClass(JWLStyleClass.FLASH_MESSAGE);
			messageWidget.addStyleClass(JWLStyleClass.FLASH_PREFIX + flashMessage.getType().getType());
			messageWidget.addStyleClass(flashMessage.isHide() ? null : JWLStyleClass.NO_HIDE);
			HtmlOutputText text = new HtmlOutputText();
			text.setValue(flashMessage.getMessage());
			messageWidget.getChildren().add(text);
			messagesContainer.getChildren().add(messageWidget);
		}

		return messagesContainer;
	}

	public void sendResponse() throws IOException {
		HtmlDiv componentCover = new HtmlDiv();
		componentCover.setId(COMPONENT_ID);
		componentCover.addStyleClass(COMPONENT_CLASS);
		componentCover.getChildren().add(this.renderMessages());
		componentCover.getChildren().addAll(this.container);
		
		if (isAjax()) {
			this.ajaxResponse(componentCover);
		} else {
			componentCover.encodeAll(this.context);
		}
	}

	private void initializeFacesContext() {
		String articleTitle = this.urlParser.getArticleTitle();
		String topicId = this.urlParser.getTopicId();
		String historyId = this.urlParser.getHistoryId();
		String userIP = this.urlParser.getUserIP();
		ArticleTO article = getArticle(articleTitle);
		
		if (articleTitle != null) {
			context.getAttributes().put(JWLContextKey.ARTICLE_TITLE, articleTitle);
		}
		
		if (article != null) {
			context.getAttributes().put(JWLContextKey.ARTICLE, article);
		}
		
		if (article != null && article.getId() != null) {
			context.getAttributes().put(JWLContextKey.ARTICLE_ID, article.getId());
		}
		
		if (topicId != null) {
			context.getAttributes().put(JWLContextKey.TOPIC_ID, Integer.parseInt(topicId));
		}
		
		if (historyId != null) {
			HistoryId history = new HistoryId(Integer.parseInt(historyId), article.getId());
			context.getAttributes().put(JWLContextKey.HISTORY_ID, history);
		}
		
		if (userIP != null) {
			context.getAttributes().put(JWLContextKey.USER_IP, userIP);
		}
		
		context.getAttributes().put(JWLContextKey.LINKER, linker);
		context.getAttributes().put("javax.faces.SEPARATOR_CHAR", 
				AbstractComponent.JWL_HTML_ID_SEPARATOR.charAt(0));
	}

	protected ArticleTO getArticle(String title) {
		ArticleTO articleTO = null;
		try {
			articleTO = getFacade().findArticleByTitle(title);
		} catch (ModelException e) {
			Logger.getLogger(ArticlePresenter.class.getName()).log(Level.SEVERE, null, e);
		}
		return articleTO;
	}
	
	protected HtmlAppForm getForm(String name) {
		HtmlAppForm form = this.forms.get(name);
		if (form == null) {
			form = this.buildForm(name);
			
			if (form != null) {
				this.forms.put(name, form);
			}
		}
		return form;
	}

}