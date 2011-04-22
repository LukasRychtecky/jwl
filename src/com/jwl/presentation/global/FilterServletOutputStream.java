package com.jwl.presentation.global;

import javax.servlet.*;
import java.io.*;

/**
 * Output stream used by GenericResponseWrapper
 *
 * @author Petr Janouch
 * @review Lukas Rychtecky
 */
public class FilterServletOutputStream extends ServletOutputStream {

	private ByteArrayOutputStream stream;

	public FilterServletOutputStream(OutputStream output) {
		this.stream = (ByteArrayOutputStream) output;
	}

	@Override
	public void write(int b) throws IOException {
		this.stream.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		this.stream.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		this.stream.write(b, off, len);
	}

	public void flushStream() throws IOException {
		this.stream.flush();
	}
}
