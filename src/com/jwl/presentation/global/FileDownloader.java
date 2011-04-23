package com.jwl.presentation.global;

import java.io.BufferedInputStream;
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

	private HttpServletResponse response;

	public FileDownloader(HttpServletResponse response) {
		this.response = response;
	}


	public void writeResponse(File file) throws IOException {
		ServletOutputStream op = this.response.getOutputStream();
		this.response.setContentType("application/image");
		this.response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		this.response.setContentLength((int) file.length());
		try {
			this.writeFileInStream(file, op);
		} catch (Exception e) {
			Logger.getLogger(FileDownloader.class.getName()).log(Level.SEVERE, null, e);
		}
		op.flush();
		op.close();
	}

	private void writeFileInStream(File file, OutputStream out) throws FileNotFoundException, IOException {
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
