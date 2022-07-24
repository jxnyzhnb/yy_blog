import request from '@/utils/request'

// 查询所有审核通过的友链列表
export function addMess(query,hasLogin) {
  return request({
    url: '/messages',
    method: 'post',
    headers: {
      isToken: hasLogin
    },
    data: query
  })
}
export function getMess() {
  return request({
    url: '/messages',
    method: 'get',
    headers: {
      isToken: false
    }
  })
}
