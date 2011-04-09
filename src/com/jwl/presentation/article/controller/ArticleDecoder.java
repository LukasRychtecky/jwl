package com.jwl.presentation.article.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.naming.NoPermissionException;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.controller.JWLDecoder;
import com.jwl.presentation.component.controller.RequestParameterMapDecoder;
import com.jwl.presentation.component.controller.UIComponentHelper;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.global.Global;
import com.jwl.presentation.global.WikiURLParser;

public class ArticleDecoder implements JWLDecoder {

	private IFacade facade;
	private Map<String, String> parameterMap;
	protected UIComponent component;
	private RequestParameterMapDecoder decoder;
	
	public ArticleDecoder(Map<String, String> parameterMap, UIComponent component) {
		this.facade = Global.getInstance().getFacade();
		this.parameterMap = parameterMap;
		this.component = component;
		decoder = new RequestParameterMapDecoder(parameterMap, JWLElements.EDIT_FORM);
	}

	@Override
	public void processDecode() throws ModelException, NoPermissionException {
		if (isArticleComponentRequest()) {
			ArticleTO article = getFilledArticle();

			if (this.isArticleEditRequest()) {
				String stringId = decoder.getMapValue(JWLElements.EDIT_ID);
				ArticleId articleId = new ArticleId(Integer.parseInt(stringId));
				article.setId(articleId);
				this.facade.updateArticle(article);

			} else if (this.isArticleSaveRequest()) {
				this.facade.createArticle(article);
			}
		}
		if(isTopicCreateRequest()) {
			handleTopicCreate();
		}
		
		if(isPostReplyRequest()){
			handlePostReplyRequest();
		}
	}
	
	private void handleTopicCreate() throws ModelException{
		TopicTO topic = new TopicTO();
<<<<<<< HEAD
		String subject = parameterMap.get(JWLElements.FORUM_SUBJECT.id);
=======
		String subject = map.get(getFullKey(JWLElements.FORUM_SUBJECT.id,JWLElements.FORUM_CREATE_TOPIC_FORM.id));
>>>>>>> 1399f2b6759522195f92d151631659ea0cb31674
		if(subject == ""){
			throw new ModelException("Subject cannot be left empty");
		}
		topic.setTitle(subject);
		PostTO post = new PostTO();
<<<<<<< HEAD
		post.setText(parameterMap.get(JWLElements.FORUM_TOPIC_TEXT.id));
		List<PostTO> posts =new ArrayList<PostTO>();
		posts.add(post);
		topic.setPosts(posts);
		String id = parameterMap.get(JWLElements.FORUM_ARTICLE_ID.id);
=======
		post.setText(map.get(getFullKey(JWLElements.FORUM_TOPIC_TEXT.id,JWLElements.FORUM_CREATE_TOPIC_FORM.id)));
		List<PostTO> posts =new ArrayList<PostTO>();
		posts.add(post);
		topic.setPosts(posts);
		String id = map.get(getFullKey(JWLElements.FORUM_ARTICLE_ID.id,JWLElements.FORUM_CREATE_TOPIC_FORM.id));
>>>>>>> 1399f2b6759522195f92d151631659ea0cb31674
		ArticleId articleId = new ArticleId(new Integer(id));
		this.facade.createForumTopic(topic, articleId);
	}
	
	private void handlePostReplyRequest() throws ModelException{
		PostTO post = new PostTO();
		String text = map.get(getFullKey(JWLElements.FORUM_POST_TEXT.id, JWLElements.FORUM_POST_REPLY_FORM.id));
		post.setText(text);
		String id = map.get(getFullKey(JWLElements.FORUM_POST_TOPIC_ID.id, JWLElements.FORUM_POST_REPLY_FORM.id));
		Integer postId = new Integer(id);
		this.facade.addForumPost(post, postId);		
	}

	private ArticleTO getFilledArticle() {
		ArticleTO article = new ArticleTO();
		article.setText(decoder.getMapValue(JWLElements.EDIT_TEXT));
		article.setTitle(decoder.getMapValue(JWLElements.EDIT_TITLE));

		String[] tags = decoder.getMapValue(JWLElements.EDIT_TAGS).split(",");
		for (int i = 0; i < tags.length; i++) {
			article.addTag(tags[i]);
		}

		article.setChangeNote(decoder.getMapValue(JWLElements.EDIT_CHANGE_NOTE));
		article.setEditor(findEditor());
		return article;
	}

	/**
	 * Find name of logged user. If his name is empty, return his IP address.
	 * 
	 * @return user name or IP address
	 */
	private String findEditor() {
		String editor = UIComponentHelper.getLogedUser(component);
		if (editor.isEmpty()) {
			WikiURLParser parser = new WikiURLParser();
			editor = parser.getUserIP();
		}
		return editor;
	}

	private boolean isArticleComponentRequest() {
		return isArticleEditRequest() || isArticleSaveRequest() ;
	}

	private boolean isArticleEditRequest() {
		return parameterMap.containsKey(decoder.getFullKey(JWLElements.EDIT_SAVE.id));
	}

	private boolean isArticleSaveRequest() {
		return parameterMap.containsKey(decoder.getFullKey(JWLElements.CREATE_SAVE.id));
	}
	
	private boolean isTopicCreateRequest(){
<<<<<<< HEAD
		return parameterMap.containsKey(JWLElements.FORUM_TOPIC_CREATE.id);
=======
		return map.containsKey(getFullKey(JWLElements.FORUM_TOPIC_CREATE.id,JWLElements.FORUM_CREATE_TOPIC_FORM.id));
	}
	
	private boolean isPostReplyRequest(){
		return map.containsKey(getFullKey(JWLElements.FORUM_POST_REPLY.id,JWLElements.FORUM_POST_REPLY_FORM.id));
>>>>>>> 1399f2b6759522195f92d151631659ea0cb31674
	}
}
