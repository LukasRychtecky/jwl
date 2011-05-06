package com.jwl.business.usecases;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.security.Role;
import com.jwl.business.usecases.interfaces.IExportACLUC;
import com.jwl.business.usecases.interfaces.IGetAllRolesUC;
import com.jwl.integration.IDAOFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lukas Rychtecky
 */
public class ExportACLUC extends AbstractUC implements IExportACLUC {

	public ExportACLUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public File export(File file) throws ModelException {
		super.checkPermission(AccessPermissions.SECURITY_IMPORT);
		
		if (file.exists()) {
			return file;
		}
		try {
			Boolean isCreated = file.createNewFile();
			if (!isCreated) {
				throw new ModelException("Can not create file " + file.getName());
			}
			
			IGetAllRolesUC uc = new GetAllRolesUC(factory);
			Set<Role> roles = uc.get();
			
			FileWriter writer = new FileWriter(file);
			writer.append("\"Context\";");
			writer.append("\"Method\";");
			
			for (Role role : roles) {
				writer.append("\"").append(role.getCode()).append("\";");
			}
			writer.append(System.getProperty("line.separator"));
			
			String context = "";
			
			for (AccessPermissions perm : AccessPermissions.values()) {
				if (context.equals(perm.getContext())) {
					writer.append(";");
				} else {
					context = perm.getContext();
					writer.append("\"").append(context).append("\";");
				}
				writer.append("\"").append(perm.getMethod()).append("\";");

				for (Role role : roles) {
					writer.append(this.getCell(role, perm));
				}
				writer.append(System.getProperty("line.separator"));
			}
			writer.flush();
			writer.close();
			
		} catch (IOException ex) {
			throw new ModelException(ex);
		}
		return file;
	}
	
	private String getCell(Role role, AccessPermissions perm) {
		if (role.getPermissions().contains(perm)) {
			return "\"X\";";
		}
		return ";";
	}
	
}
