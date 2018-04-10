package com.rssread.www.Adapter.Function;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rssread.www.Activity.MainActivity;
import com.rssread.www.Activity.WebviewActivity;
import com.rssread.www.Adapter.Item.MyBlogItem;
import com.rssread.www.Adapter.Item.MyBlogShowItem;
import com.rssread.www.R;
import com.rssread.www.Util.PopMessageUtil;
import com.rssread.www.Util.SwitchUtil;

import java.util.List;

/**
 * Created by yy on 2018/3/15.
 */
public class MyBlogListAdapter extends BaseAdapter {
    private MainActivity mContext = null;
    private List<MyBlogShowItem> myBlogShowList;
    private LayoutInflater mInflater;

    public MyBlogListAdapter(MainActivity mactivity, List<MyBlogShowItem> bi) {
        mContext = mactivity;
        myBlogShowList = bi;
        mInflater = LayoutInflater.from(mactivity);
    }

    @Override
    public int getCount() {
        int count = 0;
        if (null != myBlogShowList) {
            count = myBlogShowList.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        MyBlogShowItem item = null;
        if (null != myBlogShowList) {
            item = myBlogShowList.get(position);
        }
        return item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView itemTitle_txt, itemContxt_txt;
        LinearLayout BlogItem_layout;

        TextView categrayTitle_txt;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (myBlogShowList.get(position).getType().compareTo("title") == 0) {
            //标题显示
            convertView = mInflater.inflate(R.layout.item_blogcategray, null);
            viewHolder.categrayTitle_txt = (TextView) convertView.findViewById(R.id.BlogCategrayItem_Title);
            convertView.setTag(viewHolder);
            viewHolder.categrayTitle_txt.setText(myBlogShowList.get(position).getTitle());
        } else {
            convertView = mInflater.inflate(R.layout.item_mybloglist, null);

            viewHolder.itemTitle_txt = (TextView) convertView.findViewById(R.id.MyBlogItem_Title);
            viewHolder.itemContxt_txt = (TextView) convertView.findViewById(R.id.MyBlogItem_Context);
            viewHolder.BlogItem_layout = (LinearLayout) convertView.findViewById(R.id.MyBlogItem_layout);
            convertView.setTag(viewHolder);
            //绑定数据
            viewHolder.itemTitle_txt.setText(myBlogShowList.get(position).getTitle().substring(0, 1));
            viewHolder.itemContxt_txt.setText(myBlogShowList.get(position).getTitle());

            viewHolder.BlogItem_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopMessageUtil.Log("点击" + myBlogShowList.get(position).getTitle() + "|" + myBlogShowList.get(position).getUrl());
                    SwitchUtil.switchActivity(mContext, WebviewActivity.class)
                            .addBoolean("type",false)
                            .addString("url", myBlogShowList.get(position).getUrl())
                            .addString("title", myBlogShowList.get(position).getTitle().length()>10?myBlogShowList.get(position).getTitle().substring(0, 10)+"...":myBlogShowList.get(position).getTitle())
                            .switchToForResult(1);

                }
            });
            //----------------长按删除-----------------//
            viewHolder.BlogItem_layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mContext.DelItemPostion = position;
                    mContext.DelItemType = 1;
                    mContext.DelBlogInfoMessage("删除博客", "是否删除订阅的博客");
                    return false;
                }
            });
        }
        return convertView;
    }

    public void UpdataBlogInfo(List<MyBlogShowItem> bi) {
        this.myBlogShowList = bi;
        notifyDataSetChanged();
    }
}