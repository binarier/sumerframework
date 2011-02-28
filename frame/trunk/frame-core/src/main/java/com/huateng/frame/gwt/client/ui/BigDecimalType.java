package com.huateng.frame.gwt.client.ui;
import java.math.BigDecimal;

import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.data.SimpleTypeFormatter;
import com.smartgwt.client.data.SimpleTypeParser;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.DataBoundComponent;


public class BigDecimalType extends SimpleType {

	public BigDecimalType() {
		super("bigDecimal", FieldType.TEXT);
		
		this.setEditParser(new SimpleTypeParser() {
			
			@Override
			public Object parseInput(String value, DataClass field,
					DataBoundComponent component, Record record) {
				return new BigDecimal(value);
			}
		});
		
		SimpleTypeFormatter stf = new SimpleTypeFormatter() {
			
			@Override
			public String format(Object value, DataClass field,
					DataBoundComponent component, Record record) {
				return value == null ? "" : value.toString();
			}
		};
		
		this.setShortDisplayFormatter(stf);
		
		this.setNormalDisplayFormatter(stf);
		
		this.setEditFormatter(stf);
		
	}
}
