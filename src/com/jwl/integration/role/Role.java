package com.jwl.integration.role;

import com.jwl.integration.entity.Article;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Lukas Rychtecky
 */
@Entity
@Table(name = "security_role", catalog = "wiki", schema = "")
@NamedQueries({
	@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
	@NamedQuery(name = "Role.findById", query = "SELECT r FROM Role r WHERE r.id = :id"),
	@NamedQuery(name = "Role.findByCode", query = "SELECT r FROM Role r WHERE r.code = :code")})
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
	private Integer id;

	@Basic(optional = false)
    @Column(name = "code", nullable = false, length = 45)
	private String code;

	@ManyToMany(mappedBy = "roleList", fetch = FetchType.LAZY)
	private List<Permission> permissionList;

	@JoinTable(name = "article_exclude_role", joinColumns = {
    	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
    	@JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
	private List<Article> articleList;

	@JoinTable(name = "user_has_role", joinColumns = {
    	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
    	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
	private List<User> userList;

	public Role() {
	}

	public Role(Integer id) {
		this.id = id;
	}

	public Role(Integer id, String code) {
		this.id = id;
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Role)) {
			return false;
		}
		Role other = (Role) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.jwl.integration.role.Role[id=" + id + "]";
	}

}
