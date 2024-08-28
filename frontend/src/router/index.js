import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      redirect: '/today'
    },
    {
      path: '/today',
      name: 'today',
      component: () => import('@/views/today/TodayMain.vue')
    },
    {
      path: '/quiz',
      name: 'quiz',
      component: () => import('@/views/quiz/QuizList.vue'),
    },
    {
      path: "/quiz/:quizSetId",
      name: 'quiz-solve',
      component: () =>  import('@/views/quiz/QuizSolve.vue'),
    }
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
