<script setup>
import {getCurrentInstance, onBeforeMount} from "vue";

const {proxy} = getCurrentInstance();
const {$axios} = proxy;
onBeforeMount(() => {
  $axios.post("/sample/healthcheck").then((res) => console.log(res));
})

</script>

<template>
  <router-view></router-view>
</template>

<style scoped>
body{

}
</style>