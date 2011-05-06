package com.jwl.presentation.renderers;

import com.jwl.business.security.IIdentity;
import com.jwl.presentation.core.AbstractRenderer;
import com.jwl.presentation.url.Linker;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;

/**
 *
 * @author Lukas Rychtecky
 */
public class TagsAutocomplete extends AbstractRenderer {

	public TagsAutocomplete(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
	}
	
	public List<UIComponent> render() {
		
		return super.components;
	}
	
}
