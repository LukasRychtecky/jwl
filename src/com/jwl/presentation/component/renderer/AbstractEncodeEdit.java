package com.jwl.presentation.component.renderer;

import static com.jwl.presentation.component.enumerations.JWLElements.EDIT_CHANGE_NOTE;
import static com.jwl.presentation.component.enumerations.JWLElements.EDIT_TAGS;
import static com.jwl.presentation.component.enumerations.JWLElements.EDIT_TEXT;
import static com.jwl.presentation.component.enumerations.JWLElements.EDIT_TITLE;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputTextarea;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.util.html.component.HtmlActionForm;

/**
 * Abstract class for rendering form of article manipulation.
 * 
 * @author ostatnickyjiri
 */
public abstract class AbstractEncodeEdit extends JWLEncoder {

	protected boolean existUserName;

	public AbstractEncodeEdit(IFacade facade, boolean existUserName) {
		super(facade);
		this.existUserName = existUserName;
	}

	@Override
	protected void encodeResponse() {
		try {
			renderCode();
		} catch (IOException ex) {
			Logger.getLogger(AbstractEncodeEdit.class.getName()).log(Level.SEVERE, null, ex);
			super.addImplicitErrorFlashMessage();
		} catch (ModelException ex) {
			Logger.getLogger(AbstractEncodeEdit.class.getName()).log(Level.SEVERE, null, ex);
			super.addImplicitErrorFlashMessage();
		} finally {
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(AbstractEncodeEdit.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	protected void encodeCommonContent(List<UIComponent> formData,
			ArticleTO article, boolean existUserName, JWLElements element)
			throws IOException {
		if (element == JWLElements.CREATE_SAVE) {
			formData.add(this.encodeLabelForTitleInCreate());
			formData.add(this.encodeTitleInCreate(article.getTitle()));
		} else {
			formData.add(this.encodeLabelForTitleInEdit());
			formData.add(this.encodeTitleInEdit(article.getTitle()));
		}

		formData.add(this.encodeLabelForText());
		formData.add(this.encodeText(article.getText()));

		formData.add(this.encodeLabelForTags());
		StringBuilder tags = new StringBuilder();
		for (String tag : article.getTags()) {
			tags.append(tag).append(", ");
		}

		String tagAsString = "";
		if (tags.length() > 0) {
			tagAsString = tags.substring(0, tags.length() - 1);
		}
		formData.add(this.encodeTags(tagAsString));

		formData.add(this.encodeLabelForChangeNote());
		formData.add(this.encodeChangeNote(""));

		if (!existUserName) {
			formData.add(this.encodeUnknownUserMessage());
		}

		formData.add(this.getHtmlSubmitComponent(element,
				JWLStyleClass.EDIT_INPUT_SUBMIT));
	}

	/**
	 * Rendering all component
	 * 
	 * @throws IOException
	 */
	private void renderCode() throws IOException, ModelException {
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.EDIT_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.getFormAction());

		encodeContent(form.getChildren());

		form.encodeAll(context);
		
		encodeDivIdStart(JWLElements.EDIT_SIMILAR_ARTICLE_DIV.id);
		encodeDivEnd();
	}

	/**
	 * This method would be call in child class. Here you will call encode
	 * methods and it will be determine state of manipulation of article.
	 * 
	 * @param list
	 * @throws IOException
	 */
	protected abstract void encodeContent(List<UIComponent> formData)
			throws IOException, ModelException;

	protected UIComponent encodeLabelForTitleInEdit() throws IOException {
		return getHtmlTextComponent(EDIT_TITLE.value,
				JWLStyleClass.EDIT_LABEL_OF_TITLE);
	}

	protected UIComponent encodeLabelForTitleInCreate() throws IOException {
		return getHtmlLabelComponent(EDIT_TITLE.value, EDIT_TITLE.id,
				JWLStyleClass.EDIT_LABEL_FOR_TITLE);
	}

	/**
	 * Return title as plain text in div tag.
	 * 
	 * @param title string of title
	 * @return plain text
	 * @throws IOException
	 */
	protected UIComponent encodeTitleInEdit(String title) throws IOException {
		return getHtmlTextComponent(title, JWLStyleClass.EDIT_TITLE);
	}

	/**
	 * Return title as input text in div tag.
	 * 
	 * @param title string of title
	 * @return input tag
	 * @throws IOException
	 */
	protected UIComponent encodeTitleInCreate(String title) throws IOException {
		return getHtmlInputComponent(title, EDIT_TITLE,
				JWLStyleClass.EDIT_TITLE_INPUT);
	}

	protected UIComponent encodeLabelForText() throws IOException {
		return getHtmlLabelComponent(EDIT_TEXT.value, EDIT_TEXT.id,
				JWLStyleClass.EDIT_LABEL_FOR_TEXT);
	}

	/**
	 * Create input text for editing text
	 * 
	 * @param text string of content article
	 * @throws IOException
	 */
	protected UIComponent encodeText(String text) throws IOException {
		HtmlInputTextarea textArea = new HtmlInputTextarea();
		textArea.setRows(30);
		textArea.setCols(100);
		textArea.setValue(text);
		textArea.setId(EDIT_TEXT.id);
		return textArea;
	}

	protected UIComponent encodeLabelForTags() throws IOException {
		return getHtmlLabelComponent(EDIT_TAGS.value, EDIT_TAGS.id,
				JWLStyleClass.EDIT_LABEL_FOR_TAGS);
	}

	/**
	 * Create input text for editing tags
	 * 
	 * @param tags string of tags
	 * @throws IOException
	 */
	protected UIComponent encodeTags(String tags) throws IOException {
		return getHtmlInputComponent(tags, EDIT_TAGS,
				JWLStyleClass.EDIT_TAGS_INPUT);
	}

	protected UIComponent encodeLabelForChangeNote() throws IOException {
		return getHtmlLabelComponent(JWLElements.EDIT_CHANGE_NOTE.value,
				JWLElements.EDIT_CHANGE_NOTE.id,
				JWLStyleClass.EDIT_LABEL_FOR_CHANGE_NOTE);
	}

	protected UIComponent encodeChangeNote(String changeNote)
			throws IOException {
		return getHtmlInputComponent(changeNote, EDIT_CHANGE_NOTE,
				JWLStyleClass.EDIT_CHANGE_NOTE_INPUT);
	}

	protected UIComponent encodeUnknownUserMessage() {
		return getHtmlTextComponent("Your user name is unknown! It will "
				+ "record your IP adress.", JWLStyleClass.EDIT_MESSAGE_WARNING);
	}

}
