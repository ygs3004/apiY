<script setup>
import {getCurrentInstance, onMounted, ref} from 'vue'
import {useRouter} from "vue-router";
import {HttpStatusCode} from "axios";
const {proxy} = getCurrentInstance();
const {$axios, $utils} = proxy;

const router = useRouter();

const quizSets = ref([])
const load = async ({done}) => {
  const responseData = await searchQuizSets();
  if(responseData.length > 0){
    quizSets.value.push(...responseData);
    done('ok');
  }else{
    done('empty');
  }
}

const requestPage = ref(0);
const searchQuizSets  = async () => {
  const response = await $axios.get("/quiz/set", {
    params:{
      page: requestPage.value
    }
  });

  if (response.status === HttpStatusCode.Ok) {
    requestPage.value++;
    return response.data;
  }else{
    console.warn(response);
  }
}

const goQuizChallenge = (quizSetId) => {
  router.push(`/quiz/solve/${quizSetId}`)
}

const goQuizCreatePage = () => {
  router.push('/quiz/create')
}

const goLoginPage = () => {
  router.push("/login")
}

const isLogin = ref(!!$utils.getSessionStorageItem("loginUser"));
</script>

<template>
  <VLayout>
<!--    <VBtn @click="saveTestData">테스트</VBtn>-->
    <VInfiniteScroll class="mx-auto w-lg-50 cursor-pointer" width="90%" height="50%" :items="quizSets" :onLoad="load">
      <VBtn v-if="isLogin" color="primary" class="right-0" prepend-icon="mdi-pencil-plus" @click="goQuizCreatePage">
        퀴즈 등록하기
      </VBtn>
      <VTextField v-else bg-color="primary" variant="filled" @click="goLoginPage"  readonly>
        로그인 후 퀴즈를 등록해주세요
      </VTextField>
      <VList lines="three">
        <template v-for="(quizSet) in quizSets"
                  :key="quizSet.id">
          <VListItem
              :title="`[${quizSet.category}] ${quizSet.subject}`"
              border
              @click="goQuizChallenge(quizSet.id)">
          </VListItem>
        </template>
      </VList>
    </VInfiniteScroll>
  </VLayout>
</template>