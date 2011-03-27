package com.jwl.business.usecases.interfaces;

import com.jwl.business.exceptions.ModelException;
import java.io.File;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IGetFileUC {

	public File get(String name) throws ModelException;

}
