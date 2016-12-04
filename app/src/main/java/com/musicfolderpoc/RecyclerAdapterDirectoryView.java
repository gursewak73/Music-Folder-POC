package com.musicfolderpoc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gursewak on 12/3/2016.
 */

public class RecyclerAdapterDirectoryView extends RecyclerView.Adapter {

    private ArrayList<EntityDirectory> directoryList;
    private Context context;

    public RecyclerAdapterDirectoryView(Context context, ArrayList<EntityDirectory> directoryList) {
        this.context = context;
        this.directoryList = directoryList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_directory_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).tvName.setText(directoryList.get(position).getFileName());

            ((ViewHolder) holder).tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) context).loadDirectoryPage(directoryList.get(position).getPath());
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

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }
}