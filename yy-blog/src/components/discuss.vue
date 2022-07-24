<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="发帖子" name="pulish" v-if="hasLogin">
        <div class="editorContainer" style="width: 100%;background-color: #fdfdfd">
          <div class="article-title-container">
            &nbsp;&nbsp;文章标题:
            <el-input
              v-model="disscussForm.title"
              size="medium"
              placeholder="一句话说明你遇到的问题或分享的经验"
              style="margin-left: 15px;border-radius: 25%;width: 70%"
            />
            <el-button
              type="danger"
              size="medium"
              @click="publish"
              style="margin-left:10px">
              发布帖子
            </el-button>
          </div>
          <!--          @imgAdd="uploadImg"-->
          <mavon-editor
            ref="md"
            v-model="disscussForm.content"
            style="height:calc(110vh - 60px)"
          />
        </div>
      </el-tab-pane>
      <el-tab-pane label="最新发布" name="newDiscuss">
        <section v-for="item in discussList" style="height: 140px">
          <div>
            <a :href="'/HomePage?wid='+item.userId">
              <span><img class="discussImg" :src="item.avatar" alt=""></span>
              <span style="font-size: 18px">{{ item.nickName }}</span>
            </a>
            <span style="color: #a8a7a7;float:right;;">{{ item.createTime }}</span>
          </div>
          <div class="dis-title-div">
            <a :href="'/DiscussDetail?did='+item.id" class="dis-title">{{ item.title }}</a>
          </div>
          <div class="dis-content" style="font-size: 15px;font-weight: lighter;color: #a8a7a7;margin-top: 12px">
            {{ item.content }}
          </div>
          <div style="margin-left: 75%;position: absolute;bottom: 15px">
            <span style="color: #a8a7a7;margin-right: 20px"><i class="fa fa-eye" aria-hidden="true"></i><span style="margin-left: 5px">{{item.viewCount}}</span></span>
            <span style="color: #a8a7a7;margin-right: 20px"><i class="fa fa-commenting-o" aria-hidden="true"></i><span style="margin-left: 5px">{{item.commentCount}}</span></span>
            <span style="color: #a8a7a7"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i><span style="margin-left: 5px">{{item.likeCount}}</span></span>
          </div>
        </section>
        <el-col class="viewmore">
          <a v-show="hasMore" class="tcolors-bg" href="javascript:void(0);" @click="addMoreFun(0)">点击加载更多</a>
          <a v-show="!hasMore" class="tcolors-bg" href="javascript:void(0);">暂无更多数据</a>
        </el-col>
      </el-tab-pane>
      <el-tab-pane label="最新回复" name="newReply">
        <section v-for="item in discussList" style="height: 140px">
          <div>
            <a :href="'/HomePage?wid='+item.userId">
              <span><img class="discussImg" :src="item.avatar" alt=""></span>
              <span style="font-size: 18px">{{ item.nickName }}</span>
            </a>
<!--            <span>{{ item.newReplyTime }}</span>-->
            <span style="color: #a8a7a7;margin-left: 10px">{{item.recentDisplayTime}}</span>
            <span style="color: #a8a7a7;float:right;;">{{ item.createTime }}</span>
          </div>
          <div class="dis-title-div">
            <a :href="'/DiscussDetail?did='+item.id" class="dis-title" >{{ item.title }}</a>
          </div>
          <div class="dis-content" style="font-size: 15px;font-weight: lighter;color: #a8a7a7;margin-top: 12px">
            {{ item.content }}
          </div>
          <div style="margin-left: 75%;position: absolute;bottom: 15px">
            <span style="color: #a8a7a7;margin-right: 20px"><i class="fa fa-eye" aria-hidden="true"></i><span style="margin-left: 5px">{{item.viewCount}}</span></span>
            <span style="color: #a8a7a7;margin-right: 20px"><i class="fa fa-commenting-o" aria-hidden="true"></i><span style="margin-left: 5px">{{item.commentCount}}</span></span>
            <span style="color: #a8a7a7"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i><span style="margin-left: 5px">{{item.likeCount}}</span></span>
          </div>
        </section>
        <el-col class="viewmore">
          <a v-show="hasMore" class="tcolors-bg" href="javascript:void(0);" @click="addMoreFun(1)">点击加载更多</a>
          <a v-show="!hasMore" class="tcolors-bg" href="javascript:void(0);">暂无更多数据</a>
        </el-col>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
import {addDiscuss, getDiscuss} from "../api/discuss";
import {mixin} from "../mixins";

export default {
  mixins: [mixin],
  name: "discuss",
  data() {
    return {
      activeName: "pulish",
      hasLogin: false,
      disscussForm: {
        content: '',
        title: ''
      },
      discussList: [],
      hasMore: true,
      discussParams: {
        pageNum: 1,
        pageSize: 5,
        type: ''
      }
    }
  },
  created() {
    var userInfo = localStorage.getItem("userInfo");
    this.hasLogin = !!userInfo;
    this.activeName=this.hasLogin?'pulish':'newDiscuss'
    if (this.activeName='newDiscuss'){
      this.getNewPulish()
    }
  },
  methods: {
    addMoreFun(type) {
      if (type === 0) {
        this.getNewPulish()
      } else if (type === 1) {
        this.getNewReply()
      }
    },
    handleClick(tab, event) {
      this.discussList = []
      this.discussParams.pageNum = 1
      this.hasMore = false
      if (tab.name != 'pulish') {
        if (tab.name == 'newDiscuss') {
          this.getNewPulish()
        } else if (tab.name = 'newReply') {
          this.getNewReply()
        }
      } else {
        if (!this.hasLogin) {
          this.tip()
        }
      }
      // console.log(tab.name);
    },
    getNewPulish() {
      this.discussParams.type = 0
      getDiscuss(this.discussParams).then(response => {
        this.discussList = this.discussList.concat(response.rows)
        if (response.total <= this.discussList.length) {
          this.hasMore = false
        } else {
          this.hasMore = true
          this.discussParams.pageNum++
        }
      })
    },
    getNewReply() {
      this.discussParams.type = 1
      getDiscuss(this.discussParams).then(response => {
        this.discussList = this.discussList.concat(response.rows)
        if (response.total <= this.discussList.length) {
          this.hasMore = false
        } else {
          this.hasMore = true
          this.discussParams.pageNum++
        }
      })
    },
    publish() {
      if (this.disscussForm.title == '') {
        this.notify('你还未写帖子标题！！', 'warning')
        return false
      }
      if (this.disscussForm.content == '') {
        this.notify('你还未写帖子内容！', 'warning')
        return false
      }
      addDiscuss(this.disscussForm).then(response => {
        this.notify('发布成功！', 'success')
        this.$router.push("/DiscussDetail?did=" + response)
      })
    }
  }
}
</script>

<style scoped>
.dis-title-div{
  font-weight: bold;
  font-size: 19px;
  margin-top: 15px;
  width: 80%;

  word-break: break-all;

  text-overflow: ellipsis;

  display: -webkit-box;

  -webkit-box-orient: vertical;

  -webkit-line-clamp: 1; /* 这里是超出几行省略 */

  overflow: hidden;
}
.dis-title:hover{
  color: #2997d2;
}
.dis-content{
  width: 100%;

  word-break: break-all;

  text-overflow: ellipsis;

  display: -webkit-box;

  -webkit-box-orient: vertical;

  -webkit-line-clamp: 2; /* 这里是超出几行省略 */

  overflow: hidden;
}
.discussImg{
  width: 25px;
  height: 25px;
  border-radius: 50%;
  float: left;
  transition: all .4s ease-in-out;
  -webkit-transition: all .4s ease-in-out;
  margin-right: 15px;
  object-fit: cover;
}
.editorContainer {
  width: 90%;
  height: 90%;
  border: 1px solid #ddd;
  border-radius: 1%;
}

.article-title-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
  margin-top: 2.25rem;
}

section {
  transition: all 0.2s linear;
  position: relative;
  background: #fff;
  padding: 15px;
  margin-bottom: 20px;
  border-radius: 5px;
}

section:hover {
  transform: translate(0, -2px);
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
}
</style>
