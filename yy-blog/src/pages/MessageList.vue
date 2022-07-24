<template>
  <div>
    <sg-navbar :back="back"></sg-navbar>
    <div class="container">
      <el-row  :gutter="30">
        <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
          <notice></notice>
        </el-col>
        <el-col :sm="24"  :md="8" >
          <sg-rightlist></sg-rightlist>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import Notice from "../components/notice";
import header from "../components/header";
import articlelist from "../components/articlelist";
import rightlist from "../components/rightlist";
export default {
  name: "MessageList",
  data(){
    return{
      back:'url(static/img/headback06.png)'
    }
  },
  components:{
    'notice':Notice,
    'sg-navbar':header,
    'sg-articlelist':articlelist,
    'sg-rightlist':rightlist,
  },methods:{

  }
}
</script>

<style scoped>

</style>
