<script setup>
import {getCurrentInstance, ref} from "vue";
import {useRouter} from "vue-router";

const {proxy} = getCurrentInstance();
const {$utils} = proxy;
const router = useRouter();

const goPage = (page) => {
  router.push(`/${page}`)
}

const initOnMenu = !/Mobi|Android|iPhone|iPad|iPod/i.test(navigator.userAgent);

const onMenu = ref(initOnMenu);
const handleMenu = () => {
  onMenu.value = !onMenu.value
}

const loginUser = ref($utils.getSessionStorageItem("loginUser"));
const isLogin = ref(!!loginUser.value);
const loginUserName = ref(loginUser.value?.loginUserName || "");

const loginSuccess = () => {
  isLogin.value = true;
  const loginUser = $utils.getSessionStorageItem("loginUser");
  loginUserName.value = loginUser.loginUserName;
}

const goLoginPage = () => {
  router.push('/login')
}

const logout = () => {
  $utils.removeSessionStorageItem("loginUser");
  isLogin.value = false;
  loginUserName.value = "";
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
    <VAppBar color="primary" height="50" :title="loginUserName ? `${loginUserName}님 좋은하루 되세요!` : ``">
      <template v-slot:prepend>
        <VDivider class="my-16" length="90%"/>
        <VAppBarNavIcon @click="handleMenu"/>
      </template>
      <template v-slot:append>
        <VRow v-if="!isLogin" class="cursor-pointer" align="center" @click="goLoginPage">
          <VAppBarTitle text="Sign In"/>
          <VIcon class="mr-5 ml-2" icon="mdi-login"/>
        </VRow>
        <VRow v-else class="cursor-pointer" align="center" @click="logout">
          <VIcon class="mr-5 ml-2" icon="mdi-logout"/>
        </VRow>
      </template>
    </VAppBar>

    <VMain class="d-flex align-center justify-center" width="100vw">
      <VContainer class="px-0">
        <router-view @loginSuccess="loginSuccess"/>
      </VContainer>
    </VMain>
  </VLayout>
</template>