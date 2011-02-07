package com.jwl.business.article.process.interfaces;

import com.jwl.business.exceptions.BusinessProcessException;

public interface IProcess {

	void doIt() throws BusinessProcessException;
	
}
