package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.Validator;

/**
 * 定义数据库字段的显示属性
 * 
 * @author chenjun.li
 */
public abstract class FieldDefinition implements Serializable, InitializingBean, Validator {
	private static final long serialVersionUID = -3728306774474122680L;

	private String path;
	private String label;
	private int size = 10;
	private String suffix = "";
	private String prefix = "";
	private boolean mandatory = false;
	private boolean disableAutoComplete = false;
	private String linkedEvent;
	private boolean visible = true;

	private int labelColSpan = 1;
	private int fieldColSpan = 1;
	private String highlighted = null; // null代表不高亮
	private boolean readOnly = false;

	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
	public String getDojoConstraints()
	{
		return "";//留给子类实现
	}
	
	public Object getReference(Object context)
	{
		return null;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public boolean isDisableAutoComplete() {
		return disableAutoComplete;
	}

	public void setDisableAutoComplete(boolean disableAutoComplete) {
		this.disableAutoComplete = disableAutoComplete;
	}

	public String getLinkedEvent() {
		return linkedEvent;
	}

	public void setLinkedEvent(String linkedEvent) {
		this.linkedEvent = linkedEvent;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getLabelColSpan() {
		return labelColSpan;
	}

	public void setLabelColSpan(int labelColSpan) {
		this.labelColSpan = labelColSpan;
	}

	public int getFieldColSpan() {
		return fieldColSpan;
	}

	public void setFieldColSpan(int fieldColSpan) {
		this.fieldColSpan = fieldColSpan;
	}

	public String getHighlighted() {
		return highlighted;
	}

	public void setHighlighted(String highlighted) {
		this.highlighted = highlighted;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}
