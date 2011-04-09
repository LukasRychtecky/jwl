package com.jwl.presentation.core;

import com.jwl.business.Facade;
import com.jwl.business.IFacade;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.html.HtmlContainer;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author Lukas Rychtecky
 */
abstract public class AbstractPresenter {

	public static final String COMPONENT_CLASS = "jwl-component";
	
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

	public AbstractPresenter(FacesContext context) {
		this.context = context;
		this.container = new ArrayList<UIComponent>();
		String className = this.getClass().getSimpleName();
		String presenterName = className.substring(0, className.lastIndexOf("Presenter"));

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
		renderer.render404(this.getRequestParam(JWLURLParameters.ACTION).toString());
	}

	public void render500() throws IOException {
		AbstractRenderer renderer = new AbstractRenderer(this.context, this.linker, this.container);
		renderer.render500();
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

		this.encodeAjaxEnd(this.context);
		writer.endDocument();
		writer.flush();
		writer.close();
		servletWriter.close();

		this.context.responseComplete();
	}

	public void sendResponse() throws IOException {
		HtmlContainer componentCover = new HtmlContainer();
		componentCover.getStyleClasses().add(COMPONENT_CLASS);
		componentCover.getChildren().addAll(this.container);
		
		if (this.isAjax) {
			this.ajaxResponse(componentCover);
		} else {
			componentCover.encodeAll(this.context);
		}
	}
}
