package com.musicfolderpoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.rokpetek.breadcrumbtoolbar.BreadcrumbToolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BreadcrumbToolbar.BreadcrumbToolbarListener, FragmentManager.OnBackStackChangedListener {

    private ContentRetriever contentRetriever;
    private BreadcrumbToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (BreadcrumbToolbar) findViewById(R.id.toolbar);
        toolbar.setBreadcrumbToolbarListener(this);
        toolbar.setTitle(R.string.app_name);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        contentRetriever = new ContentRetriever(this);
        loadDirectoryPage("", false);
//        getSupportLoaderManager().initLoader(URL_LOADER, null, this);
    }

    private void clearFragmentBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
    }

   /* public void updateTabs(String path) {
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
    }*/


    public void loadDirectoryPage(String path, boolean addToBackStack) {
        ArrayList<EntityDirectory> directoryArrayList;
        if (addToBackStack) {
            directoryArrayList = contentRetriever.getAllDirectories(path);
        } else {
            directoryArrayList = contentRetriever.getStorageDirectories();
        }
//        updateTabs(contentRetriever.getCurrentPath());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.replace(R.id.flContainer, FragmentDirectoryView.newInstance(directoryArrayList, contentRetriever)).commit();
    }

   /* @Override
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
//            updateTabs(result);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void onBackStackChanged() {
        if (toolbar != null) {
            // Handle breadcrumb items add/remove anywhere, as long as you track their size
            toolbar.onBreadcrumbAction(getSupportFragmentManager().getBackStackEntryCount());
        }
    }

    @Override
    public void onBreadcrumbToolbarItemPop(int stackSize) {
        // Handle breadcrumb items add/remove anywhere, as long as you track their size
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBreadcrumbToolbarEmpty() {

    }

    @Override
    public String getFragmentName() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flContainer);
        if (fragment instanceof FragmentDirectoryView) {
            return ((FragmentDirectoryView) fragment).getFragmentName();
        }
        return null;
    }
}