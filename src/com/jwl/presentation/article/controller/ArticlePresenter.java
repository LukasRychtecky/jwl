package com.jwl.presentation.article.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.article.renderer.EncodeAttach;
import com.jwl.presentation.article.renderer.EncodeListing;
import com.jwl.presentation.component.controller.RequestParameterMapDecoder;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.renderer.EncodeCreateTopic;
import com.jwl.presentation.component.renderer.EncodeEdit;
import com.jwl.presentation.component.renderer.EncodeHistoryListing;
import com.jwl.presentation.component.renderer.EncodeHistoryView;
import com.jwl.presentation.component.renderer.EncodeTopicList;
import com.jwl.presentation.component.renderer.EncodeView;
import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.global.WikiURLParser;

public class ArticlePresenter extends AbstractPresenter {

	private WikiURLParser urlParser;
	
	public ArticlePresenter(FacesContext context) {
		super(context);
		urlParser = new WikiURLParser();
	}
	
	@Override
	public void renderDefault() throws IOException {
		new EncodeListing().encodeResponse();
	}
	
	public void renderEdit() throws IOException {
		
		
		new EncodeEdit(this.getArticleFromTitle(), linker).encodeResponse();
	}
	
	public void renderList() throws IOException {
		new EncodeListing().encodeResponse();
	}

	public void renderAttachfile() throws IOException {
		new EncodeAttach().encodeResponse();
	}
	
	public void renderDownloadfile() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Not yet implement.");
		message.encodeAll(this.context);
	}

	public void renderView() throws IOException {
		new EncodeView(this.getArticleId()).encodeResponse();
	}

	public void renderHistoryview() throws IOException {
		new EncodeHistoryView(this.getHistoryId()).encodeResponse();
	}

	public void renderHistorylist() throws IOException {
		new EncodeHistoryListing(this.getArticleId()).encodeResponse();
	}

	public void renderRestore() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("renderList");
		message.encodeAll(this.context);
	}

	public void renderTopicList() throws IOException {
		new EncodeTopicList(getArticleId()).encode();
	}

	public void renderTopicCreate() throws IOException {
		 new EncodeCreateTopic(getArticleId(), linker).encodeResponse();
	}

	
	public void decodeArticleSave() throws IOException {
		ArticleTO article = getFilledArticle();

//		if (this.isArticleEditRequest()) {
			
			try {
				this.getFacade().updateArticle(article);
			} catch (NoPermissionException ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			} catch (ModelException ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			}

//		} else if (this.isArticleSaveRequest()) {
//			this.facade.createArticle(article);
//		}
		
//		this.message = new FlashMessage("Article was saved.");
		renderView();
		
//		HtmlOutputText message = new HtmlOutputText();
//		message.setValue("Hledas: asd");
//		try {
//			message.encodeAll(this.context);
//		} catch (IOException ex) {
//			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//		}
	}
	
	private ArticleTO getFilledArticle() {
		RequestParameterMapDecoder decoder = new RequestParameterMapDecoder(getRequestParameterMap(), JWLElements.EDIT_FORM);
		ArticleTO article = new ArticleTO();
		article.setText(decoder.getMapValue(JWLElements.EDIT_TEXT));
		article.setTitle(decoder.getMapValue(JWLElements.EDIT_TITLE));

		String[] tags = decoder.getMapValue(JWLElements.EDIT_TAGS).split(",");
		for (int i = 0; i < tags.length; i++) {
			article.addTag(tags[i]);
		}

		article.setChangeNote(decoder.getMapValue(JWLElements.EDIT_CHANGE_NOTE));
		article.setEditor(findEditor());
		
		String stringId = decoder.getMapValue(JWLElements.EDIT_ID);
		ArticleId articleId = new ArticleId(Integer.parseInt(stringId));
		article.setId(articleId);
		
		return article;
	}
	
	private Map<String, String> getRequestParameterMap() {
		return this.context.getExternalContext().getRequestParameterMap();
	}
	
	private String findEditor() {
		String editor = getFacade().getIdentity().getUserName();
		if (editor.isEmpty()) {
			WikiURLParser parser = new WikiURLParser();
			editor = parser.getUserIP();
		}
		return editor;
	}
	
	public ArticleId getArticleId() {
		ArticleId articleId = null;
		
		if (this.urlParser.getArticleId() != null) {
			articleId = new ArticleId(Integer.parseInt(this.urlParser.getArticleId()));
		} else if (this.urlParser.getArticleTitle() != null) {
			ArticleTO articleTO = getArticleFromTitle();
			
			if (articleTO != null) {
				articleId = articleTO.getId(); 
			} 
		}
		
		return articleId;
	}
	
	public void decodeTopicSave() throws ModelException {
		TopicTO topic = new TopicTO();
		String subject = getRequestParameterMap().get(JWLElements.FORUM_SUBJECT.id);
		if(subject == ""){
			throw new ModelException("Subject cannot be left empty");
		}
		topic.setTitle(subject);
		PostTO post = new PostTO();
		post.setText(getRequestParameterMap().get(JWLElements.FORUM_TOPIC_TEXT.id));
		List<PostTO> posts =new ArrayList<PostTO>();
		posts.add(post);
		topic.setPosts(posts);
		String id = getRequestParameterMap().get(JWLElements.FORUM_ARTICLE_ID.id);
		ArticleId articleId = new ArticleId(new Integer(id));
		this.getFacade().createForumTopic(topic, articleId);
	}
	
	public ArticleTO getArticleFromTitle() {
		String articleTitle = this.urlParser.getArticleTitle();
		ArticleTO articleTO = null;
		
		try {
			articleTO = super.getFacade().findArticleByTitle(articleTitle);
		} catch (ModelException e) {
			Logger.getLogger(ArticlePresenter.class.getName()).log(Level.SEVERE, null, e);
		}
		return articleTO;
	}
	
	public HistoryId getHistoryId() {
		return new HistoryId(Integer.parseInt(this.urlParser.getHistoryId()), this.getArticleId());
	}
}
