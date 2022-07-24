<template>
  <div>
    <div>
      <yy-nav :back="back"></yy-nav>
      <div class="container">
        <el-row  :gutter="30">
          <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
            <yy-discuss></yy-discuss>
          </el-col>
          <el-col :sm="24"  :md="8" >
            <yy-rightlist ></yy-rightlist>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import header from "../components/header";
import friends from "../components/friends";
import rightlist from "../components/rightlist";
import discuss from "../components/discuss";

export default {
  name: "DiscussList",
  components: { //定义组件
    'yy-nav':header,
    'yy-rightlist':rightlist,
    'yy-discuss':discuss,
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
