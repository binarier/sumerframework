package com.huateng.frame.gwt.server;

/**
 * 复制自 {@link com.smartgwt.client.types.OperatorId}以避免对smartgwt包的运行时依赖
 * 
 * @author chenjun.li
 * @see com.smartgwt.client.types.OperatorId
 */
public enum OperatorId
{
	/**
	 * exactly equal to
	 */
	equals,

	/**
	 * not equal to
	 */
	notEqual,
	/**
	 * exactly equal to, if case is disregarded
	 */
	iEquals,
	/**
	 * not equal to, if case is disregarded
	 */
	iNotEqual,
	/**
	 * Greater than
	 */
	greaterThan,
	/**
	 * Less than
	 */
	lessThan,
	/**
	 * Greater than or equal to
	 */
	greaterOrEqual,
	/**
	 * Less than or equal to
	 */
	lessOrEqual,
	/**
	 * Contains as sub-string (match case)
	 */
	contains,
	/**
	 * Starts with (match case)
	 */
	startsWith,
	/**
	 * Ends with (match case)
	 */
	endsWith,
	/**
	 * Contains as sub-string (case insensitive)
	 */
	iContains,

	/**
	 * Starts with (case insensitive)
	 */
	iStartsWith,
	/**
	 * Ends with (case insensitive)
	 */
	iEndsWith,
	/**
	 * Does not contain as sub-string (match case)
	 */
	notContains,
	/**
	 * Does not start with (match case)
	 */
	notStartsWith,
	/**
	 * Does not end with (match case)
	 */
	notEndsWith,
	/**
	 * Does not contain as sub-string (case insensitive)
	 */
	iNotContains,
	/**
	 * Does not start with (case insensitive)
	 */
	iNotStartsWith,
	/**
	 * Does not end with (case insensitive)
	 */
	iNotEndsWith,
	/**
	 * Regular expression match
	 */
	regexp,
	/**
	 * Regular expression match (case insensitive)
	 */
	iregexp,
	/**
	 * value is null
	 */
	isNull,
	/**
	 * value is non-null. Note empty string (" is non-null
	 */
	notNull,
	/**
	 * value is in a set of values. Specify criterion.value as an Array
	 */
	inSet,
	/**
	 * value is not in a set of values. Specify criterion.value as an Array
	 */
	notInSet,
	/**
	 * matches another field (specify fieldName as criterion.value)
	 */
	equalsField,
	/**
	 * does not match another field (specify fieldName as criterion.value)
	 */
	notEqualField,
	/**
	 * Greater than another field (specify fieldName as criterion.value)
	 */
	greaterThanField,
	/**
	 * Less than another field (specify fieldName as criterion.value)
	 */
	lessThanField,
	/**
	 * Greater than or equal to another field (specify fieldName as
	 * criterion.value)
	 */
	greaterOrEqualField,
	/**
	 * Less than or equal to another field (specify fieldName as
	 * criterion.value)
	 */
	lessOrEqualField,
	/**
	 * Contains as sub-string (match case) another field value (specify
	 * fieldName as criterion.value)
	 */
	containsField,
	/**
	 * Starts with (match case) another field value (specify fieldName as
	 * criterion.value)
	 */
	startsWithField,
	/**
	 * Ends with (match case) another field value (specify fieldName as
	 * criterion.value)
	 */
	endsWithField,
	/**
	 * all subcriteria (criterion.criteria) are true
	 */
	and,
	/**
	 * all subcriteria (criterion.criteria) are false
	 */
	not,
	/**
	 * at least one subcriteria (criterion.criteria) is true
	 */
	or,
	/**
	 * shortcut for greaterThan + lessThan + and. Specify criterion.start and
	 * criterion.end
	 */
	between,
	/**
	 * shortcut for greaterOrEqual + lessOrEqual + and. Specify criterion.start
	 * and criterion.end
	 */
	betweenInclusive
}
