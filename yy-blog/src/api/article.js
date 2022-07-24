import request from '@/utils/request'
import fa from "element-ui/src/locale/lang/fa";

// 查询文章列表
export function articleList(query,hasLogin) {
    return request({
        url: '/article/articleList',
        method: 'get',
        headers: {
          isToken: hasLogin
        },
        params: query
    })
}

//查询最热文章
export function hotArticleList(param,hasLogin) {
    return request({
        url: '/article/hotArticleList',
        headers: {
          isToken: hasLogin
        },
        method: 'get',
        params:param
    })
}

//获取文章详情
export function getArticle(articleId,hasLogin) {
    return request({
        url: '/article/' + articleId,
        headers: {
          isToken: hasLogin
        },
        method: 'get'
    })
}

export function updateViewCount(articleId,ip) {
    return request({
        url: '/article/updateViewCount/' + articleId,
        headers: {
          isToken: false
        },
        method: 'put'
    })

}
export function addArticle(data) {
  return request({
    url: '/article/addArticle' ,
    headers: {
      isToken: true
    },
    method: 'post',
    data:data
  })

}
export function getBasicLike(id) {
  return request({
    url: '/article/basicLike/'+id ,
    headers: {
      isToken: false
    },
    method: 'get',
  })

}
