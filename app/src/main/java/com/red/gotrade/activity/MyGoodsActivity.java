package com.red.gotrade.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.red.gotrade.R;
import com.red.gotrade.adapter.GoodsAdapter;
import com.red.gotrade.adapter.MyGoodsAdapter;
import com.red.gotrade.db.DbHelper;
import com.red.gotrade.db.gen.AuthorModelDao;
import com.red.gotrade.entity.Goods;
import com.red.gotrade.entity.MyGoods;
import com.red.gotrade.model.AuthorModel;
import com.red.gotrade.model.GoodsModel;
import com.vise.log.ViseLog;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.mode.CacheMode;
import com.vise.xsnow.http.mode.CacheResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.red.gotrade.common.Common.Constance.SUPER_TOKEN;

public class MyGoodsActivity extends AppCompatActivity {

    public static final String EXTRA_TOKEN = "token";
    public static final String EXTRA_ROOT = "root";
    public static final int REQUEST_CODE_PUBLIC = 102;
    public static final int REQUEST_CODE_MODIFIED = 103;
    @BindView(R.id.tv_my_goods_back)
    ImageButton tvMyGoodsBack;
    @BindView(R.id.tv_my_goods_title)
    TextView tvMyGoodsTitle;
    @BindView(R.id.tv_my_goods_add)
    ImageButton tvMyGoodsAdd;
    @BindView(R.id.player_title_bar)
    LinearLayout playerTitleBar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.goodsFragment_container)
    FrameLayout goodsFragmentContainer;

    private String  mToken;
    private int mRoot = 3;
    MyGoodsAdapter mAdapter;
    List<MyGoods.DataBean> goodsList=new ArrayList<>();
//    List<Goods.DataBean.ListBean> goodsList=new ArrayList<>();


    public static Intent newIntent(Context packageContext, String token, int root) {
        Intent i = new Intent(packageContext, MyGoodsActivity.class);
        i.putExtra(EXTRA_TOKEN, token);
        i.putExtra(EXTRA_ROOT, root);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goods);
        ButterKnife.bind(this);

        // 1 新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        mToken = bundle.getString(EXTRA_TOKEN);
        mRoot = bundle.getInt(EXTRA_ROOT, 3);

        // 2 读本地数据库
//        AuthorModel authorModel = DbHelper.getInstance().author().queryBuilder()
//                .where(AuthorModelDao.Properties.Author_token.eq(mToken))
//                .build().list().get(0);
//        editPassword.setText("");
//        editNickname.setText(authorModel.getAuthor_nickname());
//        editAddress.setText(authorModel.getAddress());

        if (mRoot==2) {
            tvMyGoodsTitle.setText("我发布的商品");
            tvMyGoodsAdd.setVisibility(View.VISIBLE);
        }
        if (mRoot==3) {
            tvMyGoodsTitle.setText("我订购的商品");
            tvMyGoodsAdd.setVisibility(View.GONE);
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        loadData();
    }



    private void initAdapter() {
        mAdapter = new MyGoodsAdapter(goodsList, mRoot);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        //mAdapter.addHeaderView(mHeader);
        mAdapter.bindToRecyclerView(rv);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View v, int position) {
                String content = null;
                MyGoods.DataBean c = (MyGoods.DataBean) adapter.getItem(position);
                if (c!=null){
                    switch (v.getId()) {
                        case R.id.iv:
                        case R.id.rl:
                        case R.id.tv_goods_name:
                        case R.id.tv_content:
                        case R.id.tv_price:
                            Intent i = DetailActivity.newIntent(v.getContext(),
                                    mToken,
                                    mRoot,
                                    c.getGid(),
                                    c.getGoodImg().getImgUrl(),
                                    c.getGName(),
                                    c.getGSort(),
                                    c.getPrice(),
                                    c.getIntro(),
                                    c.getClient().getNickname(),
                                    c.getOwnerPhone(),
                                    c.getClient().getAddress(),
                                    c.isDeal()
                            );
                            v.getContext().startActivity(i);
                            break;
                        case R.id.tv_delete:
                            if (mRoot==3){
                                // 取消订单
                                Toasty.success(v.getContext(), "暂时不能取消！", Toast.LENGTH_SHORT, true).show();


                            }else if (mRoot==2||mRoot==1){
                                // 删除商品
                                ViseHttp.GET("delete_good")
                                        .addHeader("token", SUPER_TOKEN)
                                        .addParam("gid", c.getGid())
                                        .tag("request_delete_good")
                                        .request(new ACallback<MyGoods>() {
                                            @Override
                                            public void onSuccess(MyGoods data) {
                                                ViseLog.i("request onSuccess!");
                                                if (data == null) {
                                                    Toasty.error(MyGoodsActivity.this, "服务器没有响应", Toast.LENGTH_SHORT, true).show();
                                                    return;
                                                }

                                                if (data.getCode() != 200) {
                                                    if (data.getCode()==500) {

                                                    }
                                                    ViseLog.e("提交出错了!");
                                                    Toasty.error(MyGoodsActivity.this, "提交出错了", Toast.LENGTH_SHORT, true).show();
                                                    return;
                                                }

                                                if (TextUtils.equals(data.getMessage(), "SUCCESS") /*&& data.getData()==null*/) {
                                                    Toasty.success(MyGoodsActivity.this, "删除成功！", Toast.LENGTH_SHORT, true).show();
                                                    loadData();
                                                }
                                            }

                                            @Override
                                            public void onFail(int errCode, String errMsg) {
                                                ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                                                Toasty.error(MyGoodsActivity.this, "错误： " + errMsg, Toast.LENGTH_SHORT, true).show();
                                            }
                                        });
                            }else {
                                Toasty.info(v.getContext(), "游客不能操作商品，请登录。", Toast.LENGTH_SHORT, true).show();
                            }

                            break;
                        case R.id.tv_improve:
                            if (mRoot==3){
                                Toasty.info(MyGoodsActivity.this, "普通会员不能修改商品： ", Toast.LENGTH_SHORT, true).show();

                            }else if (mRoot==2||mRoot==1){

                                if (DbHelper.getInstance().goods().loadAll().size()!=0){
                                    DbHelper.getInstance().goods().deleteAll();
                                }
                                GoodsModel goodsModel = new GoodsModel();
                                goodsModel.setGoods_id(c.getGid());
                                goodsModel.setGoods_price(c.getPrice());
                                goodsModel.setGoods_intro(c.getIntro());
                                goodsModel.setGoods_name(c.getGName());
                                goodsModel.setGoods_sort(c.getGSort());
                                DbHelper.getInstance().goods().insert(goodsModel);

                                Intent i1 = PublishActivity.newIntent(MyGoodsActivity.this, mToken, mRoot, PublishActivity.TYPE_MODIFY);
                                startActivityForResult(i1, REQUEST_CODE_MODIFIED);

                            }else {
                                Toasty.info(MyGoodsActivity.this, "游客不能修改商品，请登录后购买。", Toast.LENGTH_SHORT, true).show();
                            }
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_MODIFIED) {
                Toasty.info(MyGoodsActivity.this, "修改成功。", Toast.LENGTH_SHORT, true).show();

                loadData();
            }
            if (requestCode == REQUEST_CODE_PUBLIC) {
                Toasty.info(MyGoodsActivity.this, "发布成功。", Toast.LENGTH_SHORT, true).show();

                loadData();
            }
        }
    }

    private void updateView() {
        mAdapter.replaceData(goodsList);
        mAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        String whosAPI = null;
        if (mRoot==3) {
            whosAPI = "get_order";
        }else if (mRoot==2){
            whosAPI = "get_good";
        }
        ViseHttp.GET(whosAPI)
                .tag("request_"+whosAPI)
                .addHeader("token", mToken)
                .setLocalCache(false)
                .request(new ACallback<MyGoods>(){
                    @Override
                    public void onSuccess(MyGoods result) {
                        ViseLog.i(result);
                        if (result == null) {
                            Toasty.error(getApplicationContext(), "服务器访问出错!", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        if (result.getData() == null) {
                            Toasty.error(getApplicationContext(), "无商品!", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        ViseLog.i(result.getData());
                        goodsList = result.getData();

                        updateView();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Toasty.error(getApplicationContext(), "请求错误! " + errMsg, Toast.LENGTH_SHORT, true).show();

                        ViseLog.e("request onFail "+"request errorCode:" + errCode + ",errorMsg:" + errMsg+ "\n" );
                    }
                });
    }

    @OnClick({R.id.tv_my_goods_back, R.id.tv_my_goods_add, R.id.tv_my_goods_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_goods_back:
                finish();
                break;
            case R.id.tv_my_goods_add:
                Intent i = PublishActivity.newIntent(MyGoodsActivity.this, mToken, mRoot, PublishActivity.TYPE_PUBLIC);
                startActivityForResult(i, REQUEST_CODE_PUBLIC);
                break;
            case R.id.tv_my_goods_title:
                loadData();
                break;
        }
    }
}
