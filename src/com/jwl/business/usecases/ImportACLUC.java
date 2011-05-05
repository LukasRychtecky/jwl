package com.jwl.business.usecases;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.Role;
import com.jwl.business.usecases.interfaces.IImportACLUC;
import com.jwl.business.usecases.interfaces.IParseACLUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;
import java.io.File;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lukas Rychtecky
 */
public class ImportACLUC extends AbstractUC implements IImportACLUC {

	public ImportACLUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void importACL(String fileName) throws ModelException {
		
		IParseACLUC uc = new ParseACLUC(factory);
		Set<Role> roles = uc.parse(fileName);
		
		try {
			this.factory.getRoleDAO().save(roles);
			
			File acl = new File(fileName);
			if (!acl.delete()) {
				Logger.getLogger(ImportACLUC.class.getName()).log(Level.SEVERE, null, "Can not delete file " + acl.getName());
			}
		} catch (DAOException e) {
			throw new ModelException(e.getMessage(), e);
		}
	}	
}
