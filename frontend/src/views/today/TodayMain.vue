<script setup>
import NewsList from "@/views/today/NewsList.vue";
import MovieList from "@/views/today/MovieRankList.vue";
import WeatherForecast from "@/views/today/WeatherForecast.vue";

</script>

<template>
  <VLayout max-width="100vw">
    <VCol>
      <VCol cols="12">
        <h1 class="mb-2">날씨예보</h1>
      </VCol>
      <VRow style="max-width: 1200px;" >
        <WeatherForecast/>
      </VRow>

      <VDivider class="my-16" length="90%"/>

      <VRow>
        <VCol cols="12">
          <h1>뉴스정보</h1>
        </VCol>
        <NewsList/>
      </VRow>

      <VDivider class="my-16" length="90%"/>

      <VRow>
        <VCol cols="0" lg="2"/>
        <VCol cols="12" lg="10">
          <h1 class="mb-10">일간 박스오피스 순위</h1>
        </VCol>
        <VCol cols="0" lg="2"/>
        <VCol cols="10">
          <MovieList/>
        </VCol>
      </VRow>

    </VCol>
  </VLayout>
</template>

<style scoped>
</style>