package com.jwl.business.knowledge.suggestors;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsException;
import com.jwl.business.knowledge.util.ArticleIterator;
import com.jwl.business.knowledge.util.IArticleIterator;
import com.jwl.business.knowledge.util.ISettingsSource;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;

public class LivablilityManager {
	ISettingsSource settings;
	IArticleDAO articleDAO;
	private static final String FEATURE_NAME = "ArticleLivability";

	private enum InputNames {
		INITIAL_VALUE("InitialValue"), POSITIVE_RATING("PositiveRating"), NEGATIVE_RATING(
				"NegativeRating"), PERIODIC_REDUCTION("PeriodicReduction"), VIEW("View");

		public String name;

		private InputNames(String name) {
			this.name = name;
		}
	}

	public LivablilityManager(IArticleDAO articleDAO, ISettingsSource settings) {
		this.settings = settings;
		this.articleDAO = articleDAO;
	}

	public double getInitialValue() {
		float initValue = 0;
		try {
			initValue = settings.getWeight(FEATURE_NAME,
					InputNames.INITIAL_VALUE.name);
		} catch (KnowledgeManagementSettingsException e) {
			Logger.getLogger(LivablilityManager.class.getName()).log(
					Level.SEVERE, "Initial value unavailable");
		}
		return initValue;
	}

	public void doPeriodicReduction() {
		IArticleIterator iterator = new ArticleIterator(articleDAO, 100);
		while (iterator.hasNext()) {
			ArticleTO article = null;
			try {
				article = iterator.getNextArticle();
			} catch (DAOException e) {
				Logger.getLogger(LivablilityManager.class.getName()).log(
						Level.SEVERE, "Article loading failed", e);
			}
			decreaseLivability(article);
		}
	}

	private void decreaseLivability(ArticleTO article) {
		double periodicDecrease = 0;
		try {
			periodicDecrease = settings.getWeight(FEATURE_NAME,
					InputNames.PERIODIC_REDUCTION.name);
		} catch (KnowledgeManagementSettingsException e) {
			Logger.getLogger(LivablilityManager.class.getName()).log(
					Level.SEVERE,
					"Could not retrieve periodic reduction value.");
			return;
		}
		double livability = article.getLivability();
		livability -= periodicDecrease;
		article.setLivability(livability);
		try {
			articleDAO.update(article);
		} catch (DAOException e) {
			Logger.getLogger(LivablilityManager.class.getName()).log(
					Level.SEVERE, "Could not update article livability.");
		}
	}

	public void handleArticleRating(ArticleId articleId, double rating) {
		double change = 0;
		if (rating <= 5) {
			try {
				change = (rating - 6)
						* settings.getWeight(FEATURE_NAME,
								InputNames.NEGATIVE_RATING.name);
			} catch (KnowledgeManagementSettingsException e) {
				Logger.getLogger(LivablilityManager.class.getName())
						.log(Level.SEVERE,
								"Could not retrieve "
										+ InputNames.NEGATIVE_RATING.name);
				return;
			}
		} else {
			try {
				change = (rating - 5)
						* settings.getWeight(FEATURE_NAME,
								InputNames.POSITIVE_RATING.name);
			} catch (KnowledgeManagementSettingsException e) {
				Logger.getLogger(LivablilityManager.class.getName())
						.log(Level.SEVERE,
								"Could not retrieve "
										+ InputNames.POSITIVE_RATING.name);
				return;
			}
		}
		try {
			ArticleTO article = articleDAO.get(articleId);
			double livability = article.getLivability();
			livability += change;
			article.setLivability(livability);
			articleDAO.update(article);
		} catch (DAOException e) {
			Logger.getLogger(LivablilityManager.class.getName()).log(
					Level.SEVERE, "Could not update article livability");
		}
	}

	public void revertArticleRating(ArticleId articleId, double rating) {
		double change = 0;
		if (rating <= 5) {
			try {
				change = (6 - rating)
						* settings.getWeight(FEATURE_NAME,
								InputNames.NEGATIVE_RATING.name);
			} catch (KnowledgeManagementSettingsException e) {
				Logger.getLogger(LivablilityManager.class.getName())
						.log(Level.SEVERE,
								"Could not retrieve "
										+ InputNames.NEGATIVE_RATING.name);
				return;
			}
		} else {
			try {
				change = (5 - rating)
						* settings.getWeight(FEATURE_NAME,
								InputNames.POSITIVE_RATING.name);
			} catch (KnowledgeManagementSettingsException e) {
				Logger.getLogger(LivablilityManager.class.getName())
						.log(Level.SEVERE,
								"Could not retrieve "
										+ InputNames.POSITIVE_RATING.name);
				return;
			}
		}
		try {
			ArticleTO article = articleDAO.get(articleId);
			double livability = article.getLivability();
			livability += change;
			article.setLivability(livability);
			articleDAO.update(article);
		} catch (DAOException e) {
			Logger.getLogger(LivablilityManager.class.getName()).log(
					Level.SEVERE, "Could not update article livability");
		}
	}

	public void addLivability(ArticleId articleId, double livability)
			throws KnowledgeException {
		try {
			ArticleTO article = articleDAO.get(articleId);
			double liv = article.getLivability();
			liv += livability;
			article.setLivability(liv);
			articleDAO.update(article);
		} catch (DAOException e) {
			throw new KnowledgeException(e);
		}
	}
	
	public void handleArticleView(ArticleId articleId){
		try {
			ArticleTO article = articleDAO.get(articleId);
			double liv = article.getLivability();
			liv += settings.getWeight(FEATURE_NAME, InputNames.VIEW.name);
			article.setLivability(liv);
			articleDAO.update(article);
		} catch (Exception e) {
			Logger.getLogger(LivablilityManager.class.getName()).log(
					Level.SEVERE, "Could not update article livability", e);
		}
	}
}
