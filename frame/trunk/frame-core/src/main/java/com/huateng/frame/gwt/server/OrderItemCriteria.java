package com.huateng.frame.gwt.server;

import java.util.ArrayList;

public class OrderItemCriteria extends SmartCriteria
{
	public static OrderItemCriteria create(String fieldName, OperatorId operator, Object value)
	{
		return new OrderItemCriteria(fieldName, operator, value);
	}
	
	public OrderItemCriteria()
	{
		
	}
	public OrderItemCriteria(String fieldName, OperatorId operator, Object value)
	{
		setFieldName(fieldName);
		setOperator(operator);
		setValue(value);
	}
	
	public OrderItemCriteria addCriteria(OrderItemCriteria otherCriteria)
	{
		if (criteria == null)
			criteria = new ArrayList<SmartCriteria>();
		criteria.add(otherCriteria);
		
		return this;
	}
	
	public OrderItemCriteria andCriteria(OrderItemCriteria otherCriteria)
	{
		if (operator == OperatorId.and)
		{
			addCriteria(otherCriteria);
			return this;
		}
		else
		{
			OrderItemCriteria parent = new OrderItemCriteria();
			parent.setOperator(OperatorId.and);
			parent.addCriteria(this);
			parent.addCriteria(otherCriteria);
			return parent;
		}
	}
	
	//--------------
	
//	public OrderItemCriteria andCriteria(OrderItemCriteria otherCriteria)
//	{
//		if (operator == OperatorId.and)
//		{
//			addCriteria(otherCriteria);
//			return this;
//		}
//		else
//		{
//			OrderItemCriteria parent = new OrderItemCriteria();
//			parent.setOperator(OperatorId.and);
//			parent.addCriteria(this);
//			parent.addCriteria(otherCriteria);
//			return parent;
//		}
//	}
}