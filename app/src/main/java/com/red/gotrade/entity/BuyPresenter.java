package com.red.gotrade.entity;

import android.view.View;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Red on 2017/12/8.
 */

public class BuyPresenter {

    public void buyGoods(View view, Goods.DataBean.ListBean listBean) {
        Toasty.info(view.getContext(), "购买: " + listBean.getGName() + " 已下单", Toast.LENGTH_SHORT).show();
    }



}
