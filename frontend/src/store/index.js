import { createStore } from "vuex";


export default createStore({
  state: {
    token: null,
    calendarYear: new Date().getFullYear(),
    calendarMonth: new Date().getMonth() + 1,
  },
  getters: {
    getAccessToken: (state) => {
      return state.token
    },
    getCalendar: (state) => {
      return {
        year: state.calendarYear,
        month: state.calendarMonth
      }
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
    }
  },
  actions: {},
  modules: {},
  plugins: []
});
