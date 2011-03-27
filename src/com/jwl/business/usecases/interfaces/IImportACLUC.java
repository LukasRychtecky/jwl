package com.jwl.business.usecases.interfaces;

import com.jwl.business.exceptions.ModelException;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IImportACLUC {

	public void importACL(String fileName) throws ModelException;

}
