package com.jwl.presentation.markdown;

import java.util.HashMap;

public class MarkdownRemover {

	MarkdownProcessor processor;

	public MarkdownRemover() {
		processor = new MarkdownProcessor("", new HashMap<String, String>(), "");
	}

	public String removeMarkdown(String text) {
		String html = processor.markdownToHtml(text);
		String plainText = removeHtmlTags(html);
		return plainText;
	}

	private String removeHtmlTags(String html) {
		int pos = 0;
		StringBuilder output = new StringBuilder();
		while (html.length() > pos) {
			if (html.charAt(pos) != '<') {
				output.append(html.charAt(pos));
				pos++;
				continue;
			}
			output.append(' ');
			while (html.length() > pos) {
				if (html.charAt(pos) == '>') {
					pos++;
					break;
				}
				pos++;
			}
		}
		return output.toString();
	}
}
