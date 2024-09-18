<script setup>

import {getCurrentInstance, ref} from "vue";
import {useRouter} from "vue-router";

const {proxy} = getCurrentInstance();
const {$axios, $modal} = proxy;
const router = useRouter();

const categoryResponse = await $axios.get("/quiz/combo/category");
const quizCombo = ref(categoryResponse.data.comboList);

const selectCategory = ref("");
const quizSubject = ref("");

const required = (value) => {
  return !!value || "값을 입력해주세요"
}

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

const submit = async (event) => {
  const checkSubmit = await event;
  if(checkSubmit.valid){
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
    if(response.status === 200){
      await router.push("/quiz")
    }else{
      $modal({
        title: "오류",
      })
    }
    console.log(response);
  }else{
    const invalidInputId = checkSubmit.errors[0].id;
    document.querySelector(`#${invalidInputId}`).focus();
  }
}

</script>

<template>
  <VCol cols="12" lg="6" offset-lg="3">
    <VSheet class="mx-auto pa-lg-10 pa-2" border>
      <VForm @submit.prevent="submit">
        <div class="text-h4 my-7">
          퀴즈 등록하기
        </div>
        <VSelect
            label="카테고리"
            :items="quizCombo"
            :rules="[required]"
            v-model="selectCategory"/>
        <VTextField
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
                <VRow class="px-4" v-for="num in 4" :key="num">
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