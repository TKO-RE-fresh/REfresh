import { createStore } from "vuex";


export default createStore({
  state: {
    token: null,
    calendarYear: new Date().getFullYear(),
    calendarMonth: new Date().getMonth() + 1,
    deptName: null,
    memberId: null,
    memberName: null,
    auth: null,
    deptList: []
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
    getMemberId: (state) => {
      return state.memberId;
    },
    getMemberName: (state) => {
      return state.memberName;
    },
    getAuth: (state) => {
      return state.auth;
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
  },
  actions: {},
  modules: {},
  plugins: []
});
