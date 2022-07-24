<template>
  <div>
    <el-tag
      closable
      :disable-transitions="false" v-if="categoryName!=''"  @close="categoryClose()">
      {{ categoryName }}
    </el-tag>
    <el-input
      class="input-new-tag"
      v-if="categoryVisible"
      v-model="categoryValue"
      ref="saveCategoryInput"
      size="small"
      @keyup.enter.native="categoryInputConfirm"
      @blur="categoryInputConfirm">
    </el-input>
    <el-button v-else  v-if="categoryName==''" class="button-new-tag" size="small" @click="showCategoryInput">+ 新建分类
    </el-button>
    <div style="border:1px solid gainsboro;width: 250px">
      <el-radio-group v-model="radio" @change="getCategoryId()">
        <el-radio v-for="(item,index) in items" :key="index" :label="item.id" @change="getCategoryName(index)">{{ item.name }}
        </el-radio>

      </el-radio-group>
    </div>

  </div>

</template>
<script>
export default {
  data() {
    return {
      categoryName:'',
      categoryVisible: false,
      categoryValue: '',
      radio: '',
      select: false,
      items: [{
        id: 1,
        name: 'dsdsd'
      }, {
        id: 2,
        name: 'dsdsd1'
      }]
    };
  },
  methods: {
    getCategoryName(index) {
      console.log(this.items[index].name)
      this.categoryName=this.items[index].name

    },
    categoryClose(tag) {
      this.categoryName=''
      this.radio=0
    },
    getCategoryId() {
      console.log(this.radio);
    },
    showCategoryInput() {
      this.categoryVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveCategoryInput.$refs.input.focus();
      });
    },

    categoryInputConfirm() {
      let categoryValue = this.categoryValue;
      this.categoryName=this.categoryValue
      this.categoryVisible = false;
      this.categoryValue = '';
    }
  }
}
</script>


<style>
.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>

}
