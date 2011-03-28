package com.jwl.business;

import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.TopicTO;

public class TopicPaginator extends AbstractEagerPaginator<TopicTO>{
	
	private ArticleId articleId;

	public ArticleId getArticleId() {
		return articleId;
	}

	public void setArticleId(ArticleId articleId) {
		this.articleId = articleId;
		
	}
	
	public void setSearchResults(List<TopicTO> topics){
		this.searchResults =topics;
	}
}
