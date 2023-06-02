import { createStore } from "vuex";
import mixins from "@/utils/mixins";


export default createStore({
  state: {
    token: null,
<<<<<<< HEAD
    annualList : [],
=======
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
    calendarYear: new Date().getFullYear(),
    calendarMonth: new Date().getMonth() + 1,
    deptName: null,
    memberId: null,
    memberName: null,
<<<<<<< HEAD
    deptList: [],
    searchInput : {
      memberName: '',
      departmentName: '',
      status: ''
    }, 
    page : 1,
=======
    deptList: []
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
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
<<<<<<< HEAD
    },
    getAnnualList : (state) => {
      return state.annualList;
    },
    getSearchInput :(state) => {
      return state.searchInput;
    },
    getPage: (state) => {
      return state.page;
=======
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
    }
  },
  mutations: {
    setAccessToken: (state, token) => {
      state.token = token;
    },
<<<<<<< HEAD
    setAnnualList : async (state) => {
      const res = await mixins.methods.$api(`admin/annual/${state.page}`,'get', { params:state.searchInput })
      state.annualList = res.data;
    },
=======
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
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
<<<<<<< HEAD
    },
    setSearchInput : (state , searchInput) => {
      state.searchInput = searchInput;
    },
    setPage : (state, page) => {
      state.page = page;
=======
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
    }
  },
  actions: {},
  modules: {},
  plugins: []
});
