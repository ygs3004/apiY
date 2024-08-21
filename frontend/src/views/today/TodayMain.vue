<script setup>
import NewsList from "@/views/today/NewsList.vue";
import MovieList from "@/views/today/MovieRankList.vue";

</script>

<template>
  <VLayout max-width="100vw">
    <VCol>
      <VRow>
        <VCol cols="12" md="6">
          <h1>일간 박스오피스 순위</h1>
          <MovieList/>
        </VCol>
<!--        <VCol cols="12" md="6">-->
<!--          <h1>일간 박스오피스 순위</h1>-->
<!--        </VCol>-->
      </VRow>

      <VDivider class="my-16" length="90%"/>

      <VRow>
        <v-col cols="12">
          <h1>뉴스정보</h1>
        </v-col>
        <news-list/>
      </VRow>
    </VCol>
  </VLayout>
</template>

<style scoped>
</style>