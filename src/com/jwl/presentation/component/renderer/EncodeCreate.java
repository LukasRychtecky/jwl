package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;

/**
 * Class for encoding create state
 * 
 * @author ostatnickyjiri
 */
public class EncodeCreate extends AbstractEncodeEdit {

	@Override
	protected void encodeContent(List<UIComponent> formData) throws IOException {
		super.encodeCreate(formData);
	}

}
