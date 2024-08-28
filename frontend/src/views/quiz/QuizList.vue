<script setup>
import {getCurrentInstance, ref} from 'vue'
import router from "@/router/index.js";
const {proxy} = getCurrentInstance();
const {$axios} = proxy;

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
  router.push(`/quiz/${quizSetId}`)
}
//
// const saveTestData = () => {
//   const testJson = {
//     category: "english",
//     subject: "영단어 퀴즈1",
//     "questions": [
//       {
//         "question": "apple",
//         "answers": [
//           {
//             "answer": "체리",
//             "isCorrect": false
//           },
//           {
//             "answer": "딸기",
//             "isCorrect": false
//           },
//           {
//             "answer": "바나나",
//             "isCorrect": false
//           },
//           {
//             "answer": "사과",
//             "isCorrect": true
//           }
//         ]
//       },
//       {
//         "question": "banana",
//         "answers": [
//           {
//             "answer": "사과",
//             "isCorrect": false
//           },
//           {
//             "answer": "오렌지",
//             "isCorrect": false
//           },
//           {
//             "answer": "포도",
//             "isCorrect": false
//           },
//           {
//             "answer": "바나나",
//             "isCorrect": true
//           }
//         ]
//       },
//       {
//         "question": "cherry",
//         "answers": [
//           {
//             "answer": "사과",
//             "isCorrect": false
//           },
//           {
//             "answer": "복숭아",
//             "isCorrect": false
//           },
//           {
//             "answer": "바나나",
//             "isCorrect": false
//           },
//           {
//             "answer": "체리",
//             "isCorrect": true
//           }
//         ]
//       },
//       {
//         "question": "dog",
//         "answers": [
//           {
//             "answer": "고양이",
//             "isCorrect": false
//           },
//           {
//             "answer": "개",
//             "isCorrect": true
//           },
//           {
//             "answer": "토끼",
//             "isCorrect": false
//           },
//           {
//             "answer": "다람쥐",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "elephant",
//         "answers": [
//           {
//             "answer": "코끼리",
//             "isCorrect": true
//           },
//           {
//             "answer": "기린",
//             "isCorrect": false
//           },
//           {
//             "answer": "호랑이",
//             "isCorrect": false
//           },
//           {
//             "answer": "사자",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "fish",
//         "answers": [
//           {
//             "answer": "물고기",
//             "isCorrect": true
//           },
//           {
//             "answer": "새",
//             "isCorrect": false
//           },
//           {
//             "answer": "포유류",
//             "isCorrect": false
//           },
//           {
//             "answer": "파충류",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "grape",
//         "answers": [
//           {
//             "answer": "포도",
//             "isCorrect": true
//           },
//           {
//             "answer": "사과",
//             "isCorrect": false
//           },
//           {
//             "answer": "배",
//             "isCorrect": false
//           },
//           {
//             "answer": "귤",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "house",
//         "answers": [
//           {
//             "answer": "집",
//             "isCorrect": true
//           },
//           {
//             "answer": "차",
//             "isCorrect": false
//           },
//           {
//             "answer": "학교",
//             "isCorrect": false
//           },
//           {
//             "answer": "병원",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "ice cream",
//         "answers": [
//           {
//             "answer": "아이스크림",
//             "isCorrect": true
//           },
//           {
//             "answer": "케이크",
//             "isCorrect": false
//           },
//           {
//             "answer": "빵",
//             "isCorrect": false
//           },
//           {
//             "answer": "초콜릿",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "juice",
//         "answers": [
//           {
//             "answer": "주스",
//             "isCorrect": true
//           },
//           {
//             "answer": "우유",
//             "isCorrect": false
//           },
//           {
//             "answer": "차",
//             "isCorrect": false
//           },
//           {
//             "answer": "커피",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "key",
//         "answers": [
//           {
//             "answer": "열쇠",
//             "isCorrect": true
//           },
//           {
//             "answer": "문",
//             "isCorrect": false
//           },
//           {
//             "answer": "시계",
//             "isCorrect": false
//           },
//           {
//             "answer": "차",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "lion",
//         "answers": [
//           {
//             "answer": "사자",
//             "isCorrect": true
//           },
//           {
//             "answer": "호랑이",
//             "isCorrect": false
//           },
//           {
//             "answer": "곰",
//             "isCorrect": false
//           },
//           {
//             "answer": "늑대",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "milk",
//         "answers": [
//           {
//             "answer": "우유",
//             "isCorrect": true
//           },
//           {
//             "answer": "주스",
//             "isCorrect": false
//           },
//           {
//             "answer": "차",
//             "isCorrect": false
//           },
//           {
//             "answer": "커피",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "notebook",
//         "answers": [
//           {
//             "answer": "노트북",
//             "isCorrect": true
//           },
//           {
//             "answer": "책",
//             "isCorrect": false
//           },
//           {
//             "answer": "펜",
//             "isCorrect": false
//           },
//           {
//             "answer": "가방",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "orange",
//         "answers": [
//           {
//             "answer": "오렌지",
//             "isCorrect": true
//           },
//           {
//             "answer": "사과",
//             "isCorrect": false
//           },
//           {
//             "answer": "배",
//             "isCorrect": false
//           },
//           {
//             "answer": "귤",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "pen",
//         "answers": [
//           {
//             "answer": "펜",
//             "isCorrect": true
//           },
//           {
//             "answer": "연필",
//             "isCorrect": false
//           },
//           {
//             "answer": "지우개",
//             "isCorrect": false
//           },
//           {
//             "answer": "종이",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "queen",
//         "answers": [
//           {
//             "answer": "여왕",
//             "isCorrect": true
//           },
//           {
//             "answer": "왕",
//             "isCorrect": false
//           },
//           {
//             "answer": "왕비",
//             "isCorrect": false
//           },
//           {
//             "answer": "공주",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "rabbit",
//         "answers": [
//           {
//             "answer": "토끼",
//             "isCorrect": true
//           },
//           {
//             "answer": "다람쥐",
//             "isCorrect": false
//           },
//           {
//             "answer": "쥐",
//             "isCorrect": false
//           },
//           {
//             "answer": "고양이",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "sun",
//         "answers": [
//           {
//             "answer": "태양",
//             "isCorrect": true
//           },
//           {
//             "answer": "달",
//             "isCorrect": false
//           },
//           {
//             "answer": "별",
//             "isCorrect": false
//           },
//           {
//             "answer": "구름",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "tree",
//         "answers": [
//           {
//             "answer": "나무",
//             "isCorrect": true
//           },
//           {
//             "answer": "꽃",
//             "isCorrect": false
//           },
//           {
//             "answer": "풀",
//             "isCorrect": false
//           },
//           {
//             "answer": "뿌리",
//             "isCorrect": false
//           }
//         ]
//       },
//       {
//         "question": "umbrella",
//         "answers": [
//           {
//             "answer": "우산",
//             "isCorrect": true
//           },
//           {
//             "answer": "모자",
//             "isCorrect": false
//           },
//           {
//             "answer": "장갑",
//             "isCorrect": false,
//           },
//           {
//             "answer": "신발",
//             "isCorrect": false,
//           },
//         ],
//       },
//     ],
//   };
//
//   $axios.put("/quiz/set/save", testJson);
// }
</script>

<template>
  <VLayout>
<!--    <VBtn @click="saveTestData">테스트</VBtn>-->
    <VInfiniteScroll class="mx-auto" min-width="50%" height="50%" :items="quizSets" :onLoad="load">
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