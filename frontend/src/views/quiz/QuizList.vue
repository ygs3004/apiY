<script setup>
import {getCurrentInstance, ref} from 'vue'
import {useRouter} from "vue-router";
const {proxy} = getCurrentInstance();
const {$axios} = proxy;

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

  if (response.status === 200) {
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

</script>

<template>
  <VLayout>
<!--    <VBtn @click="saveTestData">테스트</VBtn>-->
    <VInfiniteScroll class="mx-auto w-lg-50" width="90%" height="50%" :items="quizSets" :onLoad="load">
      <VBtn color="primary" class="right-0" prepend-icon="mdi-pencil-plus" @click="goQuizCreatePage">
        퀴즈 등록하기
      </VBtn>
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