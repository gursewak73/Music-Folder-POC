package com.musicfolderpoc;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Gursewak on 11/20/2016.
 */

public class ContentRetriever {

    private Context context;
    private String currentPath;
    private String rootPath;

    public ContentRetriever(Context context) {
        this.context = context;
    }

    public CursorLoader getData() {
        Uri uri = MediaStore.Files.getContentUri("external");
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        };
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
        CursorLoader cursorLoader = new CursorLoader(context, uri, projection, selection, null, null);
        return cursorLoader;

    }

    public String getCurrentPath() {
        return currentPath;
    }


    public ArrayList<EntityDirectory> getAllDirectories(String currentPath) {
        ArrayList<EntityDirectory> directoryArrayList = new ArrayList<>();
        if (currentPath.isEmpty()) {
            currentPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            this.currentPath = currentPath;
        }
        File file = new File(currentPath);
        File[] files = file.listFiles();
        if (files != null) {
            for (File fileData : files) {
                EntityDirectory entityDirectory = new EntityDirectory();
                entityDirectory.setFileName(fileData.getName());
                entityDirectory.setFolderName(file.getParent());
                entityDirectory.setPath(fileData.getAbsolutePath());
                directoryArrayList.add(entityDirectory);
            }
        }
        return directoryArrayList;
    }
}