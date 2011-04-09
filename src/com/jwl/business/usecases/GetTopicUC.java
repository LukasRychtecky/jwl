package com.jwl.business.usecases;

import com.jwl.business.Environment;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.IGetTopicUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

public class GetTopicUC extends AbstractUC implements IGetTopicUC {

	public GetTopicUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public TopicTO getTopic(Integer topicId) throws ModelException {
		super.checkPermission(AccessPermissions.FORUM_VIEW);
		TopicTO topic = null;		
		try {
			topic = Environment.getDAOFactory().getTopicDAO().find(topicId);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
		return topic;
	}

}
