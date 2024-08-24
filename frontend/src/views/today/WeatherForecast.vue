<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";

const {proxy} = getCurrentInstance();
const {$axios} = proxy;

const weatherForecast = ref([{
}]);

const searchWherForecast = () => {
  $axios.get("/news/latest")
      .then(response => {
        weatherForecast.value = response.data
      });
}

onMounted(() => {
  searchWherForecast();
})
</script>

<template>
  <h1>

  </h1>
</template>

<style scoped>
</style>