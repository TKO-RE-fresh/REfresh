import { createStore } from "vuex";


export default createStore({
  state: {
    token: null,
  },
  getters: {
    getAccessToken: (state) => {
      return state.token
    }
  },
  mutations: {
    setAccessToken: (state, token) => {
      state.token = token;
    }
  },
  actions: {},
  modules: {},
  plugins: []
});
