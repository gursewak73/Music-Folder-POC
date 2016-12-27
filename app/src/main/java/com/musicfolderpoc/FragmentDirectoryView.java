package com.musicfolderpoc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Gursewak on 12/3/2016.
 */

public class FragmentDirectoryView extends Fragment {

    private Context context;
    ArrayList<EntityDirectory> dirList;
    private RecyclerView recyclerView;
    private View view;
    private TextView tvNoFiles;
    private ContentRetriever contentRetriever;
    private String fragmentName;

    public static FragmentDirectoryView newInstance(ArrayList<EntityDirectory> dirList, ContentRetriever contentRetriever) {
        FragmentDirectoryView fragmentDirectoryView = new FragmentDirectoryView();
        fragmentDirectoryView.dirList = dirList;
        fragmentDirectoryView.contentRetriever = contentRetriever;
        return fragmentDirectoryView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_directory_view, container, false);
        File file = new File(contentRetriever.getCurrentPath());
        if (file != null) {
            fragmentName = file.getName();
        } else {
            fragmentName = "Storage";
        }
        init();
        return view;
    }

    private void init() {
        initViews();
        if (dirList != null) {
            extractMusicFiles();
        }
        if (dirList != null && dirList.size() == 0) {
            tvNoFiles.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            setAdapter();
        }
    }

    private void extractMusicFiles() {
        Iterator<EntityDirectory> directoryIterator = dirList.iterator();
        while (directoryIterator.hasNext()) {
            EntityDirectory entityDirectory = directoryIterator.next();
            if (!contentRetriever.isDirectory(entityDirectory.getPath()) &&
                    !contentRetriever.isMusicFile(entityDirectory.getPath())) {
                directoryIterator.remove();
            }
        }
    }

    private void setAdapter() {
        RecyclerAdapterDirectoryView recyclerAdapterDirectoryView = new RecyclerAdapterDirectoryView(context, dirList, contentRetriever);
        recyclerView.setAdapter(recyclerAdapterDirectoryView);
    }


    private void initViews() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tvNoFiles = (TextView) view.findViewById(R.id.tvNoFiles);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public String getFragmentName() {
        return fragmentName;
    }
}