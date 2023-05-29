import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },

];

const router = createRouter({
  history: createWebHistory("/"), // 또는 원하는 경로를 직접 입력해도 됩니다.
  routes,
});

export default router;
