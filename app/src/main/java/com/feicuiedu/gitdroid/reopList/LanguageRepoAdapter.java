package com.feicuiedu.gitdroid.reopList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.hotrepositor.Repo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhengshujuan on 2016/7/7.
 */
public class LanguageRepoAdapter extends BaseAdapter {
private ArrayList<Repo> datas;
    public LanguageRepoAdapter(){
        datas=new ArrayList<>();
    }
    //添加语言数据
    public void addAll(Collection<Repo> repos){
        datas.addAll(repos);
        notifyDataSetChanged();
    }
    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Repo getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            convertView=inflater.inflate(R.layout.layout_item_repo,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder= (ViewHolder) convertView.getTag();
        Repo repo=getItem(position);
        viewHolder.repoName.setText(repo.getFullName());
        viewHolder.repoInfo.setText(repo.getDescription());
        viewHolder.repoStars.setText(repo.getStargazerCount()+"");
        Toast.makeText(parent.getContext(), repo.getFullName(), Toast.LENGTH_SHORT).show();
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),viewHolder.ivIcon);
        return convertView;
    }
     class ViewHolder{
        @Bind(R.id.ivIcon)
        ImageView ivIcon;
        @Bind(R.id.tvRepoName)
        TextView repoName;
        @Bind(R.id.tvRepoInfo) TextView repoInfo;
        @Bind(R.id.tvRepoStars) TextView repoStars;

        ViewHolder(View view ){
            ButterKnife.bind(this,view);
        }
    }
}
