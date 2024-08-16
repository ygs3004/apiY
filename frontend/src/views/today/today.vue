<script setup>
import NewsList from "@/views/today/news-list.vue";

</script>

<template>
  <v-col cols="12" md="3">
    <v-row>
      <news-list/>
    </v-row>
  </v-col>
</template>

<style scoped>
.today-container{
  width:100vw;
  height: 100vh;
}
</style>