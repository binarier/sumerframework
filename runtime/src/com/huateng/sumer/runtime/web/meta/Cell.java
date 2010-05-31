package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;

public class Cell implements Serializable {
	private static final long serialVersionUID = -6474977743990657455L;
	private String width;
	private AbstractField field;
	private Object reference;
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public AbstractField getField() {
		return field;
	}
	public void setField(AbstractField field) {
		this.field = field;
	}
	public Object getReference() {
		return reference;
	}
	public void setReference(Object reference) {
		this.reference = reference;
	}
}
