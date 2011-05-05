package com.jwl.business.usecases;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.security.Role;
import com.jwl.business.usecases.interfaces.IParseACLUC;
import com.jwl.integration.IDAOFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lukas Rychtecky
 */
public class ParseACLUC extends AbstractUC implements IParseACLUC {

	public ParseACLUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public Set<Role> parse(String fileName) throws ModelException {
		super.checkPermission(AccessPermissions.SECURITY_IMPORT);
		
		File acl = new File(fileName);
		if (!acl.exists()) {
			throw new ObjectNotFoundException(fileName + " does not exist!");
		}
		if (!acl.canRead()) {
			throw new ObjectNotFoundException(fileName + " is not readable!");
		}

		Map<Integer, Role> roles = this.parsePermissions(acl);
		return new HashSet<Role>(roles.values());
	}
	private String removeQuotes(String token) {
		if (token.startsWith("\"")) {
			token = token.substring(1, token.length());
		}
		if (token.endsWith("\"")) {
			token = token.substring(0, token.length() - 1);
		}
		return token;
	}

	private Map<Integer, Role> parsePermissions(File acl) throws ModelException {
		BufferedReader buffer = null;
		Map<Integer, Role> roles = new HashMap<Integer, Role>();
		try {
			buffer = new BufferedReader(new FileReader(acl));
			String line = null;
			int lineNumber = 1;
			String context = "";

			while ((line = buffer.readLine()) != null) {

				String[] tokens = line.split(";");

				if (lineNumber == 1) {
					//TODO: check dim
					for (int i = 2; i < tokens.length; i++) {
						roles.put(i, new Role(this.removeQuotes(tokens[i])));
					}
					lineNumber++;
					continue;
				}

				String stripped = this.removeQuotes(tokens[0]);
				if (!stripped.isEmpty()) {
					context = this.removeQuotes(tokens[0]);
				}
				String method = this.removeQuotes(tokens[1]);

				for (int i = 2; i < tokens.length; i++) {
					String checkMark = this.removeQuotes(tokens[i]);
					
					if (checkMark.equalsIgnoreCase("X")) {
						String name = context + "_" + method;
						try {
							AccessPermissions perm = AccessPermissions.valueOf(name.toUpperCase());
							//TODO: check dims
							Role role = roles.get(i);
							role.addPermission(perm);
							roles.put(i, role);
						} catch (IllegalArgumentException e) {
							//TODO: skip unknow permission
						}
					}
				}

			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(ImportACLUC.class.getName()).log(Level.SEVERE, null, ex);
			throw new ModelException(ex.getMessage(), ex);
		} catch (IOException ex) {
			Logger.getLogger(ImportACLUC.class.getName()).log(Level.SEVERE, null, ex);
			throw new ModelException(ex.getMessage(), ex);
		} finally {
			try {
				buffer.close();
			} catch (IOException ex) {
				Logger.getLogger(ImportACLUC.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return roles;
	}
}
