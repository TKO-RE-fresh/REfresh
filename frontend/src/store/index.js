import { createStore } from "vuex";
import mixins from "@/utils/mixins";

export default createStore({
  state: {
    token: null,
    annualList: [],
    calendarYear: new Date().getFullYear(),
    calendarMonth: new Date().getMonth() + 1,
    deptName: null,
    memberId: null,
    memberName: null,
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
    setSearchInput: (state, searchInput) => {
      state.searchInput = searchInput;
    },
    setCurrentPage1: (state,page) => {
      state.currentPage = page;
    },
  },
  actions: {},
  modules: {},
  plugins: [],
});
