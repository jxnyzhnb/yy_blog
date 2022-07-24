<!-- 友情链接模块 -->
<template>
  <div class="tFriendsBox">
    <el-row>
      <el-col :span="12" class="tf-item" v-for="(item,index) in friendslink" :key="'f'+index">
        <a :href="item.address" target="_blank">
          <img :src="item.logo?item.logo:'static/img/tou.jpg'" :onerror="$store.state.errorImg">
          <h4>{{ item.name }}</h4>
          <p>{{ item.description }}</p>
        </a>
      </el-col>
    </el-row>
    <el-row style="margin-top: 20px">
      <!-- 说明 -->
      <div class="link-title mt-4 mb-4 ">
        添加友链
      </div>
      <blockquote style="margin-top: 20px">
        <div>名称：一鱼的个人博客</div>
        <div>简介：本是同根生，相煎何太急</div>
        <div>链接：https://www.baidu.com</div>
        <div>头像：http://zhnb.yiyu.site/2022/04/27/083c3eb8316d49439cc50f93444dd57e.jpg</div>
      </blockquote>
      <div class="mt-5 mb-5" style="margin-top: 20px">
        需要交换友链的可在下方进行提交💖
      </div>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-form   class="friendsForm" :model="friendsForm" :rules="rules" ref="friendsForm" label-position="left" label-width="60px" >

        <el-form-item  label="名称" prop="name" >
          <el-input v-model="friendsForm.name"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="description">
          <el-input v-model="friendsForm.description"></el-input>
        </el-form-item>
        <el-form-item label="链接" prop="address">
          <el-input v-model="friendsForm.address"></el-input>
        </el-form-item>
        <el-form-item label="logo" prop="logo">
          <el-input v-model="friendsForm.logo"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="margin-left: 280px" type="primary" @click="submitForm('friendsForm')">提交</el-button>
        </el-form-item>
      </el-form>
    </el-row>
  </div>
</template>

<script>
import {addLink, getAllLink} from '../api/link.js'
import {addArticle} from "../api/article";
import {mixin} from "../mixins";

export default {
  mixins:[mixin],
  data() { //选项 / 数据
    return {
      friendslink: [],//友情链接
      friendsForm: {
        name: '',
        description: '',
        address: '',
        logo: '',
      },
      rules: {
        name: {required: true, message: '请输入友链名称', trigger: 'blur'},
        description: {required: true, message: '请输入友链描述', trigger: 'blur'},
        address: {required: true, message: '请输入友链地址', trigger: 'blur'},
        logo: {required: true, message: '请输入友链logo', trigger: 'blur'},
      },
    }
  },
  methods: { //事件处理器
    getList() {
      getAllLink().then((response) => {
        this.friendslink = response
      })
    },
    submitForm(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var obj = {}
          obj['name'] = this.friendsForm.name
          obj['description'] = this.friendsForm.description
          obj['logo'] = this.friendsForm.logo
          obj['address'] = this.friendsForm.address
          var jsonData = JSON.stringify(obj);
          addLink(jsonData).then(response=>{
            this.notify("提交成功,请等待审核！",'success')
            // console.log(response)
            this.friendsForm={}
          })
          return true
        } else {
          return false;
        }
      });
    }
  },
  components: { //定义组件

  },
  created() { //生命周期函数
    this.getList()
  }
}
</script>

<style>
.friendsForm input{
  border-radius: 1%;
}
blockquote {
  line-height: 2;
  margin: 0;
  font-size: 15px;
  border-left: 0.2rem solid #49b1f5;
  padding: 10px 1rem !important;
  background-color: #ecf7fe;
  border-radius: 4px;
}

.link-title {
  color: #344c67;
  font-size: 21px;
  font-weight: bold;
  line-height: 2;
}

.tFriendsBox {
  background: #fff;
  padding: 15px;
  border-radius: 5px;
  margin-bottom: 40px;
}

.tFriendsBox h1 {
  padding: 15px 0;
  font-size: 25px;
  font-weight: bold;
}

.tFriendsBox .tf-item {
  transition: all 0.3s ease-out;
  border-radius: 5px;
  position: relative;
}

.tFriendsBox .tf-item:hover {
  background: rgba(230, 244, 250, .5);
}

.tFriendsBox .tf-item a {
  display: block;
  padding: 0 10px 0 90px;
  height: 90px;
}

.tFriendsBox .tf-item a img {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  position: absolute;
  top: 15px;
  left: 15px;
  cursor: pointer;
  object-fit: cover;
}

.tFriendsBox .tf-item a h4 {
  cursor: pointer;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  font-size: 20px;
  padding-top: 15px;
  font-weight: bold;
}

.tFriendsBox .tf-item a p {
  margin: 10px 0;
  font-size: 12px;
  line-height: 24px;
  color: #999;
  cursor: pointer;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
