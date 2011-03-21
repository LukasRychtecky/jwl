package com.jwl.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileExpert {
	private String[] imageExtensions = { "gif", "jpg", "png" };
	private String[] fileExtensions = { "pdf" };

	public boolean isSupportedFileType(String fileName) {
		List<String> extensions = new ArrayList<String>();
		extensions.addAll(Arrays.asList(this.fileExtensions));
		extensions.addAll(Arrays.asList(this.imageExtensions));

		return extensions.contains(this.getExtensionFromFileName(fileName));
	}

	private String getExtensionFromFileName(String fileName) {
		int extensionIndex = fileName.lastIndexOf('.');
		String extension = fileName.substring(extensionIndex + 1);
		extension = extension.toLowerCase();
		return extension;
	}

}
