package com.jwl.integration.role;

import com.jwl.integration.entity.Article;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@NamedQuery(name = "RoleEntity.findAll", query = "SELECT r FROM RoleEntity r"),
	@NamedQuery(name = "RoleEntity.findById", query = "SELECT r FROM RoleEntity r WHERE r.id = :id"),
	@NamedQuery(name = "RoleEntity.findByCode", query = "SELECT r FROM RoleEntity r WHERE r.code = :code")})
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
	private Integer id;

	@Basic(optional = false)
    @Column(name = "code", nullable = false, length = 45)
	private String code;

	@ManyToMany(mappedBy = "roleList", fetch = FetchType.EAGER)
	private List<PermissionEntity> permissionList;

	@JoinTable(name = "article_exclude_role", joinColumns = {
    	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
    	@JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
	private List<Article> articleList;

	public RoleEntity() {
	}

	public RoleEntity(Integer id) {
		this.id = id;
	}

	public RoleEntity(Integer id, String code) {
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

	public List<PermissionEntity> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<PermissionEntity> permissionList) {
		this.permissionList = permissionList;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
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
		if (!(object instanceof RoleEntity)) {
			return false;
		}
		RoleEntity other = (RoleEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.jwl.integration.role.RoleEntity[id=" + id + "]";
	}

}
