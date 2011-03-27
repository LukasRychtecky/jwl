package com.jwl.business.usecases.interfaces;

import java.util.List;

import com.jwl.business.exceptions.ModelException;

public interface IDeleteForumTopicsUC {
	
	public void deleteTopics(List<Integer> topicIds)throws ModelException;
}
