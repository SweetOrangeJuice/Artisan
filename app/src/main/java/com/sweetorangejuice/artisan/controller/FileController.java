package com.sweetorangejuice.artisan.controller;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Created by fortuneliu on 2017/5/10.
 */

public class FileController {
    public static byte[] getThumbnailbyObjectId(String objectId,int height,int width){
        try {
            AVFile file = AVFile.withObjectId(objectId);
            AVFile thumbnail = new AVFile(file.getName(),file.getThumbnailUrl(true, height, width),new HashMap<String, Object>());
            return thumbnail.getData();
        }catch(FileNotFoundException e){

        }catch(AVException e){

        }
        return null;
    }
    public static byte[] getPicturebyObjectId(String objectId){
        try {
            AVFile file = AVFile.withObjectId(objectId);
            return file.getData();
        }catch(FileNotFoundException e){

        }catch(AVException e){

        }
        return null;
    }
}
