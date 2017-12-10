package com.red.gotrade.adapter;


import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.red.gotrade.R;
import com.red.gotrade.Utils;
import com.red.gotrade.entity.Goods;
import com.vise.log.ViseLog;

import java.util.List;


public class GoodsAdapter extends BaseQuickAdapter<Goods.DataBean.ListBean, GoodsAdapter.GoodsViewHolder> {


    public GoodsAdapter(@Nullable List<Goods.DataBean.ListBean> data) {
        super(R.layout.item_goods,data);
    }

    @Override
    protected void convert(GoodsViewHolder helper, Goods.DataBean.ListBean item) {
        helper.addOnClickListener(R.id.tv_content);
        helper.addOnClickListener(R.id.iv);
        helper.addOnClickListener(R.id.rl);
        helper.addOnClickListener(R.id.tv_goods_name);
        helper.addOnClickListener(R.id.tv_price);
        helper.addOnClickListener(R.id.tv_sort);
        helper.addOnClickListener(R.id.button);
        helper.bind(item);
    }
//
//    private MoviePresenter mPresenter;
//
//    public GoodsAdapter(int layoutResId, List<Movie> data) {
//        super(layoutResId, data);
//
//        mPresenter = new MoviePresenter();
//    }
//
//    @Override
//    protected void convert(GoodsViewHolder helper, Movie item) {
//        ViewDataBinding binding = helper.getBinding();
//        binding.setVariable(BR.movie, item);
//        binding.setVariable(BR.presenter, mPresenter);
//        binding.executePendingBindings();
//        switch (helper.getLayoutPosition() %
//                2) {
//            case 0:
//                helper.setImageResource(R.id.iv, R.mipmap.m_img1);
//                break;
//            case 1:
//                helper.setImageResource(R.id.iv, R.mipmap.m_img2);
//                break;
//
//        }
//    }
//
//    /*  @Override
//      protected GoodsViewHolder createBaseViewHolder(View view) {
//          return new GoodsViewHolder(view);
//      }
//  */
//    @Override
//    protected View getItemView(int layoutResId, ViewGroup parent) {
//        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
//        if (binding == null) {
//            return super.getItemView(layoutResId, parent);
//        }
//        View view = binding.getRoot();
//        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
//        return view;
//    }

    public static class GoodsViewHolder extends BaseViewHolder {
        public ImageView imageView;
        public TextView goodsName;
        public TextView goodsDesc;
        public TextView goodsPrice;
        public TextView goodsSort;
        public TextView goodsDeal;
        FloatingActionButton btnBuy;

        public View root;
        public GoodsViewHolder(View view) {
            super(view);

            root = view;
            imageView = view.findViewById(R.id.iv);
            goodsName =  view.findViewById(R.id.tv_goods_name);
            goodsDesc = view.findViewById(R.id.tv_content);
            goodsPrice = view.findViewById(R.id.tv_price);
            goodsSort = view.findViewById(R.id.tv_sort);
            btnBuy = view.findViewById(R.id.button);
            goodsDeal = view.findViewById(R.id.tv_deal);
        }

        public void bind(final Goods.DataBean.ListBean c) {
            ViseLog.d(c);
            if (c==null){
                ViseLog.e(c);
                return;
            }
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = DetailActivity.newIntent(v.getContext(),
//                            c.getGid(),
//                            c.getGoodImg().getImgUrl(),
//                            c.getGName(),
//                            c.getGSort(),
//                            c.getPrice(),
//                            c.getIntro(),
//                            c.getClient().getNickname(),
//                            c.getOwnerPhone(),
//                            c.getClient().getAddress()
//                            );
//                    v.getContext().startActivity(i);
//                }
//            });

//            btnBuy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = DetailActivity.newIntent(v.getContext(),
//                            c.getGid(),
//                            c.getGoodImg().getImgUrl(),
//                            c.getGName(),
//                            c.getGSort(),
//                            c.getPrice(),
//                            c.getIntro(),
//                            c.getClient().getNickname(),
//                            c.getOwnerPhone(),
//                            c.getClient().getAddress()
//                    );
//                    v.getContext().startActivity(i);
//                }
//            });

            Glide.with(root.getContext())
                    .load(c.getGoodImg().getImgUrl())
                    .into(imageView);
            goodsName.setText(c.getGName());
            goodsDesc.setText(c.getIntro());
            goodsPrice.setText("¥ "+ c.getPrice());
            goodsSort.setText(Utils.getSortName(c.getGSort()));
            ViseLog.d(c.getDeal());
            if (c.getDeal()) {
                goodsDeal.setVisibility(View.VISIBLE);
                goodsDeal.setText("已被订购");
            }else {
                goodsDeal.setVisibility(View.GONE);
            }
        }
    }
}
