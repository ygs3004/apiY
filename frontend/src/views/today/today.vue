<script setup>
import NewsList from "@/views/today/news-list.vue";
import TestList from "@/views/today/test-list.vue";

</script>

<template>
  <v-col cols="12" md="3">
    <v-row>
      <h1>테스트</h1>
      <NewsList/>
      <test-list/>
    </v-row>
  </v-col>
</template>

<style scoped>
</style>