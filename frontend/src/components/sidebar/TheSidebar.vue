<template>
  <aside
    id="logo-sidebar"
    class="fixed top-0 left-0 z-40 w-64 h-screen pt-16 transition-transform -translate-x-full bg-white border-r border-gray-200 sm:translate-x-0 dark:bg-gray-800 dark:border-gray-700"
    aria-label="Sidebar"
  >
    <div
      class="h-full px-3 pt-4 pb-4 overflow-y-auto bg-white dark:bg-gray-800"
    >
      <div class="h-72 bg-white rounded-lg">
        <div class="flex justify-center mb-4">
          <img
            class="h-24 w-24 rounded-full object-cover"
            src="@/assets/images/user.svg"
            alt="프로필 사진"
          />
        </div>
        <div>
          <label class="flex justify-center mt-6">{{ memberName }}</label>
          <label class="flex justify-center mt-2">{{ deptName }}</label>
          <div class="ml-6">
            <div class="mt-4">
              <label class="ml-6">남은 휴가</label>
              <label class="ml-6 text-blue-500">{{ restLeave }}</label
              >일
            </div>
            <div class="mt-2">
              <label class="ml-6">사용 휴가</label>
              <label class="ml-6 text-red-500">{{ usedLeave }}</label
              >일
            </div>
          </div>
        </div>
      </div>

      <ul class="h-80 space-y-6 font-medium">
        <li>
          <router-link
            to="/"
            class="flex items-center p-2 text-gray-500 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700"
          >
            <svg
              aria-hidden="true"
              class="flex-shrink-0 w-6 h-6 text-gray-400 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fill-rule="evenodd"
                d="M5 4a3 3 0 00-3 3v6a3 3 0 003 3h10a3 3 0 003-3V7a3 3 0 00-3-3H5zm-1 9v-1h5v2H5a1 1 0 01-1-1zm7 1h4a1 1 0 001-1v-1h-5v2zm0-4h5V8h-5v2zM9 8H4v2h5V8z"
                clip-rule="evenodd"
              ></path>
            </svg>
            <span class="flex-1 ml-3 whitespace-nowrap">연차 달력</span>
          </router-link>
        </li>
        <li>
          <router-link
            to="/leaverequest"
            class="flex items-center p-2 text-gray-500 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700"
          >
            <svg
              aria-hidden="true"
              class="w-6 h-6 text-gray-400 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path d="M2 10a8 8 0 018-8v8h8a8 8 0 11-16 0z"></path>
              <path d="M12 2.252A8.014 8.014 0 0117.748 8H12V2.252z"></path>
            </svg>
            <span class="ml-3">연차 신청</span>
          </router-link>
        </li>
        <li>
          <router-link
            to="/mypage/history"
            class="flex items-center p-2 text-gray-500 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700"
          >
            <svg
              aria-hidden="true"
              class="flex-shrink-0 w-6 h-6 text-gray-400 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fill-rule="evenodd"
                d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
                clip-rule="evenodd"
              ></path>
            </svg>
            <span class="flex-1 ml-3 whitespace-nowrap">마이페이지</span>
          </router-link>
        </li>
        <li v-if="auth === 'admin'">
          <!--        <li v-if="isAdmin">-->
          <router-link
            to="/admin"
            class="flex items-center p-2 text-gray-500 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700"
          >
            <svg
              aria-hidden="true"
              class="flex-shrink-0 w-6 h-6 text-gray-400 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M5 3a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2V5a2 2 0 00-2-2H5zM5 11a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2v-2a2 2 0 00-2-2H5zM11 5a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V5zM11 13a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"
              ></path>
            </svg>
            <span class="flex-1 ml-3 whitespace-nowrap">관리자 페이지</span>
          </router-link>
        </li>
      </ul>
    </div>
  </aside>
</template>

<script>
import { ref, onMounted } from "vue";
import { useStore } from "vuex";
import Store from "@/store/index.js";

export default {
  computed: {
    getRestLeave() {
      return this.$store.getters.getRestLeave;
    },
    getUsedLeave() {
      console.log("사용한 연차: " + this.$store.getters.getUsedLeave);
      return this.$store.getters.getUsedLeave;
    },
    restLeave() {
      return this.getRestLeave;
    },
    usedLeave() {
      return this.getUsedLeave;
    },
  },
  setup() {
    const store = useStore();

    const auth = ref(Store.state.auth);
    const memberName = ref(Store.state.memberName);
    const deptName = ref(Store.state.deptName);

    const fetchLeaveInfo = () => {
      store.dispatch("fetchLeaveInfo");
    };

    onMounted(() => {
      fetchLeaveInfo();
    });

    return {
      auth,
      memberName,
      deptName,
    };
  },
};
</script>
