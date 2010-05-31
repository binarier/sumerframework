package com.huateng.sumer.runtime.web.meta;

import java.math.BigDecimal;

public class NumberField extends AbstractInputField {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ����У�飬��Сֵ
	 */
	private BigDecimal minValue = new BigDecimal(0);
	/**
	 * ����У�飬���ֵ
	 */
	private BigDecimal maxValue;
	/**
	 * �����ֶ���󳤶�
	 */
	private int maxLength = 20;
	/**
	 * ���ָ�ʽ�����ͣ���&lt;fmt:formatNumber/&gt;��type���Զ�Ӧ��Ĭ��Ϊ"number"����ѡ�Ļ���"percent","currency"�ȡ�
	 */
	private String formatType = "number";
	
	/**
	 * С��λ����ͬʱ��������У��������ʽ��
	 */
	private int fraction = 0;
	
	/**
	 * ��ʽ�����ʱ��ģ�棬��&lt;fmt:formatNumber/&gt;��pattern���Զ�Ӧ��
	 */
	private String pattern;
	
	@Override
	public String getDojoConstraints() {
		String ret = "dummy : 1";
		
		if (minValue != null)
			ret += ", min : " + String.valueOf(minValue);
		if (maxValue != null)
			ret += ", max : " + String.valueOf(maxValue);
		
		return ret;
	}

	public BigDecimal getMinValue() {
		return minValue;
	}
	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}
	public BigDecimal getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int getFraction() {
		return fraction;
	}

	public void setFraction(int fraction) {
		this.fraction = fraction;
	}
}
