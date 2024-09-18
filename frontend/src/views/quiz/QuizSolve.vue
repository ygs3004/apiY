<script setup>
import {computed, getCurrentInstance, onMounted, ref} from 'vue'
import {useRoute, useRouter} from "vue-router";

const {proxy} = getCurrentInstance();
const {$axios} = proxy;
const route = useRoute();
const router = useRouter();

onMounted(() => {
  searchQuizQuestion();
});

const quizQuestions = ref([{
  quizSet:{
    id: "",
    category: "",
    subject: "",
  },
  id:"",
  question: "",
  answers: [{
    id: "",
    answer: "",
    isSelected: false,
  }]
}]);
const isLoadingAnswer = ref(false);
const quizCategory = ref("");
const quizSubject = ref("");
const searchQuizQuestion = async () => {
  const response = await $axios.get("/quiz/question", {
    params: {
      quizSetId: route.params.quizSetId
    }
  });
  quizQuestions.value = response.data;
  const {category, subject} = quizQuestions.value[0].quizSet;
  quizCategory.value = category;
  quizSubject.value = subject
  isLoadingAnswer.value = true;
}

const curSlideIdx = ref(0);
const maxSlideIdx = computed(() => quizQuestions.value.length - 1);
const completeSolve = ref(false);
const onChangePrev = () => {
  curSlideIdx.value = Math.max(0, curSlideIdx.value - 1);
}
const onChangeNext = () => {
  curSlideIdx.value = Math.min(maxSlideIdx.value, curSlideIdx.value + 1);
}

const onSelectAnswer = (answerId, toggle) => {
  toggle()
  quizQuestions.value[curSlideIdx.value].answers.forEach(answer => answer.isSelected = answer.id === answerId);
  curSlideIdx.value === maxSlideIdx.value ? completeSolve.value = true : onChangeNext();
}

const submitAnswer =  async () => {
  const solveResult = quizQuestions.value.map(question => {
    const selectedAnswer = question.answers.find(answer => answer.isSelected);
    return {
      questionId: question.id,
      selectedAnswerId: selectedAnswer.id
    }
  });
  const requestBody = {
    quizSetId: solveResult.quizSetId = quizQuestions.value[0].quizSet.id,
    questionSolves: solveResult,
  }
  const response = await $axios.post('/quiz/solve', requestBody);
  history.pushState({
    quizResult: response.data,
    quizSubject: quizSubject.value,
    quizCategory: quizCategory.value,
  },"",);

  await router.replace({
    name: "quiz-complete",
  });
}
</script>

<template>
  <VLayout>
    <VCol cols="12" lg="4" offset-lg="4">
      <VTextField color="primary" bg-color="primary">
        [{{ quizCategory }}] {{ quizSubject }}
      </VTextField>
      <VCard class="mx-auto" elevation="24" max-width="100%">
        <VCarousel
            progress="accent"
            :continuous="false"
            :show-arrows="true"
            delimiter-icon="mdi-square"
            height="300"
            hide-delimiter-background
            hide-delimiters
            v-model="curSlideIdx"
        >
          <template v-slot:prev>
            <VBtn size="40" icon="mdi-chevron-left" color="primary" variant="tonal"
                  @click="onChangePrev"/>
          </template>
          <template v-slot:next>
            <VBtn size="40" icon="mdi-chevron-right" color="primary" variant="tonal"
                  @click="onChangeNext"/>
          </template>

          <VCarouselItem v-for="(item) in quizQuestions" :key="item.id">
            <div class="d-flex fill-height justify-center align-center px-16">
              <div class="text-h4">
                {{ item.question }}
              </div>
            </div>
          </VCarouselItem>

        </VCarousel>
      </VCard>
    </VCol>
  </VLayout>
  <VLayout height="auto">
    <VCol cols="12" lg="4" offset-lg="4">
      <VItemGroup mandatory>
        <VContainer>
          <VRow>
            <div v-if="isLoadingAnswer" style="width: 100%">
              <VCol v-if="completeSolve" cols="12">
                <VItem>
                  <VCard color="info"
                         class="d-flex align-center py-2"
                         height="auto"
                         variant="elevated"
                         @click="submitAnswer">
                    <div class="text-h6 flex-grow-1 px-3 text-center font-weight-bold">
                      제출하기
                    </div>
                  </VCard>
                </VItem>
              </VCol>
              <VCol cols="12" v-for="(item, idx) in quizQuestions[curSlideIdx].answers" :key="item.id">
                <VItem v-slot="{ toggle }">
                  <VCard :color="item.isSelected ? 'accent' : ''"
                         class="d-flex align-center py-2"
                         height="auto"
                         @click="onSelectAnswer(item.id, toggle)">
                    <div class="text-h6 flex-grow-1 px-3">
                      {{ idx + 1 }}
                      {{ item.answer }}
                    </div>
                  </VCard>
                </VItem>
              </VCol>
            </div>
            <div v-else style="width: 100%">
              <VCol cols="12" v-for="(item) in 4" :key="item">
                <VItem>
                  <VCard height="50px" loading/>
                </VItem>
              </VCol>
            </div>
          </VRow>
        </VContainer>
      </VItemGroup>
    </VCol>
  </VLayout>
</template>