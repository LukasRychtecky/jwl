package com.jwl.presentation.markdown;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jwl.presentation.global.FileURLBuilder;

/**
 * 
 * @author Petr Janouch
 * 
 */
public class MarkdownImageExtension extends MarkdownExtension {
	private static final String IMAGE_PATTERN = "!\\[.*\\]\\(.*\\)";
	private int ci = Pattern.CASE_INSENSITIVE;
	private static final String LINK_PATTERN = "link=";
	private Pattern imagePattern;
	private String alt = "";
	private String width = "";
	private String height = "";
	private String src = "";
	private String linkHref = "";
	private String position = "";
	
	private final String position_ceter = "center";
	private final String position_left = "left";
	private final String position_right = "right";

	public MarkdownImageExtension(String currentUrl,
			Map<String, String> requestParams, String outputParameter) {
		super(currentUrl, requestParams, outputParameter);
		imagePattern = Pattern.compile(IMAGE_PATTERN, ci);
	}

	@Override
	public String parse(String text) {
		StringBuffer buffer = new StringBuffer();
		Matcher matcher = this.imagePattern.matcher(text);
		while (matcher.find()) {
			String markdownImage = matcher.group();
			String html = encodeImagesToHtml(markdownImage);
			matcher.appendReplacement(buffer, html);
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	private String encodeImagesToHtml(String markdownImage) {
		this.setSrc(markdownImage);
		this.setImageOptions(markdownImage);
		return this.generateHtml();
	}

	private String generateHtml() {
		String image = this.generateHtmlImage();
		String link = this.generateHtmlLink(image);
		String div = this.generateHtmlDiv(link);
		return div;
	}

	private String generateHtmlImage() {
		StringBuilder html = new StringBuilder();
		html.append("<img");
		html.append(" ");
		html.append("src=" + "\"" + this.src + "\" ");
		html.append("alt=" + "\"" + this.alt + "\" ");
		if (!width.equals("")) {
			html.append("width=" + "\"" + this.width + "\" ");
		}
		if (!height.equals("")) {
			html.append("height=" + "\"" + this.height + "\" ");
		}
		html.append("/>");

		return html.toString();
	}

	private String generateHtmlLink(String captionText) {
		if (this.linkHref.equals("")) {
			return captionText;
		}
		StringBuilder html = new StringBuilder();
		html.append("<a");
		html.append(" ");
		html.append("href=" + "\"" + this.linkHref + "\"");
		html.append(">");
		html.append(captionText);
		html.append("</a>");
		return html.toString();
	}

	private String generateHtmlDiv(String content) {
		if (position.equals("")) {
			return content;
		}
		StringBuilder html = new StringBuilder();
		html.append("<div ");
		html.append("class=");
		if (position.equals(position_ceter)) {
			html.append("wikiImageCenter");
		}
		if (position.equals(position_right)) {
			html.append("wikiImageFloatRight");
		}
		if (position.equals(position_left)) {
			html.append("wikiImageFloatLeft");
		}
		html.append(">");
		html.append(content);
		html.append("</div>");
		return html.toString();
	}

	private void setImageOptions(String markdownImage) {
		String optionsPart = this.trimOptionsPart(markdownImage);
		List<String> options = this.getOptions(optionsPart);
		this.setCaption(options);
		this.setLink(options);
		this.setSize(options);
		this.setPosition(options);

	}

	private String trimOptionsPart(String markdownImage) {
		int bracketBegin = markdownImage.indexOf("[");
		int bracketEnd = markdownImage.indexOf("]", bracketBegin);
		String content = markdownImage.substring(bracketBegin + 1, bracketEnd);
		content = content.trim();
		return content;
	}

	private List<String> getOptions(String optionsPart) {
		List<String> options = new LinkedList<String>();
		if (optionsPart.equals("")) {
			return options;
		}
		int lastPipeIndex = 0;
		while (true) {
			int pipeIndex = optionsPart.indexOf("|", lastPipeIndex);
			if (pipeIndex == -1) {
				String lastOption = optionsPart.substring(lastPipeIndex);
				lastOption = lastOption.trim();
				options.add(lastOption);
				break;
			}
			String option = optionsPart.substring(lastPipeIndex, pipeIndex);
			option = option.trim();
			options.add(option);
			lastPipeIndex = pipeIndex + 1;
		}
		return options;
	}

	private void setCaption(List<String> options) {
		if (!options.isEmpty()) {
			this.alt = options.remove(0);
		}
	}

	private void setLink(List<String> options) {
		for (String option : options) {
			int patternIndex = option.indexOf(LINK_PATTERN);
			if (patternIndex != -1) {
				String link = option.substring(patternIndex
						+ LINK_PATTERN.length());
				link = link.trim();
				if (link.charAt(0) == '"') {
					link = link.substring(1, link.length() - 1);
					link = this.getInternalWikiLink(link);
				}
				this.linkHref = link;
			}
		}
	}

	private void setSize(List<String> options) {
		for (String option : options) {
			int pxIndex = option.indexOf("px");
			if (pxIndex != -1 && pxIndex + 2 == option.length()) {
				int xIndex = option.indexOf("x");
				if (xIndex != -1 && xIndex != pxIndex + 1) {
					this.height = option.substring(xIndex + 1, pxIndex);
					if (xIndex != 0) {
						this.width = option.substring(0, xIndex);
					}
					return;
				}
				this.width = option.substring(0, pxIndex);
				return;
			}
		}
	}

	private void setPosition(List<String> options) {
		for (String option : options) {
			if (option.equals(position_ceter)) {
				position = position_ceter;
				return;
			}
			if (option.equals(position_right)) {
				position = position_right;
				return;
			}
			if (option.equals(position_left)) {
				position = position_left;
				return;
			}
		}
	}

	private void setSrc(String markdownImage) {
		String content = this.trimSrcPart(markdownImage);
		if (content.charAt(0) == '"') {
			int endIndex = content.indexOf("\"", 1);
			if (endIndex == -1) {
				return;
			}
			String fileName = content.substring(1, endIndex);
			this.setWikiSrc(fileName);
			return;
		}
		int endIndex = content.indexOf(" ");
		if (endIndex != -1) {
			this.src = content.substring(0,endIndex);
			return;
		}
		src = content;
	}

	private String trimSrcPart(String markdownImage) {
		int bracketBegin = markdownImage.indexOf("(");
		int bracketEnd = markdownImage.indexOf(")", bracketBegin);
		String content = markdownImage.substring(bracketBegin + 1, bracketEnd);
		content = content.trim();
		return content;
	}

	private void setWikiSrc(String fileName) {
		this.src = FileURLBuilder.getFileURL(fileName);
	}

}
