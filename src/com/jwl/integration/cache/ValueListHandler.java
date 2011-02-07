package com.jwl.integration.cache;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import com.jwl.business.exceptions.IteratorJWLException;

public class ValueListHandler<T> {

	protected List<T> list;
	protected ListIterator<T> listIterator;

	public ValueListHandler() {
	}

	protected void setList(List<T> list) throws IteratorJWLException {
		this.list = list;
		if (list != null) {
			listIterator = list.listIterator();
		} else {
			throw new IteratorJWLException("List empty");
		}
	}

	public Collection<T> getList() {
		return this.list;
	}

	public int getSize() throws IteratorJWLException {
		int size = 0;

		if (list != null) {
			size = this.list.size();
		} else {
			throw new IteratorJWLException("No data");
		}

		return size;
	}

	public T getCurrentElement() throws IteratorJWLException {

		T obj = null;
		if (this.list != null) {
			int currIndex = listIterator.nextIndex();
			obj = this.list.get(currIndex);
		} else {
			throw new IteratorJWLException("No element");
		}
		return obj;

	}

	public List<T> getPreviousElements(int count) throws IteratorJWLException {
		int i = 0;
		T object = null;
		LinkedList<T> linkedList = new LinkedList<T>();
		if (this.listIterator != null) {
			while (this.listIterator.hasPrevious() && (i < count)) {
				object = this.listIterator.previous();
				linkedList.add(object);
				i++;
			}
		} else {
			throw new IteratorJWLException("No data");
		}

		return linkedList;
	}

	public List<T> getNextElements(int count)
			throws IteratorJWLException {
		int i = 0;
		T object = null;
		LinkedList<T> linkedList = new LinkedList<T>();
		if (this.listIterator != null) {
			while (this.listIterator.hasNext() && (i < count)) {
				object = this.listIterator.next();
				linkedList.add(object);
				i++;
			}
		} else {
			throw new IteratorJWLException("No data");
		}

		return linkedList;
	}

	public void resetIndex() throws IteratorJWLException {
		if (listIterator != null) {
			listIterator = list.listIterator();
		} else {
			throw new IteratorJWLException("No data");
		}
	}
}
