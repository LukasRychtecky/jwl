package com.jwl.business.article.process;

import javax.servlet.http.HttpServletRequest;

import com.jwl.business.article.AttachmentTO;
import com.jwl.business.article.process.interfaces.IProcess;
import com.jwl.business.article.usecases.CreateAttachmentUC;
import com.jwl.business.article.usecases.UploadFileUC;
import com.jwl.business.article.usecases.interfaces.ICreateAttachmentUC;
import com.jwl.business.article.usecases.interfaces.IUploadFileUC;
import com.jwl.business.exceptions.BusinessProcessException;
import com.jwl.business.exceptions.InsufficientArticleDataException;

/**
 * 
 * @author Petr Janouch
 * 
 */
public class FileUploadProcess implements IProcess {
	
	HttpServletRequest request;

	public FileUploadProcess(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void doIt() throws BusinessProcessException {
		AttachmentTO attachmentTO = this.saveAttachmentOnDisk();
		this.persistAttachment(attachmentTO);
	}

	private AttachmentTO saveAttachmentOnDisk() throws BusinessProcessException {
		IUploadFileUC uploadFile = new UploadFileUC(request);
		return uploadFile.uploadFile();
		
	}

	private Integer persistAttachment(AttachmentTO attachment)
			throws InsufficientArticleDataException {
		ICreateAttachmentUC uc = new CreateAttachmentUC();
		return uc.createAttachment(attachment);
	}

}
