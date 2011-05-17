package com.jwl.presentation.forms;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Lukas Rychtecky
 */
public class UploadedFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String originalName;
	private File tempPath;
	private Long size;

	public UploadedFile(String originalName, File tempPath, Long size) {
		this.originalName = originalName;
		this.tempPath = tempPath;
		this.size = size;
	}

	public String getOriginalName() {
		return originalName;
	}

	public Long getSize() {
		return size;
	}

	public File getTempPath() {
		return tempPath;
	}
	
}
