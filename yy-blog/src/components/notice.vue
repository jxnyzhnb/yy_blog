<template>
  <div style="border: 1px solid gainsboro;height: 660px; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
    <el-tabs class="tabs" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane name="notice" class="notice">
        <span slot="label" style='margin-right: 55px;margin-left: 55px'>
          通知<el-badge :hidden="unRead.notice<=0||activeName=='notice'" :value="unRead.notice"></el-badge>
        </span>
      </el-tab-pane>
      <el-tab-pane name="comment" class="comment">
       <span slot="label" style='margin-right: 55px;margin-left: 55px'>
          评论<el-badge :hidden="unRead.comment<=0||activeName=='comment'" :value="unRead.comment" ></el-badge>
        </span>
        <el-scrollbar style="overflow-x: hidden;overflow-y: auto;height: 580px">
        <ul
          class="list">
          <li  style="height: 80px;border-bottom: 1px  solid gainsboro;margin-top:10px" v-for="(item, index) in noticeList" :key="'noticeList' + index" >
            <div class="nn" style="float:left;;">
              <a :href="'/HomePage?wid='+item.fromId"><img style="margin-left: 20px" class="userImg" :src="item.avatar" :onerror="$store.state.errorImg" /></a>
              <div>
                <a :href="'/HomePage?wid='+item.fromId"><span style="font-weight: 500;color: black">{{item.nickName}}</span></a>
                &nbsp;&nbsp;&nbsp;
                <span style="color: grey" v-if="item.type=='comment_article'">评论了你的博文</span>
                <span style="color: grey" v-if="item.type=='comment_discuss'">评论了你的帖子</span>
                <span style="color: grey" v-else-if="item.type=='reply_article_comment'">回复了你的博文评论</span>
                <span style="color: grey" v-else-if="item.type=='reply_discuss_comment'">回复了你的帖子评论</span>
              </div>
              <div style="margin-top: 5px;font-weight: bold;text-align:left">{{item.data.comment}}</div>
              <div  style="margin-top: 5px">
                <a v-if="item.data.articleId" :href="'/DetailArticle?aid='+item.data.articleId">{{item.data.title}}</a>
                <a v-if="item.data.discussId" :href="'/DiscussDetail?did='+item.data.discussId">{{item.data.title}}</a>
              </div>
            </div>
            <div style="float:right;width: 200px;left: 0;margin-top: 15px">
              <div style="float:right;margin-right: 25px;width: 20px;color: grey"><a href="javascript:void (0)" @click="deleteNotice(item.id,index)"><i  class="fa fa-trash-o" aria-hidden="true"></i></a></div>
              <div style="height: 20px;margin-top: 18px;margin-left: 35px;color: grey">{{item.createTime}}</div>
            </div>
          </li>
        </ul>
        <p style="text-align: center" v-if="onMore&&this.noticeList.length>0" @click="findMore">查看更多</p>
        <p v-if="!onMore&&this.noticeList.length>this.noticeParams.pageSize" style="text-align: center">没有更多了</p>
          <div v-if="this.noticeList.length<=0">
            <p style="text-align: center;margin:30%;color: gray">你还没有被评论，快去创作吧！</p>
          </div>
        </el-scrollbar>
      </el-tab-pane>
      <el-tab-pane  name="like" class="like">
        <span slot="label" style='margin-right: 55px;margin-left: 55px'>
          赞<el-badge  :hidden="unRead.like<=0||activeName=='like'" :value="unRead.like"></el-badge>
        </span>
        <el-scrollbar style="overflow-x: hidden;overflow-y: auto;height: 580px">
          <ul
            class="list">
            <li  style="height: 80px;border-bottom: 1px  solid gainsboro;margin-top:10px" v-for="(item, index) in noticeList" :key="'noticeList' + index" >
              <div class="nn" style="float:left;;">
                <a :href="'/HomePage?wid='+item.fromId"><img style="margin-left: 20px" class="userImg" :src="item.avatar" :onerror="$store.state.errorImg" /></a>
                <div>
                  <a :href="'/HomePage?wid='+item.fromId"><span style="font-weight: 500;color: black">{{item.nickName}}</span></a>&nbsp;&nbsp;&nbsp;
                  <span style="color: grey" v-if="item.type=='like_article'">点赞了你的博文</span>
                  <span style="color: grey" v-if="item.type=='like_discuss'">点赞了你的帖子</span>
                  <span style="color: grey" v-if="item.type=='like_article_comment'">点赞了你的博文评论</span>
                  <span style="color: grey" v-if="item.type=='like_discuss_comment'">点赞了你的帖子评论</span>
                </div>
                <div style="margin-top: 5px;font-weight: bold;text-align:left">{{item.data.comment}}</div>
                <div  v-if="item.data.articleId" style="margin-top: 5px"><a :href="'/DetailArticle?aid='+item.data.articleId">{{item.data.title}}</a></div>
                <div  v-if="item.data.discussId" style="margin-top: 5px"><a :href="'/DiscussDetail?did='+item.data.discussId">{{item.data.title}}</a></div>
              </div>
              <div style="float:right;width: 200px;left: 0;margin-top: 15px">
                <div style="float:right;margin-right: 25px;width: 20px;color: grey"><a href="javascript:void (0)" @click="deleteNotice(item.id,index)"><i  class="fa fa-trash-o" aria-hidden="true"></i></a></div>
                <div style="height: 20px;margin-top: 18px;margin-left: 35px;color: grey">{{item.createTime}}</div>
              </div>
            </li>
          </ul>
          <p style="text-align: center" v-if="onMore&&this.noticeList.length>0" @click="findMore">查看更多</p>
          <p v-if="!onMore&&this.noticeList.length>this.noticeParams.pageSize" style="text-align: center">没有更多了</p>
          <div v-if="this.noticeList.length<=0">
            <p style="text-align: center;margin:30%;color: gray">你还没有被点赞，快去创作吧！</p>
          </div>
        </el-scrollbar>
      </el-tab-pane>
      <el-tab-pane name="letter" class="letter">
      <span slot="label" style='margin-right: 55px;margin-left: 55px;'>
          私信<el-badge :hidden='unRead.letter<=0' :value="unRead.letter">
<!--        :hidden="unRead.letter<=0||beforeActive=='letter'"-->
      </el-badge>
        </span>
        <div id="app" v-if="show">
          <div class="sidebar">
            <list v-on:noUser="showOff()"></list>
          </div>
          <div class="main">
            <chat></chat>
            <usertext></usertext>
          </div>
        </div>
        <div v-if="!show">
           <p style="text-align: center;margin:30%;color: gray">你还未有私信的人，快去关注别人吧</p>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import {deleteNotice, getNoticeList, getUnread, setRead} from "../api/notice";
import MessageList from "../pages/MessageList";
import header from "./header";
import chat from "../components/chat/message";
import list from "../components/chat/list";
import usertext from "../components/chat/usertext";

export default {
  name: 'notice',
  components:{
    chat,
    list,
    usertext
  },
  data() {
    return {
      activeName: 'notice',
      beforeActive:'',
      noticeList:[],
      onMore:true,

      noticeParams:{
        pageNum: 1,
        pageSize: 7,
        tType:'notice'
      },
      commentParams:{
        pageNum: 1,
        pageSize: 7,
        tType:'comment'
      },
      likeParams:{
        pageNum: 1,
        pageSize: 7,
        tType:'like'
      },
      queryParams: {
        pageNum: 1,
        pageSize: 7,
        tType:''
      },
      unRead:{
        notice:'',
        comment:'',
        like:'',
        letter:''
      },
      //当私信用户为0时将隐藏这些组件
      show:true

    };
  },
  created() {
    this.getUnread()
    this.getNoticeList(this.activeName)
  },
  mounted() {
    //获得当前未读的私信数量
    this.bus.$on('unReadCount', res => {
          this.unRead.letter=res
    })
    this.bus.$on('hiddenLetter',res => {
      this.show=true
    })
  },
  methods: {
    showOff(){
      console.log("noUse111r")
      this.show=false
    },
    handleClick(tab, event) {
      this.bus.$emit('isDot',1)
      console.log(tab.name);
      console.log(this.beforeActive)
      if (this.activeName=='notice'){
        this.unRead.notice=-1
      } else if (this.activeName=='comment'){
        this.unRead.comment=-1
      } else if (this.activeName=='like'){
        this.unRead.like=-1
      } else {
        this.unRead.letter=-1
      }
      this.setRead()

      this.noticeParams.pageNum=1
      this.commentParams.pageNum=1
      this.likeParams.pageNum=1
      this.noticeList=[]
      this.getNoticeList(tab.name)

    },
    getNoticeList(tType){

      this.getUnread()
      if (tType=='notice'){
        this.queryParams=this.noticeParams
      }else if (tType=='comment'){
        this.queryParams=this.commentParams
      }else if (tType=='like'){
        this.queryParams=this.likeParams
      }
      getNoticeList(this.queryParams).then((response) =>{
        if (response.rows) {
          var rows=response.rows
          this.noticeList=this.noticeList.concat(rows)
          this.onMore=response.total>this.noticeList.length
        }

      })
    },getUnread(){
      getUnread().then((response) =>{
        this.unRead=response
      })
  },
    setRead() {
      if (this.activeName) {
        if (this.activeName!='letter'){
          setRead(this.activeName)
        }

      }
    },
    deleteNotice (id,index) {
      var that = this;
      this.$confirm('是否删除这条消息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        tType: 'warning'
      }).then(() => {
        // console.log(that.$route.path);

        deleteNotice(id).then((response)=>{
           // window.location.reload();
          that.$message({
            tType: 'success',
            message: '删除成功!'
          });
         this.$delete(this.noticeList,index)

        })
      }).catch(() => {
        //
      });

    },
    findMore () {
        var active=this.activeName
        if (active=='notice'){
          this.noticeParams.pageNum++;
        }else if (active=='comment'){
          this.commentParams.pageNum++
        }else if (active=='like'){
          this.likeParams.pageNum++
        }
        this.getNoticeList(this.activeName)
    }
  }
};
</script>
<style lang="scss" scoped>
#app {
  //margin: 20px auto;
  width: 900px;
  height: 606px;
  overflow: hidden;

.sidebar, .main {
  height: 100%;
}

.sidebar {
  float: left;
  color: #000000;
  background-color: #ECEAE8;
  width: 240px;
}
.main {
  position: relative;
  overflow: hidden;
  background-color: #eee;
  //background-color: red;
}
}
.userImg{
  width: 65px;
  height: 65px;
  border-radius: 50%;
  float: left;
  transition: all .4s ease-in-out;
  -webkit-transition: all .4s ease-in-out;
  margin-right: 15px;
  object-fit: cover;
}
.userImg :hover{
  transform: rotate(360deg);
  -webkit-transform: rotate(360deg);
}
.nn div{

  width: 450px;
  margin-left: 50px;
 /* position: relative;*/
}
.chat-content {
  width: 100%;
  padding: 20px;
}
.word {
  display: flex;
  margin-bottom: 20px;
}
img{
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.info {
  margin-left: 10px;
}
.time{
  font-size: 12px;
  color: rgba(51,51,51,0.8);
  margin: 0;
  height: 20px;
  line-height: 20px;
  margin-top: -5px;
}
.info-content{
  padding: 10px;
  font-size: 14px;
  background: #fff;
  position: relative;
  margin-top: 8px;
}

  .info-content::before{
    position: absolute;
    left: -8px;
    top: 8px;
    content: '';
    border-right: 10px solid #FFF;
    border-top: 8px solid transparent;
    border-bottom: 8px solid transparent;
  }



.word-my {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}
img{
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.info {
  width: 90%;
  margin-left: 10px;
  text-align: right;
}
.time{
  font-size: 12px;
  color: rgba(51,51,51,0.8);
  margin: 0;
  height: 20px;
  line-height: 20px;
  margin-top: -5px;
  margin-right: 10px;
}
.info-content{
  max-width: 70%;
  padding: 10px;
  font-size: 14px;
  float: right;
  margin-right: 10px;
  position: relative;
  margin-top: 8px;
  background: #A3C3F6;
  text-align: left;
}

  .info-content::after{
    position: absolute;
    right: -8px;
    top: 8px;
    content: '';
    border-left: 10px solid #A3C3F6;
    border-top: 8px solid transparent;
    border-bottom: 8px solid transparent;
  }




</style>

