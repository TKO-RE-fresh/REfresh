import { createStore } from "vuex";
import axios from "axios";
import mixins from "@/utils/mixins";

const store =  createStore({
  state: {
    token: null,
    annualList: [],
    calendarYear: new Date().getFullYear(),
    calendarMonth: new Date().getMonth() + 1,
    deptName: null,
    memberId: null,
    memberName: null,
    auth: null,
    deptList: [],
    searchInput: {
      memberName: "",
      departmentName: "",
      status: "",
    },
    currentPage : 1,
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
    getSearchInput: (state) => {
      return state.searchInput;
    },
    getCurrentPage : (state) => {
      return state.currentPage
    }
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
    setAnnualList: async (state,page) => {
      const res = await mixins.methods.$api(
        `admin/annual/${page}`,
        "get",
        { params: state.searchInput }
      );
      state.annualList = res.data;
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
    setAuth: (state, auth) => {
      state.auth = auth;
    },
    setSearchInput: (state, searchInput) => {
      state.searchInput = searchInput;
    },
    setCurrentPage1: (state,page) => {
      state.currentPage = page;
    },
  },
  actions: {
    updateToken({ commit }, token) {
      commit("setAccessToken", token);
      axios.defaults.headers.common.access_token = `Bearer ${token}`;
    },
    async checkCookie({commit}) {
      try {
        const res = await axios.post('token/cookie', {});
        commit('setAccessToken', res.data.access_token);
        commit('setMemberId', res.data.memberId);
        commit('setMemberName', res.data.memberName);
        commit('setAuth', res.data.auth);
        commit('setDept', res.data.deptName);
        return res;
      } catch (e) {
        console.log('Failed to Auth', e);
      }
    },
    
  },
  modules: {},
  plugins: [],
});




store.subscribe((mutation, state) => {
  if (mutation.type === 'setAccessToken') {
    axios.defaults.headers.common.access_token = `Bearer ${state.token}`;
  }
});



export default store;