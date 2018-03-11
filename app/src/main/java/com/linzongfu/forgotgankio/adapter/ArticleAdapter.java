package com.linzongfu.forgotgankio.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
        helper.setText(R.id.tv_title, item.getDesc());
        if (item.getWho() != null) {
            helper.setText(R.id.tv_writer, "上传者：" + item.getWho().toString());
        }
        helper.addOnClickListener(R.id.cardView);
    }
}