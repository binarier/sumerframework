package com.huateng.frame.gwt.client.ui;

import java.io.Serializable;

import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.FloatPrecisionValidator;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;
import com.smartgwt.client.widgets.form.validator.IsFloatValidator;

/**
 * 用于处理带小数的数字。由于这是GWT客户端程序，所有的小数被统一使用javascript的64位 float来处理，所以这里的最大/最小值表示使用32位的float型，不会有精度损失
 * @author binarier
 *
 * @param <DATA_TYPE>
 */
public class DecimalColumnHelper<DATA_TYPE extends Serializable> extends ColumnHelper<DATA_TYPE> {
	
	private float min;
	private float max;
	private int scale;
	
	public DecimalColumnHelper(String name, String title, Integer length, float min, float max, int scale)
	{
		super(name, title, length);
		
		this.min = min;
		this.max = max;
		this.scale = scale;
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
		
		IsFloatValidator ifv = new IsFloatValidator();
		
		FloatRangeValidator frv = new FloatRangeValidator();
		frv.setMin(min);
		frv.setMax(max);
		
		FloatPrecisionValidator fpv = new FloatPrecisionValidator();
		fpv.setPrecision(scale);
		
		item.setValidators(ifv, frv, fpv);
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

}
