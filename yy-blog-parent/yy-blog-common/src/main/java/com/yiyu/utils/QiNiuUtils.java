package com.yiyu.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author zh
 * @ClassName : zh.nb.utils.QiNiuUtils
 * @Description :
 * Created by user on 2022-03-02 19:28:19
 * Copyright  2020 user. All rights reserved.
 */
//@ConfigurationProperties(prefix = "oss")
public class QiNiuUtils {
    private String accessKey;
    private String secretKey;
    private String bucket;
    public static boolean upload(File file){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "iwNLY7vcLTXfI-BL95NCZT51tUbZpP5yDU8dP5i1";
        String secretKey = "bbi9QeHy4jsm4FhFAMmClYzSRGGydjIwHLR_1w45";
        String bucket = "yzyzr";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        FileInputStream fileInputStream=null;
        try {
             fileInputStream= new FileInputStream("C:\\DeskTop\\image\\background\\1.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(fileInputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return true;
    }


}
