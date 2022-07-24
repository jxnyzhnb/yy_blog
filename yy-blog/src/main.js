// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/style.less'
import store from './store'
import MavonEditor from 'mavon-editor'
import scroll from 'vue-scroll'
//弹幕
import { vueBaberrage } from 'vue-baberrage';

// 注册mavon-editor组件
import 'mavon-editor/dist/css/index.css'
import "./utils/websocket";
import global from './utils/websocket'
import 'font-awesome/css/font-awesome.min.css'
Vue.prototype.global = global
Vue.use(scroll)
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(MavonEditor)
Vue.prototype.bus = new Vue()
Vue.use(vueBaberrage)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store
})
