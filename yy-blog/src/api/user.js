import request from '@/utils/request'

// 登录
export function userLogin(username,password) {
    return request({
        url: '/login',
        method: 'post',
        headers: {
            isToken: false
          },
        data: {'username':username,'password':password}
    })
}
export function loginGithub(param) {
  return request({
    url: '/login/github',
    method: 'post',
    headers: {
      isToken: false
    },
    data: {"code":param}
  })
}
export function userRegister(username,nickName,email,password) {
    return request({
        url: '/user/register',
        method: 'post',
        headers: {
            isToken :false
        },
        data: {"username":username,"nickName":nickName,"email":email,"password":password}
    })
}


export function logout() {
    return request({
        url: '/logout',
      headers: {
        isToken :true
      },
        method: 'post'
    })
}

export function getUserInfo(userId) {
    return request ({
        url: '/user/userInfo',
        method: 'get',
      headers: {
        isToken :true
      },
        params: {"userId":userId}
    })
}


export function savaUserInfo(userinfo) {
    return request({
        url: '/user/userInfo',
        method: 'put',
      headers: {
        isToken :true
      },
        data: userinfo
    })
}
export function profile(param,hasLogin) {
  return request({
    url: '/user/profile',
    headers: {
      isToken :hasLogin
    },
    method: 'get',
    params:param
  })
}
