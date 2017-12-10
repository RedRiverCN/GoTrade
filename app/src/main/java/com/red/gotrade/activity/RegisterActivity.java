package com.red.gotrade.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.red.gotrade.R;
import com.red.gotrade.common.Common;
import com.red.gotrade.entity.Login;
import com.red.gotrade.model.AuthorModel;
import com.vise.log.ViseLog;
import com.vise.xsnow.common.GsonUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.mode.CacheMode;
import com.vise.xsnow.http.mode.CacheResult;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.cv_add)
    CardView cvAdd;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repeatpassword)
    EditText etRepeatpassword;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.et_type)
    EditText etType;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                //fab.setImageResource(R.drawable.plus);//可能引起oom
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @OnClick(R.id.bt_go)
    public void onViewClicked() {
        // 正确性检查
        if (!TextUtils.equals(etPassword.getText().toString(), etRepeatpassword.getText().toString())) {

            Toasty.error(getApplicationContext(), "密码不同", Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString()) || TextUtils.isEmpty(etRepeatpassword.getText().toString()) ||
                TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etAddress.getText().toString())
                || TextUtils.isEmpty(etNickname.getText().toString()) || TextUtils.isEmpty(etType.getText().toString())) {

            Toasty.error(getApplicationContext(), "不能为空", Toast.LENGTH_SHORT, true).show();
            return;
        }
        if (!TextUtils.equals(etType.getText().toString(), String.valueOf(2))) {
            if (!TextUtils.equals(etType.getText().toString(), String.valueOf(3))) {
                Toasty.error(getApplicationContext(), "注册类型错误", Toast.LENGTH_SHORT, true).show();
                return;
            }
        }
        if (!Pattern.matches(Common.Constance.REGEX_MOBILE, etUsername.getText().toString())){
            Toasty.error(getApplicationContext(), "手机号格式不正确！", Toast.LENGTH_SHORT, true).show();
            return;
        }

        // 构造JSON以提交
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("phone",etUsername.getText().toString());
        map.put("password",etPassword.getText().toString());
        map.put("status",etType.getText().toString());
        map.put("nickname",etNickname.getText().toString());
        map.put("address",etAddress.getText().toString());
        String json = GsonUtil.gson().toJson(map);

        ViseHttp.POST("register")
                .setRequestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                .tag("request_register")
                .request(new ACallback<Login>() {
                    @Override
                    public void onSuccess(Login data) {
                        ViseLog.i("request onSuccess!");
                        if (data == null) {
                            Toasty.error(getApplicationContext(), "服务器没有响应", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        if (data.getCode() != 200) {
                            Toasty.error(getApplicationContext(), "提交出错了", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        if (data.getData() == null) {
                            Toasty.error(getApplicationContext(), "注册出错", Toast.LENGTH_SHORT, true).show();
                            return;
                        }

                        if (data.getData().getToken() != null) {
                            Toasty.success(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT, true).show();
                            onBackPressed();
                        }else {
                            Toasty.error(getApplicationContext(), "注册失败，服务器没有返回token", Toast.LENGTH_SHORT, true).show();
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                        Toasty.error(getApplicationContext(), "注册失败 " + errMsg, Toast.LENGTH_SHORT, true).show();
                    }
                });
    }


    @Override
    protected void onStop() {
        super.onStop();
//        Toasty.error(getApplicationContext(), "RegisterActivity销毁", Toast.LENGTH_SHORT, true).show();
        ViseLog.d("RegisterActivity销毁");
        finish();
    }
}
