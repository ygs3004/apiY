<script setup>

import {getCurrentInstance, onBeforeMount, ref} from "vue";
import {useRouter} from "vue-router";
import {HttpStatusCode} from "axios";

const {proxy} = getCurrentInstance();
const {$axios, $modal, $utils} = proxy;
const router = useRouter();

const quizCombo = ref();
onBeforeMount( async () => {
  const categoryResponse = await $axios.get("/quiz/combo/category");
  quizCombo.value = categoryResponse.data.comboList;
});

const selectCategory = ref("");
const quizSubject = ref("");

const required = $utils.required;

const createAnswer = () => {
  return {
    answer: "",
    isCorrect: false,
  }
}

const createAnswers = () => {
  const answers = [];
  for(let i = 0; i < 4; i++){
    answers.push(createAnswer());
  }
  return answers;
}

const createQuestion = () => {
  return {
    question: "",
    answers: createAnswers(),
    answerNum: 1,
  }
}

const createQuestions = () => {
  const questions = [];
  for(let i = 0; i < 5; i++){
    questions.push(createQuestion());
  }
  return questions;
}

const addQuestion = () => {
  questions.value.push(createQuestion());
}

const questions = ref(createQuestions());

const removeQuestion = (questionIdx) => {
  questions.value.splice(questionIdx, 1);
}

const isValid = ref(false);

const submit = async (event) => {
  const checkEvent = await event;

  if(isValid.value){
    questions.value.forEach( question => {
      const correctAnswerIdx = question.answerNum - 1;
      const answers = question.answers;
      for(let i = 0; i < answers.length; i++){
        answers[i].isCorrect = i === correctAnswerIdx
      }

      delete question.answerNum;
    });

    const quizSaveRequest = {
      category: selectCategory.value,
      subject: quizSubject.value,
      questions: questions.value,
    }

    const response = await $axios.put("/quiz/set", quizSaveRequest);
    if(response.status === HttpStatusCode.Ok){
      await router.push("/quiz")
    }else{
      $modal({
        title: "오류",
      })
    }
  }else{
    const invalidInputId = checkEvent.errors[0].id;
    document.querySelector(`#${invalidInputId}`).focus();
  }
}

</script>

<template>
  <VCol cols="12" lg="6" offset-lg="3">
    <VSheet class="mx-auto px-2 px-lg-10" border>
      <VForm v-model="isValid" @submit.prevent="submit">
        <div class="text-h4 my-7">
          퀴즈 등록하기
        </div>
        <VSelect
            class="mb-2"
            label="카테고리"
            :items="quizCombo"
            :rules="[required]"
            v-model="selectCategory"/>
        <VTextField
            class="mb-10"
              v-model="quizSubject"
              :rules="[required]"
              label="퀴즈 주제"/>
        <VRow v-for="(question, i) in questions" :key="i">
          <VCol>
            <VCard subtitle="(보기 옆 체크박스에 정답을 체크해주세요)">
              <template v-slot:title>
                <div class="d-flex justify-space-between">
                  <span class="text-h5">{{i + 1}}번 문제</span>
                  <VBtn v-if="questions.length > 1"
                        prepend-icon="mdi-minus-box-outline"
                        text="제거"
                        variant="elevated"
                        color="error"
                        @click="removeQuestion(i)"
                  />
                </div>
              </template>
              <VCardItem>
                <VRow>
                  <VTextarea
                      class="w-100 px-4"
                      v-model="question.question"
                      :rules="[required]"
                      label="문제"/>
                </VRow>
                <VRow class="px-4 mb-1" v-for="num in 4" :key="num">
                    <VTextField
                        v-model="question.answers[num - 1].answer"
                        :rules="[required]"
                        :label="`${num}번 보기`">
                      <template v-slot:prepend>
                        <VCheckbox base-color="primary" color="primary" v-model="question.answerNum" :value="num" hide-details hide-spin-buttons/>
                      </template>
                    </VTextField>
                </VRow>
              </VCardItem>
            </VCard>
          </VCol>
        </VRow>
        <VRow>
          <VCol align="center">
            <VBtn v-if="questions.length < 20" class="mr-3 w-33" text="문제 추가" variant="outlined" @click="addQuestion"/>
            <VBtn class="w-33" text="등록" type="submit"/>
          </VCol>
        </VRow>
      </VForm>
    </VSheet>
  </VCol>
</template>