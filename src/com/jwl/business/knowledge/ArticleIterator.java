package com.jwl.business.knowledge;

import java.util.List;

import com.jwl.business.article.ArticleTO;
import com.jwl.integration.dao.interfaces.IArticleDAO;

public class ArticleIterator implements IArticleIterator {
	private IArticleDAO articleDAO;
	private int batchSize;
	private int articleBatch;
	private int currentArticle;
	private int articleCount;
	private List<ArticleTO> loadedArticles;
	private int articleSum;
	
		
	public ArticleIterator(IArticleDAO articleDAO, int batchSize) {
		this.articleDAO = articleDAO;
		this.articleBatch = 0;
		this.currentArticle=0;
		this.articleCount=0;
		this.loadedArticles=null;
		this.batchSize=batchSize;
		this.articleSum=-1;
	}

	@Override
	public boolean hasNext() {
		if(articleSum==-1){
			articleSum =articleDAO.getCount();
		}
		if(articleCount<articleSum){
			return true;
		}
		return false;
	}

	@Override
	public ArticleTO getNextArticle() {
		if (loadedArticles == null || currentArticle == batchSize) {
			loadedArticles = articleDAO.findAll(articleBatch
					* batchSize, batchSize);
			currentArticle = 0;
			articleBatch++;
			articleSum =articleDAO.getCount();
		}
		ArticleTO a = loadedArticles.get(currentArticle);
		currentArticle++;
		articleCount++;
		return a;
	}
	

}
