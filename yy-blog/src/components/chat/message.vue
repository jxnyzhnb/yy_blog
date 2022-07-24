<template>
  <div id="message" v-scroll-bottom="">
    <div>
      <ul>
        <!--  内容是对方发的    -->
        <li v-for="msg in msgList" >
          <p class="time" >
            <span style="margin-right: 140px">{{ msg.createTime | time }}</span>
          </p>
          <div class="main" :class="{self:msg.self}">
            <!--					<p class="username">{{msg.userName}}</p>-->
            <a href="/Home"><img class="avatar" :src="msg.avatar" :onerror="$store.state.errorImg" alt=""></a>
            <p class="text">{{ msg.content }}</p>
            <!--					<img v-if="msg.messageTypeId==2" :src="msg.content" class="img">-->
          </div>
        </li>
        <!--   内容是自己发的   -->
      </ul>
    </div>
  </div>
</template>

<script>
import {getMsg} from "../../api/notice";

export default {
  name: 'message',
  data() {
    return {
      msgList: [],
      id: '',
    }
  },
  filters: {
    time(date) {
      if (date) {
        date = new Date(date);
      }
      //当前的时间
      let currentDate = new Date();
      //与当前时间的日期间隔
      let timeInterval = currentDate.getDate() - date.getDate();
      //星期数组
      let weekdays = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
      //时间范围
      let timeRange = "上午";
      if (date.getHours() > 12) {
        timeRange = "下午";
      }
      var minutes=date.getMinutes()
      if (minutes<10){
        minutes="0"+minutes;
      }
      //如果与当前时间同日
      if (date.getMonth() == currentDate.getMonth() && date.getDate() == currentDate.getDate()) {

        return `${timeRange} ${date.getHours()}:${minutes}`;
      }
      //在当前时间同一年且日期间隔在7天内
      if (date.getFullYear() == currentDate.getFullYear() && timeInterval <= 6 && timeInterval >= 1) {
        //当前时间的前一天
        if (timeInterval == 1) {
          return `昨天 ${timeRange} ${date.getHours()}:${minutes}`;
        } else {
          return `${weekdays[date.getDay()]} ${timeRange} ${date.getHours()}:${minutes}`;
        }
      }
      //如果日期超过7天
      else{
        return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()} ${timeRange}  ${date.getHours()}:${minutes}`;

      }
    }
  },
  directives: {/*这个是vue的自定义指令,官方文档有详细说明*/
    // 发送消息后滚动到底部,这里无法使用原作者的方法，也未找到合理的方法解决，暂用setTimeout的方法模拟
    'scroll-bottom'(el) {
      //console.log(el.scrollTop);
      setTimeout(function () {
        el.scrollTop += 9999;
      }, 1)
    }
  },
  methods: {
    takeAShot(fromName, toName) {
      console.log("拍了一怕");
      let s = fromName + "拍了拍" + toName;

    },
    handleMsg() {
      let that = this;
      console.log(that.global.ws);
      that.global.ws.onmessage = function(res) {
        console.log("收到服务器内容dsd1111", res);
        var msg=JSON.parse(res.data)
        that.msgList.push(msg)
      };
    },
    getMsgList() {
      console.log(this.id)
      getMsg(this.id).then(response => {
        this.msgList = response
      })
    }
  },
  mounted() {
    //获取当前需要进行私信的用户id
    this.bus.$on('currentUser', res => {
      this.id = res.userId
      this.getMsgList()
    })
    //获取当前用户发送给私信用户的信息
    this.bus.$on('currentMsg', res => {
      console.log(res)
      this.msgList.push(res)
    })

  }
}
</script>

<style lang="scss" scoped>
#message {
  //border:1px solid black;
  padding: 15px;
  height: 70%;
  max-height: 70%;
  overflow-y: scroll;
  overflow-x: hidden;

  ul {
    list-style-type: none;
    padding-left: 0;

    li {
      margin-bottom: 15px;
    }
  }

  .time {
    text-align: center;
    margin: 2px 0;

    > span {
      display: inline-block;
      padding: 0 10px;
      font-size: 12px;
      color: #FFF;
      background-color: #dcdcdc;
      border-radius: 2px;
    }
  }

  .main {
    .avatar {
      float: left;
      margin: 0 5px 0 0;
      width: 30px;
      height: 30px;
      border-radius: 50%;
      transition: all .4s ease-in-out;
      -webkit-transition: all .4s ease-in-out;
      object-fit: cover;
      //margin-left: 5px;

    }

    .text {
      display: inline-block;
      padding: 0 10px;
      max-width: 80%;
      background-color: #fafafa;
      border-radius: 4px;
      line-height: 30px;

    }

    .img {
      display: inline-block;
      height: 100px;
      width: 100px;
      margin-top: 15px;
    }
  }

  .self {
    text-align: right;

    .avatar {
      float: right;
      //margin: 0 0 0 10px;
      //margin: 0 5px 0 0;
      width: 30px;
      height: 30px;
      border-radius: 50%;
      transition: all .4s ease-in-out;
      -webkit-transition: all .4s ease-in-out;
      object-fit: cover;
      margin-right: 150px;
    }

    .text {
      display: inline-block;
      padding: 0 10px;
      max-width: 80%;
      background-color: #b2e281;
      border-radius: 4px;
      line-height: 30px;
     margin-right: 5px;
    }

    .img {
      display: inline-block;
      height: 100px;
      width: 100px;
      margin-top: 15px;
    }

    .username {
      //display: inline-block;
      position: relative;
      left: 310px;
      top: 11px;
      margin: 0 0;
      padding: 0 0;
      width: 200px;
      line-height: 15px;
      font-size: 10px;
      color: grey;
    }
  }
}
</style>
