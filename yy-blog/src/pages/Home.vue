<!-- 首页 -->
<template>
    <div>
      <yy-header :back="back" @isDB="isDB"></yy-header>
        <div class="container">
            <el-row  :gutter="30">
                <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
                    <home-article :search="searchResult"></home-article>
                </el-col>
                <el-col :sm="24"  :md="8" >
                    <homeRight></homeRight>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import header from '../components/header.vue'
import articlelist from '../components/articlelist.vue'
import rightlist from '../components/rightlist.vue'
import homeRight from "../components/homeRight";
import HomeArticle from "../components/homeArticle";
    export default {
        name:'Home',
        data() { //选项 / 数据
            return {
                searchResult:[],
              back:'url(static/img/headback03.png)'
            }
        },
        methods: { //事件处理器
          isDB(searchList){
            this.searchResult=searchList
            // console.log(this.searchResult)
          }
        },
        components: {
          HomeArticle, //定义组件
            'yy-header':header,
            'homearticle':articlelist,
            'homeRight':homeRight,
        },
        created() { //生命周期函数
          this.isDB()

        }
    }
</script>

<style>

</style>
