package com.nd.screen_word_capturing;



/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Toast;
import com.baidu.ai.aip.utils.Base64Util;
import com.baidu.ai.aip.utils.FileUtil;
import com.baidu.ai.aip.utils.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * OCR 通用识别
 */
public class General {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    static String ocrHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";

    public static void test(Context context) {
        // 通用识别url

        // 本地图片路径
        String filePath = "curlocr_test.png";
        try {
            InputStream is = context.getAssets().open(filePath);
            int lenght = is.available();
            byte[]  buffer = new byte[lenght];
            is.read(buffer);


//            String sdpath = Environment.getExternalStorageDirectory()
//                    .getAbsolutePath();
//            File file1=new File("/storage/sdcard1");
//            File[] files = file1.listFiles();

//            byte[] imgData = FileUtil.readFileByBytes("/storage/sdcard0/curlocr");
//            String imgStr = Base64Util.encode(imgData);
            String imgStr = Base64Util.encode(buffer);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = "24.dc9e4fdff3f4e7e97fd09a165e845e56.2592000.1503734239.282335-9939148";
            String result = HttpUtil.post(ocrHost, accessToken, params);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test(Bitmap bitmap, View view){
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        Bitmap bm = Bitmap.createBitmap(bitmap, loc[0], loc[1],  view.getWidth(), view.getHeight());
        test(bm);
    }
    public static void test(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] buffer = baos.toByteArray();
        try {
            String imgStr = Base64Util.encode(buffer);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = "24.dc9e4fdff3f4e7e97fd09a165e845e56.2592000.1503734239.282335-9939148";
            String result = HttpUtil.post(ocrHost, accessToken, params);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void test(byte[] buffer) {
        try {
            String imgStr = Base64Util.encode(buffer);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = "24.dc9e4fdff3f4e7e97fd09a165e845e56.2592000.1503734239.282335-9939148";
            String result = HttpUtil.post(ocrHost, accessToken, params);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] bitmap2bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
