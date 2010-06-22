package com.huateng.sumer.runtime.web.meta;


public class LabelField extends AbstractField {

	private static final long serialVersionUID = -3298971415824857927L;
	private String label;
	private boolean mandatoryMark = false;
	private boolean escapeXml = true;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isMandatoryMark() {
		return mandatoryMark;
	}

	public void setMandatoryMark(boolean mandatoryMark) {
		this.mandatoryMark = mandatoryMark;
	}

	public boolean isEscapeXml() {
		return escapeXml;
	}

	public void setEscapeXml(boolean escapeXml) {
		this.escapeXml = escapeXml;
	}

	
}
