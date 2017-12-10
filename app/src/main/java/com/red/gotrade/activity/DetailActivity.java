package com.red.gotrade.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.red.gotrade.R;
import com.red.gotrade.Utils;
import com.red.gotrade.db.DbHelper;
import com.red.gotrade.db.gen.AuthorModelDao;
import com.red.gotrade.entity.Goods;
import com.red.gotrade.model.AuthorModel;
import com.vise.log.ViseLog;
import com.vise.xsnow.common.GsonUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private static final String EXTRA_DETAIL_TOKEN = "USER_TOKEN";
    private static final String EXTRA_DETAIL_ROOT = "USER_ROOT";
    private static final String EXTRA_DETAIL_ID = "goods_id";
    private static final String EXTRA_DETAIL_IMGURL = "imgUrl";
    private static final String EXTRA_DETAIL_NAME = "gName";
    private static final String EXTRA_DETAIL_SORT = "sort";
    private static final String EXTRA_DETAIL_PRICE = "price";
    private static final String EXTRA_DETAIL_DETAIL = "detail";
    private static final String EXTRA_DETAIL_OWNERNAME = "ownerName";
    private static final String EXTRA_DETAIL_PHONE = "ownerPhone";
    private static final String EXTRA_DETAIL_ADDR = "ownerAddr";
    private static final String EXTRA_DETAIL_DEAL = "isDeal";
    @BindView(R.id.detail_back)
    ImageButton detailBack;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.player_title_bar)
    LinearLayout playerTitleBar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.iv_goods)
    ImageView ivGoods;
    @BindView(R.id.button)
    FloatingActionButton fab;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_addr)
    TextView tvAddr;
    @BindView(R.id.tv_owner)
    TextView tvOwner;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.cardView2)
    CardView cardView2;


    private String mToken;
    private int mRoot;
    private String mGId;
    private String mImgUrl;// 图片地址
    private String mGName;// 商品名
    private int mSort;// 类别
    private double mPrice;// 价格
    private String mDetail;// 介绍
    private String mOwnerName;// 联系人
    private String mOwnerPhone;// 联系电话
    private String mOwnerAddr;// 联系地址

    private String mUserPhone;
    private boolean mDeal;


    public static Intent newIntent(Context packageContext, String token, int root,
                                   String gid,
                                   String imgUrl,
                                   String gName,
                                   int sort,
                                   double price,
                                   String detail,
                                   String ownerName,
                                   String ownerPhone,
                                   String ownerAddr,
                                   boolean isDeal) {
        Intent i = new Intent(packageContext, DetailActivity.class);
        i.putExtra(EXTRA_DETAIL_TOKEN, token);
        i.putExtra(EXTRA_DETAIL_ROOT, root);
        i.putExtra(EXTRA_DETAIL_ID, gid);
        i.putExtra(EXTRA_DETAIL_IMGURL, imgUrl);
        i.putExtra(EXTRA_DETAIL_NAME, gName);
        i.putExtra(EXTRA_DETAIL_SORT, sort);
        i.putExtra(EXTRA_DETAIL_PRICE, price);
        i.putExtra(EXTRA_DETAIL_DETAIL, detail);
        i.putExtra(EXTRA_DETAIL_OWNERNAME, ownerName);
        i.putExtra(EXTRA_DETAIL_PHONE, ownerPhone);
        i.putExtra(EXTRA_DETAIL_ADDR, ownerAddr);
        i.putExtra(EXTRA_DETAIL_DEAL, isDeal);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // 获取Intent的Extra
        Intent intent = getIntent();
        if (intent != null) {
            mToken = intent.getStringExtra(EXTRA_DETAIL_TOKEN);
            mRoot = intent.getIntExtra(EXTRA_DETAIL_ROOT, 4);
            mGId = intent.getStringExtra(EXTRA_DETAIL_ID);
            mImgUrl = intent.getStringExtra(EXTRA_DETAIL_IMGURL);
            mGName = intent.getStringExtra(EXTRA_DETAIL_NAME);
            mSort = intent.getIntExtra(EXTRA_DETAIL_SORT, 1);
            mPrice = intent.getDoubleExtra(EXTRA_DETAIL_PRICE, 999);
            mDetail = intent.getStringExtra(EXTRA_DETAIL_DETAIL);
            mOwnerName = intent.getStringExtra(EXTRA_DETAIL_OWNERNAME);
            mOwnerPhone = intent.getStringExtra(EXTRA_DETAIL_PHONE);
            mOwnerAddr = intent.getStringExtra(EXTRA_DETAIL_ADDR);
            mDeal = intent.getBooleanExtra(EXTRA_DETAIL_DEAL, false);

        }

        if (mRoot==1||mRoot==2||mRoot==3){

            AuthorModel authorModel = DbHelper.getInstance().author().queryBuilder()
                    .where(AuthorModelDao.Properties.Author_token.eq(mToken))
                    .build().list().get(0);
            mUserPhone = authorModel.getPhone();
            //loadData();
        }

        tvDetail.setText("商品详情");
        Glide.with(this)
                .load(mImgUrl)
                .into(ivGoods);

        tvPhone.setText("联系电话： " + mOwnerPhone);
        tvAddr.setText("联系地址： " + mOwnerAddr);
        tvOwner.setText("发布人： " + mOwnerName);
        tvGoodsName.setText(mGName);
        tvContent.setText("  " + mDetail);
        tvSort.setText(Utils.getSortName(mSort));
        tvPrice.setText("¥ " + String.valueOf(mPrice));

//        detailBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//
//            }
//        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mRoot==1||mRoot==2||mRoot==3){
//                    Toasty.info(DetailActivity.this, "购买物品： " + mGName, Toast.LENGTH_SHORT, true).show();
//
//                }else {
//                    Toasty.info(DetailActivity.this, "游客不能买商品，请登录后购买。", Toast.LENGTH_SHORT, true).show();
//
//                }
//            }
//        });
    }

    @OnClick({R.id.detail_back, R.id.iv_goods, R.id.button, R.id.tv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detail_back:
                finish();
                break;
            case R.id.iv_goods:
                break;
            case R.id.button:
                if (!mDeal){
                    if (mRoot==3||mRoot==1){
                        //Toasty.info(getContext(), "购买物品： " + c.getGName(), Toast.LENGTH_SHORT, true).show();
                        // 构造JSON以提交下单
                        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                        map.put("gid", mGId);
                        map.put("phone",mUserPhone);
                        String json = GsonUtil.gson().toJson(map);

                        ViseHttp.POST("save_order")
                                .addHeader("token", mToken)
                                .setRequestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                                .tag("request_save_order")
                                .request(new ACallback<Goods>() {

                                    @Override
                                    public void onSuccess(Goods data) {
                                        ViseLog.i("request onSuccess!");
                                        if (data == null) {
                                            Toasty.error(getApplicationContext(), "服务器没有响应", Toast.LENGTH_SHORT, true).show();
                                            return;
                                        }
                                        if (data.getCode() != 200) {
                                            Toasty.error(getApplicationContext(), "提交出错了", Toast.LENGTH_SHORT, true).show();
                                            return;
                                        }
                                        ViseLog.i(data);
                                        if (TextUtils.equals(data.getMessage(), "SUCCESS") /*&& data.getData()==null*/) {
                                            Toasty.success(getApplicationContext(), "下单成功。", Toast.LENGTH_SHORT, true).show();
                                            setResult(RESULT_OK);
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                                        Toasty.error(getApplicationContext(), "下单失败： " + errMsg, Toast.LENGTH_SHORT, true).show();

                                    }
                                });
                    }else if (mRoot==2){
                        Toasty.info(getApplicationContext(), "商家不能买商品", Toast.LENGTH_SHORT, true).show();

                    }else {
                        Toasty.info(getApplicationContext(), "游客不能买商品，请登录后购买。", Toast.LENGTH_SHORT, true).show();

                    }
                }else {
                    Toasty.info(getApplicationContext(), "商品已被订购。", Toast.LENGTH_SHORT, true).show();

                }
                break;
            case R.id.tv_phone:
                break;
        }
    }
}
