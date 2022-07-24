<template>
  <div class="dis-detail-box">
    <div>
      <i style="color: #4db3ff;" class="fa fa-commenting fa-2x" aria-hidden="true"></i>
      <span style="font-size: 22px;margin-left: 5px">{{ detailObj.title }}</span>
    </div>
    <div style="border-bottom:1px solid #cbc9c9;height: 100px;margin-top: 15px">
      <a :href="'/HomePage?wid='+detailObj.userId"><img class="dis-img" :src="detailObj.avatar"></a>
      <a :href="'/HomePage?wid='+detailObj.userId"><span
        style="margin-top: 5px;display: inline-block;">{{ detailObj.nickName }}</span></a>
      <div style="margin-top: 20px;color: #a8a7a7;">
        <span>发布于<span style="margin-left: 5px">{{ detailObj.createTime }}</span></span>
        <span style="display:inline-block;float:right;">
        <span style="margin-right: 10px">赞<span style="margin-left: 3px">{{ detailObj.likeCount }}</span></span>
        |
        <span style="margin-right: 10px;margin-left: 5px">回复<span
          style="margin-left: 3px">{{ detailObj.commentCount }}</span></span>
        |<span style="margin-left: 10px">浏览<span style="margin-left: 3px">{{ detailObj.viewCount }}</span></span>
      </span>
      </div>
    </div>
    <div class="article-content markdown-body" v-html="detailObj.content"></div>
    <div style="margin-top: 20px">
        <span class="noLike" @click="toLike" v-if="!isLike">
      <i class="fa fa-thumbs-up fa-lg notLike" aria-hidden="true">({{detailObj.likeCount}})</i>
    </span>
      <span class="isLike" @click="toLike" v-else>
      <i style="position:absolute;top: 17px;left: 23px;color: white" class="fa fa-thumbs-up fa-lg" aria-hidden="true">({{detailObj.likeCount}})</i>
    </span>
      <span style="display: inline-block;margin-top:15px;position:absolute;margin-left: 30px">
     <span @click="toComment" class="comment"> <i style="margin-right: 5px" class="fa fa-commenting fa-lg " aria-hidden="true"></i>回复({{detailObj.commentCount}})</span>
    </span>
    </div>


  </div>

</template>

<script>
import {addViewCount, getDisussDetail} from "../api/discuss";
import {getIsLike, setLike, unLike} from "../api/like";
import {mixin} from "../mixins";

export default {
  mixins: [mixin],
  name: "detailDiscuss",
  data() {
    return {
      detailObj: {},
      did: '',
      isLike: false,
      hasLogin: false
    }
  },
  created() {
    this.did = this.$route.query.did == undefined ? "" : this.$route.query.did
    var userInfo = localStorage.getItem("userInfo");
    this.hasLogin = !!userInfo;
    this.$nextTick(function (){
      this.bus.$emit('discussId', this.did)
    })
    this.getDetail()
    this.getIsLike()
  },
  methods: {
    //点击让评论输入框获取焦点
    toComment(){
      this.bus.$emit('focusComment')
    },
    getDetail() {
      getDisussDetail(this.did).then(response => {
        this.detailObj = response
      })
      addViewCount(this.did)
    },
    toLike() {
      if (this.hasLogin) {
        // var id=this.aid
        if (this.isLike) {
          // unLike(id)
          this.detailObj.likeCount -= 1
          unLike(this.detailObj.id, 1)
          this.isLike = false
        } else {
          // setLike(id)
          this.detailObj.likeCount += 1
          setLike(this.detailObj.id, 1)
          this.isLike = true
        }
      } else {
        this.tip()
      }
    },
    getIsLike(){
      if (this.hasLogin){
        getIsLike(this.did,1).then(resposne=>{
          this.isLike=resposne
        })
      }
    },
  }
}
</script>

<style scoped>
.comment {
  color: #b6b6b6;
  /*float: left;*/
}

.comment:hover {
  color: deepskyblue;
}

.isLike {
  background-color: deepskyblue;
  height: 50px;
  width: 85px;
  border-radius: 10%;
  padding: 0;
  position: relative;
  display: inline-block;
  /*float: left;*/
}

.noLike {
  background-color: #eeecec;
  height: 50px;
  width: 85px;
  border-radius: 10%;
  padding: 0;
  position: relative;
  display: inline-block;
  /*float: left;*/
}

.notLike {
  color: #b6b6b6;
  position: absolute;
  top: 17px;
  left: 23px
}

.notLike:hover {
  color: deepskyblue;
}

.dis-img {
  width: 65px;
  height: 65px;
  border-radius: 50%;
  float: left;
  transition: all .4s ease-in-out;
  -webkit-transition: all .4s ease-in-out;
  margin-right: 15px;
  object-fit: cover;
}

.article-content {
  font-size: 15px;
  white-space: normal;
  word-wrap: break-word;
  word-break: break-all;
  overflow-x: hidden;
  margin-top: 20px;
}

.dis-detail-box {
  background-color: white;
  padding: 15px;
  border-radius: 5px;
  margin-bottom: 40px;
  font-size: 15px;
}
</style>
