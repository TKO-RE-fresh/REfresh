import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/login/LoginView";
import CalendarView from "../views/calendar/CalendarView";
import LeaveRequestView from "../views/leaveRequest/LeaveRequestView";
import AdminView from "../views/admin/AdminView";
import Cookies from '@/utils/token';
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
  if (from.path === '/') {
    next();
    return;
  }
  console.log("gg");
  const accessToken = Store.state.token;
  // 로그아웃 x 로그인 상태
  if (accessToken) {
    try {
      const res = await mixins.methods.$api('/accessToken', 'get', {
        header: {
          'Authorization': `Bearer ${accessToken}`
        }
      });
      if (accessToken !== res.headers.accessToken) {
        // 있는 토큰이 만료된 경우
        checkRefresh();
        Store.commit('setToken', res.headers.accessToken);
      } else {
        // 토큰이 유효한 경우 엑세스 토큰을 유지하고 페이지 이동
        next();
      }
    } catch (e) {
      console.log('Failed to Auth', e);
      // 로그아웃 처리
      Store.dispatch('logout');
      Cookies.removeToken();
    }
  } else {
    checkRefresh();
  }
});
async function checkRefresh() {
  const refreshToken = Cookies.get('refreshToken');
  try {
    const res = await mixins.methods.$api('/refreshToken', 'get', {
      header: {
        'Authorization': `Bearer ${refreshToken}`
      }
    });
    Store.dispatch('setToken', res.headers.accessToken);
    Cookies.set('refreshToken', res.headers.refreshToken);
  } catch (e) {
    console.log('Failed to Auth', e);
    // 로그아웃 처리
    Store.dispatch('logout');
    Cookies.removeToken();
  }
}



export default router;
