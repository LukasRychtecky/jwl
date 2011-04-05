package com.jwl.presentation.article.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.controller.JWLDecoder;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.global.WikiURLParser;
import javax.naming.NoPermissionException;

public class ArticleDecoder extends JWLDecoder {

	public ArticleDecoder(Map<String, String> map, UIComponent component,
			IFacade facade) {
		super(map, component, facade, JWLElements.EDIT_FORM.id);
	}

	@Override
	public void processDecode() throws ModelException, NoPermissionException {
		if (isArticleComponentRequest()) {
			ArticleTO article = getFilledArticle();

			if (this.isArticleEditRequest()) {
				String stringId = getMapValue(JWLElements.EDIT_ID);
				ArticleId articleId = new ArticleId(Integer.parseInt(stringId));
				article.setId(articleId);
				this.facade.updateArticle(article);

			} else if (this.isArticleSaveRequest()) {
				this.facade.createArticle(article);
			}
			
		}
		if(isTopicCreateRequest()){
			handleTopicCreate();
		}
		
		if(isPostReplyRequest()){
			handlePostReplyRequest();
		}
	}
	
	private void handleTopicCreate() throws ModelException{
		TopicTO topic = new TopicTO();
		String subject = map.get(getFullKey(JWLElements.FORUM_SUBJECT.id,JWLElements.FORUM_CREATE_TOPIC_FORM.id));
		if(subject == ""){
			throw new ModelException("Subject cannot be left empty");
		}
		topic.setTitle(subject);
		PostTO post = new PostTO();
		post.setText(map.get(getFullKey(JWLElements.FORUM_TOPIC_TEXT.id,JWLElements.FORUM_CREATE_TOPIC_FORM.id)));
		List<PostTO> posts =new ArrayList<PostTO>();
		posts.add(post);
		topic.setPosts(posts);
		String id = map.get(getFullKey(JWLElements.FORUM_ARTICLE_ID.id,JWLElements.FORUM_CREATE_TOPIC_FORM.id));
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
		article.setText(getMapValue(JWLElements.EDIT_TEXT));
		article.setTitle(getMapValue(JWLElements.EDIT_TITLE));

		String[] tags = this.getMapValue(JWLElements.EDIT_TAGS).split(",");
		for (int i = 0; i < tags.length; i++) {
			article.addTag(tags[i]);
		}

		article.setChangeNote(getMapValue(JWLElements.EDIT_CHANGE_NOTE));
		article.setEditor(findEditor());
		return article;
	}

	/**
	 * Find name of logged user. If his name is empty, return his IP address.
	 * 
	 * @return user name or IP address
	 */
	private String findEditor() {
		String editor = getLogedUser();
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
		return map.containsKey(this.getFullKey(JWLElements.EDIT_SAVE.id));
	}

	private boolean isArticleSaveRequest() {
		return map.containsKey(this.getFullKey(JWLElements.CREATE_SAVE.id));
	}
	
	private boolean isTopicCreateRequest(){
		return map.containsKey(getFullKey(JWLElements.FORUM_TOPIC_CREATE.id,JWLElements.FORUM_CREATE_TOPIC_FORM.id));
	}
	
	private boolean isPostReplyRequest(){
		return map.containsKey(getFullKey(JWLElements.FORUM_POST_REPLY.id,JWLElements.FORUM_POST_REPLY_FORM.id));
	}
}
