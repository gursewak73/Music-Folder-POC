package com.musicfolderpoc;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity /*implements LoaderManager.LoaderCallbacks*/ {

    private static final int URL_LOADER = 0;
    private ContentRetriever contentRetriever;
    private ArrayList<String> list = new ArrayList<>();
    private HashMap<String, String> dirList = new HashMap<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    final int WHAT = 1;

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == WHAT) loadDirectoryPage();
//        }
//    };

    public void loadDirectoryPage(String path) {
        ArrayList<EntityDirectory> directoryArrayList = contentRetriever.getAllDirectories(path);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flContainer, FragmentDirectoryView.newInstance(directoryArrayList)).addToBackStack("").commit();
    }

    @Override
    public void onBackPressed() {
        int fragmentCount = getFragmentManager().getBackStackEntryCount();
        if (fragmentCount > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}