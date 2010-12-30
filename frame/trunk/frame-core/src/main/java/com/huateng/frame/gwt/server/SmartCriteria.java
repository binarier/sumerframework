package com.huateng.frame.gwt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import com.huateng.frame.orm.IbatorCriteria;

public class SmartCriteria
{
	protected String fieldName;
	protected OperatorId operator;
	protected Object value;
	protected List<SmartCriteria> criteria;

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
			Assert.isTrue(criteria.size() == 2);
			List<IbatorCriteria> ic1 = criteria.get(0).toIbatorCriteria(nameMapping);
			List<IbatorCriteria> ic2 = criteria.get(1).toIbatorCriteria(nameMapping);

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
	
	public Criterion toHibernateCriterion()
	{
		switch (operator)
		{
		case or:
			Disjunction disjunction = Restrictions.disjunction();
			for (SmartCriteria cri : criteria)
				disjunction.add(cri.toHibernateCriterion());
			return disjunction;
		case and:
			Conjunction conjunction = Restrictions.conjunction();
			for (SmartCriteria cri : criteria)
				conjunction.add(cri.toHibernateCriterion());
			return conjunction;
		case equals:
			return Restrictions.eq(fieldName, value);
		case greaterThan:
			return Restrictions.gt(fieldName, value);
		case lessThan:
			return Restrictions.lt(fieldName, value);
		case greaterOrEqual:
			return Restrictions.ge(fieldName, value);
		case lessOrEqual:
			return Restrictions.le(fieldName, value);
		default:
			return null;
		}
	}

	public List<SmartCriteria> getCriteria()
	{
		return criteria;
	}

	public void setCriteria(List<SmartCriteria> criteria)
	{
		this.criteria = criteria;
	}
}
