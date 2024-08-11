<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";


const {proxy} = getCurrentInstance();
const {$axios} = proxy;

const count = ref(0);
const increment = () => {
  count.value++;
}
onMounted(() => {
  increment()
  $axios.post("/sample/healthcheck").then((res) => console.log(res));
})
</script>

<template>
  <v-layout class="rounded rounded-md">
    <v-app-bar color="secondary" title="매일 매일 행복하게 꿀잠자는 하루되기"></v-app-bar>

    <v-navigation-drawer
        expand-on-hover
        rail
    >
      <v-list>
        <v-list-item prepend-icon="mdi-menu" title="전체 메뉴" value="" ></v-list-item>
      </v-list>

      <v-divider></v-divider>

      <v-list density="compact" nav>
        <v-list-item prepend-icon="mdi-calendar" title="오늘 하루"></v-list-item>
        <v-list-item prepend-icon="mdi-pencil" title="자유게시판"></v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main class="d-flex align-center justify-center" style="min-height: 300px;">
      Main Content
    </v-main>
  </v-layout>
</template>

<style scoped>
</style>