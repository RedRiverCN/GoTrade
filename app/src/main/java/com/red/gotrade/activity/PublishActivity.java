package com.red.gotrade.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.red.gotrade.R;
import com.red.gotrade.Utils;
import com.red.gotrade.db.DbHelper;
import com.red.gotrade.db.gen.GoodsModelDao;
import com.red.gotrade.entity.Goods;
import com.red.gotrade.model.GoodsModel;
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

import static com.red.gotrade.common.Common.Constance.SUPER_TOKEN;

public class PublishActivity extends Activity {

    public static final String EXTRA_TOKEN = "token";
    public static final String EXTRA_ROOT = "root";
    public static final String EXTRA_TYPE = "type";
    public static final int TYPE_PUBLIC = 1;
    public static final int TYPE_MODIFY = 2;
    @BindView(R.id.tv_public_back)
    ImageButton tvPublicBack;
    @BindView(R.id.tv_public_title)
    TextView tvPublicTitle;
    @BindView(R.id.tv_public)
    TextView tvPublic;
    @BindView(R.id.player_title_bar)
    LinearLayout playerTitleBar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tv_gid)
    TextView tvGid;
    @BindView(R.id.edit_gname)
    EditText editGname;
    @BindView(R.id.edit_gsort)
    EditText editGsort;
    @BindView(R.id.edit_gprice)
    EditText editGprice;
    @BindView(R.id.edit_gintroduction)
    EditText editGintroduction;

    private String mToken;
    private int mRoot = 3;
    private int mType = 3;

    private String mGid;
    private Double mPrice;
    private String mIntro;
    private String mName;
    private int mSort;


    public static Intent newIntent(Context packageContext, String token, int root, int type) {
        Intent i = new Intent(packageContext, PublishActivity.class);
        i.putExtra(EXTRA_TOKEN, token);
        i.putExtra(EXTRA_ROOT, root);
        i.putExtra(EXTRA_TYPE, type);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);

        // 1 新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        mToken = bundle.getString(EXTRA_TOKEN);
        mRoot = bundle.getInt(EXTRA_ROOT, 3);
        mType = bundle.getInt(EXTRA_TYPE, TYPE_PUBLIC);

        ViseLog.d(mType);
        ViseLog.d(mRoot);
        ViseLog.d(mToken);

        // 3 初始化View
        if (mType == TYPE_PUBLIC) {
            tvPublicTitle.setText("发布商品");
        } else if (mType == TYPE_MODIFY) {
            // 2 读本地数据库
            GoodsModel goodsModel = DbHelper.getInstance().goods().loadAll().get(0);
            mGid = goodsModel.getGoods_id();
            mPrice = goodsModel.getGoods_price();
            mIntro = goodsModel.getGoods_intro();
            mName = goodsModel.getGoods_name();
            mSort = goodsModel.getGoods_sort();

            tvPublicTitle.setText("修改商品");
            tvGid.setText("商品ID"+mGid);
            editGname.setText(mName);
            editGsort.setText(Utils.getSortName(mSort));
            editGprice.setText(String.valueOf(mPrice));
            editGintroduction.setText(mIntro);

        }

    }

    @OnClick({R.id.tv_public_back, R.id.tv_public})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_public_back:
                finish();
                break;
            case R.id.tv_public:
                if (mType == TYPE_PUBLIC) {
                    // 构造JSON以提交发布
                    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                    map.put("price",editGprice.getText().toString());
                    map.put("intro",editGintroduction.getText().toString());
                    map.put("gName",editGname.getText().toString());
                    map.put("gSort",String .valueOf(Utils.getSortId(editGsort.getText().toString())));
                    String json = GsonUtil.gson().toJson(map);
                    ViseLog.d(json);
                    ViseHttp.POST("add_good")
                            .addHeader("token", mToken)
                            .setRequestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                            .tag("request_add_good")
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
                                        ViseLog.i("发布成功");
                                        //Toasty.success(getApplicationContext(), "发布成功。", Toast.LENGTH_SHORT, true).show();
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {
                                    ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                                    Toasty.error(getApplicationContext(), "访问失败： " + errMsg, Toast.LENGTH_SHORT, true).show();
                                }
                            });

                } else if (mType == TYPE_MODIFY) {
                    // 构造JSON以提交修改
                    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                    map.put("gid",mGid);
                    map.put("price",editGprice.getText().toString());
                    map.put("intro",editGintroduction.getText().toString());
                    map.put("gName",editGname.getText().toString());
                    map.put("gSort",String .valueOf(Utils.getSortId(editGsort.getText().toString())));
                    String json = GsonUtil.gson().toJson(map);
                    ViseLog.d(json);
                    ViseHttp.POST("update_good")
                            .addHeader("token", SUPER_TOKEN)
                            .setRequestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                            .tag("request_update_good")
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
                                        ViseLog.i("修改成功");
                                        //Toasty.success(getApplicationContext(), "修改成功。", Toast.LENGTH_SHORT, true).show();
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {
                                    ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                                    Toasty.error(getApplicationContext(), "访问失败： " + errMsg, Toast.LENGTH_SHORT, true).show();
                                }
                            });
                }
                break;
        }
    }
}
