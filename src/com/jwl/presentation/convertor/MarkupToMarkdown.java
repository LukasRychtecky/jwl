package com.jwl.presentation.convertor;

import java.util.HashMap;
import java.util.Map;

import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.markdown.MarkdownProcessor;

public class MarkupToMarkdown {

	public static String convert(String text) {
		WikiURLParser parser = new WikiURLParser();

		String currentUrl = parser.getCurrentURL();
		Map<String, String> parameters = parser
				.getURLParametersMinusArticleParameters();
		String outputParameter = JWLURLParameters.ARTICLE_TITLE;
		
		return markdownToHtml(text, currentUrl, parameters, outputParameter);
	}

	public static String convertForPreview(String text) {
		String currentUrl = "currentURL";
		Map<String, String> parameters = new HashMap<String, String>();
		String outputParameter = JWLURLParameters.ARTICLE_TITLE;
		
		return markdownToHtml(text, currentUrl, parameters, outputParameter);
	}
	
	private static String markdownToHtml(String text, String currentUrl,
			Map<String, String> parameters, String outputParameter) {
		MarkdownProcessor processor = new MarkdownProcessor(currentUrl,
				parameters, outputParameter);
		
		return processor.markdownToHtml(text);
	}

}
