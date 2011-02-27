package com.huateng.frame.gwt.client.ui;

import java.io.Serializable;

import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.IntegerRangeValidator;
import com.smartgwt.client.widgets.form.validator.IsIntegerValidator;

public class IntegerColumnHelper<DATA_TYPE extends Serializable> extends ColumnHelper<DATA_TYPE> {
	
	private int min;
	private int max;
	
	public IntegerColumnHelper(String name, String title, Integer length, int min, int max)
	{
		super(name, title, length);
		
		this.min = min;
		this.max = max;
	}

	@Override
	protected FormItem doCreateFormItem() {
		return new FloatItem();
	}

	@Override
	protected void setupItemAttributes(FormItem item) {
		
	}
	
	@Override
	protected void setupItemValidators(FormItem item) {
		
		IsIntegerValidator iiv = new IsIntegerValidator();
		
		IntegerRangeValidator irv = new IntegerRangeValidator();
		irv.setMin(min);
		irv.setMax(max);
		
		item.setValidators(iiv, irv);
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

}
