<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";

const {proxy} = getCurrentInstance();
const {$axios} = proxy;

const goPage = (link) => {
  location.href = link;
}

const newsList = ref([{
  title:"",
  pubDate: "",
  description: "",
  link: "",
  originallink: "",
}]);

const searchNews = () => {
  $axios.get("/news/latest")
      .then(response => {
        newsList.value = response.data
      });
}

onMounted(() => {
  searchNews();
})
</script>

<template>
  <v-card class="ma-4 bg-brown-lighten-2" v-for="news in newsList" :key="news.title" color="secondary" max-width="300px"
          max-height="400px">
    <v-card-item style="height:100%; padding-top: 15px;">
      <v-card-title>{{ news.title }}</v-card-title>
      <v-card-subtitle>{{ news.pubDate }}</v-card-subtitle>
      <v-card-text>
        {{ news.description }}
      </v-card-text>
      <v-card-actions class="float-right">
        <v-btn variant="elevated" text="보러가기" @click="goPage(news.originallink || news.link)"/>
      </v-card-actions>
    </v-card-item>
  </v-card>
</template>

<style scoped>
.cursor-pointer {
  cursor: pointer;
}
</style>