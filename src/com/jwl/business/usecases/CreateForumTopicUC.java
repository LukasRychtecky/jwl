package com.jwl.business.usecases;

import java.util.Date;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.ICreateForumTopicUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

public class CreateForumTopicUC extends AbstractUC implements
		ICreateForumTopicUC {

	public CreateForumTopicUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public Integer createTopic(TopicTO topic, ArticleId articleId)
			throws ModelException {
		checkPermission(AccessPermissions.FORUM_CREATE_TOPIC);
		try {
			PostTO initialPost = topic.getPosts().get(0);
			initialPost.setAuthor(Environment.getIdentity().getUserName());
			initialPost.setCreated(new Date());
			Integer topicId = factory.getTopicDAO().create(topic, articleId);
			factory.getPostDAO().create(initialPost, topicId);
			return topicId;
		} catch (DAOException e) {
			throw new ModelException(e);
		}

	}

}
