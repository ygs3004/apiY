<script setup>
import NewsList from "@/views/today/news-list.vue";

</script>

<template>
  <v-layout max-width="100vw">
    <v-col>
      <v-row>
        <h1>뉴스정보</h1>
      </v-row>
      <v-row>
        <news-list/>
      </v-row>
    </v-col>
  </v-layout>
</template>

<style scoped>
</style>