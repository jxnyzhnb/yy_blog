<template>
  <div  id="list" style="width: 245px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);height: 605px">
    <!--用户列表-->
    <el-scrollbar wrap-class="userList" wrap-style="height:600px;"
                  view-style="height:100%;" :native="false">
      <ul>
        <li v-for="(item,index) in users" :key="item.userId" style="border-bottom:1px solid gainsboro "
            :class="{'active':index===activeIndex}" v-on:click="changeChatUser(index,item)">
          <!--   :class="[item.id === currentSession ? 'active':'']" -->
          <div style="display: flex;justify-content: space-between">
            <div>
              <el-badge :is-dot="item.isRead" class="img_badge" style="float:left;">
                <img class="avatar"
                     :src="item.avatar" alt="">
              </el-badge>
              <div style="float:left;margin-left: 5px;width: 160px;height: 100%">
                <div>
                  <span class="name">{{ item.nickName }}</span>
                  <span style="display: inline-block;float:right;font-size: 12px" v-html="showInitDate(item.createTime,'newDate')"></span>
                </div>
                <div style="margin-top: 10px;margin-left: 10px;">{{item.content}}</div>
              </div>
            </div>
            <div>
              <!--					<el-badge :value="1==1?'在线':'离线'" :type=1==1?'danger':'info'></el-badge>-->
            </div>
          </div>
        </li>
      </ul>
    </el-scrollbar>
  </div>
</template>

<script>

import {getUserList, setLetterRead} from "../../api/notice";
import {initDate} from "../../utils/server";
import webSocket from "../../utils/websocket";

export default {
  name: 'list',
  data() {
    return {
      users: [],
      activeIndex: 0,
      isDot:false,
    }
  },
  created() {
    this.getUserList()
  },
  mounted() {
    //获取当前用户发送给私信用户的信息
    this.bus.$on('currentMsg', res => {
      console.log(res)
      // 这里有一个错误，还未解决=====================================================
      this.users[this.activeIndex].content=res.content
      this.users[this.activeIndex].isRead=true
      this.setLetterRead()
    })
  },
  methods: {
    changeChatUser: function (index, item) {
       console.log(item)
      this.users[this.activeIndex].isRead=false
      this.setLetterRead()
      this.activeIndex = index
      this.bus.$emit('currentUser', this.users[index])
      this.bus.$emit('unReadCount', 1)
    },
    getUserList() {
      getUserList().then(response => {
        this.users = response
        if (this.users.length>0){
          console.log(this.users.length)
          // webSocket.Send(this.toId,this.content)
          // console.log("dsdsdsdsd1111"+webSocket.receive())
          this.bus.$emit('currentUser', this.users[0])
        }else {
          console.log("noUser")
          this.$emit("noUser")
        }
      })
    },
    showInitDate(date, full) {
      return initDate(date, full)
    },
    setLetterRead() {
      setLetterRead(this.users[this.activeIndex].userId).then(response=>{
        //返回的是当前用户未读的私信数量
        this.bus.$emit('unReadCount',response)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
#list {
  ul {
    margin-left: 0;
    padding-left: 0;
  }

  li {
    padding-top: 14px;
    padding-bottom: 14px;
    //padding-right: 40px;
    //border-bottom: 1px solid #292C33;
    list-style: none;
    cursor: pointer;

    &:hover {
      background-color: #D8D6D6;
    }
  }

  .active {
    background-color: #C8C6C6;
  }

  li.active { /*注意这个是.不是冒号:*/
    background-color: #C8C6C6;
  }

  .img_badge {
    width: 65px;
    height: 65px;
    border-radius: 50%;
    //margin-left: 10px;
    //border:1px solid red;
  }

  .avatar {
    width: 65px;
    height: 65px;
    border-radius: 50%;
    float: left;
    transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    margin-right: 15px;
    object-fit: cover;
    margin-left: 10px;
  }

  .name {
    display: inline-block;
    margin-left: 10px;
    margin-top: 0;
    margin-bottom: 0;
  }

  .stateItem { //在线状态的样式
    /*position: absolute;*/
    /*left: 160px;*/
    //margin-left: 100px;
    //margin-right: 10px;
  }

  .userList {
    max-height: 600px;
  }

  .el-scrollbar__wrap.default-scrollbar__wrap {
    overflow-x: auto;
  }
}
</style>
