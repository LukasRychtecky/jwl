package com.jwl.presentation.component.controller;

import com.jwl.business.security.Role;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import com.jwl.presentation.component.enumerations.JWLTagAttributes;

/**
 *
 * @author Lukas Rychtecky
 */
abstract public class JWLComponent extends UIInput implements StateHolder {

	public static final String COMPONENT_FAMILY = "com.jwl.component";

	private static final String ROLE_DELIMITER = ",";
	
	public static final String JWL_UPLOAD_FILE_PAGE = "WikiUploadFile";

	private String user;
	private String role;
	
	public JWLComponent() {
		super();
	}

	public String getUserName() {
		if (null == user || user.isEmpty()) {
			Object attribute = this.getAttribute(JWLTagAttributes.REQUIRED_USER);
			user = this.getNotNullString(attribute);
		}
		return user;
	}

	public String getUserRole() {
		if (null == role) {
			Object attribute = this.getAttribute(JWLTagAttributes.REQUIRED_ROLE);
			role = this.getNotNullString(attribute);
		}
		return role;
	}

	public List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		String[] splitedRoles = this.getUserRole().split(JWLComponent.ROLE_DELIMITER);

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

	/**
	 * Return attribute data by given name
	 *
	 * @param attributeName
	 * @return
	 */
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

}
