import request from '@/utils/request'

// 查询所有审核通过的友链列表
export function getAllLink(query) {
    return request({
        url: '/link',
        method: 'get',
        headers: {
          isToken: false
        },
        params: query
    })
}
export function addLink(query) {
  return request({
    url: '/link',
    method: 'post',
    headers: {
      isToken: false
    },
    data: query
  })
}

