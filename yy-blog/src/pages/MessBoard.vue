<template>
  <div  style="">
    <Header></Header>
    <messboard></messboard>
  </div>

</template>

<script>
import Header from "../components/header";
import messboard from "../components/messboard";
export default {
  name: "MessBoard",
  components: {Header
  ,messboard}
}
</script>

<style scoped>

</style>
