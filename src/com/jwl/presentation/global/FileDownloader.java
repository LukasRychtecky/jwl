package com.jwl.presentation.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lukas Rychtecky
 */
public class FileDownloader {
	
	public static final String CONTENT_DISPOSITION_ATTACHMENT = "attachment; filename=";
	public static final String CONTENT_TYPE_CSV = "text/csv";
	public static final String CONTENT_TYPE_OCTET_STREAM = "application/octet-stream";

	private HttpServletResponse response;

	public FileDownloader(HttpServletResponse response) {
		this.response = response;
	}
	
	public void writeResponse(File file, String contentType) throws IOException {
		this.writeResponse(file, contentType, CONTENT_DISPOSITION_ATTACHMENT + file.getName());
	}

	public void writeResponse(File file, String contentType, String contentDisposition) throws IOException {
		ServletOutputStream op = this.response.getOutputStream();
		this.response.setContentType(contentType);
		this.response.setHeader("Content-Disposition", contentDisposition);
		this.response.setContentLength((int) file.length());
		try {
			this.writeFileInStream(file, op);
		} catch (Exception e) {
			Logger.getLogger(FileDownloader.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	private void writeFileInStream(File file, OutputStream out) throws FileNotFoundException, IOException {
		InputStream in = null;
		try {

			FileInputStream fileIn = new FileInputStream(file);

			byte[] outputByte = new byte[4096];
			//copy binary contect to output stream
			while (fileIn.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}
			fileIn.close();
			out.flush();
			out.close();

//			in = new BufferedInputStream(new FileInputStream(file));
//			byte data[] = new byte[1024];
//			int count = 0;
//			while((count = in.read(data, 0, 1024)) != -1) {
//				out.write(data, 0, count);
//			}

//			byte[] buffer = new byte[4 * 1024]; // 4K buffer
//			int bytesRead = 0;
//			while ((bytesRead = in.read(buffer)) != -1) {
//				out.write(buffer, 0, bytesRead);
//			}
		} finally {
			out.flush();
			out.close();
			if (in != null) {
				in.close();
			}
		}
	}
}
