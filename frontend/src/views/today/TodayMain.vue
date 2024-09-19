<script setup>
import NewsList from "@/views/today/NewsList.vue";
import MovieList from "@/views/today/MovieRankList.vue";
import WeatherForecast from "@/views/today/WeatherForecast.vue";

</script>

<template>
  <VLayout>
    <VCol cols="12">
      <VRow style="gap: 1rem">
        <span class="text-h3 my-3">날씨<span class="ml-5 text-subtitle-1">기상청 단기예보 조회서비스 정보입니다.</span></span>
      </VRow>
      <VRow style="max-width: 1200px;">
        <WeatherForecast/>
      </VRow>

      <VDivider class="my-16" length="90%"/>

      <VRow>
        <VCol cols="12">
          <div class="text-h3 my-3">뉴스</div>
        </VCol>
        <NewsList/>
      </VRow>

      <VDivider class="my-16" length="90%"/>

      <VRow>
        <VCol cols="12" lg="10">
          <div class="text-h3 my-3">일간 박스오피스</div>
        </VCol>
        <VCol cols="12" offset-lg="">
          <MovieList/>
        </VCol>
      </VRow>
    </VCol>
  </VLayout>
</template>

<style scoped>
</style>