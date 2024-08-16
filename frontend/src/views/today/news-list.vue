<script setup>
import {getCurrentInstance} from "vue";

const {proxy} = getCurrentInstance();
const {$axios} = proxy;

const newsList = await $axios.get("/news/latest").then(response => response.data);
console.log(newsList);
const goPage = (link) => {
  location.href=link;
}
</script>

<template>
  <v-row>
    <v-col v-for="news in newsList" :key="news.title" cols="12">
      <v-card color="secondary">
        <v-card-item class="cursor-pointer" @click="goPage(news.originallink || news.link)">
          <v-card-title>{{ news.title }}</v-card-title>
          <v-card-subtitle>{{ news.pubDate }}</v-card-subtitle>
          <v-card-text>
            {{ news.description }}
          </v-card-text>
        </v-card-item>
      </v-card>
    </v-col>
  </v-row>
</template>

<style scoped>
.cursor-pointer{
  cursor:pointer;
}
</style>