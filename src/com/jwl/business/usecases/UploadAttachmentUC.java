package com.jwl.business.usecases;

import com.jwl.business.FileExpert;
import com.jwl.business.article.AttachmentTO;
import com.jwl.business.article.usecases.CreateAttachmentUC;
import com.jwl.business.article.usecases.interfaces.ICreateAttachmentUC;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.IUploadAttachmentUC;
import com.jwl.integration.IDAOFactory;
import java.io.File;

/**
 *
 * @author Lukas Rychtecky
 */
public class UploadAttachmentUC extends AbstractUC implements IUploadAttachmentUC {

	public UploadAttachmentUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void upload(AttachmentTO attachment, String source, String destinationDir) throws ModelException {
		this.checkPermission(AccessPermissions.ATTACHMENT_ADD);
		
		File sourceFile = new File(source);
		File destinationFile = new File(destinationDir, sourceFile.getName());

		if (!sourceFile.exists()) {
			throw new ModelException("File is not exist, file: " + source);
		}

		FileExpert expert = new FileExpert();
		if (!expert.isSupportedFileType(source)) {
			throw new ModelException("File type is not supported, file: " + source);
		}
		
		if (!sourceFile.renameTo(destinationFile)) {
			throw new ModelException("Attachment can't be moved to " + destinationDir);
		}

		this.persistAttachment(attachment);
	}

	private Integer persistAttachment(AttachmentTO attachment) throws ModelException {
		ICreateAttachmentUC uc = new CreateAttachmentUC();
		return uc.createAttachment(attachment);
	}

}
