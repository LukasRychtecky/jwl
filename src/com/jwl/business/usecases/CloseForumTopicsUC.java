package com.jwl.business.usecases;

import java.util.List;

import com.jwl.business.Environment;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.ICloseForumTopicsUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.topic.ITopicDAO;

public class CloseForumTopicsUC extends AbstractUC implements
		ICloseForumTopicsUC {

	public CloseForumTopicsUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void closeTopics(List<Integer> topicIds) throws ModelException {
		checkPermission(AccessPermissions.FORUM_CLOSE_TOPIC);
		ITopicDAO topicDAO = Environment.getDAOFactory().getTopicDAO();
		try {
			for (Integer id : topicIds) {
				topicDAO.close(id);
			}
		} catch (DAOException e) {
			throw new ModelException(e);
		}

	}

}
