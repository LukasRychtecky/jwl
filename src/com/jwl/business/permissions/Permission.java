package com.jwl.business.permissions;

import com.jwl.business.article.ArticleId;

/**
 *
 * @author Lukas Rychtecky
 */
public class Permission {

	private String context;

	private String method;

	private ArticleId id = null;

	public Permission(String context, String method) {
		this.context = context;
		this.method = method;
	}

	public Permission(String context, String method, ArticleId id) {
		this.context = context;
		this.method = method;
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public ArticleId getArticleId() {
		return id;
	}

	public String getMethod() {
		return method;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Permission other = (Permission) obj;
		if ((this.context == null) ? (other.getContext() != null) : !this.context.equals(other.getContext())) {
			return false;
		}
		if ((this.method == null) ? (other.getMethod() != null) : !this.method.equals(other.getMethod())) {
			return false;
		}
		return true;
	}
}
