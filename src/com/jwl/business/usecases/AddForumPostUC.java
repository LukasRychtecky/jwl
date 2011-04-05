package com.jwl.business.usecases;

import java.util.Date;

import com.jwl.business.Environment;
import com.jwl.business.article.PostTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.IAddForumPostUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

public class AddForumPostUC extends AbstractUC implements IAddForumPostUC {

	public AddForumPostUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void add(PostTO post, Integer topicId) throws ModelException {
		checkPermission(AccessPermissions.FORUM_ADD_POST);
		post.setCreated(new Date());
		post.setAuthor(Environment.getIdentity().getUserName());
		try {
			factory.getPostDAO().create(post, topicId);
		} catch (DAOException e) {
			throw new ModelException(e);
		}

	}

}
