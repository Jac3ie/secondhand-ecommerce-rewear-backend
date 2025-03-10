import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import Home from '../views/Home.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/about',
    name: 'About',
    // Lazy-loaded route (code splitting)
    component: () => import('../views/About.vue'),
  },
  {
    path: '/login',
    name: 'Login',
    // Lazy-loaded route (code splitting)
    component: () => import('../views/Login.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
