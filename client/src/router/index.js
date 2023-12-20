import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../pages/MainContent.vue'),
    children: [
      {
        path: '',
        name: 'home',
        component: () => import('../views/HomeView.vue'),
      },
      {
        path: '/stats',
        name: 'stats',
        component: () => import('../views/StatsView.vue'),
      },
      {
        path: '/projects/:id',
        name: 'projects',
        component: () => import('../views/ProjectView.vue'),
      }
    ],
  },
  {
    path: '/login',
    name: 'auth',
    component: () => import('../views/AuthView.vue')
  },
  // {
  //   path: '/about',
  //   name: 'about',
  //   component: () => import('../views/AboutView.vue')
  // },
  // {
  //   path: '/projects/:projectName',
  //   name: 'projects',
  //   component: () => import('../views/ProjectView.vue')
  // },
  {
    path: '*',
    name: 'not-found',
    component: () => import('../components/404.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

const isLoggedIn = () => {
  return localStorage.getItem('user');
}

const protectedRoutes = [
  "home",
  "stats",
  "projects"
]
router.beforeEach((to, from, next) => {
  const isProtected = protectedRoutes.includes(to.name)
  if (isProtected && !isLoggedIn()) {
    next({
      path: '/login'
    })
  } else next()
})

localStorage.removeItem('user');

export default router
