package com.jwl.business.usecases;

import com.jwl.business.Environment;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.usecases.interfaces.IGetFileUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.cache.AttachmentHome;
import com.jwl.integration.entity.Attachment;
import java.io.File;

/**
 *
 * @author Lukas Rychtecky
 */
public class GetFileUC extends AbstractUC implements IGetFileUC {

	public GetFileUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public File get(String name) throws ModelException {
		try {

			String fileUniqueName = this.getFileUniqueName(name);
			if (fileUniqueName == null) {
				throw new ModelException("File not found");
			}
			return this.getFile(fileUniqueName);
		} catch (Exception e) {
			throw new ModelException("Download failed", e);
		}
	}

	private File getFile(String fileName) {
		File file = new File(Environment.getAttachmentStorage() + File.separator + fileName);
		return file;
	}

	private String getFileUniqueName(String fileTitle) {
		AttachmentHome attachmentHome = new AttachmentHome();
		Attachment attachment = attachmentHome.getAttachmentFromTitle(fileTitle);
		if (attachment != null) {
			return attachment.getUniqueFileName();
		}
		return null;
	}

}
