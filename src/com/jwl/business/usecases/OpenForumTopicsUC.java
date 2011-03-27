package com.jwl.business.usecases;

import java.util.List;

import com.jwl.business.Environment;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.usecases.interfaces.IOpenForumTopicsUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.topic.ITopicDAO;

public class OpenForumTopicsUC extends AbstractUC implements IOpenForumTopicsUC {

	public OpenForumTopicsUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void openTopics(List<Integer> topicIds) throws ModelException {
		// checkPermission(AccessPermissions.FORUM_DELETE_TOPIC);
		ITopicDAO topicDAO = Environment.getDAOFactory().getTopicDAO();
		try {
			for (Integer id : topicIds) {
				topicDAO.open(id);
			}
		} catch (DAOException e) {
			throw new ModelException(e);
		}

	}

}
