import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/login/LoginView";
import CalendarView from "../views/calendar/CalendarView";
import LeaveRequestView from "../views/leaveRequest/LeaveRequestView";
import AdminView from "../views/admin/AdminView";
import Store from '@/store/index';
import mixins from '@/utils/mixins';
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
  },
  {
    path: "/leaverequest",
    name: "leaveRequest",
    component: LeaveRequestView,
  },
  {
    path:"/admin",
    name: "AdminView",
    component : AdminView
  }
]

const router = createRouter({
  history: createWebHistory("/"), // 또는 원하는 경로를 직접 입력해도 됩니다.
  routes, // 'routes: routes'와 동일
});


router.beforeEach(async (to, from, next) => {
  console.log(to, from);
  try {
    // 권한이 있는지 확인
    const res = await Store.dispatch('checkCookie');
    if (res.status === 200) {
      if (to.path === '/') {
        next('calendar');
        return;
      }
      next();
      return;
    } else if (to.path === '/') {
      if (Store.state.token !== null) {
        next();
        return; 
      }
      mixins.methods.logout();
      next();
      return;
    }
  } catch (e) {
    next();
    return;
  }

  if (Store.state.token === null) {
    next();
    return;  
  }
  next();
});





export default router;
