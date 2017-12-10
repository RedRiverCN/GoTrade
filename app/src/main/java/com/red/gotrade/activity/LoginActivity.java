package com.red.gotrade.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.red.gotrade.R;
import com.red.gotrade.db.DbHelper;
import com.red.gotrade.entity.Login;
import com.red.gotrade.model.AuthorModel;
import com.vise.log.ViseLog;
import com.vise.xsnow.common.GsonUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.mode.CacheMode;
import com.vise.xsnow.http.mode.CacheResult;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_visitor)
    TextView tvVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        ButterKnife.bind(this);
    }
/*
*
*         Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        */

    @OnClick({R.id.bt_go, R.id.fab, R.id.tv_visitor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_go:
                // 正确性检查
                if (TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
                    Toasty.error(getApplicationContext(), "账号或密码不能为空！", Toast.LENGTH_SHORT, true).show();
                    break;
                }

                // 构造JSON以提交登录
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("phone",etUsername.getText().toString());
                map.put("password",etPassword.getText().toString());
                String json = GsonUtil.gson().toJson(map);

                ViseHttp.POST("login")
                        .setRequestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                        .tag("request_login")
                        .request(new ACallback<Login>() {
                            @Override
                            public void onSuccess(Login data) {
                                ViseLog.i("request onSuccess!");
                                if (data == null) {
                                    Toasty.error(getApplicationContext(), "服务器没有响应", Toast.LENGTH_SHORT, true).show();
                                    return;
                                }
                                if (data.getData() == null) {
                                    Toasty.error(getApplicationContext(), "账号或密码不正确！", Toast.LENGTH_SHORT, true).show();
                                    return;
                                }

                                if (data.getData().getToken() != null) {
                                    Toasty.success(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT, true).show();


                                    if (DbHelper.getInstance().author().loadAll().size()!=0){
                                        DbHelper.getInstance().author().deleteAll();
                                    }
                                    AuthorModel authorModel = new AuthorModel();
                                    authorModel.setAddress(data.getData().getAddress());
                                    authorModel.setAuthor_nickname(data.getData().getNickname());
                                    authorModel.setAuthor_token(data.getData().getToken());
                                    authorModel.setPhone(data.getData().getPhone());
                                    authorModel.setStatus(data.getData().getStatus());
                                    DbHelper.getInstance().author().insert(authorModel);

                                    Explode explode = new Explode();
                                    explode.setDuration(500);
                                    getWindow().setExitTransition(explode);
                                    getWindow().setEnterTransition(explode);

                                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                                    Intent i2 = HomeActivity.newIntent(LoginActivity.this, data.getData().getToken(), data.getData().getStatus());
                                    startActivity(i2, oc2.toBundle());
                                }else {
                                    Toasty.error(getApplicationContext(), "登录失败，服务器没有返回token", Toast.LENGTH_SHORT, true).show();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                                Toasty.error(getApplicationContext(), "登录失败： " + errMsg, Toast.LENGTH_SHORT, true).show();
                            }
                        });

        break;
            case R.id.tv_visitor:
                Explode explode1 = new Explode();
                explode1.setDuration(500);

                getWindow().setExitTransition(explode1);
                getWindow().setEnterTransition(explode1);
                ActivityOptionsCompat oc21 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                Intent i21 = HomeActivity.newIntent(LoginActivity.this, "", 4);
//                Intent i21 = HomeActivity.newIntent(LoginActivity.this, "test", 2);
                startActivity(i21, oc21.toBundle());

                break;
        }
    }



    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toasty.error(getApplicationContext(), "LoginActivity销毁", Toast.LENGTH_SHORT, true).show();
        ViseLog.d("LoginActivity销毁");
        finish();
    }
}
