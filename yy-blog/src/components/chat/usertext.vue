<template>
  <div id="uesrtext" style="width: 660px;height: 150px;margin-top: 0">
    <div>
      <el-popover placement="top-start" width="400" trigger="click" class="emoBox">
        <div class="emotionList">
          <a href="javascript:void(0);" @click="getEmo(index)" v-for="(item,index) in faceList" :key="index" class="emotionItem">{{item}}</a>
        </div>
        <el-button id="emojiBtn" class="emotionSelect" slot="reference">
          <i class="fa fa-smile-o" aria-hidden="true"></i>
        </el-button>
      </el-popover>
    <el-upload
            class="upload-btn"
            action="/ossFileUpload?module=group-chat"
            :before-upload="beforeAvatarUpload"
            :on-success="imgSuccess"
            :on-error="imgError"
            :show-file-list="false"
            accept=".jpg,.jpeg,.png,.JPG,JPEG,.PNG,.gif,.GIF">
      <el-button id="uploadImgBtn" icon="el-icon-picture-outline"></el-button>
    </el-upload>
    </div>
    <label for="textarea"></label><textarea id="textarea" placeholder="按 Ctrl + Enter 发送" v-model="content" v-on:keyup="addMessage">
    </textarea>
    <el-button id="sendBtn"  type="primary" style="margin-left: 400px;;" size="mini" @click="addMessageByClick" >发送(S)</el-button>
  </div>
</template>

<script>
import {mixin} from "../../mixins";

const appData=require("../../utils/emoji.json")//引入存放emoji表情的json文件
import webSocket, {receive} from "../../utils/websocket"

export default {
  name: 'uesrtext',
  data () {
    return {
      faceList:[],//表情包数据
      content:'',
      toId:'',
      reply:''
    }
  },
  mounted(){
    for (let i in appData){//读取json文件保存数据给数组
      this.faceList.push(appData[i].char);
    }
    this.bus.$on('currentUser',res => {
      this.toId=res.userId
      console.log(res) // 输出的是header组件传过来的搜索框中的关键字
    })
  },
  methods: {
    handleMsg(toId,content,type) {
      let that = this;
      console.log(that.global.ws);
      if (that.global.ws && that.global.ws.readyState == 1) {
        console.log("发送信息", content);
        var message={
          toId:toId,
          content:content,
          type:type,
          createTime:new Date()
        }
        that.global.ws.send(JSON.stringify(message));
      }
      that.global.ws.onmessage = function(res) {
        console.log("收到服务器内容dsd", res);
        var msg=JSON.parse(res.data)
        that.bus.$emit('currentMsg', msg)
      };
    },
    addMessageByClick(){
      if(!this.content || this.content.match(/^[ ]*$/)) {
        this.$message({
          showClose: true,
          message: '不能发送空白信息'
        });
        return;
      }
        // console.log(this.toId)
      //发送消息
     this.handleMsg(this.toId,this.content,"letter")
      //给message组件发送发送的消息内容
      this.content='';
    },
  	addMessage (e) {
  		if (e.ctrlKey && e.keyCode ===13 && this.content.length) {
  		   this.addMessageByClick();
  		}
  	},
    /**
     *       图片上传的方法
     */
    //上传前
    beforeAvatarUpload(file) {
      //不给机器人发送图片
      if (this.currentSession.username=="机器人") {
        this.$message.error("瓦力拒绝接收你的图片！")
      }
      //判断图片大小
      let isLt1M = file.size / 1024 / 1024 < 1;
      console.log(file)
      if (!isLt1M) {
        this.$message.error('上传图片大小不能超过 1MB!');
      }
      //判断图片的格式
      let imgType=file.name.substring(file.name.lastIndexOf(".")+1);
      imgType=imgType.toLowerCase();
      let isImg=imgType==='jpg'|| imgType==='png'|| imgType==='jpeg'||imgType==='gif';
       if (!isImg){
         this.$message.error('上传图片格式不符合要求！');
       }
      return isLt1M&&isImg;
    },
    // 图片上传成功
    imgSuccess(response, file, fileList) {
      console.log("图片url为："+response);
      let msgObj=new Object();
      msgObj.content=response;
      //设置消息类型为图片
      msgObj.messageTypeId=2;
      if (this.currentSession.username=="群聊"){
        this.$store.state.stomp.send("/ws/groupChat",{},JSON.stringify(msgObj));
      }else {
        msgObj.from=this.$store.state.currentUser.username;
        msgObj.fromNickname=this.$store.state.currentUser.nickname;
        msgObj.to=this.currentSession.username;
        this.$store.state.stomp.send("/ws/chat",{},JSON.stringify(msgObj));
        //提交私聊消息记录
        this.$store.commit('addMessage',msgObj);
      }
    },
    // 图片上传失败
    imgError(err, file, fileList){
      this.$message.error("上传失败");
    },
    //获取Emoji
    getEmo(index){
      var textArea=document.getElementById('textarea');
      //将选中的表情插入到输入文本的光标之后
      function changeSelectedText(obj, str) {
        if (window.getSelection) {
          // 非IE浏览器
          textArea.setRangeText(str);
          // 在未选中文本的情况下，重新设置光标位置
          textArea.selectionStart += str.length;
          textArea.focus()
        } else if (document.selection) {
          // IE浏览器
          obj.focus();
          var sel = document.selection.createRange();
          sel.text = str;
        }
      }
      changeSelectedText(textArea,this.faceList[index]);
      this.content=textArea.value;// 要同步data中的数据
      // console.log(this.faceList[index]);
      return;

    },
  }
}
</script>


<style lang="scss">
  /* el-popover是和app同级的，所以scoped的局部属性设置无效 */
  /* 需要设置全局style */
  .el-popover{
    height:200px;
    width:450px;
    overflow: scroll;
    overflow-x:auto;
  }
</style>

<style lang="scss" scoped>
#uesrtext {
	position: absolute;
  bottom: 0;
  right: 0;
  width: 100%;
  height: 40%;
  border: solid 1px #DDD;
  background-color: white;
  > textarea {
  	padding: 10px;
  	width: 95%;
  	height: 45%;
  	border: none;
  	outline: none;
    resize: none;//禁止拉伸
  }
  #sendBtn{


  }
  #uploadImgBtn{
    border: none;
    padding-bottom: 0;
    margin-bottom: 0;
    padding-left: 12px;
  }
  #uploadImgBtn:hover{
    background-color: white;
  }
  #emojiBtn{
    border: none;
    padding-right: 0px;
    padding-bottom: 0px;
    margin-bottom: 0px;
  }
  #emojiBtn:hover{
    background-color: white;
  }
  .upload-btn{
    display: inline-block;
  }
}
.emotionList{
  display: flex;
  flex-wrap: wrap;
  padding:5px;
}
.emotionItem{
  width:10%;
  font-size:20px;
  text-align:center;
}
/*包含以下四种的链接*/
.emotionItem {
  text-decoration: none;
}
/*正常的未被访问过的链接*/
.emotionItem:link {
  text-decoration: none;
}
/*已经访问过的链接*/
.emotionItem:visited {
  text-decoration: none;
}
/*鼠标划过(停留)的链接*/
.emotionItem:hover {
  text-decoration: none;
}
/* 正在点击的链接*/
.emotionItem:active {
  text-decoration: none;
}
</style>
