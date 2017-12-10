package com.red.gotrade.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.widget.Toast;

import com.red.gotrade.R;
import com.red.gotrade.fragment.GoodsFragment;
import com.red.gotrade.fragment.MyFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity implements MyFragment.OnFragmentInteractionListener {

    public FragmentManager fragmentManager;
    private GoodsFragment goodsFragment;
    private MyFragment myFragment;
    private String token;
    private int root = 3;
    public static final String POSITION = "position";
    public static final int FRAGMENT_ONE=0;
    public static final int FRAGMENT_TWO=1;
    public static final String EXTRA_TOKEN="token";
    public static final String EXTRA_ROOT="root";

    private int position;


    public static Intent newIntent(Context packageContext, String token, int root) {
        Intent i = new Intent(packageContext, HomeActivity.class);
        i.putExtra(EXTRA_TOKEN, token);
        i.putExtra(EXTRA_ROOT, root);
        return i;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode,resultCode,intent);

        if (myFragment!=null){
            myFragment.onActivityResult(requestCode,resultCode,intent);
        }
        if (goodsFragment!=null){
            goodsFragment.onActivityResult(requestCode,resultCode,intent);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        token = bundle.getString(EXTRA_TOKEN);
        root = bundle.getInt(EXTRA_ROOT, 3);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        fragmentManager=getSupportFragmentManager();
        showFragment(FRAGMENT_ONE);



        BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_goods) {
                    showFragment(FRAGMENT_ONE);
                }
                if (tabId == R.id.tab_my) {
                    if (!TextUtils.isEmpty(token)){
                        showFragment(FRAGMENT_TWO);
                    }else {
                        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this);
                        Intent i2 = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(i2, oc2.toBundle());

                        Toasty.error(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT, true).show();
                        finish();
                    }
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                //Toasty.info(getApplicationContext(), "刷新数据", Toast.LENGTH_SHORT, true).show();
                if (tabId == R.id.tab_goods) {
                    goodsFragment.loadData();
                }
                if (tabId == R.id.tab_my) {
                    if (!TextUtils.isEmpty(token)){
//                        showFragment(FRAGMENT_TWO);
                    }else {
                        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this);
                        Intent i2 = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(i2, oc2.toBundle());

                        Toasty.error(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT, true).show();
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //屏幕旋转时记录位置
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //屏幕恢复时取出位置
        showFragment(savedInstanceState.getInt(POSITION));
        super.onRestoreInstanceState(savedInstanceState);
    }


    public void showFragment(int index){

        FragmentTransaction ft=fragmentManager.beginTransaction();
        hideFragment(ft);

        //注意这里设置位置
        position = index;

        switch (index){
            case FRAGMENT_ONE:
                if (goodsFragment==null){
                    goodsFragment=GoodsFragment.newInstance(token, root);
                    ft.add(R.id.contentContainer,goodsFragment);
                }else {
                    ft.show(goodsFragment);
                }

                break;
            case FRAGMENT_TWO:

                if (myFragment==null){
                    myFragment=MyFragment.newInstance(token, root);
                    ft.add(R.id.contentContainer,myFragment);
                }else {
                    ft.show(myFragment);
                }

                break;
        }

        ft.commit();
    }

    public void hideFragment(FragmentTransaction ft){
        //如果不为空，就先隐藏起来
        if (goodsFragment!=null){
            ft.hide(goodsFragment);
        }
        if(myFragment!=null) {
            ft.hide(myFragment);
        }
    }

    @Override
    public void onUserSignOut() {
        //注销
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this);
        Intent i2 = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(i2, oc2.toBundle());

        Toasty.info(getApplicationContext(), "已注销登录", Toast.LENGTH_SHORT, true).show();
        finish();
    }
}
