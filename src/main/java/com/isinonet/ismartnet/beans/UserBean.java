package com.isinonet.ismartnet.beans;

/**
 * Created by Administrator on 2018-06-06.
 * 2018-06-06
 */
public class UserBean {
    private Integer id;
    private String username;
    private String password;

    public UserBean() {
        super();
    }

    public UserBean(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
