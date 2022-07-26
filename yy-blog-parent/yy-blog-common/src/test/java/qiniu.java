import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.yiyu.YyBlogCommonApplication;
import com.yiyu.utils.RedisCache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * @author zh
 * @ClassName : PACKAGE_NAME.qiniu
 * @Description :
 * Created by user on 2022-03-02 20:12:23
 * Copyright  2020 user. All rights reserved.
 */
@SpringBootTest(classes = YyBlogCommonApplication.class)
//@ConfigurationProperties(prefix = "oss")
public class qiniu {
    @Autowired
    private RedisCache redisCache;
    private String accessKey;
    private String secretKey;
    private String bucket;

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Test
    public void upload() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = new Date() + "/yiyu.png";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("C:\\DeskTop\\image\\background\\1.jpg");
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
    }

}
