package com.huateng.frame.gwt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.huateng.frame.orm.IbatorCriteria;

public class SmartCriteria
{
	private String fieldName;
	private OperatorId operator;
	private Object value;
	private SmartCriteria criteria[];

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public OperatorId getOperator()
	{
		return operator;
	}

	public void setOperator(OperatorId operator)
	{
		this.operator = operator;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public SmartCriteria[] getCriteria()
	{
		return criteria;
	}

	public void setCriteria(SmartCriteria[] criteria)
	{
		this.criteria = criteria;
	}

	public void validate()
	{
		// TODO SQL注入检查
	}

	public List<IbatorCriteria> toIbatorCriteria(Map<String, String> nameMapping)
	{
		validate();

		List<IbatorCriteria> result = new ArrayList<IbatorCriteria>();
		if (criteria != null)
		{
			Assert.isTrue(criteria.length == 2);
			List<IbatorCriteria> ic1 = criteria[0].toIbatorCriteria(nameMapping);
			List<IbatorCriteria> ic2 = criteria[1].toIbatorCriteria(nameMapping);

			switch (operator)
			{
			case or:
				// (A||B)||(C||D) == A||B||C||D
				result.addAll(ic1);
				result.addAll(ic2);
				break;
			case and:
				// (A||B)(C||D) == AC||AD||BC||BD
				for (IbatorCriteria c1 : ic1)
				{
					for (IbatorCriteria c2 : ic2)
					{
						IbatorCriteria m = new IbatorCriteria();
						m.merge(c1);
						m.merge(c2);
						result.add(m);
					}
				}
				break;
			default:
				Assert.isTrue(false);
				break;
			}
		} else
		{
			// 转换成Criteria
			IbatorCriteria c = new IbatorCriteria();
			String colName = nameMapping.get(fieldName);
			switch (operator)
			{
			case equals:
				c.addCriterion(colName + " = ", value, fieldName);
				break;
			case greaterThan:
				c.addCriterion(colName + " > ", value, fieldName);
				break;
			case lessThan:
				c.addCriterion(colName + " < ", value, fieldName);
				break;
			case greaterOrEqual:
				c.addCriterion(colName + " >= ", value, fieldName);
				break;
			case lessOrEqual:
				c.addCriterion(colName + " <= ", value, fieldName);
				break;
			}
			if (c.isValid())
				result.add(c);
		}
		return result;
	}
}
