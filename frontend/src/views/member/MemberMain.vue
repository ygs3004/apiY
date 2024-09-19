<script setup>
import {getCurrentInstance, ref} from "vue";
import {useRouter} from "vue-router";

const {proxy} = getCurrentInstance();
const {$utils} = proxy;

const router = useRouter();
const required = $utils.required
const isEmail = $utils.isEmail

const visible = ref(false);
const isValid = ref(false);

const goSignupPage = () => {
  router.push("/signup")
}

const submit = () => {
  console.log(isValid.value)
  if (!isValid.value) return
  alert("로그인!")
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
          density="compact"
          placeholder="계정(이메일)"
          prepend-inner-icon="mdi-email-outline"
          variant="outlined"
          :rules="[required, isEmail]"
      ></VTextField>

      <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
        Password
        <a
            class="text-caption text-decoration-none text-blue"
            href="#"
            rel="noopener noreferrer"
            target="_blank"
        >
          비밀번호를 잃어버리셨나요?</a>
      </div>

      <VTextField
          :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="visible ? 'text' : 'password'"
          density="compact"
          placeholder="비밀번호"
          prepend-inner-icon="mdi-lock-outline"
          variant="outlined"
          :rules="[required]"
          @click:append-inner="visible = !visible"
      ></VTextField>
      <!--      <VCard-->
      <!--          class="mb-12"-->
      <!--          color="surface-variant"-->
      <!--          variant="tonal"-->
      <!--      >-->
      <!--        <VCardText class="text-medium-emphasis text-caption">-->
      <!--          Warning: After 3 consecutive failed login attempts, you account will be temporarily locked for three hours. If you must login now, you can also click "Forgot login password?" below to reset the login password.-->
      <!--        </VCardText>-->
      <!--      </VCard>-->

      <VBtn class="my-5 bold"
          color="primary"
          size="large"
          variant="elevated"
          type="submit"
          text="로그인"
          block>
      </VBtn>
      <VBtn class="my-1"
          color="success"
          size="large"
          variant="elevated"
          block
          @click="goSignupPage">
        회원가입
        <v-icon icon="mdi-chevron-right"></v-icon>
      </VBtn>
    </VCard>
  </VForm>
</template>