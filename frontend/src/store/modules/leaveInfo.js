import mixins from '@/utils/mixins';
// import axios from "axios";

export default {
  state: {
    restLeave: null,
    usedLeave: null,
  },
  getters: {
    getRestLeave: (state) => state.restLeave,
    getUsedLeave: (state) => state.usedLeave,
  },
  mutations: {
    setRestLeave(state, restLeave) {
      state.restLeave = restLeave;
    },
    setUsedLeave(state, usedLeave) {
      state.usedLeave = usedLeave;
    },
  },
  actions: {
    async fetchLeaveInfo({ commit }) {
      try {
        const response = await mixins.methods.$api("leaveRequest", "get", {});
        console.log(
          "store에서 rest휴가 정보 받아오기 (response): " +
            response.data.restLeaveCount
        );
        console.log(
          "store에서 used휴가 정보 받아오기 (response): " +
            response.data.usedLeaveCount
        );
        commit("setRestLeave", response.data.restLeaveCount);
        commit("setUsedLeave", response.data.usedLeaveCount);
      } catch (error) {
        console.log(
          "휴가 정보를 가져오는데 실패했습니다. 에러메시지: " + error
        );
      }
    },
  },
};
