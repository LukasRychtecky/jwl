package com.jwl.business.usecases;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.IDeleteForumPostUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

public class DeleteForumPostUC extends AbstractUC implements IDeleteForumPostUC {

	public DeleteForumPostUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void deletePost(Integer postId) throws ModelException {
		checkPermission(AccessPermissions.FORUM_DELETE_POST);
		try {
			factory.getPostDAO().delete(postId);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
