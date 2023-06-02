import { createStore } from "vuex";
import mixins from "@/utils/mixins";


export default createStore({
  state: {
    token: null,
    annualList : [],
    calendarYear: new Date().getFullYear(),
    calendarMonth: new Date().getMonth() + 1,
    deptName: null,
    memberId: null,
    memberName: null,
    deptList: [],
    searchInput : {
      memberName: '',
      departmentName: '',
      status: ''
    }, 
    page : 1,
  },
  getters: {
    getAccessToken: (state) => {
      return state.token;
    },
    getCalendar: (state) => {
      return {
        year: state.calendarYear,
        month: state.calendarMonth
      }
    },
    getDepartment: (state) => {
      return state.deptName;
    },
    getDepartmentList: (state) => {
      return state.deptList;
    },
    getAnnualList : (state) => {
      return state.annualList;
    },
    getSearchInput :(state) => {
      return state.searchInput;
    },
    getPage: (state) => {
      return state.page;
    }
  },
  mutations: {
    setAccessToken: (state, token) => {
      state.token = token;
    },
    setAnnualList : async (state) => {
      const res = await mixins.methods.$api(`admin/annual/${state.page}`,'get', { params:state.searchInput })
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
    setSearchInput : (state , searchInput) => {
      state.searchInput = searchInput;
    },
    setPage : (state, page) => {
      state.page = page;
    }
  },
  actions: {},
  modules: {},
  plugins: []
});
