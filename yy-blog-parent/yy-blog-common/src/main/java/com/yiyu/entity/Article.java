package com.yiyu.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2022-02-14 15:39:55
 */
@Document(indexName = "article")
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("yy_article")
@Accessors(chain = true)
public class Article {
    @TableId
    //让es知道这是id
    @Id
    private Long id;
    //标题
    //analyzer是存储时的分词器=ik_max_word意为会把一句话拆分分成最多的词条，
    // searchAnalyzer是在搜索时的分词器，ik_smart意为将词条拆分的更加聪明(就是按需要来拆分，拆分的不是那么细)
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;
    //文章内容
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;
    //文章摘要
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String summary;
    //所属分类id
    @Field(type = FieldType.Long)
    private Long categoryId;
    //分类名
    @TableField(exist = false)
    private String categoryName;
    //作者名
//    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @TableField(exist = false)
    private String nickName;
    //缩略图
    @Field(type = FieldType.Keyword,index = false)
    private String thumbnail;
    //是否置顶（0否，1是）
    @Field(type = FieldType.Integer)
    private Integer isTop;
    //状态（0已发布，1草稿）
    @Field(type = FieldType.Keyword,index = false)
    private Integer status;
    //访问量
    @Field(type = FieldType.Long)
    private Long viewCount;
    //点赞人数
    private Integer likeCount;
    //是否允许评论 1是，0否
    @Field(type = FieldType.Keyword,index = false)
    private Integer isComment;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time,pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
//    @Field(type = FieldType.Date, format= DateFormat.date_optional_time)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Field(type = FieldType.Date)
    private Date updateTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}

