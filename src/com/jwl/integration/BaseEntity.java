package com.jwl.integration;

import javax.persistence.MappedSuperclass;

/**
 * This is the base for all the entities in the application. The abstract method 
 * provides a nice way to get a textual representation of who or what the entity 
 * is (role, article,...). Since it is baked into the superclass, it will be 
 * available for all entity classes.
 * 
 * @author Petr Dytrych
 * 
 */

@MappedSuperclass
public abstract class BaseEntity {

	public abstract Integer getId();

	public abstract void setId(Integer id);
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + this.getId();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

}
