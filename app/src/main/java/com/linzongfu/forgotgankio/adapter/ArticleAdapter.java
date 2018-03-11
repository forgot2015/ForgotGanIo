package com.linzongfu.forgotgankio.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.linzongfu.forgotgankio.MyApplication;
import com.linzongfu.forgotgankio.R;
import com.linzongfu.forgotgankio.bean.DataByCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by forgot on 2018/3/11.
 * Email: forgot2015@gmail.com
 */

public class ArticleAdapter extends BaseQuickAdapter<DataByCategory.ResultsBean, BaseViewHolder> {

    public ArticleAdapter() {
        super(R.layout.item_article_title);
    }

    @Override
    public void setNewData(@Nullable List<DataByCategory.ResultsBean> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataByCategory.ResultsBean item) {
        if (item.getType().equals("福利")) {
            helper.getView(R.id.iv_welfare).bringToFront();
            helper.getView(R.id.iv_welfare).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_article).setVisibility(View.GONE);
            Picasso.with(MyApplication.getInstance())
                    .load(item.getUrl())
                    .into((ImageView) helper.getView(R.id.iv_welfare));
        } else {
            helper.getView(R.id.ll_article).bringToFront();
            helper.getView(R.id.iv_welfare).setVisibility(View.GONE);
            helper.getView(R.id.ll_article).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_title, item.getDesc());
            if (item.getWho() != null) {
                helper.setText(R.id.tv_writer, "上传者：" + item.getWho().toString());
            }
            helper.addOnClickListener(R.id.cardView);
        }

    }
}