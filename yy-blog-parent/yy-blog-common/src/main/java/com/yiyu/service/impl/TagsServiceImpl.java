package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.ArticleConst;
import com.yiyu.dto.back.BackTagsDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.entity.Tags;
import com.yiyu.entity.User;
import com.yiyu.mapper.TagsMapper;
import com.yiyu.service.TagsService;
import com.yiyu.service.UserService;
import com.yiyu.utils.HttpCodeEnum;
import com.yiyu.vo.back.BackConditionVo;
import com.yiyu.vo.back.BackTagsVo;
import com.yiyu.vo.back.IdsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.ResponseResult;
import com.yiyu.dto.front.TagsDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Tags)表服务实现类
 *
 * @author makejava
 * @since 2022-05-03 20:13:53
 */
@Service("tagsService")
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Autowired
    private UserService userService;
    @Override
    public ResponseResult<List<TagsDTO>> getTagsList() {
        List<Tags> tags = list();
        List<TagsDTO> tagss = BeanCopyUtils.copyBeanList(tags, TagsDTO.class);
        return ResponseResult.okResult(tagss);
    }

    @Override
    public ResponseResult<PageDTO<BackTagsDTO>> tagsList(BackConditionVo backConditionVo) {
        Page<Tags> page=new Page<>(backConditionVo.getPageNum(),backConditionVo.getPageSize());
        LambdaQueryWrapper<Tags> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(backConditionVo.getQ())){
            queryWrapper.like(Tags::getTagName,backConditionVo.getQ());
        }
        page(page,queryWrapper);
        List<Tags> tagsList = page.getRecords();
        List<BackTagsDTO> tagsLists=tagsList.stream()
                .map(tags -> BeanCopyUtils.copyBean(tags,BackTagsDTO.class).setNickName(userService.lambdaQuery().eq(User::getId,tags.getCreateBy()).select(User::getNickName).one().getNickName()))
                .collect(Collectors.toList());
        PageDTO<BackTagsDTO> pageDTO = new PageDTO<>(tagsLists,page.getTotal());
        return ResponseResult.okResult(pageDTO);
    }

    @Override
    public ResponseResult<Object> updateOrAddTags(BackTagsVo backTagsVo) {
        if (lambdaQuery().eq(Tags::getTagName,backTagsVo.getTagName()).count()>0){
            return ResponseResult.errorResult(HttpCodeEnum.TAGS_EXIST);
        }
        Tags tags = BeanCopyUtils.copyBean(backTagsVo, Tags.class);
        saveOrUpdate(tags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<Object> deleteTags(IdsVo idsVo) {
        removeByIds(idsVo.getIds());
        return ResponseResult.okResult();
    }

}

