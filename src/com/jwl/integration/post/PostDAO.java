package com.jwl.integration.post;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.jwl.business.article.PostTO;
import com.jwl.integration.BaseDAO;
import com.jwl.integration.convertor.PostConverter;
import com.jwl.integration.entity.Post;
import com.jwl.integration.entity.Topic;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.DuplicateEntryException;

public class PostDAO extends BaseDAO implements IPostDAO {

	@Override
	public void create(PostTO post, Integer topicId) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		Post entity = PostConverter.convertToEntity(post);
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			Topic topic = em.find(Topic.class, topicId);
			entity.setTopic(topic);
			em.persist(entity);
			em.flush();
			if (localTrans) {
				ut.commit();
			}
		} catch (EntityExistsException e) {
			try {
				ut.rollback();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new DuplicateEntryException(e);
		} catch (Throwable e) {
			try {
				ut.rollback();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new DAOException(e);
		} finally{
			closeEntityManager(em);
		}

	}

}
