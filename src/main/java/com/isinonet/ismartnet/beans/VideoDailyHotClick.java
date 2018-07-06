package com.isinonet.ismartnet.beans;

import java.util.Date;

public class VideoDailyHotClick {
    private Short id;

    private String url1;

    private Long cCount;

    private Date modDate;

    private Date atime;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1 == null ? null : url1.trim();
    }

    public Long getcCount() {
        return cCount;
    }

    public void setcCount(Long cCount) {
        this.cCount = cCount;
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