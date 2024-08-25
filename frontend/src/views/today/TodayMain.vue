<script setup>
import NewsList from "@/views/today/NewsList.vue";
import MovieList from "@/views/today/MovieRankList.vue";
import WeatherForecast from "@/views/today/WeatherForecast.vue";

</script>

<template>
  <VLayout max-width="100vw" >
    <VCol>
      <VCol cols="12">
        <VRow style="gap: 1rem">
          <h1 class="mb-2">날씨</h1>
          <VTextField variant="plain">기상청 단기예보 조회서비스 정보입니다.</VTextField>
        </VRow>
      </VCol>
      <VRow style="max-width: 1200px;" >
        <WeatherForecast/>
      </VRow>

      <VDivider class="my-16" length="90%"/>

      <VRow>
        <VCol cols="12">
          <h1>뉴스</h1>
        </VCol>
        <NewsList/>
      </VRow>

      <VDivider class="my-16" length="90%"/>

      <VRow>
        <VCol cols="0" lg="2"/>
        <VCol cols="12" lg="10">
          <h1 class="mb-10">일간 박스오피스</h1>
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