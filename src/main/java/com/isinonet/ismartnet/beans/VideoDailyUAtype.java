package com.isinonet.ismartnet.beans;

import java.util.Date;

public class VideoDailyUAtype {
    private Integer id;

    private String uaType;

    private Integer uaCount;

    private Date modDate;

    private Date atime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUaType() {
        return uaType;
    }

    public void setUaType(String uaType) {
        this.uaType = uaType == null ? null : uaType.trim();
    }

    public Integer getUaCount() {
        return uaCount;
    }

    public void setUaCount(Integer uaCount) {
        this.uaCount = uaCount;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public Date getAtime() {
        return atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }
}