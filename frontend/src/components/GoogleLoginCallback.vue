<script setup>
import {getCurrentInstance} from "vue";
import {useRouter} from "vue-router";
import {HttpStatusCode} from "axios";

const {proxy} = getCurrentInstance();
const {$utils, $axios} = proxy;
const router = useRouter();

const randomState = $utils.getSessionStorageItem("googleState");
$utils.removeLocalStorageItem('googleState');
const query = router.currentRoute.value.query
// const token = queryParam.get("access_token");
const code = query.code;
const state = query.state;

const emit = defineEmits(['loginSuccess'])
// 비정상 접근
if (randomState !== state) {
  router.push("/login");
} else {
  const response = await $axios.post("/member/login", {
    email: "google",
    password: "password",
    socialType: "GOOGLE",
    socialCode: code,
  });

  if (response.status === HttpStatusCode.Ok) {
    $utils.setSessionStorageItem("loginUser", response.data);
    emit("loginSuccess");
    await router.push("/");
  }else{
    await router.push("/login");
  }
}
</script>

<template>
  <div>
  </div>
</template>