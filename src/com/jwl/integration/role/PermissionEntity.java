package com.jwl.integration.role;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "permission", catalog = "wiki", schema = "")
@NamedQueries({
	@NamedQuery(name = "PermissionEntity.findAll", query = "SELECT p FROM PermissionEntity p"),
	@NamedQuery(name = "PermissionEntity.findById", query = "SELECT p FROM PermissionEntity p WHERE p.id = :id"),
	@NamedQuery(name = "PermissionEntity.findByContext", query = "SELECT p FROM PermissionEntity p WHERE p.context = :context"),
	@NamedQuery(name = "PermissionEntity.findByMethod", query = "SELECT p FROM PermissionEntity p WHERE p.method = :method"),
	@NamedQuery(name = "PermissionEntity.findByMethodAndContext", query = "SELECT p FROM PermissionEntity p WHERE p.method = :method AND p.context = :context")})
public class PermissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private List<RoleEntity> roleList;

	public PermissionEntity() {
	}

	public PermissionEntity(Integer id) {
		this.id = id;
	}

	public PermissionEntity(Integer id, String context, String method) {
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

	public List<RoleEntity> getRoleList() {
		return roleList;
	}

	public void addRole(RoleEntity role) {
		if (this.roleList == null) {
			this.roleList = new ArrayList<RoleEntity>();
		}
		this.roleList.add(role);
	}

	public void setRoleList(List<RoleEntity> roleList) {
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
		if (!(object instanceof PermissionEntity)) {
			return false;
		}
		PermissionEntity other = (PermissionEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.jwl.integration.role.PermissionEntity[id=" + id + "]";
	}

}
