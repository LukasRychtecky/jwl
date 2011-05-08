package com.jwl.presentation.global;

import com.jwl.business.article.AttachmentTO;
import com.jwl.presentation.enumerations.JWLElements;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Lukas Rychtecky
 */
public class FileMover {

	private String jwlHome = "";
	private HttpServletRequest request;
	private File dir;
	private FileItem receivedFile;
	private String originalFileName = "";
	private String uniqueFileName = "";
	private String explicitFileName = "";
	private AttachmentTO attachment;

	public FileMover(HttpServletRequest request) {
		this.request = request;
		this.jwlHome = request.getSession().getServletContext().getRealPath("/jwl/");
		this.attachment = new AttachmentTO();
	}

	public String moveToTMP() throws IOException, FileUploadException, Exception {
		this.dir = new File(this.jwlHome + File.separator + "private" + File.separator + "tmp");

		this.checkDir();
		this.parseFileUploadRequest();
		this.saveFileOnDisc(this.dir, this.receivedFile);
		return this.dir.getPath() + File.separator + this.uniqueFileName;
	}

	public AttachmentTO getAttachment() {
		this.attachment.setUniqueName(this.uniqueFileName);
		return this.attachment;
	}

	protected void checkDir() throws IOException {
		if (!this.dir.exists()) {
			throw new IOException(this.dir.getAbsolutePath() + " does not exist.");
		}

		if (!this.dir.canWrite()) {
			throw new IOException(this.dir.getAbsolutePath() + " is not writable!");
		}
	}

	private List<?> getParsedRequest() throws FileUploadException {
		DiskFileItemFactory fileItemFactory = getFileItemFactory();
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		return uploadHandler.parseRequest(this.request);
	}

	private DiskFileItemFactory getFileItemFactory() {
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setSizeThreshold(1 * 1024 * 1024); // 1 MB
		fileItemFactory.setRepository(this.dir);
		return fileItemFactory;
	}

	private void saveFileOnDisc(File destinationDir, FileItem fileItem)	throws Exception {
		File file = null;
		if (this.explicitFileName.isEmpty()) {
			file = new File(destinationDir, this.createUniqueFileName());
		} else {
			file = new File(destinationDir, this.explicitFileName);
		}
		
		fileItem.write(file);
	}

	private String generateUniqueName(FileItem file) {
		int hash = file.hashCode();
		return String.valueOf(hash);
	}

	private String createUniqueFileName() {
		this.uniqueFileName = this.generateUniqueName(this.receivedFile);
		this.uniqueFileName = this.addSuffixToUniqueName(this.originalFileName, this.uniqueFileName);
		return this.uniqueFileName;
	}

	private String addSuffixToUniqueName(String originalName, String uniqueName) {
		int suffixIndex = originalName.lastIndexOf('.');
		if (suffixIndex != -1) {
			uniqueName += originalName.substring(suffixIndex);
		}
		return uniqueName;
	}

	private void parseFileUploadRequest() throws FileUploadException {
		List<?> items = this.getParsedRequest();
		Iterator<?> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();

			if (item.isFormField()) {
				this.createAttachment(item);
			} else {
				this.originalFileName = item.getName();
				this.attachment.setOriginalName(originalFileName);
				this.receivedFile = item;
			}
		}
	}

	private void createAttachment(FileItem item) {
		 if (item.getFieldName().endsWith("articleTitle")) {
			this.attachment.setArticleTitle(item.getString());
		} else if (item.getFieldName().endsWith("desc")) {
			this.attachment.setDescription(item.getString());
			this.attachment.setTitle(item.getString());
		}
	}
}
