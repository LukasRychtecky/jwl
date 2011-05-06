package com.jwl.business.usecases;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.usecases.interfaces.IGetAllTagsUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lukas Rychtecky
 */
public class GetAllTagsUC extends AbstractUC implements IGetAllTagsUC {

	public GetAllTagsUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public List<String> get() throws ModelException {
		List<String> tags = new ArrayList<String>();
		try {
			tags = super.factory.getTagDAO().getAll();
		} catch (DAOException e) {
			throw new ModelException(e);
		}
		return tags;
	}
	
}
