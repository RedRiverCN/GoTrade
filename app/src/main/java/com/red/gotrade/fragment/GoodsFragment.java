package com.red.gotrade.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.red.gotrade.R;
import com.red.gotrade.activity.DetailActivity;
import com.red.gotrade.adapter.GoodsAdapter;
import com.red.gotrade.db.DbHelper;
import com.red.gotrade.db.gen.AuthorModelDao;
import com.red.gotrade.entity.Goods;
import com.red.gotrade.entity.MyGoods;
import com.red.gotrade.model.AuthorModel;
import com.vise.log.ViseLog;
import com.vise.xsnow.common.GsonUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.mode.CacheMode;
import com.vise.xsnow.http.mode.CacheResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;


public class GoodsFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TOKEN = "param1";
    private static final String ARG_ROOT = "param2";
    private static final int REQUEST_CODE_ORDER = 104;
    //
//    // TODO: Rename and change types of parameters
    private String mToken;
    private int mRoot;
    private String mUserPhone;
    private boolean haveDatasFlag=false;
//
//    private OnFragmentInteractionListener mListener;
//
    RecyclerView mRecyclerView;
    GoodsAdapter mAdapter;
    List<Goods.DataBean.ListBean> goodsList=new ArrayList<>();



    public GoodsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GoodsFragment newInstance(String token, int root) {
        GoodsFragment fragment = new GoodsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TOKEN, token);
        args.putInt(ARG_ROOT, root);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case REQUEST_CODE_ORDER:
                    loadData();

                    break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取参数
        if (getArguments() != null) {
            mToken = getArguments().getString(ARG_TOKEN);
            mRoot = getArguments().getInt(ARG_ROOT);
        }
        if (mRoot==1||mRoot==2||mRoot==3){

            AuthorModel authorModel = DbHelper.getInstance().author().queryBuilder()
                    .where(AuthorModelDao.Properties.Author_token.eq(mToken))
                    .build().list().get(0);
            mUserPhone = authorModel.getPhone();
            //loadData();
        }
    }

    private void initAdapter() {
        mAdapter = new GoodsAdapter(goodsList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.isFirstOnly(false);
        //mAdapter.addHeaderView(mHeader);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, final View v, int position) {
                String content = null;
                Goods.DataBean.ListBean c = (Goods.DataBean.ListBean) adapter.getItem(position);
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
                                    c.getDeal()
                            );
                            v.getContext().startActivity(i);
                            break;
                        case R.id.button:
                            if (!c.getDeal()){
                                if (mRoot==3||mRoot==1){
                                    //Toasty.info(getContext(), "购买物品： " + c.getGName(), Toast.LENGTH_SHORT, true).show();
                                    // 构造JSON以提交下单
                                    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                                    map.put("gid",c.getGid());
                                    map.put("phone",mUserPhone);
                                    String json = GsonUtil.gson().toJson(map);

                                    ViseHttp.POST("save_order")
                                            .addHeader("token", mToken)
                                            .setRequestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                                            .tag("request_save_order")
                                            .request(new ACallback<MyGoods>() {

                                                @Override
                                                public void onSuccess(MyGoods data) {
                                                    ViseLog.i("request onSuccess!");
                                                    if (data == null) {
                                                        Toasty.error(v.getContext(), "服务器没有响应", Toast.LENGTH_SHORT, true).show();
                                                        return;
                                                    }
                                                    ViseLog.d(data.getCode()+data.getMessage());
                                                    if (data.getCode() != 200) {
                                                        if (data.getCode() == 500) {
                                                            Toasty.error(v.getContext(), "已经订购了", Toast.LENGTH_SHORT, true).show();
                                                            return;
                                                        }
                                                        Toasty.error(v.getContext(), "提交出错了", Toast.LENGTH_SHORT, true).show();
                                                        return;
                                                    }

                                                    ViseLog.i(data);
                                                    if (TextUtils.equals(data.getMessage(), "SUCCESS") /*&& data.getData()==null*/) {
                                                        Toasty.success(v.getContext(), "下单成功。", Toast.LENGTH_SHORT, true).show();
                                                        loadData();
                                                    }
                                                }

                                                @Override
                                                public void onFail(int errCode, String errMsg) {
                                                    ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                                                    Toasty.error(v.getContext(), "下单失败： " + errMsg, Toast.LENGTH_SHORT, true).show();

                                                }
                                            });
                                }else if (mRoot==2){
                                    Toasty.info(v.getContext(), "商家不能买商品", Toast.LENGTH_SHORT, true).show();

                                }else {
                                    Toasty.info(v.getContext(), "游客不能买商品，请登录后购买。", Toast.LENGTH_SHORT, true).show();

                                }
                            }else {
                                Toasty.info(v.getContext(), "商品已被订购。", Toast.LENGTH_SHORT, true).show();

                            }

                            break;
                    }
                }
            }
        });
    }

    private void updateView() {
        if (haveDatasFlag) {
            mAdapter.replaceData(goodsList);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void loadData() {
        ViseHttp.POST("all_good")
                .tag("request_all_good")
                .setLocalCache(true)
                .cacheKey("all")//缓存Key
                .cacheMode(CacheMode.FIRST_REMOTE)
                .cacheTime(1800000)//缓存保留30分钟
                .request(new ACallback<CacheResult<Goods> >(){
                    @Override
                    public void onSuccess(CacheResult<Goods> result) {
                        ViseLog.i(result);
                       if (result == null || result.getCacheData() == null) {
                            Toasty.error(getContext(), "服务器访问出错!", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        if (result.getCacheData().getData() == null) {
                            Toasty.error(getContext(), "无商品!", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        ViseLog.i(result.getCacheData().getData().getList());
                        goodsList = result.getCacheData().getData().getList();
                        haveDatasFlag=true;
                        updateView();
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Toasty.error(getContext(), "请求错误! " + errMsg, Toast.LENGTH_SHORT, true).show();

                        ViseLog.e("request onFail "+"request errorCode:" + errCode + ",errorMsg:" + errMsg+ "\n" );
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_goods, container, false);

        mRecyclerView = parentView.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(parentView.getContext()));
        initAdapter();
        loadData();

        return parentView;
    }


//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
