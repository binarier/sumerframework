package com.huateng.sumer.runtime.web.meta;

import java.math.BigDecimal;

public class NumberField extends AbstractInputField {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用于校验，最小值
	 */
	private BigDecimal minValue = new BigDecimal(0);
	/**
	 * 用于校验，最大值
	 */
	private BigDecimal maxValue;
	/**
	 * 输入字段最大长度
	 */
	private int maxLength = 20;
	/**
	 * 数字格式化类型，与&lt;fmt:formatNumber/&gt;的type属性对应，默认为"number"，可选的还有"percent","currency"等。
	 */
	private String formatType = "number";
	
	/**
	 * 小数位数，同时用于输入校验和输出格式化
	 */
	private int fraction = 0;
	
	/**
	 * 格式化输出时的模版，与&lt;fmt:formatNumber/&gt;的pattern属性对应。
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
