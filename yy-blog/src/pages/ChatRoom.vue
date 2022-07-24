<template>
  <div id="app">
    <div class="sidebar">
      <list></list>
    </div>
    <div class="main">
      <message></message>
      <usertext></usertext>
    </div>
  </div>
</template>

<script>

  import list from '../components/chat/list.vue'
  import message from '../components/chat/message.vue'
  import usertext from '../components/chat/usertext.vue'

  export default {
    name: 'ChatRoom',
    data () {
      return {

      }
    },
    mounted:function() {
      //初始化数据
      this.$store.dispatch('initData');
      //连接WebSocket服务
      this.$store.dispatch('connect');

    },
    created () {
      // //在页面加载时读取sessionStorage里的状态信息
      // if (sessionStorage.getItem("state") ) {
      //   this.$store.replaceState(Object.assign({}, this.$store.state,JSON.parse(sessionStorage.getItem("store"))))
      // }

      //在页面刷新时将vuex里的最新信息保存到sessionStorage里
      window.addEventListener("beforeunload",()=>{
        sessionStorage.setItem("state",JSON.stringify(this.$store.state))
      })
    },
    components:{
      list,
      message,
      usertext
    }
  }
</script>

<style lang="scss" scoped>
  #app {

    margin: 20px auto;
  /*  width: 900px;
    height: 650px;*/
    overflow: hidden;
    //border-radius: 10px;
    .sidebar, .main{
      height: 100%;
    }
    .sidebar {
      //border:1px solid red;
      float: left;
      //color: #000000;
      //background-color: #ECEAE8;
      width: 240px;
    }
    .main {
      position: relative;
      overflow: hidden;
      //background-color: #eee;
    }
  }
</style>

