package com.yiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiyu.constants.ArticleConst;
import com.yiyu.dto.back.BackCategoryDTO;
import com.yiyu.dto.front.CategoryDTO;
import com.yiyu.dto.front.PageDTO;
import com.yiyu.dto.front.UserCategoryDTO;
import com.yiyu.entity.Article;
import com.yiyu.entity.Category;
import com.yiyu.entity.User;
import com.yiyu.exception.SystemException;
import com.yiyu.mapper.CategoryMapper;
import com.yiyu.service.ArticleService;
import com.yiyu.service.CategoryService;
import com.yiyu.service.UserService;
import com.yiyu.utils.BeanCopyUtils;
import com.yiyu.utils.HttpCodeEnum;
import com.yiyu.utils.ResponseResult;
import com.yiyu.utils.SecurityUtils;
import com.yiyu.vo.back.BackConditionVo;
import com.yiyu.vo.back.IdsVo;
import com.yiyu.vo.front.HotArtCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-02-14 16:00:38
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult<List<CategoryDTO>> getCategoryList() {
        //查询发表的文章(status为0)
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, ArticleConst.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //运用stream流得到分类id的set集合(),也可以用distinct()方法进行去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //通过id得到所有分类集合
        List<Category> categories = listByIds(categoryIds);
        //运用stream进行过滤得到正常的分类(status为0)
        categories = categories.stream()
                .filter(o -> ArticleConst.CATEGORY_STATUS_NORMAL.equals(o.getStatus()))
                .collect(Collectors.toList());
        //转换成categoryVo
        List<CategoryDTO> categoryList = BeanCopyUtils.copyBeanList(categories, CategoryDTO.class);

        return ResponseResult.okResult(categoryList);
    }

    @Override
    public ResponseResult<List<UserCategoryDTO>> getCategoryDetail(HotArtCategoryVo hotArtCategoryVo) {
        Integer type = hotArtCategoryVo.getType();
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //查看登录用户分类专栏
        if (type.equals(ArticleConst.HOT_ARTICLE_TYPE_SELF)) {
            Long userId = SecurityUtils.getUserId();
            queryWrapper.eq(Category::getCreateBy, userId);
        } else if (type.equals(ArticleConst.HOT_ARTICLE_TYPE_OTHER)) {
            //查看别人主页分类专栏
            queryWrapper.eq(Category::getCreateBy, hotArtCategoryVo.getWid());
        } else if (type.equals(ArticleConst.HOT_ARTICLE_TYPE_DETAIL)) {
            //查看文章详情分类专栏
            Long articleId = hotArtCategoryVo.getAid();
            //获得作者id
            Long createBy = articleService.lambdaQuery().eq(Article::getId, articleId).select(Article::getCreateBy).one().getCreateBy();
            if (Objects.nonNull(createBy)) {
                queryWrapper.eq(Category::getCreateBy, createBy);
            } else {
                throw new SystemException(HttpCodeEnum.PARAMETER_ERROR);
            }
        }
        return categoryDetail(queryWrapper);

    }

    @Override
    public ResponseResult<List<UserCategoryDTO>> categories() {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getCreateBy, userId)
                .select(Category::getName, Category::getId);
        List<Category> categories = list(queryWrapper);
        List<UserCategoryDTO> userCategorys = BeanCopyUtils.copyBeanList(categories, UserCategoryDTO.class);
        return ResponseResult.okResult(userCategorys);
    }

    @Override
    public List<UserCategoryDTO> getCategoryByName() {
        //查询名字相同的分类的数量
        //获得去重后的分类名字
        List<Object> categoryNames = listObjs(new LambdaQueryWrapper<Category>().eq(Category::getStatus, ArticleConst.CATEGORY_STATUS_NORMAL).select(Category::getName).groupBy(Category::getName));
        //stream流处理数据
        //通过分类名获得相同分类名的分类对象集合
        //通过分类对象集合再次转换成stream流将分类集合中的id查询出对应分类的文章数量并对相同分类名的数量进行相加
        //转换成dto
        return categoryNames.stream()
                .map(categoryName -> lambdaQuery().eq(Category::getName, categoryName).select(Category::getId, Category::getName).list())
                .map(categorys -> new UserCategoryDTO().setCount(categorys.stream().mapToInt(category -> articleService.lambdaQuery().eq(Article::getCategoryId, category.getId()).eq(Article::getStatus, ArticleConst.ARTICLE_STATUS_NORMAL).count()).sum()).setName(categorys.get(0).getName()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseResult<PageDTO<BackCategoryDTO>> categoryList(BackConditionVo backCategoryVo) {
        Page<Category> page=new Page<>(backCategoryVo.getPageNum(),backCategoryVo.getPageSize());
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(backCategoryVo.getQ())){
            queryWrapper.like(Category::getName,backCategoryVo.getQ());
        }
        page(page,queryWrapper);
        List<Category> categories =page.getRecords();
        List<BackCategoryDTO> backCategorys=categories.stream()
                .map(category -> BeanCopyUtils.copyBean(category,BackCategoryDTO.class).setNickName(userService.lambdaQuery().eq(User::getId,category.getCreateBy()).select(User::getNickName).one().getNickName()))
                .collect(Collectors.toList());
        PageDTO<BackCategoryDTO> pageDTO=new PageDTO<>(backCategorys,page.getTotal());
        return ResponseResult.okResult(pageDTO);
    }

    @Override
    public ResponseResult<Object> updateStatus(Long id, String status) {
        LambdaUpdateWrapper<Category> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(Category::getStatus,status)
                .eq(Category::getId,id);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<Object> deleteCategory(IdsVo idsVo) {
        removeByIds(idsVo.getIds());
        return ResponseResult.okResult();
    }

    private ResponseResult<List<UserCategoryDTO>> categoryDetail(LambdaQueryWrapper<Category> queryWrapper) {
        //只查询id和名称
        queryWrapper.select(Category::getId, Category::getName);
        List<Category> categoryList = list(queryWrapper);
        //封装dto
        List<UserCategoryDTO> userCategorys = BeanCopyUtils.copyBeanList(categoryList, UserCategoryDTO.class);
        //封装数据
        userCategorys = userCategorys.stream()
                .map(category -> category.setCount(articleService.lambdaQuery().eq(Article::getCategoryId, category.getId()).count()))
                .collect(Collectors.toList());
        return ResponseResult.okResult(userCategorys);
    }
}

