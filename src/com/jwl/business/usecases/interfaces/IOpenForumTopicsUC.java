package com.jwl.business.usecases.interfaces;

import java.util.List;

import com.jwl.business.exceptions.ModelException;

public interface IOpenForumTopicsUC {
	
	public void openTopics(List<Integer> topicIds) throws ModelException;
}
