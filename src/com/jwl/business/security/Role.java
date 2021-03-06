package com.jwl.business.security;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public class Role {

	private String code;
	private RoleId id;
	private Set<ArticleTO> articles;
	private Set<ArticleId> articlesId = null;
	private List<AccessPermissions> perms;

	public Role(String code) {
		this.code = code;
		this.articles = new HashSet<ArticleTO>();
		this.perms = new ArrayList<AccessPermissions>();
	}

	public Role(String code, RoleId id) {
		this(code);
		this.id = id;
	}

	public void addPermission(AccessPermissions perm) {
		this.perms.add(perm);
	}

	public List<AccessPermissions> getPermissions() {
		return new ArrayList<AccessPermissions>(this.perms);
	}

	public void setPermissionss(List<AccessPermissions> perms) {
		this.perms = perms;
	}

	public String getCode() {
		return code;
	}

	public RoleId getId() {
		return id;
	}

	public void setId(RoleId id) {
		this.id = id;
	}

	public void addArticle(ArticleTO article) {
		this.articles.add(article);
		if (this.articlesId != null) {
			this.articlesId.add(article.getId());
		}
	}

	public Set<ArticleTO> getArticles() {
		return new HashSet<ArticleTO>(this.articles);
	}

	public Set<ArticleId> getArticlesId() {
		if (this.articlesId == null) {
			this.articlesId = new HashSet<ArticleId>();
			for (ArticleTO article : this.articles) {
				this.articlesId.add(article.getId());
			}
		}
		return new HashSet<ArticleId>(this.articlesId);
	}

	@Override
	public String toString() {
		return this.code;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Role)) {
			return false;
		}
		Role r = (Role) o;

		if (!(r.getCode() == null ? this.getCode() != null : !r.getCode().equals(this.getCode()))) {
			return false;
		}
		return true;
	}


}
