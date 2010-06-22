package com.huateng.sumer.runtime.web.meta;

public abstract class AbstractInputField extends AbstractField {

	private static final long serialVersionUID = -3532672804655288993L;
	private String path;
	private int size = 10;
	private String suffix = "";
	private String prefix = "";
	private boolean mandatory = false;
	private boolean disableAutoComplete = false;
	private String linkedEvent;
	private String highlighted = null; // null代表不高亮
	private String prompt;

	private String label;
	private int labelColSpan = 1;
	
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

	public int getLabelColSpan() {
		return labelColSpan;
	}

	public void setLabelColSpan(int labelColSpan) {
		this.labelColSpan = labelColSpan;
	}

	public String getHighlighted() {
		return highlighted;
	}

	public void setHighlighted(String highlighted) {
		this.highlighted = highlighted;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
}
