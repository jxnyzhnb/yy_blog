<template>
  <div class="create">
    <div class="indexContainer" style="width: 100%;">

      <div class="editorContainer" style="width: 100%;background-color: #fdfdfd">
        <div class="article-title-container">
          <el-input
            v-model="ruleForm.title"
            size="medium"
            placeholder="输入文章标题"
            style="margin-left: 55px;border-radius: 25%;width: 70%"
          />
          <el-button
            type="danger"
            size="medium"
            class="save-btn">
            <!--          @click="saveArticleDraft"
                      v-if="article.isDraft != 0"-->
            保存草稿
          </el-button>
          <el-button
            type="danger"
            size="medium"
            @click="publish"
            style="margin-left:10px">
            发布文章
          </el-button>
        </div>
<!--          @imgAdd="uploadImg"-->
        <mavon-editor
          ref="md"
          v-model="msg.mdValue"
          style="height:calc(100vh - 60px)"
        />
      </div>
    </div>
    <el-dialog title="发布文章" :visible.sync="publishVisible" width="700px" center>
      <el-form style="margin: 0" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
               class="demo-ruleForm">
        <el-form-item label="摘要" prop="summary">
          <el-input v-model="ruleForm.summary"></el-input>
        </el-form-item>
        <el-form-item label="分类">
          <div>
            <el-tag
              closable
              :disable-transitions="false" v-if="categoryName!=''" @close="categoryClose()">
              {{ categoryName }}
            </el-tag>
            <el-input
              style="width: 100px"
              class="input-new-tag"
              v-if="categoryVisible"
              v-model="categoryValue"
              ref="saveCategoryInput"
              size="small"
              @keyup.enter.native="categoryInputConfirm"
              @blur="categoryInputConfirm">
            </el-input>
            <el-button v-if="categoryName==''&&!categoryVisible" class="button-new-tag" size="small"
                       @click="showCategoryInput">+ 新建分类
            </el-button>
            <div v-if="categoryList.length>0" style="border:1px solid gainsboro;width: 250px;margin-left: 10px">
              <el-radio-group v-model="radio" @change="getCategoryId()">
                <el-radio style="margin-left: 5px" v-for="(item,index) in categoryList" :key="index" :label="item.id"
                          @change="getCategoryName(index)">{{ item.name }}
                </el-radio>
              </el-radio-group>
            </div>
            <p v-if="isSelect" style="font-size: 12px;color: red">请为文章选择分类！</p>
          </div>
        </el-form-item>
        <el-form-item label="标签" prop="tags">
          <div>
            <el-select
              v-model="tagsIds"
              multiple
              filterable
              allow-create
              default-first-option
              placeholder="请输入或选择文章标签"
              @change="selectTags()"

            >
              <el-option
                v-bind:disabled="gtFive"
                v-for="item in options"
                :key="item.id"
                :label="item.tagName"
                :value="item.id">
              </el-option>
            </el-select>
            <div v-if="gtFive" style="font-size: 12px;color: red">标签不能超过5个！</div>
          </div>
        </el-form-item>
        <el-form-item label="封面" prop="picture">
          <el-upload
            class="avatar-uploader"
            name="img"
            :action="uploadURL"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="ruleForm.thumbnail" :src="ruleForm.thumbnail?ruleForm.thumbnail:'static/img/tou.jpg'"
                 :onerror="$store.state.errorImg" class="avatar">
            <i style="background-color: white;" v-else class="el-icon-plus avatar-uploader-icon"></i>
            <div slot="tip" class="el-upload__tip" style="margin-top: 0">点击上传文章图片，只能上传jpg/png文件，且不超过1mb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="是否置顶" prop="isTop">
          <el-radio v-model="ruleForm.isTop" :label="1">是</el-radio>
          <el-radio v-model="ruleForm.isTop" :label="0">否</el-radio>
        </el-form-item>
      </el-form>
      <span slot="footer">
                <el-button type="primary" size="mini" @click="save('ruleForm')">发布</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
import {VueEditor} from "vue2-editor";
import store from "../store";
// import markdown from '../components/markdown'
import {getCategories, getCategoryList} from "../api/category";
import {addArticle} from "../api/article";
import {getTagsList} from "../api/tags";


export default {
  name: 'createArticle',
  components: {
    // markdown,
    VueEditor
  },
  created() {
    this.uploadURL = store.state.baseURL + 'upload'
    this.getCategoryList()
    this.getTagsList()
  },
  data() {
    return {
      ruleForm: {
        title: '',
        categoryId: '',
        isTop: 0,
        thumbnail: '',
        summary: '',
      },

      categoryList: [],
      rules: {
        title: {required: true, message: '请输入标题', trigger: 'blur'},
        summary: {required: true, message: '请输入摘要', trigger: 'blur'},
      },
      categoryName: '',
      categoryId: '',
      isSelect:false, //是否选择分类标签,没选择就显示提示
      categoryVisible: false,
      categoryValue: '',
      radio: '',
      htmlForEditor: '',
      uploadURL: '',
      msgShow: '我要显示的内容',
      dilogStatus: false,
      msg: {
        mdValue: ''
      },
      publishVisible: false,
      //判断标签是否大于5个
      gtFive: false,

      //存储标签id
      tagsIds: [],
      inputVisible: false,
      //标签数据
      options: []

    }
  },

  methods: {
    selectTags(){
      if (this.tagsIds.length>=5){
        this.gtFive=true
      }else {
        this.gtFive=false
      }

    },
    getCategoryName(index) {
      console.log(this.categoryList[index].name)
      this.categoryName = this.categoryList[index].name

    },
    categoryClose(tag) {
      this.categoryName = ''
      this.radio = 0
    },
    getCategoryId() {
      this.categoryId = this.radio
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
      this.categoryName = this.categoryValue
      this.categoryVisible = false;
      this.categoryValue = '';
    },

    handleChange(value) {
      var tagsId = value[value.length - 1]
      this.tagsIds.push(tagsId);
      var checkLabel = this.$refs['cascaderItem'].getCheckedNodes()
      this.value = checkLabel[0].data.tagName
      this.handleInputConfirm()
      console.log(this.tagsIds)
    },
    showInput() {
      this.inputVisible = true;

      // this.$refs.saveTagInput.focus();
    },
    childEventHandler: function (res) {
      // res会传回一个data,包含属性content和htmlValue，具体含义请自行翻译
      this.msg = res;
    },
    closeMaskFn: function () {
      this.msgShow = '';
      this.dilogStatus = false;
    },
    publish() {
      if (this.ruleForm.title==''){
        this.notify('你还未写文章标题！！','warning')
        return false
      }
      if (this.msg.mdValue == '') {
        this.notify('你还未写文章内容！！','warning')
        return false
      } else {
        this.publishVisible = true
      }
    },
    getTagsList() {
      getTagsList().then(response => {
        this.options = response
      })
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.tagsIds.length <= 0 || this.tagsIds.length > 5) {
            this.gtFive = true
            return
          }
          if (this.categoryName == '') {
            this.isSelect=true
            return
          }
          var obj = {}
          obj['title'] = this.ruleForm.title
          obj['categoryId'] = this.categoryId
          obj['categoryName'] = this.categoryName
          obj['thumbnail'] = this.ruleForm.thumbnail
          obj['isTop'] = this.ruleForm.isTop
          obj['summary'] = this.ruleForm.summary
          obj['content'] = this.msg.mdValue
          obj['tagsIds'] = this.tagsIds
          var jsonData = JSON.stringify(obj);
          addArticle(jsonData).then(response => {
            var _this = this
            console.log(response)
            _this.$message.success('发布成功');
            this.$router.push({path: '/DetailArticle?aid=' + response});

          })
          return true
        } else {
          return false;
        }
      });
    },
    getCategoryList() {
      getCategories().then((response) => {
        this.categoryList = response
      })
    },
    handleAvatarSuccess(res, file) {
      if (res.code == 200) {
        this.ruleForm.thumbnail = res.data;
      } else {
        this.$message.error('上传图片失败');
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
  }
}
</script>

<style lang="less" >
.article-title-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
  margin-top: 2.25rem;
}
.save-btn {
  margin-left: 0.75rem;
  background: #fff;
  color: #f56c6c;
}
.create {
  height: 100%;
  //overflow-y: scroll;
  .demo-ruleForm {
    margin-top: 20px;
    .el-input,
    .el-select {
      width: 500px;
    }
  }

  .editor {
    width: 95%;
    margin: auto;

    #editor {
      height: 500px;
    }
  }

  .save {
    margin-top: 40px;
    margin-bottom: 40px;
    width: 80px;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}
</style>
<style lang="scss" scoped>
.show {
  position: absolute;
  left: 0;
  top: 0;
}

.indexContainer {
  width: 100%;
  height: 100%;
  //background: #ddd;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.btnsContainer {
  position: absolute;
  z-index: 10;
  left: 65px;
  top: 5px;
  height: 25px;
  min-width: 300px;
  // background: pink;
  display: flex;
  justify-content: flex-start;
  align-items: center;

  /*.btn {
    display: inline-block;
    border: 1px solid #ccc;
    margin-right: 10px;
    box-sizing: border-box;
    padding: 0 10px;
    background: #fff;
    font-size: 12px;
    height: 25px;
    line-height: 25px;
    cursor: pointer;
    moz-user-select: -moz-none;
    -moz-user-select: none;
    -o-user-select: none;
    -khtml-user-select: none; !* you could also put this in a class *!
    -webkit-user-select: none; !* and add the CSS class here instead *!
    -ms-user-select: none;
    user-select: none; !**禁止选中文字*!
    &:active {
      opacity: 0.8;
      background: lightblue;
    }
  }*/
}

.maskContainer {
  position: absolute;
  left: 0;
  top: 0;
  height: 100%;
  width: 100%;
  height: 100vh;
  width: 100vw;
  background: gray;
  // z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;

  .contentContainer {
    width: 60%;
    height: 60%;
    background: #fefefe;
    padding: 20px;
    box-sizing: border-box;
    position: relative;

    .showAreaContainer {
      height: 100%;
      width: 100%;
      outline: none;
      background: #eee;
      display: block;
      resize: none;
      padding: 10px;
      box-sizing: border-box;
    }

    .closeBtnContainer {
      position: absolute;
      height: 30px;
      width: 30px;
      right: -40px;
      top: -40px;
      border: 1px solid #fff;
      border-radius: 50%;

      &::before {
        content: '';
        position: absolute;
        width: 70%;
        height: 2px;
        display: block;
        background: #fff;
        left: 15%;
        top: calc(50% - 1px);
        transform: rotate(45deg);
        -webkit-transform: rotate(45deg);
        -moz-transform: rotate(45deg);
      }

      &::after {
        content: '';
        position: absolute;
        width: 70%;
        height: 2px;
        display: block;
        background: #fff;
        left: 15%;
        top: calc(50% - 1px);
        transform: rotate(-45deg);
        -webkit-transform: rotate(-45deg);
        -moz-transform: rotate(-45deg);
      }
    }

  }

}

.editorContainer {
  width: 90%;
  height: 90%;
  border: 1px solid #ddd;
  border-radius: 1%;
}

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
<style>
.el-cascader input {
  width: 90px !important;
}
</style>
