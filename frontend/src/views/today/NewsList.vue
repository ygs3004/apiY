<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";

const {proxy} = getCurrentInstance();
const {$axios} = proxy;

const goPage = (link) => {
  location.href = link;
}

const newsList = ref([{
  title:"",
  pubDate: "",
  description: "",
  link: "",
  originallink: "",
}]);

const searchNews = () => {
  $axios.get("/news/latest")
      .then(response => {
        newsList.value = response.data
      });
}

onMounted(() => {
  searchNews();
})
</script>

<template>
  <VCard class="mx-auto mx-lg-2 my-4 bg-brown-lighten-2" v-for="newsResponse in newsList" :key="newsResponse.title" color="secondary"
         max-width="300px"
         max-height="400px">
    <VCardItem style="height:100%; padding-top: 15px;">
      <VCardTitle>{{ newsResponse.title }}</VCardTitle>
      <VCardSubtitle>{{ newsResponse.pubDate }}</VCardSubtitle>
      <VCardText>
        {{ newsResponse.description }}
      </VCardText>
      <VCardActions class="float-right">
        <VBtn variant="elevated" text="보러가기" @click="goPage(newsResponse.originallink || newsResponse.link)"/>
      </VCardActions>
    </VCardItem>
  </VCard>
</template>