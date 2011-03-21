package com.jwl.business.article.process;

import com.jwl.business.Environment;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jwl.business.article.process.interfaces.IProcess;
import com.jwl.business.exceptions.BusinessProcessException;
import com.jwl.integration.cache.AttachmentHome;
import com.jwl.integration.entity.Attachment;

public class FileDownloadProcess implements IProcess {

	private HttpServletResponse response;
	private HttpServletRequest request;

	public FileDownloadProcess(HttpServletRequest request, HttpServletResponse response) {
		this.response = response;
		this.request = request;
	}

	@Override
	public void doIt() throws BusinessProcessException {
		try {
			String fileTitle = this.getFileTitle();
			String fileUniqueName = this.getFileUniqueName(fileTitle);
			if (fileUniqueName == null) {
				throw new BusinessProcessException("file not found");
			}
			File requestedFile = this.getFile(fileUniqueName);
			this.writeFileInResponse(requestedFile);
		} catch (Exception e) {
			throw new BusinessProcessException("download failed");
		}

	}

	private String getFileTitle() {
		return request.getParameter("jwlfilename");
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

	private void writeFileInResponse(File file) throws IOException {
		ServletOutputStream op = response.getOutputStream();
		response.setContentType("application/image");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ file.getName());
		response.setContentLength((int) file.length());
		try {
			this.writeFileInStream(file, op);
		} catch (Exception e) {
			// TODO: handle exception
		}
		op.flush();
		op.close();
	}

	private void writeFileInStream(File file, OutputStream out)
			throws FileNotFoundException, IOException {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[4 * 1024]; // 4K buffer
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
}
