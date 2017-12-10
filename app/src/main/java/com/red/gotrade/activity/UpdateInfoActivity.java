package com.red.gotrade.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.red.gotrade.R;
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

public class UpdateInfoActivity extends AppCompatActivity {

    public static final String EXTRA_TOKEN = "token";
    public static final String EXTRA_ROOT = "root";
    @BindView(R.id.introduction_back)
    ImageButton introductionBack;
    @BindView(R.id.introduction_title)
    TextView introductionTitle;
    @BindView(R.id.introduction_finish)
    TextView introductionFinish;
    @BindView(R.id.player_title_bar)
    LinearLayout playerTitleBar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_nickname)
    EditText editNickname;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_introduction)
    EditText editIntroduction;
    @BindView(R.id.edit_old_password)
    EditText editOldPassword;
    private String mToken;
    private int mRoot = 3;
    private Boolean passwordChange = false;

    public static Intent newIntent(Context packageContext, String token, int root) {
        Intent i = new Intent(packageContext, UpdateInfoActivity.class);
        i.putExtra(EXTRA_TOKEN, token);
        i.putExtra(EXTRA_ROOT, root);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        ButterKnife.bind(this);

        // 1 新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        mToken = bundle.getString(EXTRA_TOKEN);
        mRoot = bundle.getInt(EXTRA_ROOT, 3);

        // 2 读本地数据库
        AuthorModel authorModel = DbHelper.getInstance().author().queryBuilder()
                .where(AuthorModelDao.Properties.Author_token.eq(mToken))
                .build().list().get(0);
        editPassword.setText("");
        editNickname.setText(authorModel.getAuthor_nickname());
        editAddress.setText(authorModel.getAddress());

    }

    @OnClick({R.id.introduction_back, R.id.introduction_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.introduction_back:
                finish();
                break;
            case R.id.introduction_finish:

                if (!TextUtils.isEmpty(editPassword.getText().toString()) && !TextUtils.isEmpty(editOldPassword.getText().toString())) {
                    if (TextUtils.equals(editPassword.getText().toString(), editOldPassword.getText().toString())) {
                        Toasty.error(getApplicationContext(), "新旧密码相同", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    // 提交修改个人密码
                    // 构造JSON以提交
                    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                    map.put("oldPassword", editOldPassword.getText().toString());
                    map.put("newPassword", editPassword.getText().toString());
                    String json = GsonUtil.gson().toJson(map);
                    ViseHttp.POST("changePW")
                            .addHeader("token", mToken)
                            .setRequestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                            .tag("request_changePW")
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
                                        Toasty.success(getApplicationContext(), "密码修改成功，需重新登录。", Toast.LENGTH_SHORT, true).show();

                                        passwordChange=true;
                                        Intent intent = new Intent();
                                        intent.putExtra("result", "success");
                                        setResult(RESULT_OK, intent);
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {
                                    ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                                    Toasty.error(getApplicationContext(), "修改失败： " + errMsg, Toast.LENGTH_SHORT, true).show();

                                }
                            });
                }else {
                    passwordChange = false;
                }
                // 有效性检查
                if (TextUtils.isEmpty(editNickname.getText().toString()) ||
                        TextUtils.isEmpty(editAddress.getText().toString())) {

                    Toasty.error(getApplicationContext(), "不能为空", Toast.LENGTH_SHORT, true).show();
                    return;
                }

                // 提交修改个人信息
                // 构造JSON以提交
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("nickname", editNickname.getText().toString());
                map.put("address", editAddress.getText().toString());
                String json = GsonUtil.gson().toJson(map);
                ViseHttp.POST("update_user")
                        .addHeader("token", mToken)
                        .setRequestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                        .tag("request_update_user")
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
                                Toasty.success(getApplicationContext(), "信息修改成功。", Toast.LENGTH_SHORT, true).show();

                                finish();

                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                                Toasty.error(getApplicationContext(), "修改失败： " + errMsg, Toast.LENGTH_SHORT, true).show();

                            }
                        });
                break;
        }
    }
}
