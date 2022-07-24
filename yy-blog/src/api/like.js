import request from "../utils/request";
export function setLike(id,type) {
  return request({
    url: '/like/setLike',
    headers: {
      isToken: true
    },
    method: 'post',
    data: {"typeId":id,"type":type}
  })
}
export function unLike(id,type) {
  return request({
    url: '/like/unLike',
    headers: {
      isToken: true
    },
    method: 'post',
    data: {"typeId":id,"type":type}
  })
}
export function getIsLike(id,type) {
  return request({
    url: '/like/isLike',
    headers: {
      isToken: true
    },
    method: 'get',
    params:{"typeId":id,"type":type}

  })
}
