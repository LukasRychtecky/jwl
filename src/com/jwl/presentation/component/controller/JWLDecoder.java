package com.jwl.presentation.component.controller;

import javax.naming.NoPermissionException;

import com.jwl.business.exceptions.ModelException;

public interface JWLDecoder {

	void processDecode() throws ModelException, NoPermissionException;
	
}
