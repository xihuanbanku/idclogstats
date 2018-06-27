package com.isinonet.ismartnet.beans;

import java.util.ArrayList;
import java.util.List;

public class UAtypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UAtypeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPTypeIsNull() {
            addCriterion("p_type is null");
            return (Criteria) this;
        }

        public Criteria andPTypeIsNotNull() {
            addCriterion("p_type is not null");
            return (Criteria) this;
        }

        public Criteria andPTypeEqualTo(String value) {
            addCriterion("p_type =", value, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeNotEqualTo(String value) {
            addCriterion("p_type <>", value, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeGreaterThan(String value) {
            addCriterion("p_type >", value, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeGreaterThanOrEqualTo(String value) {
            addCriterion("p_type >=", value, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeLessThan(String value) {
            addCriterion("p_type <", value, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeLessThanOrEqualTo(String value) {
            addCriterion("p_type <=", value, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeLike(String value) {
            addCriterion("p_type like", value, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeNotLike(String value) {
            addCriterion("p_type not like", value, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeIn(List<String> values) {
            addCriterion("p_type in", values, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeNotIn(List<String> values) {
            addCriterion("p_type not in", values, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeBetween(String value1, String value2) {
            addCriterion("p_type between", value1, value2, "pType");
            return (Criteria) this;
        }

        public Criteria andPTypeNotBetween(String value1, String value2) {
            addCriterion("p_type not between", value1, value2, "pType");
            return (Criteria) this;
        }

        public Criteria andIsMobileIsNull() {
            addCriterion("is_mobile is null");
            return (Criteria) this;
        }

        public Criteria andIsMobileIsNotNull() {
            addCriterion("is_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andIsMobileEqualTo(Short value) {
            addCriterion("is_mobile =", value, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsMobileNotEqualTo(Short value) {
            addCriterion("is_mobile <>", value, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsMobileGreaterThan(Short value) {
            addCriterion("is_mobile >", value, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsMobileGreaterThanOrEqualTo(Short value) {
            addCriterion("is_mobile >=", value, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsMobileLessThan(Short value) {
            addCriterion("is_mobile <", value, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsMobileLessThanOrEqualTo(Short value) {
            addCriterion("is_mobile <=", value, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsMobileIn(List<Short> values) {
            addCriterion("is_mobile in", values, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsMobileNotIn(List<Short> values) {
            addCriterion("is_mobile not in", values, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsMobileBetween(Short value1, Short value2) {
            addCriterion("is_mobile between", value1, value2, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsMobileNotBetween(Short value1, Short value2) {
            addCriterion("is_mobile not between", value1, value2, "isMobile");
            return (Criteria) this;
        }

        public Criteria andIsPcIsNull() {
            addCriterion("is_pc is null");
            return (Criteria) this;
        }

        public Criteria andIsPcIsNotNull() {
            addCriterion("is_pc is not null");
            return (Criteria) this;
        }

        public Criteria andIsPcEqualTo(Short value) {
            addCriterion("is_pc =", value, "isPc");
            return (Criteria) this;
        }

        public Criteria andIsPcNotEqualTo(Short value) {
            addCriterion("is_pc <>", value, "isPc");
            return (Criteria) this;
        }

        public Criteria andIsPcGreaterThan(Short value) {
            addCriterion("is_pc >", value, "isPc");
            return (Criteria) this;
        }

        public Criteria andIsPcGreaterThanOrEqualTo(Short value) {
            addCriterion("is_pc >=", value, "isPc");
            return (Criteria) this;
        }

        public Criteria andIsPcLessThan(Short value) {
            addCriterion("is_pc <", value, "isPc");
            return (Criteria) this;
        }

        public Criteria andIsPcLessThanOrEqualTo(Short value) {
            addCriterion("is_pc <=", value, "isPc");
            return (Criteria) this;
        }

        public Criteria andIsPcIn(List<Short> values) {
            addCriterion("is_pc in", values, "isPc");
            return (Criteria) this;
        }

        public Criteria andIsPcNotIn(List<Short> values) {
            addCriterion("is_pc not in", values, "isPc");
            return (Criteria) this;
        }

        public Criteria andIsPcBetween(Short value1, Short value2) {
            addCriterion("is_pc between", value1, value2, "isPc");
            return (Criteria) this;
        }

        public Criteria andIsPcNotBetween(Short value1, Short value2) {
            addCriterion("is_pc not between", value1, value2, "isPc");
            return (Criteria) this;
        }

        public Criteria andOsTypeIsNull() {
            addCriterion("os_type is null");
            return (Criteria) this;
        }

        public Criteria andOsTypeIsNotNull() {
            addCriterion("os_type is not null");
            return (Criteria) this;
        }

        public Criteria andOsTypeEqualTo(String value) {
            addCriterion("os_type =", value, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeNotEqualTo(String value) {
            addCriterion("os_type <>", value, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeGreaterThan(String value) {
            addCriterion("os_type >", value, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeGreaterThanOrEqualTo(String value) {
            addCriterion("os_type >=", value, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeLessThan(String value) {
            addCriterion("os_type <", value, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeLessThanOrEqualTo(String value) {
            addCriterion("os_type <=", value, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeLike(String value) {
            addCriterion("os_type like", value, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeNotLike(String value) {
            addCriterion("os_type not like", value, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeIn(List<String> values) {
            addCriterion("os_type in", values, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeNotIn(List<String> values) {
            addCriterion("os_type not in", values, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeBetween(String value1, String value2) {
            addCriterion("os_type between", value1, value2, "osType");
            return (Criteria) this;
        }

        public Criteria andOsTypeNotBetween(String value1, String value2) {
            addCriterion("os_type not between", value1, value2, "osType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeIsNull() {
            addCriterion("browser_type is null");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeIsNotNull() {
            addCriterion("browser_type is not null");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeEqualTo(String value) {
            addCriterion("browser_type =", value, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeNotEqualTo(String value) {
            addCriterion("browser_type <>", value, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeGreaterThan(String value) {
            addCriterion("browser_type >", value, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("browser_type >=", value, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeLessThan(String value) {
            addCriterion("browser_type <", value, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeLessThanOrEqualTo(String value) {
            addCriterion("browser_type <=", value, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeLike(String value) {
            addCriterion("browser_type like", value, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeNotLike(String value) {
            addCriterion("browser_type not like", value, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeIn(List<String> values) {
            addCriterion("browser_type in", values, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeNotIn(List<String> values) {
            addCriterion("browser_type not in", values, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeBetween(String value1, String value2) {
            addCriterion("browser_type between", value1, value2, "browserType");
            return (Criteria) this;
        }

        public Criteria andBrowserTypeNotBetween(String value1, String value2) {
            addCriterion("browser_type not between", value1, value2, "browserType");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}