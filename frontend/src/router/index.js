import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/login/LoginView"
import CalendarView from "../views/calendar/CalendarView"
const routes = [
  {
    path: "/",
    name: "login",
    component: LoginView,
  },
  {
    path: "/calendar",
    name: "calendar",
    component: CalendarView,
  }
];

const router = createRouter({
  history: createWebHistory("/"), // 또는 원하는 경로를 직접 입력해도 됩니다.
  routes,
});

export default router;