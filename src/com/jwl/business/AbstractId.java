package com.jwl.business;

/**
 * This class makes skeleton of Id.
 *
 * @author Lukas Rychtecky
 */
public abstract class AbstractId {

	private Integer id;

	public AbstractId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AbstractId)) {
			return false;
		}
		if (getClass() != object.getClass()) {
            return false;
        }

		final AbstractId other = (AbstractId) object;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + this.id;
		return hash;
	}

	@Override
	public String toString() {
		return Integer.toString(this.id);
	}
}
