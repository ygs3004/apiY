<script setup>
import {getCurrentInstance, ref} from "vue";
import {useRouter} from "vue-router";
import {HttpStatusCode} from "axios";

const {proxy} = getCurrentInstance();
const {$utils, $axios} = proxy;

const router = useRouter();
const required = $utils.required
const isEmail = $utils.isEmail

const visible = ref(false);
const isValid = ref(false);

const goSignupPage = () => {
  router.push("/signup")
}

const email = ref("");
const password = ref("");

const emit = defineEmits(['loginSuccess'])
const submit = async (event) => {
  const checkEvent = await event;
  if (isValid.value) {
    const response = await $axios.post("/member/login", {
      email: email.value,
      password: password.value,
    })

    if (response.status === HttpStatusCode.Ok) {
      $utils.setSessionStorageItem("loginUser", response.data);
      emitLoginSuccess();
      await router.push("/");
    }

  } else {
    const invalidInputId = checkEvent.errors[0].id;
    document.querySelector(`#${invalidInputId}`).focus();
  }
}

const emitLoginSuccess = () => {
  emit('loginSuccess');
}

const googleLogin = () => {
  // Google's OAuth 2.0 endpoint for requesting an access token
  const oauth2Endpoint = 'https://accounts.google.com/o/oauth2/v2/auth';
  const scope = [
    "openid",
    "https://www.googleapis.com/auth/userinfo.profile",
    "https://www.googleapis.com/auth/userinfo.email",
  ].join(" ");

  // Create <form> element to submit parameters to OAuth 2.0 endpoint.
  const form = document.createElement('form');
  form.setAttribute('method', 'GET'); // Send as a GET request.
  form.setAttribute('action', oauth2Endpoint);

  const key = import.meta.env.VITE_GOOGLE_LOGIN_KEY;
  const randomState = (Math.random() * 1000000).toString().substring(0, 6);
  $utils.setSessionStorageItem("googleState", randomState);
  // Parameters to pass to OAuth 2.0 endpoint.
  const params = {
    'client_id': key,
    'redirect_uri': `${window.location.origin}/login/google`,
    'response_type': 'code',
    scope,
    'include_granted_scopes': 'true',
    state: randomState
  };

  // Add form parameters as hidden input values.
  for (const p in params) {
    const input = document.createElement('input');
    input.setAttribute('type', 'hidden');
    input.setAttribute('name', p);
    input.setAttribute('value', params[p]);
    form.appendChild(input);
  }

  // Add form to page and submit it to open the OAuth 2.0 endpoint.
  document.body.appendChild(form);
  form.submit();
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

      <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
        Password
        <!--        <a-->
        <!--            class="text-caption text-decoration-none text-blue"-->
        <!--            href="#"-->
        <!--            rel="noopener noreferrer"-->
        <!--            target="_blank"-->
        <!--        >-->
        <!--&lt;!&ndash;          비밀번호를 잃어버리셨나요?&ndash;&gt;-->
        <!--        </a>-->
      </div>

      <VTextField
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

      <VBtn class="my-5 bold"
            color="info"
            size="large"
            variant="elevated"
            text="구글 계정으로 로그인"
            @click="googleLogin"
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
  <router-view @loginSuccess="emitLoginSuccess"></router-view>
</template>