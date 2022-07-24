package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.LinkConst;
import com.yiyu.entity.Link;
import com.yiyu.mapper.LinkMapper;
import com.yiyu.service.LinkService;
import com.yiyu.utils.HttpCodeEnum;
import com.yiyu.vo.front.LinkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.ResponseResult;
import com.yiyu.dto.front.LinkDTO;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-02-14 16:00:38
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Autowired
    private LinkMapper linkMapper;
    @Override
    public ResponseResult<List<LinkDTO>> getAllLink() {
        //审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, LinkConst.LINK_STATUS_NORMAL);
        List<Link> linkList = list(queryWrapper);
        //转换为Vo
        List<LinkDTO> links = BeanCopyUtils.copyBeanList(linkList, LinkDTO.class);
        return ResponseResult.okResult(links);
    }

    @Override
    public ResponseResult<Object> addLink(LinkVo linkVo) {
        if (lambdaQuery().eq(Link::getAddress,linkVo.getAddress()).count()>0){
            return ResponseResult.errorResult(HttpCodeEnum.LINK_EXIST);
        }
        Link link = BeanCopyUtils.copyBean(linkVo, Link.class);
        save(link);
        return ResponseResult.okResult();
    }
}

