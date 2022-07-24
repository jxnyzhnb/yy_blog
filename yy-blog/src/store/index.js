import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";
// import * as getters from './getters.js'

Vue.use(Vuex)

/** 状态定义 */
export default new Vuex.Store({
  state:{
    loading: false,
    themeObj: 0,//主题
    keywords: '',//关键词
    errorImg: 'this.onerror=null;this.src="' + require('../../static/img/tou.jpg') + '"',
    baseURL: 'http://localhost:7777/',
    GITHUB_CLIENT_ID: '95c777ecb7de85346c49',
    GITHUB_REDIRECT_URL: 'http://localhost:8081/Login?login=1',
    STATE: "yiyu",
    id: null,
    avatar: null,
    nickName: null,
    signature: null,
  },
  mutations: {
    login(state, user) {
      state.id = user.id;
      state.avatar = user.avatar;
      state.nickName = user.nickName;
      state.signature = user.signature;
    },
    logout(state) {
      state.id = null;
      state.avatar = null;
      state.nickName = null;
      state.signature = null;
    },
    updateAvatar(state, avatar) {
      state.avatar = avatar;
    },
    updateUserInfo(state, user) {
      state.nickName = user.nickName;
      state.signature = user.signature;
    }
  },
  actions: {},
  modules: {},
  plugins: [createPersistedState({
    storage: window.localStorage
  })]
})
