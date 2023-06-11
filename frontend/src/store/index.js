import { createStore } from "vuex";
import axios from "axios";
import leaveInfo from "./modules/leaveInfo";
import mixins from "@/utils/mixins";

const store = createStore({
  modules: {
    leaveInfo,
  },
  state: {
    token: null,
    annualList: [],
    historyList: [],
    calendarYear: new Date().getFullYear(),
    calendarMonth: new Date().getMonth() + 1,
    deptName: null,
    memberId: null,
    memberName: null,
    auth: null,
    deptList: [],
    manageSearch: {
      memberName: "",
      departmentName: "",
      status: "",
    },
    historySearch: {
      status: "",
      type: "",
      year: 0,
    },
  },
  getters: {
    getAccessToken: (state) => {
      return state.token;
    },
    getCalendar: (state) => {
      return {
        year: state.calendarYear,
        month: state.calendarMonth,
      };
    },
    getDepartment: (state) => {
      return state.deptName;
    },
    getDepartmentList: (state) => {
      return state.deptList;
    },
    getMemberId: (state) => {
      return state.memberId;
    },
    getMemberName: (state) => {
      return state.memberName;
    },
    getAuth: (state) => {
      return state.auth;
    },
    getAnnualList: (state) => {
      return state.annualList;
    },
    getManageSearch: (state) => {
      return state.manageSearch;
    },
    getHistoryList: (state) => {
      return state.historyList;
    },
    getHistorySearch: (state) => {
      return state.historySearch;
    },
  },
  mutations: {
    setDeptName: (state, deptName) => {
      state.deptName = deptName;
    },
    setMemberId: (state, memberId) => {
      state.memberId = memberId;
    },
    setMemberName: (state, memberName) => {
      state.memberName = memberName;
    },
    setAccessToken: (state, token) => {
      state.token = token;
    },
    setAnnualList: (state, list) => {
      state.annualList = list;
    },
    setHistoryList: (state, list) => {
      state.historyList = list;
    },
    setCalendarDate: (state, date) => {
      const { year, month } = date;
      state.calendarYear = year;
      state.calendarMonth = month;
    },
    setDept: (state, deptName) => {
      state.deptName = deptName;
    },
    setDeptList: (state, deptList) => {
      state.deptList = deptList;
    },
    setManageSearch: (state, manageSearch) => {
      state.manageSearch = manageSearch;
    },
    setHistorySearch: (state, searchInput) => {
      state.historySearch = searchInput;
    },
    setAuth: (state, auth) => {
      state.auth = auth;
    },
  },
  actions: {
    updateToken({ commit }, token) {
      commit("setAccessToken", token);
      axios.defaults.headers.common.access_token = `Bearer ${token}`;
    },
    async checkCookie({ commit }) {
      try {
        const res = await axios.post("token/cookie", {});
        commit("setAccessToken", res.data.access_token);
        commit("setMemberId", res.data.memberId);
        commit("setMemberName", res.data.memberName);
        commit("setAuth", res.data.auth);
        commit("setDept", res.data.deptName);
        return res;
      } catch (e) {
        console.log("Failed to Auth", e);
      }
    },

    async fetchAnnualList({ commit, state }, page) {
      const res = await mixins.methods.$api(`admin/annual/${page}`, "get", {
        params: state.manageSearch,
      });
      commit("setAnnualList", res.data);
    },

    async fetchHistoryList({ commit, state }, page) {
      const res = await mixins.methods.$api(`myPage/history/${page}`, "get", {
        params: state.historySearch,
      });
      commit("setHistoryList", res.data);
    },
  },
  plugins: [],
});
store.subscribe((mutation, state) => {
  if (mutation.type === "setAccessToken") {
    if (state.token !== null) {
      axios.defaults.headers.common.access_token = `Bearer ${state.token}`;      
    }
  }
});

export default store;
