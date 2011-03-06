package com.jwl.integration.role;

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
@Table(name = "permission", catalog = "wiki", schema = "")
@NamedQueries({
	@NamedQuery(name = "Permission.findAll", query = "SELECT p FROM Permission p"),
	@NamedQuery(name = "Permission.findById", query = "SELECT p FROM Permission p WHERE p.id = :id"),
	@NamedQuery(name = "Permission.findByContext", query = "SELECT p FROM Permission p WHERE p.context = :context"),
	@NamedQuery(name = "Permission.findByMethod", query = "SELECT p FROM Permission p WHERE p.method = :method")})
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
	private Integer id;

	@Basic(optional = false)
    @Column(name = "context", nullable = false, length = 45)
	private String context;

	@Basic(optional = false)
    @Column(name = "method", nullable = false, length = 45)
	private String method;

	@JoinTable(name = "role_has_permission", joinColumns = {
    	@JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
    	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
	private List<Role> roleList;

	public Permission() {
	}

	public Permission(Integer id) {
		this.id = id;
	}

	public Permission(Integer id, String context, String method) {
		this.id = id;
		this.context = context;
		this.method = method;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
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
		if (!(object instanceof Permission)) {
			return false;
		}
		Permission other = (Permission) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.jwl.integration.role.Permission[id=" + id + "]";
	}

}
