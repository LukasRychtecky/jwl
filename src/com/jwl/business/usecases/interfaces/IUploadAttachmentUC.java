package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.AttachmentTO;
import com.jwl.business.exceptions.ModelException;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IUploadAttachmentUC {

	public void upload(AttachmentTO attachment, String source, String destinationDir) throws ModelException;

}
