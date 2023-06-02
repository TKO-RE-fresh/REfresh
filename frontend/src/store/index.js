import { createStore } from "vuex";
import mixins from "@/utils/mixins";


export default createStore({
  state: {
    token: null,
    annualList : [],
  },
  getters: {
    getAccessToken: (state) => {
      return state.token
    },
    getAnnualList : (state) => {
      return state.annualList;
    }

  },
  mutations: {
    setAccessToken: (state, token) => {
      state.token = token;
    },
    setAnnualList : async( state, page, searchInput) => {
      const res = await mixins.methods.$api(`admin/annual/${page}`,'get', { params:searchInput })
      state.annualList = res.data;
    }
  },
  actions: {},
  modules: {},
  plugins: []
});
