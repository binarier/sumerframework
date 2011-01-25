package com.huateng.frame.orm;

import java.util.Collection;

import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.CountProjection;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.PropertyExpression;
import org.hibernate.criterion.PropertyProjection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Subqueries;

/**
 * 类型化的 {@link Property}类，由Hibernate3.5代码直接改编。
 * 由于使用函数泛型写起来太麻烦，所以不使用 {@link Property#forName(String)}这种类厂模式，直接使用构造函数。
 * @author chenjun.li
 *
 * @param <T> 属性类型
 * 
 */
public class TypedProperty<T>
{
	private String propertyName;
	
	public TypedProperty( String propertyName)
	{
		this.propertyName = propertyName;
	}
	
	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	public Criterion between(T min, T max) {
		return Restrictions.between(getPropertyName(), min, max);
	}

	public Criterion in(Collection<T> values) {
		return Restrictions.in(getPropertyName(), values);
	}

	public Criterion in(T ... values) {
		return Restrictions.in(getPropertyName(), values);
	}

	public SimpleExpression like(T value) {
		return Restrictions.like(getPropertyName(), value);
	}

	public SimpleExpression like(String value, MatchMode matchMode) {
		return Restrictions.like(getPropertyName(), value, matchMode);
	}

	public SimpleExpression eq(T value) {
		return Restrictions.eq(getPropertyName(), value);
	}

	public SimpleExpression ne(T value) {
		return Restrictions.ne(getPropertyName(), value);
	}

	public SimpleExpression gt(T value) {
		return Restrictions.gt(getPropertyName(), value);
	}

	public SimpleExpression lt(T value) {
		return Restrictions.lt(getPropertyName(), value);
	}

	public SimpleExpression le(T value) {
		return Restrictions.le(getPropertyName(), value);
	}

	public SimpleExpression ge(T value) {
		return Restrictions.ge(getPropertyName(), value);
	}

	public PropertyExpression eqProperty(TypedProperty<T> other) {
		return Restrictions.eqProperty( getPropertyName(), other.getPropertyName() );
	}

	public PropertyExpression neProperty(TypedProperty<T> other) {
		return Restrictions.neProperty( getPropertyName(), other.getPropertyName() );
	}
	
	public PropertyExpression leProperty(TypedProperty<T> other) {
		return Restrictions.leProperty( getPropertyName(), other.getPropertyName() );
	}

	public PropertyExpression geProperty(TypedProperty<T> other) {
		return Restrictions.geProperty( getPropertyName(), other.getPropertyName() );
	}
	
	public PropertyExpression ltProperty(TypedProperty<T> other) {
		return Restrictions.ltProperty( getPropertyName(), other.getPropertyName() );
	}

	public PropertyExpression gtProperty(TypedProperty<T> other) {
		return Restrictions.gtProperty( getPropertyName(), other.getPropertyName() );
	}
	
	public Criterion isNull() {
		return Restrictions.isNull(getPropertyName());
	}

	public Criterion isNotNull() {
		return Restrictions.isNotNull(getPropertyName());
	}

	public Criterion isEmpty() {
		return Restrictions.isEmpty(getPropertyName());
	}

	public Criterion isNotEmpty() {
		return Restrictions.isNotEmpty(getPropertyName());
	}
	
	public CountProjection count() {
		return Projections.count(getPropertyName());
	}
	
	public AggregateProjection max() {
		return Projections.max(getPropertyName());
	}

	public AggregateProjection min() {
		return Projections.min(getPropertyName());
	}

	public AggregateProjection avg() {
		return Projections.avg(getPropertyName());
	}
	
	public PropertyProjection group() {
		return Projections.groupProperty(getPropertyName());
	}
	
	public Order asc() {
		return Order.asc(getPropertyName());
	}

	public Order desc() {
		return Order.desc(getPropertyName());
	}

	/**
	 * Get a component attribute of this property
	 */
	public <TT> TypedProperty<TT> getProperty(String propertyName, Class<TT> clazz) {
		return new TypedProperty<TT>( getPropertyName() + '.' + propertyName );
	}
	
	public Criterion eq(DetachedCriteria subselect) {
		return Subqueries.propertyEq( getPropertyName(), subselect );
	}

	public Criterion ne(DetachedCriteria subselect) {
		return Subqueries.propertyNe( getPropertyName(), subselect );
	}

	public Criterion lt(DetachedCriteria subselect) {
		return Subqueries.propertyLt( getPropertyName(), subselect );
	}

	public Criterion le(DetachedCriteria subselect) {
		return Subqueries.propertyLe( getPropertyName(), subselect );
	}

	public Criterion gt(DetachedCriteria subselect) {
		return Subqueries.propertyGt( getPropertyName(), subselect );
	}

	public Criterion ge(DetachedCriteria subselect) {
		return Subqueries.propertyGe( getPropertyName(), subselect );
	}

	public Criterion notIn(DetachedCriteria subselect) {
		return Subqueries.propertyNotIn( getPropertyName(), subselect );
	}

	public Criterion in(DetachedCriteria subselect) {
		return Subqueries.propertyIn( getPropertyName(), subselect );
	}

	public Criterion eqAll(DetachedCriteria subselect) {
		return Subqueries.propertyEqAll( getPropertyName(), subselect );
	}

	public Criterion gtAll(DetachedCriteria subselect) {
		return Subqueries.propertyGtAll( getPropertyName(), subselect );
	}

	public Criterion ltAll(DetachedCriteria subselect) {
		return Subqueries.propertyLtAll( getPropertyName(), subselect );
	}

	public Criterion leAll(DetachedCriteria subselect) {
		return Subqueries.propertyLeAll( getPropertyName(), subselect );
	}

	public Criterion geAll(DetachedCriteria subselect) {
		return Subqueries.propertyGeAll( getPropertyName(), subselect );
	}

	public Criterion gtSome(DetachedCriteria subselect) {
		return Subqueries.propertyGtSome( getPropertyName(), subselect );
	}

	public Criterion ltSome(DetachedCriteria subselect) {
		return Subqueries.propertyLtSome( getPropertyName(), subselect );
	}

	public Criterion leSome(DetachedCriteria subselect) {
		return Subqueries.propertyLeSome( getPropertyName(), subselect );
	}

	public Criterion geSome(DetachedCriteria subselect) {
		return Subqueries.propertyGeSome( getPropertyName(), subselect );
	}
	
}
