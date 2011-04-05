package com.jwl.business.usecases.interfaces;

import com.jwl.business.exceptions.ModelException;

public interface IDeleteForumPostUC {
	
	public void deletePost(Integer postId) throws ModelException;
}
