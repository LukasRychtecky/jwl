package com.jwl.business.usecases;

import java.util.ArrayList;
import java.util.List;

import com.jwl.business.ArticlePair;
import com.jwl.business.Environment;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.knowledge.ArticleIdPair;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.usecases.interfaces.IGetMergeSuggestionsUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;

public class GetMergeSuggestionsUC extends AbstractUC implements
		IGetMergeSuggestionsUC {

	public GetMergeSuggestionsUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public List<ArticlePair> getMergeSuggestions() {
		IKnowledgeManagementFacade knowledgeFacade = Environment.getKnowledgeFacade();
		List<ArticleIdPair> articlesIdpairs = new ArrayList<ArticleIdPair>();
		try {
			articlesIdpairs =knowledgeFacade.getPregeneratedMergeSuggestions();
		} catch (KnowledgeException e1) {
		}
		IArticleDAO adao = factory.getArticleDAO();
		List<ArticlePair> articlePairs = new ArrayList<ArticlePair>();
		try{
		for(ArticleIdPair aip : articlesIdpairs){
			ArticleTO article1 = adao.get(aip.getId1());
			ArticleTO article2 = adao.get(aip.getId2());
			articlePairs.add(new ArticlePair(article1, article2));
		}
		}catch(DAOException e){}
		return articlePairs;
	}
	
}
