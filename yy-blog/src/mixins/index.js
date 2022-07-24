import {MessageBox} from "element-ui";
import router from '@/router'

export const mixin = {

  methods: {
    //提示信息
   tip(){
     MessageBox.confirm('你还未登录，您可以继续留在该页面，或者进行登录', '系统提示', {
         confirmButtonText: '前往登录',
         cancelButtonText: '取消',
         type: 'warning'
       }
     ).then(() => {
       localStorage.setItem('logUrl', router.currentRoute.fullPath);
       router.push({
         path: '/Login?login=1'
       });

     }).catch(() => {
       // location.reload()
     })
    },
    notify(message,type){
      this.$message({
        message: message,
        type: type
      });
    }
  }
}
