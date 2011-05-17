package com.jwl.presentation.markdown;

import java.util.HashMap;
import java.util.Map;

import com.jwl.presentation.url.WikiURLParser;

public class MarkupToMarkdown {

	public static String convert(String text) {
		WikiURLParser parser = new WikiURLParser();

		String currentUrl = parser.getCurrentURL();
		Map<String, String> parameters = parser
				.getURLParametersMinusArticleParameters();
		
		return markdownToHtml(text, currentUrl, parameters);
	}

	public static String convertForPreview(String text) {
		String currentUrl = "currentURL";
		Map<String, String> parameters = new HashMap<String, String>();
		
		return markdownToHtml(text, currentUrl, parameters);
	}
	
	private static String markdownToHtml(String text, String currentUrl,
			Map<String, String> parameters) {
		MarkdownProcessor processor = new MarkdownProcessor(currentUrl, parameters);
		
		return processor.markdownToHtml(text);
	}

}
