import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  //修改为没有#号的路由
  mode:"history",
	scrollBehavior(to, from, savePosition) { // 在点击浏览器的“前进/后退”，或者切换导航的时候触发。
		if (savePosition) {
			return savePosition;
		} else {
            var top;
            if (window.innerWidth >= 700) {
                 top = 676
            } else {
                 top = 267
            }
			return {
				x: 0,
				y: top
			}
		}
	},
	routes: [{
			path: '/',
			component: resolve => require(['../pages/Home.vue'], resolve),
			meta: {
				auth: true
			},
			name: 'Home'
		}, //首页
		{
			path: '/Home',
			component: resolve => require(['../pages/Home.vue'], resolve),
			meta: {
				auth: true
			},
			name: 'Home'
		}, //首页
		{
			path: '/Share',
			component: resolve => require(['../pages/Share.vue'], resolve),
			meta: {
				auth: true
			},
			name: 'Share'
		}, //分类
		{
			path: '/DetailArticle',
			component: resolve => require(['../pages/DetailArticle.vue'], resolve),
			meta: {
				auth: true
			},
			name: 'DetailArticle'
		}, //分享详情
		{
			path: '/FriendsLink',
			component: resolve => require(['../pages/FriendsLink.vue'], resolve),
			meta: {
				auth: true
			},
			name: 'FriendsLink'
		}, //友链


		{
			path: '/Login',
			component: resolve => require(['../pages/Login.vue'], resolve),
			meta: {
				auth: false
			},
			name: 'Login'
		}, //注册登录
		{
			path: '/UserInfo',
			component: resolve => require(['../pages/UserInfo.vue'], resolve),
			meta: {
				auth: true
			},
			name: 'UserInfo'
		}, //用户个人中心
    {
      path: '/addArticle',
      component: resolve => require(['../pages/AddArticle'], resolve),
      meta: {
        auth: true
      },
      name: 'addArticle'
    }, //创作
    {
      path: '/messageList',
      component: resolve => require(['../pages/MessageList'], resolve),
      meta: {
        auth: true
      },
      name: 'messageList'
    }, //通知
    {
      path: '/MyArticle',
      component: resolve => require(['../pages/MyArticle'], resolve),
      meta: {
        auth: true
      },
      name: 'myArticle'
    }, //个人博客
    {
      path: '/HomePage',
      component: resolve => require(['../pages/MyArticle'], resolve),
      meta: {
        auth: true
      },
      name: 'myArticle'
    }, //其他的个人主页
    {
      path: '/test',
      component: resolve => require(['../components/test'], resolve),
      meta: {
        auth: true
      },
      name: 'test'
      //测试新功能
    },
    {
      path: '/chat',
      component: resolve => require(['../pages/ChatRoom'], resolve),
      meta: {
        auth: true
      },
      name: 'ChatRoom'
    },//私信聊天
    {
      path: '/Messboard',
      component: resolve => require(['../pages/MessBoard'], resolve),
      meta: {
        auth: false
      },
      name: 'MessBoard'
    },//私信聊天
    {
      path: '/Discuss',
      component: resolve => require(['../pages/DiscussList'], resolve),
      meta: {
        auth: false
      },
      name: 'Discuss'
    },//私信聊天
    {
      path: '/DiscussDetail',
      component: resolve => require(['../pages/DiscussDetail'], resolve),
      meta: {
        auth: true
      },
      name: 'DiscussDetail'
    },
   /* {
      path: '*',
      component: resolve => require(['../pages/NotFound'], resolve),
      meta: {
        auth: true
      },
      name: 'NotFound'
    },//私信聊天*/
  ]
})
