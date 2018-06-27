package com.isinonet.ismartnet.beans;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UAType {
    private Integer id;

    private String pType;

    private Short isMobile;

    private String osType;

    private String browserType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType == null ? null : pType.trim();
    }

    public Short getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Short isMobile) {
        this.isMobile = isMobile;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType == null ? null : osType.trim();
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType == null ? null : browserType.trim();
    }
}