import request from '@/utils/request'

export function addDiscuss(query) {
    return request({
        url: '/discuss',
        method: 'post',
        headers: {
          isToken: true
        },
        data: query
    })
}

export function getDiscuss(query) {
  return request({
    url: '/discuss',
    method: 'get',
    headers: {
      isToken: false
    },
    params: query
  })
}
export function getDisussDetail(query) {
  return request({
    url: '/discuss/'+query,
    method: 'get',
    headers: {
      isToken: false
    }
  })
}
export function addViewCount(query) {
  return request({
    url: '/discuss/view/'+query,
    method: 'put',
    headers: {
      isToken: false
    }
  })
}
