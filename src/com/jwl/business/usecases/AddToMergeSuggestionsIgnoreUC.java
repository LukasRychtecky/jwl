package com.jwl.business.usecases;

import java.util.List;

import com.jwl.business.Environment;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.knowledge.util.ArticleIdPair;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.IAddToMergeSuggestionsIgnoreUC;
import com.jwl.integration.IDAOFactory;

public class AddToMergeSuggestionsIgnoreUC extends AbstractUC implements IAddToMergeSuggestionsIgnoreUC{

	public AddToMergeSuggestionsIgnoreUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void addToIgnored(List<ArticleIdPair> idPairs) throws ModelException {
		checkPermission(AccessPermissions.KNOWLEDGE_ADMINISTER);
		IKnowledgeManagementFacade knowledgefacade=Environment.getKnowledgeFacade();
		for(ArticleIdPair idPair : idPairs){
			try {
				knowledgefacade.addIgnoredMergeSuggestion(idPair);
			} catch (KnowledgeException e) {
				throw new ModelException(e);
			}
		}
	}
	

}
