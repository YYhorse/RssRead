package com.rssread.www.Adapter.Function;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rssread.www.Activity.MainActivity;
import com.rssread.www.Activity.WebviewActivity;
import com.rssread.www.Adapter.Item.BlogItem;
import com.rssread.www.R;
import com.rssread.www.Util.PopMessageUtil;
import com.rssread.www.Util.SwitchUtil;

import java.util.List;

/**
 * Created by yy-surface on 2018/3/14.
 */
public class BlogListAdapter extends BaseAdapter {
    private MainActivity mContext = null;
    private List<BlogItem> blogItems = null;
    public BlogListAdapter(MainActivity mactivity, List<BlogItem> bi) {
        mContext = mactivity;
        blogItems = bi;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (null != blogItems) {
            count = blogItems.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        BlogItem item = null;
        if (null != blogItems) {
            item = blogItems.get(position);
        }
        return item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView itemTitle_txt,itemContxt_txt;
        LinearLayout BlogItem_layout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_bloglist, null);
            viewHolder.itemTitle_txt = (TextView) convertView.findViewById(R.id.BlogItem_Title);
            viewHolder.itemContxt_txt = (TextView) convertView.findViewById(R.id.BlogItem_Context);
            viewHolder.BlogItem_layout = (LinearLayout) convertView.findViewById(R.id.BlogItem_layout);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        //---------------获取最新数据--------------//
        viewHolder.itemTitle_txt.setText(blogItems.get(position).getTitle().substring(0,1));
        viewHolder.itemContxt_txt.setText(blogItems.get(position).getTitle());
        //----------------点击事件-----------------//
        viewHolder.BlogItem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopMessageUtil.Log("点击" + blogItems.get(position).getTitle() + "|" + blogItems.get(position).getUrl());
                SwitchUtil.switchActivity(mContext, WebviewActivity.class)
                        .addString("url", blogItems.get(position).getUrl())
                        .addString("title", blogItems.get(position).getTitle())
                        .switchToForResult(2);
            }
        });
        //----------------长按删除-----------------//
        viewHolder.BlogItem_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mContext.DelItemPostion = position;
                mContext.DelBlogInfoMessage("删除博客","是否删除该博客");
                return false;
            }
        });
        return convertView;
    }

    public void UpdataBlogInfo(List<BlogItem> bi){
        this.blogItems = bi;
        notifyDataSetChanged();
    }
}
