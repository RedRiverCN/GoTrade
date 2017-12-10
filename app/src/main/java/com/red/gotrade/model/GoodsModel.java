package com.red.gotrade.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Red on 2017/12/9.
 */

@Entity
public class GoodsModel implements Serializable {
    @Id(autoincrement = true)
    private Long id;
    private String goods_id;//标识
    private Double goods_price;//价格
    private String goods_intro;//简介
    private String goods_name;//商品名
    private int goods_sort;//种类
    private static final long serialVersionUID = 1537378234876555585L;
    @Generated(hash = 1249578567)
    public GoodsModel(Long id, String goods_id, Double goods_price,
            String goods_intro, String goods_name, int goods_sort) {
        this.id = id;
        this.goods_id = goods_id;
        this.goods_price = goods_price;
        this.goods_intro = goods_intro;
        this.goods_name = goods_name;
        this.goods_sort = goods_sort;
    }
    @Generated(hash = 971639536)
    public GoodsModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGoods_id() {
        return this.goods_id;
    }
    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }
    public Double getGoods_price() {
        return this.goods_price;
    }
    public void setGoods_price(Double goods_price) {
        this.goods_price = goods_price;
    }
    public String getGoods_intro() {
        return this.goods_intro;
    }
    public void setGoods_intro(String goods_intro) {
        this.goods_intro = goods_intro;
    }
    public String getGoods_name() {
        return this.goods_name;
    }
    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
    public int getGoods_sort() {
        return this.goods_sort;
    }
    public void setGoods_sort(int goods_sort) {
        this.goods_sort = goods_sort;
    }

}