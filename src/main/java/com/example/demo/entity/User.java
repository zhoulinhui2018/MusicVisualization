package com.example.demo.entity;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author 周林辉
 * @since 2020-05-06 16:36:39
 */
public class User implements Serializable {
    private static final long serialVersionUID = -44548646471721463L;
    
    private Integer id;
    
    private String name;
    
    private String password;

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}