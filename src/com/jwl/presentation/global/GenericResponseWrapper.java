package com.jwl.presentation.global;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

/**
 * Generic stand-in-stream enabling writing in the response
 *
 * @author Petr Janouch
 */
public class GenericResponseWrapper extends HttpServletResponseWrapper {

	private ByteArrayOutputStream output;
	private FilterServletOutputStream fos;
	private int contentLength;
	private String contentType;
	private PrintWriter pw;

	public GenericResponseWrapper(HttpServletResponse response) {
		super(response);
		this.output = new ByteArrayOutputStream();
		this.fos = null;
		this.pw = null;
	}

	public byte[] getData() {
		return output.toByteArray();
	}

	@Override
	public ServletOutputStream getOutputStream() {
		this.fos = new FilterServletOutputStream(this.output);
		return this.fos;
	}

	@Override
	public PrintWriter getWriter() {
		this.pw = new PrintWriter(getOutputStream(), true);
		return this.pw;
	}

	@Override
	public void setContentLength(int length) {
		this.contentLength = length;
		super.setContentLength(length);
	}

	public int getContentLength() {
		return this.contentLength;
	}

	@Override
	public void setContentType(String type) {
		this.contentType = type;
		super.setContentType(type);
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public String toString() {
		return this.output.toString();
	}

	public void flush() throws IOException {
		if (this.pw != null) {
			this.pw.flush();
		}
		if (this.fos != null) {
			this.fos.flush();
		}
	}
}
