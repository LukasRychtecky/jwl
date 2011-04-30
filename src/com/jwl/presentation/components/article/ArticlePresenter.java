package com.jwl.presentation.components.article;

// <editor-fold defaultstate="collapsed">
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletResponse;

import com.jwl.business.Environment;
import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.security.IIdentity;
import com.jwl.business.security.Role;
import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLContextKey;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.global.Global;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.renderers.EncodeAdministrationConsole;
import com.jwl.presentation.renderers.EncodeAttach;
import com.jwl.presentation.renderers.EncodeCreate;
import com.jwl.presentation.renderers.EncodeEdit;
import com.jwl.presentation.renderers.EncodeHistoryListing;
import com.jwl.presentation.renderers.EncodeHistoryView;
import com.jwl.presentation.renderers.EncodeListing;
import com.jwl.presentation.renderers.EncodeTopicCreate;
import com.jwl.presentation.renderers.EncodeTopicList;
import com.jwl.presentation.renderers.EncodeTopicView;
import com.jwl.presentation.renderers.EncodeView;
import com.jwl.presentation.renderers.units.ArticleSuggestionsComponent;
import com.jwl.presentation.renderers.units.FlashMessage;
import com.jwl.presentation.renderers.units.RatingComponent;
import com.jwl.presentation.url.RequestMapDecoder;

// </editor-fold>
public class ArticlePresenter extends AbstractPresenter {

	@Override
	public void renderDefault() {
		super.renderParams.put("paginator", this.getFacade().getPaginator());
		container.addAll(new EncodeListing(linker, this.getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderCreate() {
		if (!super.getFacade().getIdentity().isAuthenticated()) {
			FlashMessage warn = new FlashMessage("Your user name is unknown! "
					+ "It will record your IP adress.",
					FlashMessage.FlashMessageType.WARNING);
			this.messages.add(warn);
		}
		super.renderParams.put("form", this.createFormArticleCreate());
		container.addAll(new EncodeCreate(linker, this.getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderEdit() {
		ArticleTO article = (ArticleTO) context.getAttributes().get(JWLContextKey.ARTICLE);
		
		if (article == null) {
			FlashMessage message = new FlashMessage("Article not found!",
					FlashMessage.FlashMessageType.WARNING);
			super.messages.add(message);
			super.redirect("default");
		} else {
			HtmlAppForm form = super.getForm("ArticleEdit");
			form.get("text").setValue(article.getText());

			StringBuilder tags = new StringBuilder();
			for (String tag : article.getTags()) {
				tags.append(tag).append(", ");
			}
			if (tags.length() > 0) {
				form.get("tags").setValue(tags.substring(0, tags.length() - 1));
			}

			form.get("title").setValue(article.getTitle());
			renderParams.put("article", article);
			renderParams.put("form", form);
			container.addAll(new EncodeEdit(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
		}
	}

	public void renderList() {
		this.renderDefault();
	}

	public void renderView() throws ModelException {
		ArticleTO article = (ArticleTO) super.context.getAttributes().get(
				JWLContextKey.ARTICLE);
		List<ArticleTO> similarArticles = getFacade().getSimilarArticlesInView(
				article);
		renderParams.put("similarArticles", similarArticles);
		container.addAll(new EncodeView(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderAttachFile() {
		container.addAll(new EncodeAttach(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderAdministrationConsole() {
		container.addAll(new EncodeAdministrationConsole(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderHistoryView() throws ModelException {		
		HistoryId historyId = (HistoryId) context.getAttributes().get(JWLContextKey.HISTORY_ID);
		renderParams.put("history", getFacade().getHistory(historyId));
		container.addAll(new EncodeHistoryView(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderHistoryList() throws ModelException {
		ArticleTO article = (ArticleTO) context.getAttributes().get(JWLContextKey.ARTICLE);
		renderParams.put("histories", super.getFacade().getHistories(article.getId()));
		container.addAll(new EncodeHistoryListing(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderTopicList() throws ModelException {
		ArticleTO article = (ArticleTO) context.getAttributes().get(JWLContextKey.ARTICLE);
		renderParams.put("paginator", super.getFacade().getArticleForumTopics(article.getId()));
		container.addAll(new EncodeTopicList(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderTopicCreate() {
		container.addAll(new EncodeTopicCreate(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderTopicView() throws ModelException {
		boolean isAnswering = super.urlParser.containsAnswering();
		String stringQuopteTopicId = super.urlParser.getQuopteTopicId();
		Integer quopteTopicId = null;
		if (stringQuopteTopicId != null) {
			quopteTopicId = Integer.parseInt(stringQuopteTopicId);
		}
		
		renderParams.put("answering", isAnswering);
		renderParams.put("quotePostId", quopteTopicId);
		
		Integer topicId = (Integer) this.context.getAttributes().get(JWLContextKey.TOPIC_ID);
		renderParams.put("topic", this.getFacade().getTopic(topicId));
		container.addAll(new EncodeTopicView(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderArticleSuggestions() {
		Map<String, String> requestParams = context.getExternalContext().getRequestParameterMap();
		String title = requestParams.get("jwltitle");
		String tags = requestParams.get("jwltext");
		String text = requestParams.get("jwltags");
		IKnowledgeManagementFacade knowledgeFacade = Environment.getKnowledgeFacade();
		List<ArticleTO> similarArticles = knowledgeFacade.suggestSimilarArticlesEdit(tags, title, text);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.setContentType("text/plain");
		container.addAll(ArticleSuggestionsComponent.getComponent(
				similarArticles, context));
	}

	public void renderRating() throws ModelException {
		Map<String, String> requestParams = context.getExternalContext().getRequestParameterMap();
		String ratedValue = requestParams.get("jwlratedValue");
		String articleId = requestParams.get("jwlarticleId");
		float ratedValueNum = Float.parseFloat(ratedValue);
		int articleIdNum = Integer.parseInt(articleId);
		ArticleId id = new ArticleId(articleIdNum);
		this.getFacade().rateArticle(id, ratedValueNum);
		ArticleTO article = this.getFacade().getArticle(id);
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(RatingComponent.getRatingInternals(article.getRatingAverage(), article.getId()));
		container.addAll(components);
	}

	protected HtmlAppForm buildArticleForm(String name) {
		HtmlAppForm form = new HtmlAppForm(name);
		form.addText("title", "Title", null);
		form.addTextArea("text", "Text", null).setStyleClass("markMe");
		form.addText("tags", "Tags", null);
		form.addText("changeNote", "Change note", null);
		return form;
	}

	public HtmlAppForm createFormArticleCreate() {
		HtmlAppForm form = this.buildArticleForm("ArticleCreate");
		form.addSubmit("submit", "Create", null);
		form.setAction(this.linker.buildForm("articleCreate", "view"));
		return form;
	}

	public HtmlAppForm createFormArticleEdit() {
		HtmlAppForm form = this.buildArticleForm("ArticleEdit");
		form.addHidden("title", null);
		form.addSubmit("submit", "Save", null);
		form.setAction(this.linker.buildForm("articleUpdate", "view"));
		return form;
	}

	public void decodeTopicCreate() throws ModelException {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.FORUM_CREATE_TOPIC_FORM);

		PostTO post = new PostTO();
		post.setText(decoder.getValue(JWLElements.FORUM_TOPIC_TEXT));
		List<PostTO> posts = new ArrayList<PostTO>();
		posts.add(post);

		TopicTO topic = new TopicTO();
		String subject = decoder.getValue(JWLElements.FORUM_SUBJECT);
		topic.setTitle(subject);
		topic.setPosts(posts);

		ArticleId articleId = (ArticleId) super.context.getAttributes().get(
				JWLContextKey.ARTICLE_ID);
		Integer topicId = this.getFacade().createForumTopic(topic, articleId);

		super.context.getAttributes().put(JWLContextKey.TOPIC_ID, topicId);
	}

	public void decodePostReply() throws ModelException {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.FORUM_POST_REPLY_FORM);
		String text = decoder.getValue(JWLElements.FORUM_POST_TEXT);
		String id = decoder.getValue(JWLElements.FORUM_POST_TOPIC_ID);

		PostTO post = new PostTO();
		post.setText(text);
		Integer postId = new Integer(id);
		this.getFacade().addForumPost(post, postId);
	}

	public void decodePostDelete() throws ModelException {
		for (Entry<String, String> e : getRequestParamMap().entrySet()) {
			if (e.getKey().contains(JWLElements.FORUM_POST_ADMIN_FORM.id)) {
				String idStr = e.getKey().substring(
						JWLElements.FORUM_POST_ADMIN_FORM.id.length() + 1);
				int hyphenIndx = idStr.indexOf('-');
				if (hyphenIndx != -1) {
					idStr = idStr.substring(0, hyphenIndx);

				}

				Integer postId = new Integer(idStr);
				this.getFacade().deleteForumPost(postId);
				return;
			}
		}
	}

	public void decodeArticleCreate() {
		try {
			HtmlAppForm form = super.getForm("ArticleCreate");
			ArticleTO article = this.prepareArticle(form);
			ArticleId articleId = this.getFacade().createArticle(article);
			article.setId(articleId);

			messages.add(new FlashMessage("Article has been saved."));
			super.context.getAttributes().put(JWLContextKey.ARTICLE, article);
		} catch (NoPermissionException ex) {
			FlashMessage message = new FlashMessage(
					"You don't have a permission.",
					FlashMessage.FlashMessageType.WARNING);
			super.messages.add(message);
		} catch (ModelException ex) {
			super.defaultProcessException(ex);
		}
	}

	public void decodeArticleUpdate() {
		try {
			HtmlAppForm form = super.getForm("ArticleEdit");
			ArticleTO article = this.prepareArticle(form);
			article.setId(this.getFacade().findArticleByTitle(article.getTitle()).getId());
			this.getFacade().updateArticle(article);
			messages.add(new FlashMessage("Article has been saved."));
			super.context.getAttributes().put(JWLContextKey.ARTICLE, article);
		} catch (NoPermissionException ex) {
			FlashMessage message = new FlashMessage(
					"You don't have a permission.",
					FlashMessage.FlashMessageType.WARNING);
			super.messages.add(message);
		} catch (ModelException ex) {
			super.defaultProcessException(ex);
		}
	}

	public void decodeArticleDelete() {
		try {
			ArticleId articleId = (ArticleId) super.context.getAttributes().get(JWLContextKey.ARTICLE_ID);
			this.getFacade().deleteArticle(articleId);
			messages.add(new FlashMessage("Article was deleted."));
		} catch (ModelException ex) {
			super.defaultProcessException(ex);
		}
	}

	public void decodeArticleLock() {
		try {
			ArticleId articleId = (ArticleId) super.context.getAttributes().get(JWLContextKey.ARTICLE_ID);
			this.getFacade().lockArticle(articleId);
			messages.add(new FlashMessage("Article was locked."));
		} catch (ModelException ex) {
			super.defaultProcessException(ex);
		}
	}

	public void decodeArticleUnlock() {
		try {
			ArticleId articleId = (ArticleId) super.context.getAttributes().get(JWLContextKey.ARTICLE_ID);
			this.getFacade().unlockArticle(articleId);
			messages.add(new FlashMessage("Article was locked."));
		} catch (ModelException ex) {
			super.defaultProcessException(ex);
		}
	}

	public void decodeHistoryRestore() throws ModelException {
		HistoryId historyId = (HistoryId) super.context.getAttributes().get(
				JWLContextKey.HISTORY_ID);
		this.getFacade().restoreArticle(historyId);
		messages.add(new FlashMessage("Article was restored."));
	}

	public void decodeTopicList() throws ModelException {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.FORUM_TOPIC_ADMIN_FORM);

		List<Integer> topicIds = getCheckedTopicIds();
		if (topicIds.isEmpty()) {
			return;
		}

		if (decoder.containsKey(JWLElements.FORUM_TOPIC_DELETE)) {
			this.getFacade().deleteForumTopics(topicIds);
		} else if (decoder.containsKey(JWLElements.FORUM_TOPIC_CLOSE)) {
			this.getFacade().closeForumTopics(topicIds);
		} else if (decoder.containsKey(JWLElements.FORUM_TOPIC_OPEN)) {
			this.getFacade().openForumTopics(topicIds);
		}
	}

	private List<Integer> getCheckedTopicIds() {
		int elementCheckBoxLength = JWLElements.FORUM_TOPIC_ADMIN_FORM.id.length()
				+ JWLElements.FORUM_TOPIC_CHBX.id.length()
				+ (2 * AbstractComponent.JWL_HTML_ID_SEPARATOR.length());

		List<Integer> topicIds = new ArrayList<Integer>();
		for (Entry<String, String> e : getRequestParamMap().entrySet()) {
			if (e.getKey().contains(JWLElements.FORUM_TOPIC_CHBX.id)) {
				String idPart = e.getKey().substring(elementCheckBoxLength);
				int topicId = Integer.parseInt(idPart);
				;
				topicIds.add(topicId);
			}
		}
		return topicIds;
	}

	private ArticleTO prepareArticle(HtmlAppForm form) {
		ArticleTO article = new ArticleTO();
		article.setTitle(form.get("title").getValue().toString());
		article.setText(form.get("text").getValue().toString());

		String[] tags = form.get("tags").getValue().toString().split(",");
		for (int i = 0; i < tags.length; i++) {
			article.addTag(tags[i].trim());
		}

		article.setChangeNote(form.get("changeNote").getValue().toString());

		String editor = getFacade().getIdentity().getUserName();
		if (editor.isEmpty()) {
			editor = (String) this.context.getAttributes().get(
					JWLContextKey.USER_IP);
		}
		article.setEditor(editor);

		return article;
	}
}
