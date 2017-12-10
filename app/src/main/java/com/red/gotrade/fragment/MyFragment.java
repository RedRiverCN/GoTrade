package com.red.gotrade.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.red.gotrade.R;
import com.red.gotrade.Utils;
import com.red.gotrade.activity.MyGoodsActivity;
import com.red.gotrade.activity.PublishActivity;
import com.red.gotrade.activity.UpdateInfoActivity;
import com.red.gotrade.db.DbHelper;
import com.red.gotrade.db.gen.AuthorModelDao;
import com.red.gotrade.model.AuthorModel;
import com.vise.log.ViseLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import okhttp3.internal.Util;

import static android.app.Activity.RESULT_OK;


public class MyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TOKEN = "param1";
    private static final String ARG_ROOT = "param2";
    public static final int REQUEST_CODE_MODIFIED = 101;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.rl_improve_my_info)
    RelativeLayout rlImproveMyInfo;
    @BindView(R.id.tv_my_goods)
    TextView tvMyGoods;
    @BindView(R.id.rl_my_goods)
    RelativeLayout rlMyGoods;
    @BindView(R.id.player_user_avatar)
    ImageView playerUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_sign_out)
    TextView tvSignOut;
    Unbinder unbinder;


    // TODO: Rename and change types of parameters
    private String mUserName;
    private String mToken;
    private int mRoot;


    private OnFragmentInteractionListener mListener;

    public MyFragment() {
        // Required empty public constructor
    }

    //    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MyFragment.
//     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String token, int root) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TOKEN, token);
        args.putInt(ARG_ROOT, root);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mToken = getArguments().getString(ARG_TOKEN);
            mRoot = getArguments().getInt(ARG_ROOT);
        }
        AuthorModel authorModel = DbHelper.getInstance().author().queryBuilder()
                .where(AuthorModelDao.Properties.Author_token.eq(mToken))
                .build().list().get(0);
        mUserName = authorModel.getAuthor_nickname();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_my, container, false);

        unbinder = ButterKnife.bind(this, parentView);

        tvUserName.setText(Utils.getRootName(mRoot)+ mUserName);
        if (mRoot==1||mRoot==2||mRoot==3) {
            tvInfo.setText("修改个人信息");
        }
        if (mRoot==2) {
            tvMyGoods.setText("我发布的商品");
        }
        if (mRoot==3) {
            tvMyGoods.setText("我订购的商品");
        }

        return parentView;
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        super.onActivityResult(requestCode,resultCode,intent);
        switch(requestCode){
            case REQUEST_CODE_MODIFIED:
                if(resultCode == RESULT_OK){
                    ViseLog.d("修改密码后注销");
                    mListener.onUserSignOut();
                }
                break;
        }
    }

    //
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({R.id.rl_improve_my_info, R.id.rl_my_goods, R.id.tv_user_name, R.id.tv_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_improve_my_info:
                if (mRoot==1||mRoot==2||mRoot==3) {
                    Intent i = UpdateInfoActivity.newIntent(getActivity(), mToken, mRoot);
                    startActivityForResult(i, REQUEST_CODE_MODIFIED);
                }
                break;
            case R.id.rl_my_goods:
                if (mRoot==2) {
                    ActivityOptionsCompat oc = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
                    Intent i = MyGoodsActivity.newIntent(getActivity(), mToken, mRoot);
                    startActivity(i, oc.toBundle());
                }
                if (mRoot==3) {
                    ActivityOptionsCompat oc = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
                    Intent i = MyGoodsActivity.newIntent(getActivity(), mToken, mRoot);
                    startActivity(i, oc.toBundle());
                }
                break;
            case R.id.tv_user_name:
                Toasty.info(view.getContext(), "用户昵称： " + mUserName, Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.tv_sign_out:
                mListener.onUserSignOut();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onUserSignOut();
    }
}
