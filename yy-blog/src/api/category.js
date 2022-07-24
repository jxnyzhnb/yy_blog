import request from '@/utils/request'

// 查询分类列表
export function getCategoryList() {
    return request({
        url: '/category/getCategoryList',
        headers: {
          isToken: false
        },
        method: 'get'
    })
}
export function getCategories() {
  return request({
    url: '/category/categories',
    headers: {
      isToken: true
    },
    method: 'get'
  })
}
export function getCategory(query, hasLogin) {
  return request({
    url: '/category/categoryDetail',
    headers: {
      isToken: hasLogin
    },
    method: 'get',
    params:query
  })
}
