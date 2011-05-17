package com.jwl.presentation.markdown;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownLinkExtension extends MarkdownExtension {

	private static final String INTERNAL_LINK_PATTERN = "\\[.*\\]\\(\".*\".*\\)";
	private static final String ARTICLE_TITLE_PATTERN = "\\(\".*\"\\)";
	
	private Pattern internalLinkPattern;
	private Pattern articleTitlePattern;
	
	private String linkPatternStart;
	private String linkPatternEnd; 
	
	public MarkdownLinkExtension(String currentUrl, Map<String, String> requestParams) {
		super(currentUrl, requestParams);
		internalLinkPattern = Pattern.compile(INTERNAL_LINK_PATTERN, Pattern.CASE_INSENSITIVE);
		articleTitlePattern = Pattern.compile(ARTICLE_TITLE_PATTERN, Pattern.CASE_INSENSITIVE);
	}

	@Override
	public String parse(String markdownSource) {
		return encodeInternalLinksToMarkdownLinks(markdownSource);
	}
	
	private String encodeInternalLinksToMarkdownLinks(String text) {
		StringBuffer buffer = new StringBuffer();
		Matcher matcher = this.internalLinkPattern.matcher(text);
		while(matcher.find()){
			String internalLinkPattern = matcher.group();
			String markdownLink = encodeToMarkdownLink(internalLinkPattern);
			matcher.appendReplacement(buffer,markdownLink);
		} 
		matcher.appendTail(buffer);
		
		return buffer.toString();
	}
	
	private String encodeToMarkdownLink(String internalLink) {
		StringBuffer buffer = new StringBuffer();
		Matcher matcher = articleTitlePattern.matcher(internalLink);
		while(matcher.find()){
			String wrappedArticleTitle = matcher.group();
			String clearArticleTitle = getClearLink(wrappedArticleTitle);
			String articleTitleURL = getInternalWikiLink(clearArticleTitle);
			String wrappedMarkdownlLink = getWrappedLink(articleTitleURL);
			matcher.appendReplacement(buffer, wrappedMarkdownlLink);
		} 
		matcher.appendTail(buffer);
		
		return buffer.toString();
	}

	private String getClearLink(String wrappedTitle) {
		linkPatternStart = wrappedTitle.substring(0, 1);
		linkPatternEnd = wrappedTitle.substring(wrappedTitle.indexOf('"', 2) + 1);
		return wrappedTitle.substring(2, wrappedTitle.indexOf('"', 2));
	}
	
	private String getWrappedLink(String markdownLink) {
		return linkPatternStart + markdownLink + linkPatternEnd;
	}

}
