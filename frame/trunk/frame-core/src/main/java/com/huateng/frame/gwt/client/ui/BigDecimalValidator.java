package com.huateng.frame.gwt.client.ui;

import java.math.BigDecimal;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

public class BigDecimalValidator extends CustomValidator
{
	private BigDecimal min;
	private BigDecimal max;
	private Integer precision;
	
	public BigDecimalValidator()
	{
	}
	
	public BigDecimalValidator(BigDecimal min, BigDecimal max, Integer precision)
	{
		this.min = min;
		this.max = max;
		this.precision = precision;
	}

	@Override
	protected boolean condition(Object value) {
		
		if (value == null)
			return true;

		if (!(value instanceof String))
		{
			setErrorMessage("数据类型不对");
			return false;
		}
		
		String text = (String)value;
		try
		{
			BigDecimal target = new BigDecimal(text);

			if (min != null && target.compareTo(min) < 0)
			{
				setErrorMessage("最小值为" + min);
				return false;
			}
			
			if (max != null && target.compareTo(max) > 0 )
			{
				setErrorMessage("最大值为" + max);
				return false;
			}
			
			if (precision != null && target.scale() > precision)
			{
				setErrorMessage("最多" + precision + "位小数");
				return false;
			}
			
		}
		catch (NumberFormatException nfe)
		{
			setErrorMessage(nfe.getLocalizedMessage());
			return false;
		}
				
		return true;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

}
