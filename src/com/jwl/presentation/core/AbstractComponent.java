package com.jwl.presentation.core;

import com.jwl.business.exceptions.ModelException;
import java.io.IOException;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.jwl.business.security.Role;
import com.jwl.presentation.enumerations.JWLPresenters;
import com.jwl.presentation.global.ExceptionLogger;
import com.jwl.presentation.url.WikiURLParser;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Lukas Rychtecky
 */
abstract public class AbstractComponent extends UIInput implements StateHolder {

	public static final String COMPONENT_TYPE = "com.jwl.component.Widget";
	public static final String COMPONENT_FAMILY = "com.jwl.component";
	private static final String ROLE_DELIMITER = ",";
	public static final String JWL_UPLOAD_FILE_PAGE = "WikiUploadFile";
	public static final String JWL_DOWNLOAD_FILE_PAGE = "WikiFile";
	public static final String JWL_HTML_ID_SEPARATOR = "-";
	public static final String TAG_PARAM_REQUIRED_USER = "user";
	public static final String TAG_PARAM_REQUIRED_ROLE = "role";
	public static final String TAG_PARAM_ARTICLE_INITIAL_PAGE = "initialPage";
	private String user;
	private String role;

	public AbstractComponent() {
		super();
	}

	private void route(FacesContext context) {
		WikiURLParser parser = new WikiURLParser();
		String presenterParametr = parser.getPresenter();

		AbstractPresenter presenter = null;
		if (presenterParametr == null) {
			presenter = this.getPresenter();
		} else {
			presenter = this.getPresenterInstance(presenterParametr);
		}

		try {
			this.loginUser(context, presenter);

			Router router = new Router();
			router.route(presenter);
		} catch (IOException ex) {
			ExceptionLogger.severe(getClass(), ex);
		}
	}

	private AbstractPresenter getPresenterInstance(String presenterParametr) {
		JWLPresenters jwlPresenter = JWLPresenters.getFromId(presenterParametr);
		Class<? extends AbstractPresenter> clazz = jwlPresenter.clazz;
		AbstractPresenter newInstance = null;
		try {
			newInstance = clazz.newInstance();
		} catch (InstantiationException e) {
			ExceptionLogger.severe(getClass(), e);
		} catch (IllegalAccessException e) {
			ExceptionLogger.severe(getClass(), e);
		}
		return newInstance;
	}

	@Override
	public void encodeAll(FacesContext context) {
		this.route(context);
	}

	abstract public AbstractPresenter getPresenter();

	protected String getUserName() {
		if (null == user || user.isEmpty()) {
			Object attribute = this.getAttribute(TAG_PARAM_REQUIRED_USER);
			user = this.getNotNullString(attribute);
		}
		return user;
	}

	protected String getUserRole() {
		if (role == null || role.isEmpty()) {
			Object attribute = this.getAttribute(TAG_PARAM_REQUIRED_ROLE);
			role = this.getNotNullString(attribute);
		}
		return role;
	}

	protected String getNotNullString(Object nullableObject) {
		return (null != nullableObject ? nullableObject.toString() : "");
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	protected Object getAttribute(String attributeName) {
		ValueExpression binding = this.getValueExpression(attributeName);
		ELContext context = this.getFacesContext().getELContext();
		if (null != binding && null != context) {
			return binding.getValue(context);
		} else {
			return null;
		}
	}

	@Override
	public Object saveState(FacesContext context) {
		Object values[] = new Object[2];
		values[0] = super.saveState(context);
		values[1] = user;
		return values;
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		user = (String) values[1];
		super.restoreState(context, values[0]);
	}

	private void loginUser(FacesContext context, AbstractPresenter presenter)
			throws IOException {
		Set<Role> roles = new HashSet<Role>();
		
		String[] splitedRoles = this.getUserRole().split(ROLE_DELIMITER);
		for (String splitedRole : splitedRoles) {
			splitedRole = splitedRole.trim().toLowerCase();
			if (!splitedRole.isEmpty()) {
				roles.add(new Role(splitedRole));
			}
		}

		this.user = this.getUserName();
		if (this.user.isEmpty()) {
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			this.user = request.getRemoteAddr();
		}

		try {
			presenter.loginUser(this.user, roles);
		} catch (ModelException e) {
			ExceptionLogger.severe(getClass(), e);
			presenter.render500();
		}
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
