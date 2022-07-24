<!-- 文章详情 -->
<template>
    <div>
        <yy-nav :back="back"></yy-nav>
        <div  class="container" id="detail">
            <el-row  :gutter="30">
                <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:0;">
                    <articleDetail @thumbnail="getThumbnail"></articleDetail>
                    <message></message>
                </el-col>
                <el-col :sm="24"  :md="8" >
                    <rightlist></rightlist>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import header from '../components/header.vue'
import rightlist from '../components/rightlist.vue'
import articleDetail from '../components/articleDetail.vue'
import message from '../components/message.vue'
    export default {
        name:'DetailShare',
        data() { //选项 / 数据
            return {
              // back:'url(static/img/headback04.png)'
              back:''
            }
        },
        methods: { //事件处理器
          getThumbnail(thumbnail){
            this.back="url("+thumbnail+")"
          }
        },
        components: {//定义组件
            'yy-nav':header,
            'articleDetail':articleDetail,
            'message':message,
            'rightlist':rightlist,
        },
        created() { //生命周期函数

        },
        mounted(){
            var anchor = document.querySelector("#detail");
            // console.log(anchor,anchor.offsetTop);
            var top = anchor.offsetTop-60;
            document.body.scrollTop = top;
             // Firefox
             document.documentElement.scrollTop = top;
             // Safari
             window.pageYOffset = top;
        }
    }
</script>

<style>
</style>
