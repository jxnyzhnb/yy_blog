<!-- 右侧固定导航栏 -->
<template>
  <div class="rightlistBox">
    <section
      v-if="(this.$route.path=='/Friendslink'&&!hasLogin)||(this.$route.path=='/Share'&&!hasLogin)||(this.$route.path=='/discuss'&&!hasLogin)?false:true">
      <div class="r1-head">
        <a :href="'/HomePage?wid='+this.dataMap.id"> <img class="uImg" style="float:left;"
                                                          :src="dataMap.avatar?dataMap.avatar:$store.state.errorImg"
                                                          alt=""></a>
        <div style="float:left;margin: 20px">
          <div style="font-weight: bold;font-size: 20px"><a
            :href="'/HomePage?wid='+this.dataMap.id">{{ dataMap.nickName }}</a></div>
          <div style="margin-top: 10px">码龄{{ dataMap.codeAge }}</div>
        </div>
      </div>
      <div class="r1-body" style="margin-top: 120px;margin-bottom: 10px;">
        <div class="catch-me">
          <div class="" style="height: 50px">
            <div style="float:left;margin-left: 20px">
              <div><span style="font-size: 18px">{{ dataMap.articleCount }}</span></div>
              <div><a :href="'/HomePage?wid='+this.dataMap.id"><span class="createA"
                                                                     style="font-size: 18px">原创</span></a></div>
            </div>
            <div style="float:left;margin-left: 20px">
              <div><span
                style="font-size: 18px">{{
                  dataMap.viewCount > 10000 ? dataMap.viewCount % 10000 + "+" : dataMap.viewCount
                }}</span>
              </div>
              <div><span style="font-size: 18px;color: grey">访问</span></div>
            </div>
            <div style="float:left;margin-left: 20px">
              <div><span style="font-size: 18px">{{ dataMap.followCount }}</span></div>
              <div><span style="font-size: 18px;color: grey">粉丝</span></div>
            </div>
            <div style="float:left;margin-left: 20px">
              <div><span style="font-size: 18px">{{ dataMap.likeCount }}</span></div>
              <div><span style="font-size: 18px;color: grey">获赞</span></div>
            </div>
            <div style="float:left;margin-left: 20px">
              <div><span style="font-size: 18px">{{ dataMap.commentCount }}</span></div>
              <div><span style="font-size: 18px;color: grey">评论</span></div>
            </div>
          </div>
          <div v-if="!dataMap.self" style="margin-top: 20px;text-align: center;">
            <span style="display: inline-block" @click="followAuthor">
                      <el-button round v-if="!this.dataMap.follow" size="small" plain
                                 style="color: #808080;background-color: white;margin-right: 10px;width: 120px">+关注</el-button>
                      <el-button round v-else size="small" plain
                                 style="color: #808080;background-color: white;margin-right: 10px;width: 120px">已关注</el-button>
            </span>
          </div>
        </div>
      </div>
    </section>
    <section class="rs4">
      <h2 class="ui label">热门文章</h2>
      <ul style="margin-left: 10px">
        <li v-for="(item, index) in browseList" :key="'browseList' + index">
          <span> <img :src="item.thumbnail" style="width: 30px;height: 30px" alt=""/></span>
          <span
            style="line-height:30px;width: 220px;display:inline-block;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
            <a style="" :href="'/DetailArticle?aid=' + item.id" target="_blank">

          {{ item.title }}</a></span>
          <span style="color: grey;float:right;display: inline-block;line-height: 30px">{{
              item.viewCount > 10000 ? item.viewCount % 10000 + "万+" : item.viewCount
            }}阅读</span>

        </li>
      </ul>
    </section>
    <section class="rs4"
             v-if="categoryList.length>0&&(this.$route.path=='/DetailArticle'||this.$route.path=='/HomePage'||this.$route.path=='/MyArticle')">
      <h2 class="ui label">分类专栏</h2>
      <ul style="margin-left: 10px">
        <li v-for="(item, index) in categoryList" :key="'categoryList' + index">
          <span><a :href="'/Share?classId='+item.id">{{ item.name }}</a></span>
          <span style="float:right;">{{ item.count }}篇</span>
        </li>
      </ul>
    </section>
    <section class="rs4" v-if="this.$route.path=='/DetailArticle'">
      <h2 class="ui label">最新评论</h2>
      <ul style="margin-left: 10px">

        <li v-for="(item, index) in artCommentList" :key="'artCommentList' + index">
          <div style=""><a :href="'/DetailArticle?aid='+item.typeId"><span class="userArticle"
                                                                           style="font-size: 15px;color: #797777;font-weight: lighter;">
            {{ item.title }}</span></a>
          </div>
          <div style=" font-size: 15px"><a :href="'/HomePage?wid='+item.createBy"><span class="userArticle"
                                                                                        style="font-weight: normal;font-size: 15px">
            {{ item.nickName }}:
          </span></a>
            {{ item.content }}
          </div>
        </li>
      </ul>
    </section>
    <!-- 右侧上滑小图片 -->
    <div
      v-if="this.$store.state.themeObj.user_start != 0"
      :class="gotoTop ? 'toTop hidden' : 'toTop goTop hidden'"
      @click="toTopfun"
    >
      <img
        :src="
          this.$store.state.themeObj.right_img
            ? this.$store.state.themeObj.right_img
            : 'static/img/scroll.png'
        "
        alt=""
      />
    </div>
    <div
      v-else
      :class="gotoTop ? 'toTophui hidden' : 'toTophui goTophui hidden'"
      @click="toTopfun"
    >
      <img
        :src="
          this.$store.state.themeObj.right_img
            ? this.$store.state.themeObj.right_img
            : 'static/img/scroll.png'
        "
        alt=""
      />
    </div>
  </div>
</template>


<script>
import {hotArticleList} from "../api/article";
import {profile} from "../api/user";
import {setFollow, unFollow} from "../api/follow";
import {mixin} from "../mixins";
import {getNewComment} from "../api/comment";
import {getCategory} from "../api/category";

export default {
  mixins: [mixin],
  data() {
    //选项 / 数据
    return {
      hasLogin: false,
      id: '', //文章id
      dataMap: {},
      //用户id
      wid: '',
      fixDo: false,
      follow: false,
      loveme: false,
      gotoTop: false, //返回顶部
      going: false, //是否正在执行上滑动作
      browseList: "", //热门文章 浏览量最多
      artCommentList: "", //最新评论
      categoryList: '', //分类列表
      queryParams: {
        type: 0,
        wid: '', //用户id
        aid: ''//文章id
      }
    };
  },
  mounted() {
  },
  methods: {
    followAuthor() {
      if (this.hasLogin) {
        //如果id为null说明当前路由不为articleDeatil
        var id = this.id != '' ? this.id : -1
        var type
        if (this.$route.path == '/DiscussDetail') {
          type = 'disucss'
          this.wid=this.dataMap.id
        } else if (this.$route.path == '/DetailArticle') {
          type = 'article'
          this.wid=this.dataMap.id
        } else {
          type="other"
        }
        if (this.dataMap.follow) {
          this.dataMap.follow = false
          unFollow(this.wid, id,type)
        } else {
          setFollow(this.wid, id,type)
          this.dataMap.follow = true
        }
      } else {
        this.tip()
      }
    },
    getBasic() {
      var obj = {}
      if (this.$route.path == '/DiscussDetail') {
        obj["source"] = 'discuss_detail'
      } else if (this.$route.path == '/DetailArticle') {
        obj["source"] = 'article_detail'
      }
      // console.log("dsdsdsdsdsdsdsd" + this.id)
      if (this.id) {
        obj['id'] = this.id
      } else {
        obj["source"] = 'homepage'
        if (this.wid) {
          obj["userId"] = this.wid
        }
      }
      if (!(this.$route.path == '/Friendslink' && !this.hasLogin) || (this.$route.path == '/Share' && !this.hasLogin) || (this.$route.path == '/discuss' && !hasLogin)) {
        profile(obj, this.hasLogin).then(resonse => {
          this.dataMap = resonse
        })
      }


    },
    //事件处理器
    toTopfun: function (e) {
      var that = this;
      this.gotoTop = false;
      this.going = true;
      var timer = setInterval(function () {
        //获取滚动条距离顶部高度
        var osTop =
          document.documentElement.scrollTop || document.body.scrollTop;
        var ispeed = Math.floor(-osTop / 7);
        document.documentElement.scrollTop = document.body.scrollTop =
          osTop + ispeed;
        //到达顶部，清除定时器
        if (osTop == 0) {
          that.going = false;
          clearInterval(timer);
          timer = null;
        }
      }, 30);
    },
    getHotArticleList() {
      hotArticleList(this.queryParams, this.hasLogin).then((response) => {
        this.browseList = response;
      });
    },
    getNewComment() {
      console.log("comment" + this.id)
      getNewComment(this.id).then(response => {
        this.artCommentList = response
      })
    },
    getCategory() {

      getCategory(this.queryParams, this.hasLogin).then(response => {
        this.categoryList = response
      })
    }
  },
  components: {
    //定义组件
  },
  created() {
    //生命周期函数
    var that = this;
    var userInfo = localStorage.getItem("userInfo");
    this.hasLogin = !!userInfo;
    that.wid = that.$route.query.wid == undefined ? '' : parseInt(that.$route.query.wid);//获取传参的aid
    if (this.$route.path == '/DetailArticle') {
      this.bus.$on('articleId', res => {
        this.id = res
        this.getBasic()
        this.getNewComment()
      })
    } else if (this.$route.path == '/DiscussDetail') {
      this.bus.$on('discussId', res => {
        this.id = res
        this.getBasic()
        this.getNewComment()
      })
    } else {
      this.getBasic()
    }


    window.onscroll = function () {
      var t = document.documentElement.scrollTop || document.body.scrollTop;
      // console.log(t);
      if (!that.going) {
        if (t > 600) {
          that.gotoTop = true;
        } else {
          that.gotoTop = false;
        }
      }
      if (t > 1200) {
        that.fixDo = true;
      } else {
        that.fixDo = false;
      }
    };
    if (this.$route.path == '/DetailArticle') {
      this.queryParams.aid = this.$route.query.aid == undefined ? '' : parseInt(this.$route.query.aid);//获取传参的aid
      this.queryParams.type = 3
    } else if (this.$route.path == '/MyArticle') {
      this.queryParams.type = 1
    } else if (this.$route.path == '/HomePage') {
      this.queryParams.wid = this.$route.query.wid == undefined ? '' : parseInt(this.$route.query.wid);//获取传参的aid
      this.queryParams.type = 2
    }
    //查询浏览量最多的10篇文章数据
    this.getHotArticleList();
    //查询作者的分类专栏
    this.getCategory()
  },
};
</script>

<style lang="less">
.userArticle:hover {
  color: #f1d079;
}

.createA:hover {
  color: grey;
}

.uImg {
  height: 60px !important;
  width: 100px !important;
  border-radius: 100%;
  transition: all .4s ease-in-out;
  -webkit-transition: all .4s ease-in-out;
  object-fit: cover;
  margin-left: 50px;
}

.rightlistBox {
  position: relative;
}

.rightlistBox section {
  transition: all 0.2s linear;
  position: relative;
  background: #fff;
  padding: 15px;
  margin-bottom: 20px;
  border-radius: 5px;
}

.rightlistBox section:hover {
  transform: translate(0, -2px);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}

.rightlistBox .r1-head {
  text-align: center;
  border-radius: 4px 4px 0 0;
  position: relative;
  /*box-shadow: inset 0 -70px 100px -50px rgba(0,0,0,.5);*/
}

.rightlistBox .r1-head img {
  width: 100%;
  min-height: 100px;
}

.rightlistBox .r1-head h1 {
  position: absolute;
  bottom: 5px;
  margin: 0 0 0 -65px;
  font-size: 20px;
  letter-spacing: 0.5px;
  color: #fff;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.7);
  font-weight: 700;
  width: 130px;
  left: 50%;
}

.rightlistBox .r1-head h1 span {
  opacity: 0.3;
}

.rightlistBox .r1-body p {
  font-size: 14px;
  margin: 5px 0 8px 0;
  font-weight: 700;
  text-align: center;
}

.rightlistBox .r1-body .catch-me {
  text-align: center;
}

.rightlistBox .r1-body .catch-me a {
  display: inline-block;
  margin-bottom: 7px;
  position: relative;
  text-decoration: none;
}

.rightlistBox .r1-body .catch-me a:hover i {
  color: #fff;
  background: #f4692c;
}

.rightlistBox .r1-body .catch-me a i {
  display: inline-block;
  font-size: 18px;
  width: 42px;
  height: 42px;
  line-height: 42px;
  border-radius: 42px;
  color: rgba(0, 0, 0, 0.5);
  background: rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease-in-out;
  font-style: normal;
  margin: 0 3.2px;
}

/*************do you like me*******************/
.rightlistBox .rs2 {
  /*padding:10px 0 4px 0;*/
  min-height: 100px;
}

.rightlistBox .rs2.fixed {
  position: fixed;
  top: 40px;
  width: 22%;
}

.rightlistBox .rs2 p {
  color: #df2050;
  cursor: pointer;
  font-size: 20px;
  margin-bottom: 10px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  margin-top: 10px;
  font-weight: 500;
}

.rightlistBox .rs2 div {
  color: #df2050;
  cursor: pointer;
  text-align: center;
  font-size: 40px;
  position: absolute;
  width: 100%;
  height: 100px;
  line-height: 100px;
  left: 0;
  top: 30px;
}

.rightlistBox .rs2 div i.heart {
  display: inline-block;
  text-align: center;
  width: 100px;
  height: 100px;
  margin-left: -20px;
  margin-top: -5px;
  background: url(../../static/img/heart.png) no-repeat;
  background-position: 0 0;
  cursor: pointer;
  -webkit-transition: background-position 1s steps(28);
  transition: background-position 1s steps(28);
  -webkit-transition-duration: 0s;
  transition-duration: 0s;
  vertical-align: middle;
}

.rightlistBox .rs2 div i.heart:hover {
  transform: scale(1.15);
  -webkit-transform: scale(1.15);
}

.rightlistBox .rs2 div i.heart.active {
  -webkit-transition-duration: 1s;
  transition-duration: 1s;
  background-position: -2800px 0;
}

.rightlistBox .rs2 div span {
  margin-left: -30px;
}

/**********排队来说*************/
.rightlistBox .rs3 .rs3-item {
  font-size: 13px;
  line-height: 20px;
}

.rightlistBox .rs3 .rs3-item a {
  display: block;
  padding: 5px;
  transition: all 0.3s ease-out;
  border-bottom: 1px solid #ddd;
  margin: 5px 0;
}

.rightlistBox .rs3 .rs3-item a:hover {
  background: rgba(230, 244, 250, 0.5);
  border-radius: 5px;
}

.rightlistBox .rs3 .rs3-photo {
  float: left;
}

.rightlistBox .rs3 .rs3-photo img {
  border-radius: 50%;
  width: 32px;
  height: 32px;
  object-fit: cover;
}

.rightlistBox .rs3 .rs3-inner {
  margin-left: 40px;
}

.rightlistBox .rs3 .rs3-inner .rs3-author {
  font-weight: 700;
}

.rightlistBox .rs3 .rs3-inner .rs3-text {
  font-size: 12px;
  text-align: justify;
}

.rightlistBox .rs3 .rs3-item:last-child a {
  border-bottom: none;
}

/************排队看这些***************/
.rightlistBox .rs4 li {
  padding: 8px 0;
  text-align: justify;
}

.rightlistBox .rs4 li a {
  font-weight: 600;
}

.rightlistBox .rs4 li a:hover {
  color: #64609e;
}

/*回到顶部*/
/*返回到顶部*/
.toTop {
  position: fixed;
  right: 40px;
  top: -150px;
  z-index: 99;
  width: 70px;
  height: 900px;
  transition: all 0.5s 0.3s ease-in-out;
  cursor: pointer;
}

.goTop {
  top: -950px;
}

.toTop img,
.toTophui img {
  width: 100%;
  height: auto;
}

.toTophui {
  position: fixed;
  right: 10px;
  bottom: 80px;
  z-index: 99;
  width: 120px;
  height: 120px;
  transition: all 0.5s 0.3s ease-in-out;
  cursor: pointer;
  animation: toflow 2s ease-in-out infinite;
}

@keyframes toflow {
  0% {
    /*top:400px;*/
    transform: scale(0.95) translate(0, 10px);
  }
  50% {
    /*top:410px;*/
    transform: scale(1) translate(0, 0px);
  }
  100% {
    /*top:400px;*/
    transform: scale(0.95) translate(0, 10px);
  }
}

.goTophui {
  bottom: 120vh;
}
</style>
