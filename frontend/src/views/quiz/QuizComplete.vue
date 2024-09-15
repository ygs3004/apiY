<script setup>
import {computed, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const quizResult = ref(history.state.quizResult);
const quizSubject = ref(history.state.quizSubject);
const quizCategory = ref(history.state.quizCategory);
const quizPoint = computed(() => {
  return (quizResult.value.correctCount / quizResult.value.questionCount) * 100;
});

const goQuizList = () => {
  router.push("/quiz");
}

</script>

<template>
  <VLayout>
    <VCol cols="12" lg="4" offset-lg="4">
      <VCard
          class="mx-auto"
          width="100%"
          height="100%"
      >
        <template v-slot:title>
          <span class="font-weight-black">[{{quizCategory}}] {{quizSubject}}</span>
          <br/>
          <span class="font-weight-black">당신의 점수는!</span>
        </template>

        <VCardItem class="pt-4 align-center justify-center" >
          <VTextField variant="plain" >
            <span class="text-h2 font-weight-black align-center">{{quizPoint}}점</span>
          </VTextField>

        </VCardItem>

        <VCardActions class="px-5">
          <VTextField variant="plain">
            총 문항: {{quizResult.questionCount}}, 정답: {{quizResult.correctCount}}
          </VTextField>
          <VSpacer></VSpacer>
          <VBtn
              text="목록으로 돌아가기"
              @click="goQuizList"
          ></VBtn>
        </VCardActions>
      </VCard>
    </VCol>
  </VLayout>
</template>