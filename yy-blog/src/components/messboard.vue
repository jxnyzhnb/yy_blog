<template>
  <div>
    <!-- banner -->
    <div class="message-banner">
      <!-- 弹幕输入框 -->
      <div class="message-container">
        <h1 class="message-title">留言板</h1>
        <div class="animated fadeInUp message-input-wrapper">
          <input
            v-model="messageContent"
            @click="show = true"
            @keyup.enter="addToList"
            placeholder="说点什么吧"
            style=" background-color: transparent;"
          />
          <button
            class="ml-3 animated bounceInLeft"
            @click="addToList"
            v-show="show"
            style="margin-left: 10px;width: 80px;font-size: 16px;background-color: transparent;color: white"
          >
            发送
          </button>
        </div>
      </div>
      <!-- 弹幕列表 -->
      <div class="barrage-container">
        <vue-baberrage :barrageList="barrageList">
          <template v-slot:default="slotProps">
            <span class="barrage-items">
              <img
                :src="slotProps.item.avatar"
                width="30"
                height="30"
                style="border-radius:50%"
              />
              <span class="ml-2">{{ slotProps.item.nickName }} :</span>
              <span class="ml-2">{{ slotProps.item.messageContent }}</span>
            </span>
          </template>
        </vue-baberrage>
      </div>
    </div>
  </div>
</template>

<script>
import {addMess, getMess} from "../api/message";
import UserInfo from "../pages/UserInfo";

export default {
  name:'messboard',
  mounted() {
    this.listMessage();
  },
  data() {
    return {
      show: false,
      messageContent: "",
      barrageList: []
    };
  },
  methods: {
    addToList() {
      if (this.messageContent.trim() == "") {
        this.$message.error( '留言不能为空！');
        return false;
      }
      const userAvatar = this.$store.state.avatar
        ? this.$store.state.avatar
        : "../../static/img/d41d8cd98f00b204e9800998ecf8427e.jpg";
      const userNickname = this.$store.state.nickName
        ? this.$store.state.nickName
        : "游客";
      var message = {
        avatar: userAvatar,
        nickName: userNickname,
        messageContent: this.messageContent,
        time: Math.floor(Math.random() * (10 - 7)) + 7
      };
      this.barrageList.push(message);
      this.messageContent = "";
      addMess(message,this.$store.state.nickName!=null)
    },
    listMessage() {
      getMess().then( data  => {
          this.barrageList = data;
      });
    }
  }
};
</script>

<style scoped>
.message-banner {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  /*height: 100vh;*/
  background: url('../../static/img/headback08.png') ;
  animation: header-effect 1s;
  height: 650px;
  /*position: relative;*/
  width: 100%;
  background-size: cover;
  background-position: center 50%;
  background-repeat: no-repeat;
}
.message-title {
  color: #eee;
  font-size: 40px;
  font-weight: bold;
  animation: title-scale 1s;
}
.message-container {
  position: absolute;
  width: 360px;
  top: 35%;
  left: 0;
  right: 0;
  text-align: center;
  z-index: 5;
  margin: 0 auto;
  color: #ffffff;
}
.message-input-wrapper {
  display: flex;
  justify-content: center;
  height: 2.5rem;
  margin-top: 2rem;
}
.message-input-wrapper input {
  outline: none;
  width: 70%;
  border-radius: 20px;
  height: 100%;
  padding: 0 1.25rem;
  color: #eee;
  border: #fff 1px solid;
}
.message-input-wrapper input::-webkit-input-placeholder {
  color: #eeee;
}
.message-input-wrapper button {
  outline: none;
  border-radius: 20px;
  height: 100%;
  padding: 0 1.25rem;
  border: #fff 1px solid;
}
.barrage-container {
  position: absolute;
  top: 50px;
  left: 0;
  right: 0;
  bottom: 0;
  height: calc(100% - 50px);
  width: 100%;
}
.barrage-items {
  background: rgba(17, 14, 14, 0.7);
  border-radius: 100px;
  color: #fff;
  padding: 5px 10px 5px 5px;
  align-items: center;
  display: flex;
}
</style>
