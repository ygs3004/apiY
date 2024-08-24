<script setup>
import {computed, getCurrentInstance, onMounted, ref} from "vue";

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

const item1 = computed(() => {
  return movieRankList.value.slice(0, 5)
});

const item2 = computed(() => {
  return movieRankList.value.slice(5)
});


onMounted(() => {
  $axios.get("/movie/rank/yesterday").then(response => {
    movieRankList.value = response.data.map(movieRank => {
      return {
        ...movieRank,
        title: `${movieRank.rank}. ${movieRank.movieName}`,
        subtitle: '개봉일',
      }
    })
  });
})
</script>

<template>
  <VRow>
    <VCol cols="12" lg="4" class="py-0">
      <v-list lines="three" :items="item1" item-props variant="text" bg-color="pastel">
        <template v-slot:title="{item}">
          <div class="font-weight-bold">
            {{item.rank}}. {{item.movieName}}
            <span v-if="item.rankOldAndNew === 'NEW'" class="text-red text-caption font-weight-bold"> New</span>
            <VIcon v-if="item.rankChange > 0" icon="mdi-arrow-up-bold" size="20"/>
            <VIcon v-if="item.rankChange < 0" icon="mdi-arrow-down-bold" size="20"/>
            <div class="text-caption">(개봉일: {{item.openDate}})</div>
          </div>
        </template>
        <template v-slot:subtitle="{item}">
          <div class="d-flex" style="gap:0.5rem; margin: 5px 0;">
            <span>일일관객수: {{item.audienceDayCnt}}</span>
            <span>누적관객수: {{item.audienceTotalCnt}}</span>
          </div>
          <div class="d-flex" style="gap:0.5rem">
            <span>일일 관객수 변화량: {{item.audienceChange}} ({{item.audienceChangeRatio}}%)</span>
          </div>
        </template>
      </v-list>
    </VCol>
    <VCol cols="12" lg="4" class="py-0">
      <v-list lines="three" :items="item2" item-props  variant="text" bg-color="pastel">
        <template v-slot:title="{item}">
          <div class="font-weight-bold">
            {{item.rank}}. {{item.movieName}}
            <span v-if="item.rankOldAndNew === 'NEW'" class="text-red text-caption font-weight-bold"> New</span>
            <VIcon v-if="item.rankChange > 0" icon="mdi-arrow-up-bold" size="20"/>
            <VIcon v-if="item.rankChange < 0" icon="mdi-arrow-down-bold" size="20"/>
            <div class="text-caption">(개봉일: {{item.openDate}})</div>
          </div>
        </template>
        <template v-slot:subtitle="{item}">
          <div class="d-flex " style="gap:0.5rem; margin: 5px 0;">
            <span>일일관객수: {{item.audienceDayCnt}}</span>
            <span>누적관객수: {{item.audienceTotalCnt}}</span>
          </div>
          <div class="d-flex" style="gap:0.5rem">
            <span>일일 관객수 변화량: {{item.audienceChange}} ({{item.audienceChangeRatio}}%)</span>
          </div>
        </template>
      </v-list>
    </VCol>
  </VRow>

</template>

<style scoped>
</style>