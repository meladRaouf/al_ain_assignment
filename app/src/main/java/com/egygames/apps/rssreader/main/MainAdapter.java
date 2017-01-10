package com.egygames.apps.rssreader.main;
/**
 * Articles recyclerview adapter
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egygames.apps.rssreader.model.Article;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import com.egygames.apps.rssreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private final View.OnClickListener listener;
    private final Picasso picasso;
    private final Context context;
    private ArrayList<Article> data;

    public MainAdapter(Context context, View.OnClickListener listener) {
        data = new ArrayList<>();
        this.listener = listener;
        picasso = Picasso.with(context);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        view.setOnClickListener(listener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;


    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article item=data.get(position);
        holder.title.setText(item.getTitle());
        if(item.getMainImg()!=null &&!item.getMainImg().isEmpty()) {
            picasso.load(item.getMainImg()).placeholder(R.drawable.default_img).into(holder.img);
        }

        //set the view tag to article position, so we can retrieve the clicked article.
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItems(List<Article> list) {
        this.data.addAll(list);
    }
    public void reset() {
        this.data.clear();

    }

    public Article getItem(int position) {
        return data.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.title)
        TextView title;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(listener);

        }
    }
}
