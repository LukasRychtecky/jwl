package com.jwl.business.article.usecases;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jwl.business.FileExpert;
import com.jwl.business.article.AttachmentTO;
import com.jwl.business.article.usecases.interfaces.IUploadFileUC;
import com.jwl.business.exceptions.BusinessProcessException;
import com.jwl.presentation.component.enumerations.JWLElements;

public class UploadFileUC implements IUploadFileUC {

	private static final String DESTINATION_DIR_PATH = "c:\\wikifiles";
	private static final String TMP_DIR_PATH = DESTINATION_DIR_PATH
			+ File.separator + "tmp";
	private HttpServletRequest request;
	private File tmpDir;
	private File destinationDir;
	String articleTitle = "";
	String fileTitle = "";
	String originalFileName = "";
	String uniqueFileName = "";
	String description = "";
	FileItem receivedFile;

	public UploadFileUC(HttpServletRequest request) {
		this.request = request;
		this.tmpDir = new File(TMP_DIR_PATH);
		this.destinationDir = new File(DESTINATION_DIR_PATH);
	}

	@Override
	public AttachmentTO uploadFile() throws BusinessProcessException {
		try {
			this.setupUpload();
			this.parseFileUploadRequest();
			this.checkIfFileIsSupported();
			this.saveFileOnDisc(destinationDir, receivedFile);

			return createAttachmentTO(articleTitle, fileTitle, originalFileName,
					uniqueFileName, description);

		} catch (IOException e) {
			throw new BusinessProcessException(e);
		} catch (FileUploadException e) {
			throw new BusinessProcessException(e);
		} catch (Exception e) {
			throw new BusinessProcessException(
					"The file could not be saved on disc:", e);
		}
	}

	private void setupUpload() throws IOException {
		checkIfPathExist(tmpDir);
		checkIfPathExist(destinationDir);
		ckeckIfPathIsDirectory(tmpDir);
		ckeckIfPathIsDirectory(destinationDir);
	}

	private void checkIfPathExist(File path) throws IOException {
		if (!destinationDir.exists()) {
			throw new IOException(path + " does not exist.");
		}
	}

	private void ckeckIfPathIsDirectory(File path) throws IOException {
		if (path.isFile()) {
			throw new IOException(path + " is not a directory");
		}
	}

	private void parseFileUploadRequest() throws FileUploadException {
		List<?> items = getParsedReques();
		Iterator<?> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (item.isFormField()) {
				this.saveInformationFromFormFields(item);
			} else {
				originalFileName = item.getName();
				receivedFile = item;
			}
		}
	}

	private List<?> getParsedReques() throws FileUploadException {
		DiskFileItemFactory fileItemFactory = getFileItemFactory();
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		return uploadHandler.parseRequest(request);
	}

	private DiskFileItemFactory getFileItemFactory() {
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setSizeThreshold(1 * 1024 * 1024); // 1 MB
		fileItemFactory.setRepository(tmpDir);
		return fileItemFactory;
	}

	private void saveInformationFromFormFields(FileItem item) {
		if (item.getFieldName().endsWith(JWLElements.FILE_TITLE.id)) {
			fileTitle = item.getString();
		}
		if (item.getFieldName().endsWith(JWLElements.FILE_ARTICLE_TITLE.id)) {
			articleTitle = item.getString();
		}
		if (item.getFieldName().endsWith(JWLElements.FILE_DESCRIPTION.id)) {
			description = item.getString();
		}
	}

	private void checkIfFileIsSupported() throws BusinessProcessException {
		if (originalFileName == null
				|| !this.isSupportedFileType(originalFileName)) {
			throw new BusinessProcessException("File type is not supported.");
		}
	}

	private boolean isSupportedFileType(String originalFileName) {
		FileExpert fileExpert = new FileExpert();
		return fileExpert.isSupportedFileType(originalFileName);
	}

	private void saveFileOnDisc(File destinationDir, FileItem fileItem)
			throws Exception {
		File file = new File(destinationDir, createUniqueFileName());
		fileItem.write(file);
	}

	private String createUniqueFileName() {
		uniqueFileName = FileExpert.generateUniqueName(receivedFile);
		uniqueFileName = this.addSuffixToUniqueName(originalFileName,
				uniqueFileName);
		return uniqueFileName;
	}

	private String addSuffixToUniqueName(String originalName, String uniqueName) {
		int suffixIndex = originalName.lastIndexOf('.');
		if (suffixIndex != -1) {
			uniqueName = uniqueName + originalName.substring(suffixIndex);
		}
		return uniqueName;
	}

	private AttachmentTO createAttachmentTO(String articleTitle,
			String fileTitle, String originalFileName, String uniqueFileName,
			String fileDescription) {
		AttachmentTO attachment = new AttachmentTO();
		attachment.setTitle(fileTitle);
		attachment.setOriginalName(originalFileName);
		attachment.setUniqueName(uniqueFileName);
		attachment.setDescription(fileDescription);
		attachment.setArticleTitle(articleTitle);

		return attachment;
	}
}
