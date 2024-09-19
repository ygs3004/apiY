<script setup>
import {computed, getCurrentInstance, ref} from "vue";
import {useRouter} from "vue-router";

const {proxy} = getCurrentInstance();
const {$forceUpdate} = proxy;
const router = useRouter();

const goPage = (page) => {
  router.push(`/${page}`)
}

const initOnMenu = !/Mobi|Android|iPhone|iPad|iPod/i.test(navigator.userAgent);

const onMenu = ref(initOnMenu);
const handleMenu = () => {
  onMenu.value = !onMenu.value
}

const isLogin = ref(!!localStorage.getItem("token"));

const login = () => {
  isLogin.value = true;
}

const goLoginPage = () => {
  router.push('/login')
}

const logout = () => {
  localStorage.removeItem("token");
  isLogin.value = false;
  goLoginPage();
}

</script>

<template>
  <VLayout class="rounded rounded-md" width="100%">
    <VNavigationDrawer
        expand-on-hover
        rail
        v-model="onMenu"
        color="secondary"
    >

      <VList density="default">
        <VListItem prepend-icon="mdi-home-circle-outline" title="홈" @click="goPage('today')"></VListItem>
        <VListItem prepend-icon="mdi-help-box-outline" title="퀴즈" @click="goPage('quiz')"></VListItem>
      </VList>
    </VNavigationDrawer>
    <VAppBar color="primary" title="꿀잠" height="50">
      <template v-slot:prepend>
        <VDivider class="my-16" length="90%"/>
        <VAppBarNavIcon @click="handleMenu"/>
      </template>
      <template v-slot:append>
        <VRow v-if="!isLogin" class="cursor-pointer" align="center" @click="goLoginPage">
          <VAppBarTitle text="로그인"/>
          <VIcon class="mr-5 ml-2" icon="mdi-login"/>
        </VRow>
        <VRow v-else class="cursor-pointer" align="center" @click="logout">
          <VAppBarTitle text="로그아웃"/>
          <VIcon class="mr-5 ml-2" icon="mdi-logout"/>
        </VRow>
      </template>
    </VAppBar>

    <VMain class="d-flex align-center justify-center" width="100vw">
      <VContainer class="px-0">
        <router-view v-on:login="login"/>
      </VContainer>
    </VMain>
  </VLayout>
</template>