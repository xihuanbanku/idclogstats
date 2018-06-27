package com.isinonet.ismartnet.beans;

import java.util.Date;

public class VideoDailyGender {
    private Integer id;

    private String gender;

    private Integer genderCount;

    private Date modDate;

    private Date atime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Integer getGenderCount() {
        return genderCount;
    }

    public void setGenderCount(Integer genderCount) {
        this.genderCount = genderCount;
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