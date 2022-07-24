<!-- 文章列表 -->
<template>
  <el-row class="sharelistBox">
    <el-col :span="24" class="s-item tcommonBox" v-for="(item,index) in articleList" :key="'article'+index">
            <span class="s-round-date">
                <span class="month" v-html="showInitDate(item.createTime,'month')+'月'"></span>
                <span class="day" v-html="showInitDate(item.createTime,'date')"></span>
            </span>
      <header>
        <h1>
          <a :href="'/DetailArticle?aid='+item.id" target="_blank" v-html="item.title">
          </a>
        </h1>
        <h2>
          <span style="font-weight: bold">{{ item.nickName }}</span>发表于
          <i class="fa fa-fw fa-clock-o"></i><span
          v-html="showInitDate(item.createTime,'all')">{{ showInitDate(item.createTime, 'all') }}</span> •
          <i class="fa fa-fw fa-eye"></i>{{ item.viewCount }} 次围观 •

        </h2>
        <div class="ui label" >
          <a :href="'/Share?classId='+item.categoryId">{{ item.categoryName }}</a>
        </div>
      </header>
      <div class="article-content">
        <p class="summaryP" style="text-indent:2em;" v-html="item.summary">
        <p style="max-height:300px;overflow:hidden;text-align:center;">
          <img :src="item.thumbnail" alt="" class="maxW">
        </p>
      </div>
      <div class="viewdetail">
        <a class="tcolors-bg" :href="'/DetailArticle?aid='+item.id" target="_blank">
          阅读全文>>
        </a>
      </div>

    </el-col>
    <el-col class="viewmore">
      <a v-show="hasMore" class="tcolors-bg" href="javascript:void(0);" @click="addMoreFun">点击加载更多</a>
      <a v-show="!hasMore" class="tcolors-bg" href="javascript:void(0);">暂无更多数据</a>
    </el-col>
  </el-row>
</template>

<script>
import {initDate} from '../utils/server.js'
import {articleList} from '../api/article'

export default {
  name: 'Share',
  props: ['search'],
  data() { //选项 / 数据
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        categoryId: 0,
        //检索数据关键词
        q: '',
        wid: '',
        type:0  //该页面作为不同路由的标志(0代表首页,1代表我的文章,2代表其他人的个人主页)
      },
      articleList: [],
      hasMore: true,
      hasLogin:false,

    }
  },

  methods: { //事件处理器
    showInitDate: function (oldDate, full) {
      return initDate(oldDate, full)
    },
    getList() {
      if (this.$route.path == '/HomePage') {
        this.queryParams.wid = (this.$route.query.wid == undefined ? '' : this.$route.query.wid);//获取传参的classId
        this.queryParams.type=2
      }else if (this.$route.path=='/MyArticle'){
        this.queryParams.type=1
      }
      articleList(this.queryParams,this.hasLogin).then((response) => {
        this.articleList = this.articleList.concat(response.rows)
        if (response.total <= this.articleList.length) {
          this.hasMore = false
        } else {
          this.hasMore = true
          this.queryParams.pageNum++
        }
      })
    },
    showSearchShowList: function (initData) {//展示数据
      console.log(this.articleList)
      if (initData) {
        this.articleList = []
      }
      this.getList()
    },
    addMoreFun: function () {//查看更多
      this.showSearchShowList(false);
    },
    routeChange: function () {
      var that = this;
      this.queryParams.categoryId = (that.$route.query.classId == undefined ? 0 : parseInt(that.$route.query.classId));//获取传参的classId
      this.queryParams.q = (that.$route.query.q == undefined ? '' : that.$route.query.q);//获取传参的classId
      this.hasLogin = !!localStorage.getItem("userInfo");
      this.showSearchShowList(true);
    }
  },
  components: { //定义组件

  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'routeChange',
    '$store.state.keywords': 'routeChange'
  },
  created() { //生命周期函数
    // console.log(this.$route);
    var that = this;
    that.routeChange();
  }, mounted() {
    this.bus.$on('is', res => {
      this.queryParams.q = res
      console.log(res) // 输出的是header组件传过来的搜索框中的关键字
      this.showSearchShowList(true);
    })
  }
}
</script>

<style>
/*摘要*/
.summaryP{
  overflow: hidden;
  display:-webkit-box;
  text-overflow: ellipsis;
  -webkit-box-orient:vertical;
  -webkit-line-clamp:2;
}
/*分享标题*/
.shareTitle {
  margin-bottom: 40px;
  position: relative;
  border-radius: 5px;
  background: #fff;
  padding: 15px;
}

.shareclassTwo {
  width: 100%;
}

.shareclassTwo li {
  display: inline-block;
}

.shareclassTwo li a {
  display: inline-block;
  padding: 3px 7px;
  margin: 5px 10px;
  color: #fff;
  border-radius: 4px;
  background: #64609E;
  border: 1px solid #64609E;
  transition: transform 0.2s linear;
  -webkit-transition: transform 0.2s linear;
}

.shareclassTwo li a:hover {
  transform: translate(0, -3px);
  -webkit-transform: translate(0, -3px);
}

.shareclassTwo li a.active {
  background: #fff;
  color: #64609E;

}

/*文章列表*/
.sharelistBox {
  transition: all 0.5s ease-out;
  font-size: 15px;
}


/*.sharelistBox .viewmore a:hover,.s-item .viewdetail a:hover{
    background: #48456C;
}*/
</style>
