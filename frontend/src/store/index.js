import { createStore } from "vuex";


export default createStore({
  state: {
    token: null,
    calendarYear: new Date().getFullYear(),
    calendarMonth: new Date().getMonth() + 1,
    deptName: null,
    memberId: null,
    memberName: null,
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
    }
  },
  mutations: {
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
    }
  },
  actions: {},
  modules: {},
  plugins: []
});
