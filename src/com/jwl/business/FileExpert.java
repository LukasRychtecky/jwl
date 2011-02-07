package com.jwl.business;


import org.apache.commons.fileupload.FileItem;

/**
 * 
 * @author Petr Janouch
 * 
 *         Nekomu muze prijit divny, ze se kontrolujou pouze pripony. jedna se
 *         pouze o provizorni reseni. Pokud znate nejakou kvalitni knihovnu,
 *         ktera urci typ souboru na zaklade obsahu, klidne to nejak dodelejte.
 */

public class FileExpert {
	String[] imageExtensions = { "gif", "jpg", "png" };
	String[] fileExtensions = { "pdf" };
	private static final String FILE_STORE_DIR_PATH = "c:\\wikifiles";

	public boolean isSupportedFileType(String fileName) {
		String extension = this.getExtensionFromFileName(fileName);
		if (this.isInFileExtensions(extension)
				|| this.isInImageExtensions(extension)) {
			return true;
		}
		return false;
	}

	private String getExtensionFromFileName(String fileName) {
		int extensionIndex = fileName.lastIndexOf('.');
		String extension = fileName.substring(extensionIndex + 1);
		extension = extension.toLowerCase();
		return extension;
	}

	private boolean isInImageExtensions(String extension) {
		for (String imageExtension : imageExtensions) {
			if (imageExtension.equals(extension)) {
				return true;
			}
		}
		return false;
	}

	private boolean isInFileExtensions(String extension) {
		for (String fileExtension : fileExtensions) {
			if (fileExtension.equals(extension)) {
				return true;
			}
		}
		return false;
	}

	public static String generateUniqueName(FileItem file) {
		int hash = file.hashCode();
		return String.valueOf(hash);
	}
	
	public static String getFileStorePath(){
		return FILE_STORE_DIR_PATH;
	}

}
