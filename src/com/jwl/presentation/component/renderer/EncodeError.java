package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


final public class EncodeError extends JWLEncoder {

	public EncodeError() {
		super(null);
	}

	@Override
	protected void encodeResponse() {
		try {
			super.encodeCriticalFlashMessages();
		} catch (IOException ex) {
			Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
