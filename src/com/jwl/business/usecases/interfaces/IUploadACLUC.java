/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwl.business.usecases.interfaces;

import com.jwl.business.exceptions.ModelException;
import java.io.File;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IUploadACLUC {
	
	public void upload(File file, String fileName) throws ModelException;
	
}
