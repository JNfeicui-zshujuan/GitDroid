package com.feicuiedu.gitdroid.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhengshujuan on 2016/7/9.
 */
public class LocalRepoAdapter extends BaseAdapter {
    private final ArrayList<LocalRepo> datas;

    public LocalRepoAdapter() {
        datas=new ArrayList<>();
    }

    public void setDatas(Collection<LocalRepo> repos) {
        datas.clear();
        datas.addAll(repos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public LocalRepo getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.layout_item_repo, parent, false);
            convertView.setTag(new ViewHold(convertView));
        }
        ViewHold viewHold = (ViewHold) convertView.getTag();
        LocalRepo repo = getItem(position);
        ImageLoader.getInstance().displayImage(repo.getAvatatr(), viewHold.imageView);
        viewHold.tvRepoInfo.setText(repo.getDescription());
        viewHold.tvRepoName.setText(repo.getFullName());
        viewHold.tvRepoStars.setText(String.format("star: %d", repo.getStarCount()));
        return convertView;
    }

    class ViewHold {
        @Bind(R.id.ivIcon)
        ImageView imageView;
        @Bind(R.id.tvRepoName)
        TextView tvRepoName;
        @Bind(R.id.tvRepoInfo)
        TextView tvRepoInfo;
        @Bind(R.id.tvRepoStars)
        TextView tvRepoStars;

        public ViewHold(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
