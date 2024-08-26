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
  <VLayout class="rounded rounded-md" width="100%">
    <VNavigationDrawer
        expand-on-hover
        rail
        v-model="onMenu"
        color="secondary"
    >

      <VList density="default" >
        <VListItem prepend-icon="mdi-home-circle-outline" title="홈" @click="goPage('today')"></VListItem>
        <VListItem prepend-icon="mdi-help-box-outline" title="퀴즈" @click="goPage('quiz')"></VListItem>
      </VList>
    </VNavigationDrawer>
    <VAppBar color="primary" title="매일 매일 행복하게 꿀잠" height="50">
      <template v-slot:prepend>
        <VDivider class="my-16" length="90%"/>
        <VAppBarNavIcon @click="handleMenu"/>
      </template>
    </VAppBar>

    <VMain class="d-flex align-center justify-center pl-0" width="98vw">
      <VContainer class="px-0">
        <router-view/>
      </VContainer>
    </VMain>
  </VLayout>
</template>

<style scoped>
body{

}
</style>