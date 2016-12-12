package com.musicfolderpoc;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity /*implements LoaderManager.LoaderCallbacks*/ {

    private static final int URL_LOADER = 0;
    private ContentRetriever contentRetriever;
    private ArrayList<String> list = new ArrayList<>();
    private HashMap<String, String> dirList = new HashMap<>();
    private Toolbar toolbar;
    private String tabTitles[] = new String[]{"Tab1", "Tab2", "Tab3"};
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        /*tabLayout.addTab(tabLayout.newTab().setText("test1"));
        tabLayout.addTab(tabLayout.newTab().setText("test2"));
        tabLayout.addTab(tabLayout.newTab().setText("test3"));
        tabLayout.addTab(tabLayout.newTab().setText("test4"));
        tabLayout.addTab(tabLayout.newTab().setText("test1"));
        tabLayout.addTab(tabLayout.newTab().setText("test2"));
        tabLayout.addTab(tabLayout.newTab().setText("test3"));
        tabLayout.addTab(tabLayout.newTab().setText("test4"));
        tabLayout.addTab(tabLayout.newTab().setText("test1"));


        //Set Custom tab Background
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            LinearLayout linearLayout = (LinearLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_layout, tabLayout, false);
            TextView tvTabText = (TextView) linearLayout.findViewById(R.id.tab_title);
            tvTabText.setText(tab.getText());
            tab.setCustomView(linearLayout);
        }*/
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });

        contentRetriever = new ContentRetriever(this);
        loadDirectoryPage("");
//        getSupportLoaderManager().initLoader(URL_LOADER, null, this);
    }

    public void updateTabs(String path) {
        String[] locationList = path.split("/");
        tabLayout.removeAllTabs();
        for (String location : locationList) {
            tabLayout.addTab(tabLayout.newTab().setText(location));
        }
        //Set Custom tab Background
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            LinearLayout linearLayout = (LinearLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_layout, tabLayout, false);
            TextView tvTabText = (TextView) linearLayout.findViewById(R.id.tab_title);
            tvTabText.setText(tab.getText());
            tab.setCustomView(linearLayout);
        }
        tabLayout.getTabAt(locationList.length - 1).select();
    }

 /*   @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return contentRetriever.getData();
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Cursor cursor = (Cursor) data;
        if (cursor != null) {
            int indexData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int indexDisplayName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
//                Log.wtf("path", cursor.getString(columnIndex));
                dirList.put(cursor.getString(indexDisplayName),cursor.getString(indexData));
//                list.add(cursor.getString(indexDisplayName));
            }
            handler.sendEmptyMessage(WHAT);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }*/

    public void loadDirectoryPage(String path) {
        ArrayList<EntityDirectory> directoryArrayList = contentRetriever.getAllDirectories(path);
        updateTabs(contentRetriever.getCurrentPath());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flContainer, FragmentDirectoryView.newInstance(directoryArrayList, contentRetriever)).addToBackStack("").commit();
    }

    @Override
    public void onBackPressed() {
        int fragmentCount = getFragmentManager().getBackStackEntryCount();
        if (fragmentCount > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            String path = contentRetriever.getCurrentPath();
            String[] arr = path.split("/");
            String result = "";
            if (arr.length > 0) {
                result = path.substring(0, path.lastIndexOf("/" + arr[arr.length - 1]));
            }
            updateTabs(result);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
}