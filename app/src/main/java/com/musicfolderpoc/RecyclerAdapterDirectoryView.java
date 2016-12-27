package com.musicfolderpoc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.OnClickListener;

/**
 * Created by Gursewak on 12/3/2016.
 */

public class RecyclerAdapterDirectoryView extends RecyclerView.Adapter {

    private ArrayList<EntityDirectory> directoryList;
    private Context context;
    private ContentRetriever contentRetriever;

    public RecyclerAdapterDirectoryView(Context context, ArrayList<EntityDirectory> directoryList, ContentRetriever contentRetriever) {
        this.context = context;
        this.directoryList = directoryList;
        this.contentRetriever = contentRetriever;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_directory_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            if (contentRetriever.isDirectory(directoryList.get(position).getPath())) {
                ((ViewHolder) holder).ivThumb.setImageResource(R.drawable.ic_folder);
            } else if (contentRetriever.isMusicFile(directoryList.get(position).getPath())) {
                ((ViewHolder) holder).ivThumb.setImageResource(R.drawable.ic_music_note);
            }
            if (!TextUtils.isEmpty(contentRetriever.getCurrentPath()) && contentRetriever.getCurrentPath().equalsIgnoreCase("ROOT")) {
                ((ViewHolder) holder).ivOptions.setVisibility(View.GONE);
            } else {
                ((ViewHolder) holder).ivOptions.setVisibility(View.VISIBLE);
            }
            ((ViewHolder) holder).tvName.setText(directoryList.get(position).getFileName());

            ((ViewHolder) holder).rlRoot.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contentRetriever.isDirectory(directoryList.get(position).getPath())) {
                        ((MainActivity) context).loadDirectoryPage(directoryList.get(position).getPath(), true);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return directoryList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private ImageView ivThumb;
        private ImageView ivOptions;
        private RelativeLayout rlRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            rlRoot = (RelativeLayout) itemView.findViewById(R.id.rlRoot);
            ivThumb = (ImageView) itemView.findViewById(R.id.ivThumb);
            ivOptions = (ImageView) itemView.findViewById(R.id.ivOptions);
        }
    }
}