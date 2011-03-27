package com.jwl.business.usecases;

import java.util.List;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.IIncreaseLivablityUC;
import com.jwl.integration.IDAOFactory;

public class IncreaseLivabilityUC extends AbstractUC implements IIncreaseLivablityUC {

	public IncreaseLivabilityUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void addLivability(List<ArticleId> ids, double livability)
			throws ModelException {
		this.checkPermission(AccessPermissions.KNOWLEDGE_ADMINISTER);
		
		IKnowledgeManagementFacade knowledgeFacade=Environment.getKnowledgeFacade();
		for(ArticleId id:ids){
			try {
				knowledgeFacade.addLivability(id, livability);
			} catch (KnowledgeException e) {
				throw new ModelException(e);
			}
		}
		
	}

}
