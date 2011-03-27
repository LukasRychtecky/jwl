package com.jwl.business.usecases.interfaces;

import java.util.List;

import com.jwl.business.exceptions.ModelException;

public interface ICloseForumTopicsUC {
	
	public void closeTopics(List<Integer> topicIds) throws ModelException;
}
