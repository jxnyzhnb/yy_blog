import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.yiyu.YyBlogApplication;
import com.yiyu.service.NoticeService;
import com.yiyu.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zh
 * @ClassName : PACKAGE_NAME.test
 * @Description :
 * Created by user on 2022-03-03 08:52:35
 * Copyright  2020 user. All rights reserved.
 */
@SpringBootTest(classes = YyBlogApplication.class)
public class test {
    @Autowired
    private NoticeService noticeService;
    @Test
    public void test(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
        String format = simpleDateFormat.format(date);
        System.out.println(format);
    }
    @Test
    public void te(){
        System.out.println(DateUtils.getNowToNextDaySeconds());
    }

}
