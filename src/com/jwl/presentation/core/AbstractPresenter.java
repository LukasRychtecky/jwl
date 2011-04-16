package com.jwl.presentation.core;

import com.jwl.business.Facade;
import com.jwl.business.IFacade;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.component.renderer.FlashMessage;
import com.jwl.presentation.html.AppForm;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlContainer;
import com.jwl.presentation.html.HtmlInputExtended;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.StringWriter;
import java.lang.reflect.Method;
import javax.faces.application.StateManager;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author Lukas Rychtecky
 */
abstract public class AbstractPresenter {

	public static final String SERIALIZED_STATE_KEY = "com.jwl.presentation.core.view.serializedstate";
	public static final String COMPONENT_CLASS = "jwl-component";
	public static final String CREATE_FORM = "createForm";
	
	public static final String HTML_ELEMENT = "html";
	public static final String BODY_ELEMENT = "body";
	public static final String LANG_ATTRIBUTE = "lang";

	public static final String CONTENT_TYPE = "text/html";
	public static final String ENCODING = "utf-8";

	protected FacesContext context;
	protected Linker linker;
	private IFacade facade = null;
	protected List<UIComponent> container;
	private Boolean isAjax = Boolean.FALSE;
	protected AppForm form;
	protected List<FlashMessage> messages;

	public AbstractPresenter(FacesContext context) {
		this.context = context;
		this.messages = new ArrayList<FlashMessage>();
		this.container = new ArrayList<UIComponent>();
		String className = this.getClass().getSimpleName();
		Integer lastIndex = className.lastIndexOf("Presenter");
		String presenterName = "";
		if (lastIndex > -1) {
			presenterName = className.substring(0, lastIndex);
		}

		Map<String, String> requestParams = context.getExternalContext().getRequestParameterMap();
		
		String method = requestParams.get(JWLURLParameters.METHOD);
		if (method != null && method.equals("ajax")) {
			this.isAjax = Boolean.TRUE;
		}
		if (this.isAjax) {
			this.linker = new Linker(context, presenterName, requestParams.get(JWLURLParameters.URI));
		} else {
			this.linker = new Linker(context, presenterName);
		}
		try {
			this.prepareForm();
		} catch (IOException ex) {
			Logger.getLogger(AbstractPresenter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void prepareForm() throws IOException {
		Map<String, String> request = this.context.getExternalContext().getRequestParameterMap();
		String formName = request.get(AppForm.FORM_NAME);
		if (formName != null && !formName.isEmpty()) {
			Method method;
			try {
				method = this.getClass().getMethod(CREATE_FORM + formName);
				HtmlAppForm form = (HtmlAppForm) method.invoke(this);

				for (String key : request.keySet()) {
					if (!key.startsWith(AppForm.PREFIX)) {
						continue;
					}

					HtmlInputExtended input = form.get(key.substring(AppForm.PREFIX.length()));
					if (input == null) {
						continue;
					}

					Object value = request.get(key);
					if (input.getComponent() instanceof HtmlSelectBooleanCheckbox) {
						value = (value.toString().equals("on") ? Boolean.TRUE : Boolean.FALSE);
					}
					input.setValue(value);
				}

				this.form = form;
			} catch (NoSuchMethodException ex) {
				this.logException(new RuntimeException("No such method found " + this.getClass().toString() + "." + CREATE_FORM + formName, ex));
				this.render404();
			} catch (SecurityException ex) {
				this.logException(new RuntimeException("Method " + this.getClass().toString() + "." + CREATE_FORM + formName + " must be declarated as public.", ex));
				this.render500();
			} catch (IllegalAccessException ex) {
				this.logException(ex);
				this.render500();
			} catch (IllegalArgumentException ex) {
				this.logException(ex);
				this.render500();
			} catch (InvocationTargetException ex) {
				this.logException(ex);
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

	protected Boolean isAjax() {
		return this.isAjax;
	}

	protected Object getRequestParam(String key) {
		return this.context.getExternalContext().getRequestMap().get(key);
	}

	protected String buildLink(String action) {
		return this.linker.build(action);
	}

	protected String buildFormLink(String action) {
		return this.linker.buildForm(action);
	}

	protected String getFormValue(String key) {
		return this.context.getExternalContext().getRequestParameterMap().get("jwl:" + key);
	}

	public void logException(Exception e) {
		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}

	public void renderDefault() throws IOException {
	}

	public void render404() throws IOException {
		AbstractRenderer renderer = new AbstractRenderer(this.context, this.linker, this.container);
		renderer.render404(this.context.getExternalContext().getRequestParameterMap().get(JWLURLParameters.ACTION).toString());
		this.sendResponse();
	}

	public void render500() throws IOException {
		AbstractRenderer renderer = new AbstractRenderer(this.context, this.linker, this.container);
		renderer.render500();
		this.sendResponse();
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
		HtmlContainer messagesContainer = new HtmlContainer();
		messagesContainer.setId("jwl-messages");

		for (FlashMessage flashMessage : this.messages) {
			HtmlContainer messageWidget = new HtmlContainer();
			messageWidget.getStyleClasses().add(JWLStyleClass.FLASH_MESSAGE);
			messageWidget.getStyleClasses().add(JWLStyleClass.FLASH_PREFIX +
											flashMessage.getType().getType() +
											(flashMessage.isHide() ? "" : JWLStyleClass.NO_HIDE));
			HtmlOutputText text = new HtmlOutputText();
			text.setValue(flashMessage.getMessage());
			messageWidget.getChildren().add(text);
			messagesContainer.getChildren().add(messageWidget);
		}

		return messagesContainer;
	}

	public void sendResponse() throws IOException {
		HtmlContainer componentCover = new HtmlContainer();
		componentCover.getStyleClasses().add(COMPONENT_CLASS);
		componentCover.getChildren().add(this.renderMessages());
		componentCover.getChildren().addAll(this.container);
		
		if (this.isAjax) {
			this.ajaxResponse(componentCover);
		} else {
			componentCover.encodeAll(this.context);
		}
	}
}
