import request from '@/utils/request'

// 发送文章评论
export function sendComment(type, typeId, rootId, toCommentId, toCommentUserId, content) {
  return request({
    url: '/comment',
    method: 'post',
    data: {
      "typeId": typeId,
      "type": type,
      "rootId": rootId,
      "toCommentId": toCommentId,
      "toCommentUserId": toCommentUserId,
      "content": content
    }
  })
}


export function getComment(query,haslogin) {
  return request({
    url: '/comment/commentList',
    method: 'get',
    headers: {
      isToken: haslogin
    },
    params: query
  })
}


export function getNewComment(query) {
  return request({
    url: '/comment/getNewComment',
    method: 'get',
    headers: {
      isToken: false
    },
    params: {"aid":query}
  })
}
