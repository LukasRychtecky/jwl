package com.jwl.business.article;


public class AttachmentTO {
	private Integer id;
	private String title;
	private String originalName;
	private String uniqueName;
	private String description;
	private String articleTitle;
	

	public AttachmentTO(String title, String originalName, String uniqueName,
			String description) {
		super();
		this.title = title;
		this.originalName = originalName;
		this.uniqueName = uniqueName;
		this.description = description;
	}
	public AttachmentTO(){
		
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getArticleTitle() {
		return articleTitle;
	}
	
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

}
