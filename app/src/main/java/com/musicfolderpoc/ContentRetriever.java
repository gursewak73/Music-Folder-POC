package com.musicfolderpoc;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gursewak on 11/20/2016.
 */

public class ContentRetriever {

    private Context context;
    private String currentPath;
    private String[] cardsPath;

    public ContentRetriever(Context context) {
        this.context = context;
    }

    /**
     * Returns all available SD-Cards in the system (include emulated)
     * <p>
     * Warning: Hack! Based on Android source code of version 4.3 (API 18)
     * Because there is no standard way to get it.
     *
     * @return paths to all available SD-Cards in the system (include emulated)
     */
    public static String[] getStorageDirectories(Context pContext) {
        // Final set of paths
        final Set<String> rv = new HashSet<>();

        //Get primary & secondary external device storage (internal storage & micro SDCARD slot...)
        File[] listExternalDirs = ContextCompat.getExternalFilesDirs(pContext, null);
        for (int i = 0; i < listExternalDirs.length; i++) {
            if (listExternalDirs[i] != null) {
                String path = listExternalDirs[i].getAbsolutePath();
                int indexMountRoot = path.indexOf("/Android/data/");
                if (indexMountRoot >= 0 && indexMountRoot <= path.length()) {
                    //Get the root path for the external directory
                    rv.add(path.substring(0, indexMountRoot));
                }
            }
        }
        return rv.toArray(new String[rv.size()]);
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

    public ArrayList<EntityDirectory> getStorageDirectories() {
        ArrayList<EntityDirectory> directoryArrayList = new ArrayList<>();
        cardsPath = getStorageDirectories(context);
        currentPath = "Root";
        for (String path : cardsPath) {
            File fileData = new File(path);
            EntityDirectory entityDirectory = new EntityDirectory();
            entityDirectory.setFileName(fileData.getName());
            entityDirectory.setFolderName(fileData.getParent());
            entityDirectory.setPath(fileData.getAbsolutePath());
            directoryArrayList.add(entityDirectory);
        }
        return directoryArrayList;
    }


    public ArrayList<EntityDirectory> getAllDirectories(String currentPath) {
        ArrayList<EntityDirectory> directoryArrayList = new ArrayList<>();
        if (currentPath.isEmpty()) {
            currentPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            this.currentPath = currentPath;
        } else {
            this.currentPath = currentPath;
        }
//        for (String path : cardsPath) {
//            if (currentPath.equalsIgnoreCase(path)) {
//                currentPath = "ROOT";
//            }
//        }
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

    public boolean isDirectory(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            return true;
        }
        return false;
    }

    /*
    * Get the extension of a file.
    */
    private static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public boolean isMusicFile(String path) {
        File file = new File(path);
        String extension = getExtension(file);
        if (!TextUtils.isEmpty(extension) && extension.equalsIgnoreCase("mp3")) {
            return true;
        }
        return false;
    }
}