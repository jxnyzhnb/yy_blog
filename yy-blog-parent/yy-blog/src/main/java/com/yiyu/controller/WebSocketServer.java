package com.yiyu.controller;

import com.alibaba.fastjson.JSON;
import com.yiyu.constants.NoticeConst;
import com.yiyu.vo.front.MsgVo;
import com.yiyu.service.WebSocketService;
import com.yiyu.utils.RedisCache;
import com.yiyu.dto.front.LetterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint(value = "/webSocket/{sid}")
public class WebSocketServer {

    private static WebSocketService webSocketService;
    private static RedisCache redisCache;

    @Autowired
    public void webSocketServer(WebSocketService webSocketService, RedisCache redisCache) {
        WebSocketServer.webSocketService = webSocketService;
        WebSocketServer.redisCache = redisCache;
    }


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketMap.put(sid, this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
        this.sid = sid;
        try {
            //连接成功消息
            sendMessage("ok", this.session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        if (!"undefined".equals(sid)) {
            webSocketMap.remove(sid);  //从set中删除
            subOnlineCount();           //在线数减1
            System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        if (!"undefined".equals(sid)) {
            System.out.println("收到来自窗口" + sid + "的信息:" + message);
            try {
                MsgVo msg = JSON.parseObject(message, MsgVo.class);
                if (msg.getType().equals(NoticeConst.T_TYPE_LETTER)) {
                    LetterDTO letters = webSocketService.replyLetter(msg, Long.valueOf(sid));
                    System.out.println(JSON.toJSONString(letters));
                    //给该用户回消息
                    sendMessage(JSON.toJSONString(letters), this.session);
                    //给接受消息的用户发送消息
                    //判断接受消息的用户是否登录
                    WebSocketServer socketServer = webSocketMap.get(msg.getToId().toString());
                    if (Objects.nonNull(socketServer)) {
                        //给当前用户回复的用户发送消息
                        letters.setSelf(false);
                        sendMessage(JSON.toJSONString(letters), socketServer.session);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("error");
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message, Session toSession) throws IOException {
        toSession.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        //System.out.println("推送消息到窗口"+sid+"，推送内容:"+message);
       /* for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    public static void sendAllMessage(String message) {
      /*  try {
            for (WebSocketServer item : webSocketSet) {
                item.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSocketServer that = (WebSocketServer) o;
        return Objects.equals(session, that.session) && Objects.equals(sid, that.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, sid);
    }
}
