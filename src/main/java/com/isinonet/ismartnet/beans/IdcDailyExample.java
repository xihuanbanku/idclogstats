package com.isinonet.ismartnet.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class IdcDailyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IdcDailyExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andWebsiteIdIsNull() {
            addCriterion("website_id is null");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdIsNotNull() {
            addCriterion("website_id is not null");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdEqualTo(Integer value) {
            addCriterion("website_id =", value, "websiteId");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdNotEqualTo(Integer value) {
            addCriterion("website_id <>", value, "websiteId");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdGreaterThan(Integer value) {
            addCriterion("website_id >", value, "websiteId");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("website_id >=", value, "websiteId");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdLessThan(Integer value) {
            addCriterion("website_id <", value, "websiteId");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdLessThanOrEqualTo(Integer value) {
            addCriterion("website_id <=", value, "websiteId");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdIn(List<Integer> values) {
            addCriterion("website_id in", values, "websiteId");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdNotIn(List<Integer> values) {
            addCriterion("website_id not in", values, "websiteId");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdBetween(Integer value1, Integer value2) {
            addCriterion("website_id between", value1, value2, "websiteId");
            return (Criteria) this;
        }

        public Criteria andWebsiteIdNotBetween(Integer value1, Integer value2) {
            addCriterion("website_id not between", value1, value2, "websiteId");
            return (Criteria) this;
        }

        public Criteria andStatDateIsNull() {
            addCriterion("stat_date is null");
            return (Criteria) this;
        }

        public Criteria andStatDateIsNotNull() {
            addCriterion("stat_date is not null");
            return (Criteria) this;
        }

        public Criteria andStatDateEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date =", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date <>", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThan(Date value) {
            addCriterionForJDBCDate("stat_date >", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date >=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThan(Date value) {
            addCriterionForJDBCDate("stat_date <", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("stat_date <=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateIn(List<Date> values) {
            addCriterionForJDBCDate("stat_date in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("stat_date not in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("stat_date between", value1, value2, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("stat_date not between", value1, value2, "statDate");
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

        public Criteria andComeFromIsNull() {
            addCriterion("come_from is null");
            return (Criteria) this;
        }

        public Criteria andComeFromIsNotNull() {
            addCriterion("come_from is not null");
            return (Criteria) this;
        }

        public Criteria andComeFromEqualTo(Short value) {
            addCriterion("come_from =", value, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andComeFromNotEqualTo(Short value) {
            addCriterion("come_from <>", value, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andComeFromGreaterThan(Short value) {
            addCriterion("come_from >", value, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andComeFromGreaterThanOrEqualTo(Short value) {
            addCriterion("come_from >=", value, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andComeFromLessThan(Short value) {
            addCriterion("come_from <", value, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andComeFromLessThanOrEqualTo(Short value) {
            addCriterion("come_from <=", value, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andComeFromIn(List<Short> values) {
            addCriterion("come_from in", values, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andComeFromNotIn(List<Short> values) {
            addCriterion("come_from not in", values, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andComeFromBetween(Short value1, Short value2) {
            addCriterion("come_from between", value1, value2, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andComeFromNotBetween(Short value1, Short value2) {
            addCriterion("come_from not between", value1, value2, "comeFrom");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNull() {
            addCriterion("province_id is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIsNotNull() {
            addCriterion("province_id is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceIdEqualTo(Short value) {
            addCriterion("province_id =", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotEqualTo(Short value) {
            addCriterion("province_id <>", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThan(Short value) {
            addCriterion("province_id >", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdGreaterThanOrEqualTo(Short value) {
            addCriterion("province_id >=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThan(Short value) {
            addCriterion("province_id <", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdLessThanOrEqualTo(Short value) {
            addCriterion("province_id <=", value, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdIn(List<Short> values) {
            addCriterion("province_id in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotIn(List<Short> values) {
            addCriterion("province_id not in", values, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdBetween(Short value1, Short value2) {
            addCriterion("province_id between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andProvinceIdNotBetween(Short value1, Short value2) {
            addCriterion("province_id not between", value1, value2, "provinceId");
            return (Criteria) this;
        }

        public Criteria andIsNewComerIsNull() {
            addCriterion("is_new_comer is null");
            return (Criteria) this;
        }

        public Criteria andIsNewComerIsNotNull() {
            addCriterion("is_new_comer is not null");
            return (Criteria) this;
        }

        public Criteria andIsNewComerEqualTo(Short value) {
            addCriterion("is_new_comer =", value, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIsNewComerNotEqualTo(Short value) {
            addCriterion("is_new_comer <>", value, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIsNewComerGreaterThan(Short value) {
            addCriterion("is_new_comer >", value, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIsNewComerGreaterThanOrEqualTo(Short value) {
            addCriterion("is_new_comer >=", value, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIsNewComerLessThan(Short value) {
            addCriterion("is_new_comer <", value, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIsNewComerLessThanOrEqualTo(Short value) {
            addCriterion("is_new_comer <=", value, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIsNewComerIn(List<Short> values) {
            addCriterion("is_new_comer in", values, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIsNewComerNotIn(List<Short> values) {
            addCriterion("is_new_comer not in", values, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIsNewComerBetween(Short value1, Short value2) {
            addCriterion("is_new_comer between", value1, value2, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIsNewComerNotBetween(Short value1, Short value2) {
            addCriterion("is_new_comer not between", value1, value2, "isNewComer");
            return (Criteria) this;
        }

        public Criteria andIspIsNull() {
            addCriterion("isp is null");
            return (Criteria) this;
        }

        public Criteria andIspIsNotNull() {
            addCriterion("isp is not null");
            return (Criteria) this;
        }

        public Criteria andIspEqualTo(String value) {
            addCriterion("isp =", value, "isp");
            return (Criteria) this;
        }

        public Criteria andIspNotEqualTo(String value) {
            addCriterion("isp <>", value, "isp");
            return (Criteria) this;
        }

        public Criteria andIspGreaterThan(String value) {
            addCriterion("isp >", value, "isp");
            return (Criteria) this;
        }

        public Criteria andIspGreaterThanOrEqualTo(String value) {
            addCriterion("isp >=", value, "isp");
            return (Criteria) this;
        }

        public Criteria andIspLessThan(String value) {
            addCriterion("isp <", value, "isp");
            return (Criteria) this;
        }

        public Criteria andIspLessThanOrEqualTo(String value) {
            addCriterion("isp <=", value, "isp");
            return (Criteria) this;
        }

        public Criteria andIspLike(String value) {
            addCriterion("isp like", value, "isp");
            return (Criteria) this;
        }

        public Criteria andIspNotLike(String value) {
            addCriterion("isp not like", value, "isp");
            return (Criteria) this;
        }

        public Criteria andIspIn(List<String> values) {
            addCriterion("isp in", values, "isp");
            return (Criteria) this;
        }

        public Criteria andIspNotIn(List<String> values) {
            addCriterion("isp not in", values, "isp");
            return (Criteria) this;
        }

        public Criteria andIspBetween(String value1, String value2) {
            addCriterion("isp between", value1, value2, "isp");
            return (Criteria) this;
        }

        public Criteria andIspNotBetween(String value1, String value2) {
            addCriterion("isp not between", value1, value2, "isp");
            return (Criteria) this;
        }

        public Criteria andPvIsNull() {
            addCriterion("pv is null");
            return (Criteria) this;
        }

        public Criteria andPvIsNotNull() {
            addCriterion("pv is not null");
            return (Criteria) this;
        }

        public Criteria andPvEqualTo(Integer value) {
            addCriterion("pv =", value, "pv");
            return (Criteria) this;
        }

        public Criteria andPvNotEqualTo(Integer value) {
            addCriterion("pv <>", value, "pv");
            return (Criteria) this;
        }

        public Criteria andPvGreaterThan(Integer value) {
            addCriterion("pv >", value, "pv");
            return (Criteria) this;
        }

        public Criteria andPvGreaterThanOrEqualTo(Integer value) {
            addCriterion("pv >=", value, "pv");
            return (Criteria) this;
        }

        public Criteria andPvLessThan(Integer value) {
            addCriterion("pv <", value, "pv");
            return (Criteria) this;
        }

        public Criteria andPvLessThanOrEqualTo(Integer value) {
            addCriterion("pv <=", value, "pv");
            return (Criteria) this;
        }

        public Criteria andPvIn(List<Integer> values) {
            addCriterion("pv in", values, "pv");
            return (Criteria) this;
        }

        public Criteria andPvNotIn(List<Integer> values) {
            addCriterion("pv not in", values, "pv");
            return (Criteria) this;
        }

        public Criteria andPvBetween(Integer value1, Integer value2) {
            addCriterion("pv between", value1, value2, "pv");
            return (Criteria) this;
        }

        public Criteria andPvNotBetween(Integer value1, Integer value2) {
            addCriterion("pv not between", value1, value2, "pv");
            return (Criteria) this;
        }

        public Criteria andUvIsNull() {
            addCriterion("uv is null");
            return (Criteria) this;
        }

        public Criteria andUvIsNotNull() {
            addCriterion("uv is not null");
            return (Criteria) this;
        }

        public Criteria andUvEqualTo(Integer value) {
            addCriterion("uv =", value, "uv");
            return (Criteria) this;
        }

        public Criteria andUvNotEqualTo(Integer value) {
            addCriterion("uv <>", value, "uv");
            return (Criteria) this;
        }

        public Criteria andUvGreaterThan(Integer value) {
            addCriterion("uv >", value, "uv");
            return (Criteria) this;
        }

        public Criteria andUvGreaterThanOrEqualTo(Integer value) {
            addCriterion("uv >=", value, "uv");
            return (Criteria) this;
        }

        public Criteria andUvLessThan(Integer value) {
            addCriterion("uv <", value, "uv");
            return (Criteria) this;
        }

        public Criteria andUvLessThanOrEqualTo(Integer value) {
            addCriterion("uv <=", value, "uv");
            return (Criteria) this;
        }

        public Criteria andUvIn(List<Integer> values) {
            addCriterion("uv in", values, "uv");
            return (Criteria) this;
        }

        public Criteria andUvNotIn(List<Integer> values) {
            addCriterion("uv not in", values, "uv");
            return (Criteria) this;
        }

        public Criteria andUvBetween(Integer value1, Integer value2) {
            addCriterion("uv between", value1, value2, "uv");
            return (Criteria) this;
        }

        public Criteria andUvNotBetween(Integer value1, Integer value2) {
            addCriterion("uv not between", value1, value2, "uv");
            return (Criteria) this;
        }

        public Criteria andVnIsNull() {
            addCriterion("vn is null");
            return (Criteria) this;
        }

        public Criteria andVnIsNotNull() {
            addCriterion("vn is not null");
            return (Criteria) this;
        }

        public Criteria andVnEqualTo(Integer value) {
            addCriterion("vn =", value, "vn");
            return (Criteria) this;
        }

        public Criteria andVnNotEqualTo(Integer value) {
            addCriterion("vn <>", value, "vn");
            return (Criteria) this;
        }

        public Criteria andVnGreaterThan(Integer value) {
            addCriterion("vn >", value, "vn");
            return (Criteria) this;
        }

        public Criteria andVnGreaterThanOrEqualTo(Integer value) {
            addCriterion("vn >=", value, "vn");
            return (Criteria) this;
        }

        public Criteria andVnLessThan(Integer value) {
            addCriterion("vn <", value, "vn");
            return (Criteria) this;
        }

        public Criteria andVnLessThanOrEqualTo(Integer value) {
            addCriterion("vn <=", value, "vn");
            return (Criteria) this;
        }

        public Criteria andVnIn(List<Integer> values) {
            addCriterion("vn in", values, "vn");
            return (Criteria) this;
        }

        public Criteria andVnNotIn(List<Integer> values) {
            addCriterion("vn not in", values, "vn");
            return (Criteria) this;
        }

        public Criteria andVnBetween(Integer value1, Integer value2) {
            addCriterion("vn between", value1, value2, "vn");
            return (Criteria) this;
        }

        public Criteria andVnNotBetween(Integer value1, Integer value2) {
            addCriterion("vn not between", value1, value2, "vn");
            return (Criteria) this;
        }

        public Criteria andVtIsNull() {
            addCriterion("vt is null");
            return (Criteria) this;
        }

        public Criteria andVtIsNotNull() {
            addCriterion("vt is not null");
            return (Criteria) this;
        }

        public Criteria andVtEqualTo(Integer value) {
            addCriterion("vt =", value, "vt");
            return (Criteria) this;
        }

        public Criteria andVtNotEqualTo(Integer value) {
            addCriterion("vt <>", value, "vt");
            return (Criteria) this;
        }

        public Criteria andVtGreaterThan(Integer value) {
            addCriterion("vt >", value, "vt");
            return (Criteria) this;
        }

        public Criteria andVtGreaterThanOrEqualTo(Integer value) {
            addCriterion("vt >=", value, "vt");
            return (Criteria) this;
        }

        public Criteria andVtLessThan(Integer value) {
            addCriterion("vt <", value, "vt");
            return (Criteria) this;
        }

        public Criteria andVtLessThanOrEqualTo(Integer value) {
            addCriterion("vt <=", value, "vt");
            return (Criteria) this;
        }

        public Criteria andVtIn(List<Integer> values) {
            addCriterion("vt in", values, "vt");
            return (Criteria) this;
        }

        public Criteria andVtNotIn(List<Integer> values) {
            addCriterion("vt not in", values, "vt");
            return (Criteria) this;
        }

        public Criteria andVtBetween(Integer value1, Integer value2) {
            addCriterion("vt between", value1, value2, "vt");
            return (Criteria) this;
        }

        public Criteria andVtNotBetween(Integer value1, Integer value2) {
            addCriterion("vt not between", value1, value2, "vt");
            return (Criteria) this;
        }

        public Criteria andSpnIsNull() {
            addCriterion("spn is null");
            return (Criteria) this;
        }

        public Criteria andSpnIsNotNull() {
            addCriterion("spn is not null");
            return (Criteria) this;
        }

        public Criteria andSpnEqualTo(Integer value) {
            addCriterion("spn =", value, "spn");
            return (Criteria) this;
        }

        public Criteria andSpnNotEqualTo(Integer value) {
            addCriterion("spn <>", value, "spn");
            return (Criteria) this;
        }

        public Criteria andSpnGreaterThan(Integer value) {
            addCriterion("spn >", value, "spn");
            return (Criteria) this;
        }

        public Criteria andSpnGreaterThanOrEqualTo(Integer value) {
            addCriterion("spn >=", value, "spn");
            return (Criteria) this;
        }

        public Criteria andSpnLessThan(Integer value) {
            addCriterion("spn <", value, "spn");
            return (Criteria) this;
        }

        public Criteria andSpnLessThanOrEqualTo(Integer value) {
            addCriterion("spn <=", value, "spn");
            return (Criteria) this;
        }

        public Criteria andSpnIn(List<Integer> values) {
            addCriterion("spn in", values, "spn");
            return (Criteria) this;
        }

        public Criteria andSpnNotIn(List<Integer> values) {
            addCriterion("spn not in", values, "spn");
            return (Criteria) this;
        }

        public Criteria andSpnBetween(Integer value1, Integer value2) {
            addCriterion("spn between", value1, value2, "spn");
            return (Criteria) this;
        }

        public Criteria andSpnNotBetween(Integer value1, Integer value2) {
            addCriterion("spn not between", value1, value2, "spn");
            return (Criteria) this;
        }

        public Criteria andModTimeIsNull() {
            addCriterion("mod_time is null");
            return (Criteria) this;
        }

        public Criteria andModTimeIsNotNull() {
            addCriterion("mod_time is not null");
            return (Criteria) this;
        }

        public Criteria andModTimeEqualTo(Date value) {
            addCriterion("mod_time =", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeNotEqualTo(Date value) {
            addCriterion("mod_time <>", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeGreaterThan(Date value) {
            addCriterion("mod_time >", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("mod_time >=", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeLessThan(Date value) {
            addCriterion("mod_time <", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeLessThanOrEqualTo(Date value) {
            addCriterion("mod_time <=", value, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeIn(List<Date> values) {
            addCriterion("mod_time in", values, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeNotIn(List<Date> values) {
            addCriterion("mod_time not in", values, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeBetween(Date value1, Date value2) {
            addCriterion("mod_time between", value1, value2, "modTime");
            return (Criteria) this;
        }

        public Criteria andModTimeNotBetween(Date value1, Date value2) {
            addCriterion("mod_time not between", value1, value2, "modTime");
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