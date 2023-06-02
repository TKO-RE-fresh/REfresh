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
<<<<<<< HEAD
    auth: null,
    deptList: []
=======
    deptList: [],
    searchInput: {
      memberName: "",
      departmentName: "",
      status: "",
    },
    page: 1,
>>>>>>> f8529818cdf89fd77d50027f41936c9a1ea4053d
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
<<<<<<< HEAD
    getMemberId: (state) => {
      return state.memberId;
    },
    getMemberName: (state) => {
      return state.memberName;
    },
    getAuth: (state) => {
      return state.auth;
    }
=======
    getAnnualList: (state) => {
      return state.annualList;
    },
    getSearchInput: (state) => {
      return state.searchInput;
    },
    getPage: (state) => {
      return state.page;
    },
>>>>>>> f8529818cdf89fd77d50027f41936c9a1ea4053d
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
    setAnnualList: async (state) => {
      const res = await mixins.methods.$api(
        `admin/annual/${state.page}`,
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
<<<<<<< HEAD
    setAuth: (state, auth) => {
      state.auth = auth;
=======
    setSearchInput: (state, searchInput) => {
      state.searchInput = searchInput;
    },
    setPage: (state, page) => {
      state.page = page;
>>>>>>> f8529818cdf89fd77d50027f41936c9a1ea4053d
    },
  },
  actions: {},
  modules: {},
  plugins: [],
});
