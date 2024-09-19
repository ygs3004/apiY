import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      redirect: '/today',
      component: () => import('@/layout/MainLayout.vue'),
      children: [
        {
          path: '/login',
          name: 'login',
          component: () => import('@/views/member/MemberMain.vue'),
        },
        {
          path: '/signup',
          name: 'signup',
          component: () => import('@/views/member/MemberSignup.vue'),
        },
        {
          path: 'today',
          name: 'today',
          component: () => import('@/views/today/TodayMain.vue')
        },
        {
          path: 'quiz',
          name: 'quiz',
          component: () => import('@/views/quiz/QuizList.vue'),
        },
        {
          path: "quiz/solve/:quizSetId",
          name: 'quiz-solve',
          component: () =>  import('@/views/quiz/QuizSolve.vue'),
        },
        {
          path: "quiz/complete",
          name: 'quiz-complete',
          component: () =>  import('@/views/quiz/QuizComplete.vue'),
        },
        {
          path: "quiz/create",
          name: 'quiz-create',
          component: () =>  import('@/views/quiz/QuizCreate.vue'),
        },
      ]
    },

    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue')
    // }
  ]
})

export default router
