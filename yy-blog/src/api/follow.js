import request from '@/utils/request'


export function getFollow(query) {
  return request({
    url: '/follow/getFollow/'+query,
    method: 'get',
    headers: {
      isToken: true
    }
  })
}
export function setFollow(toUserId,typeId,type) {
  return request({
    url: '/follow',
    method: 'post',
    headers: {
      isToken: true
    },
    data:{"toUserId":toUserId,"typeId":typeId,"type":type}
  })
}
export function  unFollow(toUserId,articleId) {
  return request({
    url: '/follow/unFollow',
    method: 'post',
    headers: {
      isToken: true
    },
    data:{"toUserId":toUserId,"articleId":articleId}
  })
}
