<!-- 友情链接 -->
<template>
    <div style="background:url('../../static/img/creationBack.png')">
        <sg-nav :back="back"></sg-nav>
        <div class="container" >
            <el-row  :gutter="60">
                <el-col :sm="60" :md="60" style="transition:all .5s ease-out;margin-bottom:30px;">
                    <create-article ></create-article>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import header from '../components/header.vue'
import rightlist from '../components/rightlist.vue'
import message from '../components/message.vue'
import CreateArticle from "../components/createArticle";
    export default {
        name: 'addArticle',
      data() { //选项 / 数据
            return {
              back:'url(static/img/headback05.png)'
            }
        },
        methods: { //事件处理器

        },
        components: {
          CreateArticle, //定义组件
            'sg-nav':header,
            'sg-message':message,
            'create-article':CreateArticle,
            'sg-rightlist':rightlist,

        },
        created() { //生命周期函数

        }
    }
</script>

<style>

</style>
