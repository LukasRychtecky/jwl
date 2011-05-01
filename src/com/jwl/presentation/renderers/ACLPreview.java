package com.jwl.presentation.renderers;

import com.jwl.business.security.AccessPermissions;
import com.jwl.business.security.IIdentity;
import com.jwl.business.security.Role;
import com.jwl.presentation.core.AbstractRenderer;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlHeaderPanelGrid;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.Linker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author Lukas Rychtecky
 */
public class ACLPreview extends AbstractRenderer {

	public ACLPreview(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
	}
	
	public List<UIComponent> render() {
		@SuppressWarnings("unchecked")
		Set<Role> roles = (Set<Role>) super.params.get("acl");
		
		HtmlHeaderPanelGrid table = new HtmlHeaderPanelGrid();
		table.setColumns(roles.size() + 2);
		table.setStyleClass("jwl-acl-preview jwl-grid");
		
		List<UIComponent> headers = new ArrayList<UIComponent>();		
		headers.add(this.buildCell("Context"));
		headers.add(this.buildCell("Method"));
		
		for (Role role : roles) {
			headers.add(this.buildCell(role.getCode()));
		}
		
		table.setHeaders(headers);
			
		String context = "";
		for (AccessPermissions perm : AccessPermissions.values()) {
			if (context.equals(perm.getContext())) {
				table.getChildren().add(this.buildCell(""));
			} else {
				context = perm.getContext();
				table.getChildren().add(this.buildCell(context));
			}
			table.getChildren().add(this.buildCell(perm.getMethod()));
			
			for (Role role : roles) {
				table.getChildren().add(this.getCell(role, perm));
			}
			
		}
		super.components.add(table);
		this.renderNavigation();
		return super.components;
	}
	
	private void renderNavigation() {
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass("jwl-navigation");
		HtmlLink linkImport = new HtmlLink();
		linkImport.setValue(this.linker.buildForm("importACL", "default"));
		linkImport.setText("Import");
		linkImport.setIsAjax(true);
		linkImport.setStyleClass("jwl-action-button");
		div.getChildren().add(linkImport);
		
		HtmlLink linkBack = new HtmlLink();
		linkBack.setValue(this.linker.buildLink("default"));
		linkBack.setText("Back");
		linkBack.setStyleClass("jwl-action-button");
		linkBack.setIsAjax(true);
		div.getChildren().add(linkBack);
		
		super.components.add(div);
	}
	
	private UIComponent getCell(Role role, AccessPermissions perm) {
		HtmlSelectBooleanCheckbox checkbox = new HtmlSelectBooleanCheckbox();
		checkbox.getAttributes().put("disabled", true);
		if (role.getPermissions().contains(perm)) {
			checkbox.setValue(true);
		}
		return checkbox;
	}
	
	private UIComponent buildCell(String text) {
		HtmlOutputText cell = new HtmlOutputText();
		cell.setValue(text);
		return cell;
	}
	
}
