package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.dto.front.MessageDTO;
import com.yiyu.mapper.MessageMapper;
import com.yiyu.entity.Message;
import com.yiyu.service.MessageService;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.IpUtil;
import com.yiyu.utils.ResponseResult;
import com.yiyu.vo.front.MessageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * (Message)表服务实现类
 *
 * @author makejava
 * @since 2022-06-03 10:15:03
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Resource
    private HttpServletRequest request;
    @Override
    public ResponseResult<Object> saveMessage(MessageVo messageVo) {
        // 获取用户ip
        String ipAddr = IpUtil.getIpAddr(request);
        String ipSource = IpUtil.getIpSource(ipAddr);
        Message message = Message.builder()
                .nickName(messageVo.getNickname())
                .avatar(messageVo.getAvatar())
                .messageContent(messageVo.getMessageContent())
                .time(messageVo.getTime())
                .createTime(new Date())
                .ipAddress(IpUtil.getIpAddr(request))
                .ipSource(ipSource).build();
        save(message);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<List<MessageDTO>> getMessage() {
        // 查询留言列表
        List<Message> messageList = list(new LambdaQueryWrapper<Message>()
                .select(Message::getId, Message::getNickName, Message::getAvatar, Message::getMessageContent, Message::getTime));
        List<MessageDTO> messages = BeanCopyUtils.copyBeanList(messageList, MessageDTO.class);
        return ResponseResult.okResult(messages);
    }
}

