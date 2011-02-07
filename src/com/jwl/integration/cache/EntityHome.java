package com.jwl.integration.cache;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import com.jwl.integration.BaseEntity;
import com.jwl.integration.dao.EntityManagerDAO;

public class EntityHome<T extends BaseEntity> implements Serializable {

	private static final long serialVersionUID = -4555760599051861480L;
	protected EntityManagerDAO entityManagerDao;

	public EntityHome() {
		entityManagerDao = new EntityManagerDAO();
	}
	private Integer id;
	private T instance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if (this.id != id) {
			this.id = id;
			if (null != this.instance && id != this.instance.getId()) {
				this.instance = null;
			}
		}
	}

	public T getInstance() {
		if (instance == null) {
			if (id != null) {
				instance = loadInstance();
			} else {
				instance = createInstance();
			}
		}
		return instance;
	}

	public T loadInstance() {
		return entityManagerDao.find(getClassType(), this.getId());
	}

	public T createInstance() {
		try {
			instance = getClassType().newInstance();
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setInstance(T instance) {
		this.instance = instance;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getClassType() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	private boolean isManaged() {
		return getInstance().getId() != null;
	}

	public String save() {
		if (isManaged()) {
			entityManagerDao.updateObject(getInstance());
		} else {
			entityManagerDao.createObject(getInstance());
		}
		return "saved";
	}

	public void delete() {
		if (null != this.instance) {
			entityManagerDao.deleteObject(this.instance);
		}
	}
}
