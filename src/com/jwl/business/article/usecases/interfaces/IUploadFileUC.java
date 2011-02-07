package com.jwl.business.article.usecases.interfaces;

import com.jwl.business.article.AttachmentTO;
import com.jwl.business.exceptions.BusinessProcessException;

public interface IUploadFileUC {

	
	public AttachmentTO uploadFile() throws BusinessProcessException;
	
}
