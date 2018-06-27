package com.isinonet.ismartnet.beans;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class IdcDaily {
    private Integer id;

    private Integer websiteId;

    private Date statDate;

    private Short isMobile;

    private Short comeFrom;

    private Short provinceId;

    private Short isNewComer;

    private String isp;

    private Long pv;

    private Long uv;

    private Integer vn;

    private Integer vt;

    private Integer spn;

    private Long sip;

    private Date modTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    public Short getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Short isMobile) {
        this.isMobile = isMobile;
    }

    public Short getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(Short comeFrom) {
        this.comeFrom = comeFrom;
    }

    public Short getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Short provinceId) {
        this.provinceId = provinceId;
    }

    public Short getIsNewComer() {
        return isNewComer;
    }

    public void setIsNewComer(Short isNewComer) {
        this.isNewComer = isNewComer;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp == null ? null : isp.trim();
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getUv() {
        return uv;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public Integer getVn() {
        return vn;
    }

    public void setVn(Integer vn) {
        this.vn = vn;
    }

    public Integer getVt() {
        return vt;
    }

    public void setVt(Integer vt) {
        this.vt = vt;
    }

    public Integer getSpn() {
        return spn;
    }

    public void setSpn(Integer spn) {
        this.spn = spn;
    }

    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public Long getSip() {
        return sip;
    }

    public void setSip(Long sip) {
        this.sip = sip;
    }
}