import { createStore } from "vuex";
import mixins from "@/utils/mixins";

export default createStore({
  state: {
    token: null,
    annualList: [],
    historyList : [],
    calendarYear: new Date().getFullYear(),
    calendarMonth: new Date().getMonth() + 1,
    deptName: null,
    memberId: null,
    memberName: null,
    deptList: [],
    manageSearch: {
      memberName: "",
      departmentName: "",
      status: "",
    },
    historySearch : {
      status: '',
      type: '',
      year: 0
    }
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
    getManageSearch: (state) => {
      return state.manageSearch;
    },
    getHistoryList: (state) => {
      return state.historyList;
    },
    getHistorySearch: (state) => {
      return state.historySearch;
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
        { params: state.manageSearch }
      );
      state.annualList = res.data;
    },
    setHistoryList: async (state, page) => {
      const res = await mixins.methods.$api(
        `myPage/history/${page}`,
        'get',
        {params: state.historySearch}
      )
      state.historyList = res.data;
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
    setHistorySearch: (state,searchInput) =>{
      state.historySearch = searchInput;
    },
  },
  actions: {},
  modules: {},
  plugins: [],
});