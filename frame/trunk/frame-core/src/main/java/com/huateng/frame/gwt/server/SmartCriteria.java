package com.huateng.frame.gwt.server;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

@JsonIgnoreProperties("_constructor")
public class SmartCriteria
{
	private static final ObjectMapper mapper = new ObjectMapper();
	protected String fieldName;
	protected OperatorId operator;
	protected Object value;
	protected List<SmartCriteria> criteria;
	
	public static SmartCriteria createFromJSON(String json) throws JsonParseException, JsonMappingException, IOException
	{
		assert json != null;
		
		return mapper.readValue(json, SmartCriteria.class);
	}
	
	public boolean isValid()
	{
		//两种情况是有效的
		if (operator == OperatorId.and || operator == OperatorId.or || operator == OperatorId.not)
		{
			//有子条件
			return criteria != null && criteria.size() > 0;
		}
		else
		{
			//字段名必须有值
			return StringUtils.isNotBlank(fieldName);
		}
	}

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
		case iContains:
			//慎用
			return Restrictions.ilike(fieldName, "%"+value+"%");
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
