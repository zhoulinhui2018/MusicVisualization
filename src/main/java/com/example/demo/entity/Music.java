package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Music)实体类
 *
 * @author makejava
 * @since 2020-04-12 20:55:06
 */
public class Music implements Serializable {
    private static final long serialVersionUID = 818773221213445809L;
    
    private Integer id;
    
    private String name;
    
    private String url;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}