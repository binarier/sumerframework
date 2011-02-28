package com.huateng.frame.gwt.client.ui;

import java.io.Serializable;
import java.math.BigDecimal;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * 用于处理带小数的数字。由于这是GWT客户端程序，所有的小数被统一使用javascript的64位 BigDecimal来处理，所以这里的最大/最小值表示使用32位的BigDecimal型，不会有精度损失
 * @author binarier
 *
 * @param <DATA_TYPE>
 */
public class DecimalColumnHelper<DATA_TYPE extends Serializable> extends ColumnHelper<DATA_TYPE> {
	
	private BigDecimal min;
	private BigDecimal max;
	private int precision;
	
	public DecimalColumnHelper(String name, String title, Integer length, BigDecimal min, BigDecimal max, int precision)
	{
		super(name, title, length);
		
		this.min = min;
		this.max = max;
		this.precision = precision;
	}

	@Override
	protected FormItem doCreateFormItem() {
		return new TextItem();
	}

	@Override
	protected void setupItemAttributes(FormItem item) {
		
	}
	
	@Override
	protected void setupItemValidators(FormItem item) {
		
		BigDecimalValidator bdv = new BigDecimalValidator(min, max, precision);
		
		item.setValidators(bdv);
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

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

}
