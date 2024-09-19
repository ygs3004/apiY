<script setup>
import {getCurrentInstance, ref} from "vue";
import {HttpStatusCode} from "axios";
import {useRouter} from "vue-router";

const {proxy} = getCurrentInstance();
const {$axios, $utils, $modal} = proxy;
const required = $utils.required
const isEmail = $utils.isEmail
const router = useRouter();

const isSameCheckPassword = () => {
  return password.value === checkPassword.value || "확인 값이 다릅니다."
}

const visible = ref(false);
const isValid = ref(false);
const signupLoading = ref(false);

const email = ref("");
const password = ref("");
const checkPassword = ref("");
const name = ref("");

const submit = async (event) => {
  const checkEvent = await event;
  if (isValid.value){
    signupLoading.value = true;
      const response = await $axios.post('/member/signup', {
        email: email.value,
        password: password.value,
        name: name.value,
      })
    signupLoading.value = false;

    if(response.status === HttpStatusCode.Ok) {
      $modal({
        title: "가입완료",
        content: "가입을 환영합니다!<br> 로그인 후 회원 기능을 이용할 수 있습니다."
      })
      await router.push("/login")
    }

  }else{
    const invalidInputId = checkEvent.errors[0].id;
    document.querySelector(`#${invalidInputId}`).focus();
  }

}
</script>

<template>
  <VForm v-model="isValid" @submit.prevent="submit">
    <VCard
        class="mx-auto pa-12 pb-8"
        elevation="8"
        max-width="448"
        rounded="lg"
    >

      <div class="text-subtitle-1 text-medium-emphasis">Email</div>
      <VTextField
          v-model="email"
          density="compact"
          placeholder="계정(이메일)"
          prepend-inner-icon="mdi-email-outline"
          variant="outlined"
          :rules="[required, isEmail]"
      ></VTextField>

      <div class="text-subtitle-1 text-high-emphasis bold">이름</div>
      <VTextField
          v-model="name"
          density="compact"
          placeholder="이름"
          variant="outlined"
          :rules="[required]"
      ></VTextField>

      <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
        비밀번호
      </div>

      <VTextField
          class="mb-1"
          v-model="password"
          :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="visible ? 'text' : 'password'"
          density="compact"
          placeholder="비밀번호"
          prepend-inner-icon="mdi-lock-outline"
          variant="outlined"
          :rules="[required]"
          @click:append-inner="visible = !visible"
      ></VTextField>


      <VTextField
          v-model="checkPassword"
          :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="visible ? 'text' : 'password'"
          density="compact"
          placeholder="비밀번호 확인"
          prepend-inner-icon="mdi-lock-outline"
          variant="outlined"
          :rules="[required, isSameCheckPassword]"
          @click:append-inner="visible = !visible"
      ></VTextField>


      <VBtn
          :loading="signupLoading"
          class="my-5 bold"
          color="primary"
          size="large"
          variant="elevated"
          type="submit"
          text="가입하기"
          block>
      </VBtn>
    </VCard>
  </VForm>
</template>