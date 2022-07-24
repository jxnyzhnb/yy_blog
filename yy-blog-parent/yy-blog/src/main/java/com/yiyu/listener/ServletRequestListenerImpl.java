package com.yiyu.listener;



import com.yiyu.constants.AdminConst;
import com.yiyu.utils.IpUtil;
import com.yiyu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * request监听
 *
 * @author 11921
 */

@Component
public class ServletRequestListenerImpl implements ServletRequestListener {
    @Autowired
    private RedisCache redisCache;

    /**
     * 获得请求的ip,从而获得UV
     * @param sre
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        HttpSession session = request.getSession();
        String ip = (String) session.getAttribute("ip");
        System.out.println("session存储的ip是"+ip);
        // 判断当前ip是否访问，增加访问量
        String ipAddr = IpUtil.getIpAddr(request);
        if (!ipAddr.equals(ip)) {
            System.out.println("当前的ip是"+ipAddr);
            session.setAttribute("ip", ipAddr);
            redisCache.incrby(AdminConst.UNIQUE_VIEW_COUNT);
        }
        // 将ip存入redis，统计每日用户量
        redisCache.setCacheSet(AdminConst.IP_SET,ipAddr);
    }
    //每天凌晨两点清空ip集合
    @Scheduled(cron = " 0 2 0 * * ?")
    private void clear() {
        //清空redis中的ip
        redisCache.deleteObject(AdminConst.IP_SET);
    }


}
