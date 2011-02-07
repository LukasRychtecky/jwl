package com.jwl.presentation.article.renderer;

import java.io.IOException;

import javax.faces.component.html.HtmlOutputLink;

import com.jwl.business.IFacade;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.component.renderer.JWLEncoder;
import com.jwl.util.html.component.HtmlLinkProperties;

/**
 * Class for rendering of message that article doesn't exist.
 * 
 * @author ostatnickyjiri
 */
public class EncodeNotExist extends JWLEncoder {

	public EncodeNotExist(IFacade facade) {
		super(facade);
		// TODO fix this
	}

	@Override
	protected void encodeResponse() {
		try {
			encodeMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rendering of message and link to creating the new article
	 * 
	 * @throws IOException
	 */
	private void encodeMessage() throws IOException {
		writer.write("<p>");
		writer.write("Article does not exist! Do you want ");
		encodeLinkToCreate("create it");
		writer.write("?");
		writer.write("</p>");
	}

	/**
	 * Creating link to creating the new article
	 * 
	 * @param label
	 * @throws IOException
	 */
	private void encodeLinkToCreate(String label) throws IOException {
		HtmlLinkProperties linkProperties = new HtmlLinkProperties();
		linkProperties.setValue(label);
		linkProperties.addParameter(JWLURLParameters.ACTION, ArticleActions.EDIT);

		HtmlOutputLink link = this.getHtmlLinkComponent(linkProperties);
		link.encodeAll(this.context);
	}

}
