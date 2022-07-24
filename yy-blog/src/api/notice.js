import request from "../utils/request";

export function getIsRead() {
  return request({
    url: '/notice/getIsRead',
    headers: {
      isToken: true
    },
    method: 'get'
  })
}
export function getNoticeList(params) {
  return request({
    url: '/notice/getNewNotice',
    headers: {
      isToken: true
    },
    method: 'get',
    params:params
  })

}
export function getUnread() {
  return request({
    url: '/notice/getUnread',
    headers: {
      isToken: true
    },
    method: 'get',
  })

}
export function setRead(param) {
  return request({
    url: '/notice/setRead/'+param,
    headers: {
      isToken: true
    },
    method: 'put',
  })
}
export function setLetterRead(param) {
  return request({
    url: '/notice/setLetterRead/'+param,
    headers: {
      isToken: true
    },
    method: 'put',
  })
}
export function deleteNotice(param) {
  return request({
    url: '/notice/delete/'+param,
    headers: {
      isToken: true
    },
    method: 'delete',
  })
}


export function getMsg(param) {
  return request({
    url: '/notice/getLetterList',
    headers: {
      isToken: true
    },
    method: 'get',
    params: {'toId':param}
  })
}
export function getUserList() {
  return request({
    url: '/notice/getUserList',
    headers: {
      isToken: true
    },
    method: 'get',
  })
}

