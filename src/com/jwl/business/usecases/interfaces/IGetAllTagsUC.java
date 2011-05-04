package com.jwl.business.usecases.interfaces;

import com.jwl.business.exceptions.ModelException;
import java.util.List;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IGetAllTagsUC {
	
	public List<String> get() throws ModelException;
	
}
