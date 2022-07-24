<template>
  <div>
    <yy-nav :back="back"></yy-nav>
    <div class="container">
      <el-row  :gutter="30">
        <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
          <discuss-detail></discuss-detail>
          <yy-comment></yy-comment>
        </el-col>
        <el-col :sm="24"  :md="8" >
          <yy-rightlist ></yy-rightlist>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import discussDetail from "../components/detailDiscuss";
import header from "../components/header";
import rightlist from "../components/rightlist";
import message from "../components/message";

export default {
  name: "DiscussDetail",
  components:{
    discussDetail,
    'yy-nav':header,
    'yy-rightlist':rightlist,
    'yy-comment':message
  },
  data(){
    return{
      back:''
    }
  }
}
</script>

<style scoped>

</style>
