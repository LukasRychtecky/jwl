package com.jwl.business.usecases.interfaces;

import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;

public interface IIncreaseLivablityUC {
	public void addLivability(List<ArticleId> ids, double livability) throws ModelException;
}
