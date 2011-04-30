package com.jwl.presentation.core;

import com.jwl.business.exceptions.ModelException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.jwl.business.IFacade;
import com.jwl.business.security.IIdentity;
import com.jwl.business.security.Role;
import com.jwl.presentation.enumerations.JWLPresenters;
import com.jwl.presentation.global.ExceptionLogger;
import com.jwl.presentation.global.Global;
import com.jwl.presentation.url.WikiURLParser;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
abstract public class AbstractComponent extends UIInput implements StateHolder  {

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
		
		Router router = new Router();
		try {
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
		this.setUserNameAndRoles();
		this.setJWLHome();
		this.route(context);
	}

	abstract public AbstractPresenter getPresenter();

	public String getUserName() {
		if (null == user || user.isEmpty()) {
			Object attribute = this.getAttribute(TAG_PARAM_REQUIRED_USER);
			user = this.getNotNullString(attribute);
		}
		return user;
	}

	public String getUserRole() {
		if (null == role) {
			Object attribute = this.getAttribute(TAG_PARAM_REQUIRED_ROLE);
			role = this.getNotNullString(attribute);
		}
		return role;
	}

	public Set<Role> getRoles() {
		Set<Role> roles = new HashSet<Role>();
		String[] splitedRoles = this.getUserRole().split(ROLE_DELIMITER);

		String role = null;
		for (int i = 0; i < splitedRoles.length; i++) {
			role = splitedRoles[i].trim().toLowerCase();
			if (role.length() > 0) {
				roles.add(new Role(role));
			}
		}

		return roles;
	}

	protected String getNotNullString(Object nullableObject){
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

	public void setUser(String user) {
		this.user = user;
	}

	public void setRole(String role) {
		this.role = role;
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

	private void setJWLHome() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		
		IFacade facade = Global.getInstance().getFacade();
		facade.setJWLHome(request.getSession().getServletContext().getRealPath("/jwl/"));
	}

	
	private void setUserNameAndRoles() {
		try {
			String userName = getUserName();

			if (userName.isEmpty()) {
				WikiURLParser parser = new WikiURLParser();
				userName = parser.getUserIP();
			}
			
			IFacade facade = Global.getInstance().getFacade();
			facade.createIdentity(userName, this.getRoles());
		} catch (ModelException ex) {
			Logger.getLogger(AbstractComponent.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	

}

