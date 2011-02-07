package com.jwl.integration.cache;

import java.util.List;
import com.jwl.integration.entity.Article;

public class ArticleHome extends EntityHome<Article> {

	private static final long serialVersionUID = -7352404533083573829L;
	private static final String FIND_BY_TITLE = "Article.findByTitle";

	public Article getArticleByTitle(String title) {
		if (null == title || title.isEmpty()) { 
			return null;
		}

		String[] params = new String[]{title};
		List<?> results = this.entityManagerDao.doNamedQuery(ArticleHome.FIND_BY_TITLE, params);
		if (results.isEmpty()) {
			return null;
		}
		Article article = (Article) results.get(0);
		if (article != null) {
			this.setInstance(article);
			return article;
		} else {
			return null;
		}
	}
}
