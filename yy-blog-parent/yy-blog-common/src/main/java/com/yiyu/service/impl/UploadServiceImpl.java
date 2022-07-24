package com.yiyu.service.impl;

import com.google.gson.Gson;
import com.yiyu.constants.OtherConst;
import com.yiyu.exception.SystemException;
import com.yiyu.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.yiyu.utils.HttpCodeEnum;
import com.yiyu.utils.PathUtils;
import com.yiyu.utils.ResponseResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author zh
 * @ClassName : zh.nb.service.impl.UploadServiceImpl
 * @Description :
 * Created by user on 2022-03-02 20:36:48
 * Copyright  2020 user. All rights reserved.
 */
@ConfigurationProperties(prefix = "oss")
@Service
@Data
public class UploadServiceImpl implements UploadService {
    private String accessKey;
    private String secretKey;
    private String bucket;
    @Override
    public ResponseResult<Object> uploadImg(MultipartFile img) {
        //判断文件格式
        //获取文件原始名
        String originalFilename = img.getOriginalFilename();
        assert originalFilename != null;
        if (!originalFilename.endsWith(".png")&&!originalFilename.endsWith(".jpg")){
            throw new SystemException(HttpCodeEnum.WRONG_FILE_FORMAT);
        }
        //判断是否成功上传至oss,返回访问图片的url
        String url = uploadOss(img);
        return ResponseResult.okResult(url);
    }
    private String uploadOss(MultipartFile imgFile){
        //构造一个带指定 Region 对象的配置类,对象存储服务在哪个地区,autoRegion
        // 是自动根据accessKey和secretKey进行识别
        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        String filePath = PathUtils.generateFilePath(Objects.requireNonNull(imgFile.getOriginalFilename()));
        InputStream fileInputStream = null;
        try {
            fileInputStream = imgFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(fileInputStream, filePath, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            return OtherConst.QINIU_URL_PREIX +filePath;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            //文件上传失败
            throw new SystemException(HttpCodeEnum.FILE_UPLOAD_FILE) ;
        }
    }

}
