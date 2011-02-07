package com.jwl.util.markdown;

import java.util.Map;

import org.pegdown.PegDownProcessor;

public class MarkdownProcessor {

	private MarkdownExtension imageExtension;
	private MarkdownExtension linkExtension;
	
	public MarkdownProcessor(String currentUrl,
			Map<String, String> requestParams, String outputParameter) {
		imageExtension = new MarkdownImageExtension(currentUrl, requestParams,
				outputParameter);
		linkExtension = new MarkdownLinkExtension(currentUrl, requestParams,
				outputParameter);
	}
	
	public String markdownToHtml(String markdownSource){
		if (null == markdownSource || markdownSource.trim().equals("")) {
			return "";
		}
		
		String preprocessed = preprocess(markdownSource);
		String processed = process(preprocessed);
		String postprocessed = postprocess(processed);
		return postprocessed;
	}
	
	private String preprocess(String markdownSource) {
		String preprocessed;
		preprocessed = imageExtension.parse(markdownSource);
		preprocessed = linkExtension.parse(preprocessed);
		return preprocessed;
	}

	private String process(String preprocessed) {
		PegDownProcessor processor = new PegDownProcessor();		
		String processed = processor.markdownToHtml(preprocessed);
		return processed;
	}
	
	private String postprocess(String processed) {
		return processed;
	}
	
	public String encodeAmpersand(String text) {
		return text.replace("&", "&amp;");
	}


}
