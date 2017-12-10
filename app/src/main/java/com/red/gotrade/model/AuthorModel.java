package com.red.gotrade.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * @Description: 个人信息实体类
 * @author: <a href="http://xiaoyaoyou1212.360doc.com">DAWI</a>
 * @date: 2017/6/5 20:29
 */
@Entity
public class AuthorModel implements Serializable {
    @Id(autoincrement = true)
    private Long id;
    private String author_token;//标识
    private String address;//地址
    private String author_nickname;//昵称
    private String phone;//简介
    private int status;//权限
    private static final long serialVersionUID = 6201378234876555585L;
    @Generated(hash = 1874424392)
    public AuthorModel(Long id, String author_token, String address,
            String author_nickname, String phone, int status) {
        this.id = id;
        this.author_token = author_token;
        this.address = address;
        this.author_nickname = author_nickname;
        this.phone = phone;
        this.status = status;
    }
    @Generated(hash = 1129386739)
    public AuthorModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAuthor_token() {
        return this.author_token;
    }
    public void setAuthor_token(String author_token) {
        this.author_token = author_token;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAuthor_nickname() {
        return this.author_nickname;
    }
    public void setAuthor_nickname(String author_nickname) {
        this.author_nickname = author_nickname;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

}
