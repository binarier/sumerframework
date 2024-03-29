package com.huateng.sumer.sandbox.web.db.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TblAdmGroupAuthoritiesExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    public TblAdmGroupAuthoritiesExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    protected TblAdmGroupAuthoritiesExample(TblAdmGroupAuthoritiesExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    public static class Criteria {
        protected List<String> criteriaWithoutValue;

        protected List<Map<String, Object>> criteriaWithSingleValue;

        protected List<Map<String, Object>> criteriaWithListValue;

        protected List<Map<String, Object>> criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList<String>();
            criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
            criteriaWithListValue = new ArrayList<Map<String, Object>>();
            criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List<String> getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List<Map<String, Object>> getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List<Map<String, Object>> getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List<Map<String, Object>> getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List<Object> list = new ArrayList<Object>();
            list.add(value1);
            list.add(value2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andGroupIdIsNull() {
            addCriterion("group_id is null");
            return this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("group_id is not null");
            return this;
        }

        public Criteria andGroupIdEqualTo(Integer value) {
            addCriterion("group_id =", value, "groupId");
            return this;
        }

        public Criteria andGroupIdNotEqualTo(Integer value) {
            addCriterion("group_id <>", value, "groupId");
            return this;
        }

        public Criteria andGroupIdGreaterThan(Integer value) {
            addCriterion("group_id >", value, "groupId");
            return this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_id >=", value, "groupId");
            return this;
        }

        public Criteria andGroupIdLessThan(Integer value) {
            addCriterion("group_id <", value, "groupId");
            return this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("group_id <=", value, "groupId");
            return this;
        }

        public Criteria andGroupIdIn(List<Integer> values) {
            addCriterion("group_id in", values, "groupId");
            return this;
        }

        public Criteria andGroupIdNotIn(List<Integer> values) {
            addCriterion("group_id not in", values, "groupId");
            return this;
        }

        public Criteria andGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("group_id between", value1, value2, "groupId");
            return this;
        }

        public Criteria andGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("group_id not between", value1, value2, "groupId");
            return this;
        }

        public Criteria andAuthorityIdIsNull() {
            addCriterion("authority_id is null");
            return this;
        }

        public Criteria andAuthorityIdIsNotNull() {
            addCriterion("authority_id is not null");
            return this;
        }

        public Criteria andAuthorityIdEqualTo(String value) {
            addCriterion("authority_id =", value, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdNotEqualTo(String value) {
            addCriterion("authority_id <>", value, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdGreaterThan(String value) {
            addCriterion("authority_id >", value, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdGreaterThanOrEqualTo(String value) {
            addCriterion("authority_id >=", value, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdLessThan(String value) {
            addCriterion("authority_id <", value, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdLessThanOrEqualTo(String value) {
            addCriterion("authority_id <=", value, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdLike(String value) {
            addCriterion("authority_id like", value, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdNotLike(String value) {
            addCriterion("authority_id not like", value, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdIn(List<String> values) {
            addCriterion("authority_id in", values, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdNotIn(List<String> values) {
            addCriterion("authority_id not in", values, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdBetween(String value1, String value2) {
            addCriterion("authority_id between", value1, value2, "authorityId");
            return this;
        }

        public Criteria andAuthorityIdNotBetween(String value1, String value2) {
            addCriterion("authority_id not between", value1, value2, "authorityId");
            return this;
        }
    }
}