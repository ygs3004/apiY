<script setup>
import {getCurrentInstance, onBeforeMount, ref} from "vue";
import router from "@/router/index.js";

const {proxy} = getCurrentInstance();
const {$axios} = proxy;

const count = ref(0);
const increment = () => {
  count.value++;
}

const goPage = (page) => {
  router.push(`/${page}`)
}

console.log(navigator.userAgent)
const initOnMenu = !/Mobi|Android|iPhone|iPad|iPod/i.test(navigator.userAgent);

const onMenu = ref(initOnMenu);
const handleMenu = () => {
  onMenu.value = !onMenu.value
}

onBeforeMount(() => {
  increment()
  $axios.post("/sample/healthcheck").then((res) => console.log(res));
})
</script>

<template>
  <v-layout class="rounded rounded-md">

    <v-navigation-drawer
        expand-on-hover
        rail
        v-model="onMenu"
        color="secondary"
    >

      <v-list density="default" >
        <v-list-item prepend-icon="mdi-calendar" title="오늘 하루" @click="goPage('today')"></v-list-item>
        <v-list-item prepend-icon="mdi-pencil" title="자유게시판" @click="goPage('board')"></v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar color="primary" title="매일 매일 행복하게 꿀잠자는 하루되기">
      <template v-slot:prepend>
        <VDivider class="my-16" length="90%"/>
        <v-app-bar-nav-icon @click="handleMenu"/>
      </template>
    </v-app-bar>

    <v-main class="d-flex align-center justify-center" min-height="100vh" min-width="100vw">
      <v-container>
        <router-view/>
      </v-container>
    </v-main>
  </v-layout>
</template>

<style scoped>
</style>