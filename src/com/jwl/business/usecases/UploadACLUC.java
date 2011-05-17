package com.jwl.business.usecases;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.IUploadACLUC;
import com.jwl.integration.IDAOFactory;
import java.io.File;

/**
 *
 * @author Lukas Rychtecky
 */
public class UploadACLUC extends AbstractUC implements IUploadACLUC {

	public UploadACLUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void upload(File file, String fileName) throws ModelException {
		super.checkPermission(AccessPermissions.SECURITY_IMPORT);
		File destinationFile = new File(fileName);		
		file.renameTo(destinationFile);
	}
	
}
