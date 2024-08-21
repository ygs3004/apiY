<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";

const {proxy} = getCurrentInstance();
const {$axios} = proxy;


const movieRankList = ref([{
  audienceChange: "" ,
  audienceChangeRatio: "",
  audienceDayCnt: "",
  audienceTotalCnt: "",
  movieName: "",
  openDate: "",
  rank: "",
  rankChange: "",
  rankDate: "",
  rankOldAndNew: "",
}])


onMounted(() => {
  $axios.get("/movie/rank/yesterday").then(response => {
    movieRankList.value = response.data.map(movieRank => {
      return {
        ...movieRank,
        title: `${movieRank.rank}. ${movieRank.movieName}`,
        subtitle: '개봉일',
      }
    })
    console.log(movieRankList.value)
  });
})
</script>

<template>
  <v-list lines="two" :items="movieRankList" item-props>
    <template v-slot:title="{item}">
      <div>
        {{item.rank}}. {{item.movieName}} {{item.rankOldAndNew}}
        <span v-if="item.rankOldAndNew === 'NEW'" class="text-red text-caption font-weight-bold"> New</span>
        <v-icon v-if="item.rankChange > 0" icon="mdi-arrow-up-bold" size="20"/>
        <v-icon v-if="item.rankChange < 0" icon="mdi-arrow-down-bold" size="20"/>
        <div class="text-caption">(개봉일: {{item.openDate}})</div>

      </div>
    </template>
    <template v-slot:subtitle="{item}">
      <div class="d-flex " style="gap:0.5rem; margin: 5px 0;">
        <span>전일관객수: {{item.audienceDayCnt}}</span>
        <span>누적관객수: {{item.audienceTotalCnt}}</span>
      </div>
      <div class="d-flex" style="gap:0.5rem">
        <span>일일 관객수 변화량: {{item.audienceChange}} ({{item.audienceChangeRatio}}%)</span>
      </div>
    </template>
  </v-list>
</template>

<style scoped>
</style>