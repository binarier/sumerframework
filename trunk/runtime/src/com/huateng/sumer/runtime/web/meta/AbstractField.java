package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;

/**
 * 定义数据库字段的显示属性
 * 
 * @author chenjun.li
 */
public abstract class AbstractField implements Serializable {
	private static final long serialVersionUID = -3728306774474122680L;

	private boolean visible = true;

	private boolean readOnly = false;
	
	private int colSpan = 1;

	public String getDojoConstraints()
	{
		return "";//留给子类实现
	}
	
	public Object getReference(Object context)
	{
		return null;
	}


	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}


	public int getColSpan() {
		return colSpan;
	}


	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
}
