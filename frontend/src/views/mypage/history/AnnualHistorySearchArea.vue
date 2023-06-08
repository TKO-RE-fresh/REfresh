<template>
  <div class="navbar px-10">
    <div class="flex-1 ml-5">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="currentColor"
        class="w-10 h-10 text-gray-600"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          d="M15.666 3.888A2.25 2.25 0 0013.5 2.25h-3c-1.03 0-1.9.693-2.166 1.638m7.332 0c.055.194.084.4.084.612v0a.75.75 0 01-.75.75H9a.75.75 0 01-.75-.75v0c0-.212.03-.418.084-.612m7.332 0c.646.049 1.288.11 1.927.184 1.1.128 1.907 1.077 1.907 2.185V19.5a2.25 2.25 0 01-2.25 2.25H6.75A2.25 2.25 0 014.5 19.5V6.257c0-1.108.806-2.057 1.907-2.185a48.208 48.208 0 011.927-.184"
        />
      </svg>

      <h3 class="normal-case text-3xl font-mice-bold text-gray-600 ">내 연차 조회</h3>
    </div>
  </div>

  <form @submit="onFormSubmit($event)">
    <div
      class="flex bg-base-80 border border-slate-300 rounded-lg shadow-md shadow-slate-300/40 my-10 mx-16"
    >
      <div
        class="py-2 flex items-center justify-between space-x-16 w-full mx-10"
      >
        <div class="flex space-x-6">
          <!-- 종류 검색 -->
          <div class="flex items-center space-x-3 font-nanum">
            <label>연차 종류</label>
            <select class="select select-bordered" @change="setType($event)">
              <option
                v-for="(item, idx) in typeOptions"
                :key="idx"
                :value="item.value"
              >
                {{ item.option }}
              </option>
            </select>
          </div>
          <!-- 연차 상태 -->
          <div class="flex items-center space-x-3 font-mice">
            <label>연차 상태</label>
            <select class="select select-bordered" @change="setStatus($event)">
              <option
                v-for="(item, idx) in statusOptions"
                :key="idx"
                :value="item.value"
              >
                {{ item.option }}
              </option>
            </select>
          </div>
          <!--연도 검색 -->
          <div class="flex items-center space-x-3 font-Gmarket">
            <label>연도</label>
            <select class="select select-bordered" @change="setYear($event)">
              <option :value="0">연도 선택</option>
              <option v-for="n in year" :key="n" :value="n">{{ n }}년</option>
            </select>
          </div>
        </div>
        <button
          type="submit"
          class="bg-rose-400 px-5 py-2 rounded-xl text-white hover:bg-blue-700"
        >
          조회
        </button>
      </div>
    </div>
  </form>
</template>

<script setup>
import { onMounted, ref, reactive } from "vue";
import Store from "@/store/index.js";
import mixins from "@/utils/mixins";


const statusOptions = [
  { option: "상태 선택", value: "" },
  { option: "대기", value: "WAITING" },
  { option: "승인", value: "AGREE" },
  { option: "반려", value: "REJECT" },
  { option: "취소", value: "CANCEL" },
];

const typeOptions = [
  { option: "종류 선택", value: "" },
  { option: "연차", value: "ANNUAL_LEAVE" },
  { option: "오전 반차", value: "MORNING_HALF" },
  { option: "오후 반차", value: "AFTERNOON_HALF" },
];

const year = ref([]);

const searchInput = reactive({
  status: "",
  type: "",
  year: 0,
});

function setStatus(e) {
  searchInput.status = e.target.value;
}

function setType(e) {
  searchInput.type = e.target.value;
}

function setYear(e) {
  searchInput.year = e.target.value;
}


async function onFormSubmit(e) {
  e.preventDefault();
  Store.commit("setHistorySearch", searchInput);
  await Store.dispatch('fetchHistoryList', 1)
}

onMounted(async () => {
  const current = new Date().getFullYear();
  const res = await mixins.methods.$api("/myPage/history/year", "get", {});
  for (let i = current; i >= res.data; i--) {
    year.value.push(i);
  }
  await Store.dispatch('fetchHistoryList', 1);

});
</script>

<style>
@font-face {
    font-family: 'MICEGothic Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-01@1.0/MICEGothic Bold.woff2') format('woff2');
    font-weight: 700;
    font-style: normal;
}

@font-face {
    font-family: 'MICEGothic';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-01@1.0/MICEGothic.woff2') format('woff2');
    font-weight: 400;
    font-style: normal;
}

.font-mice-bold {
  font-family: 'MICEGothic Bold';
}

.font-mice {
  font-family: 'MICEGothic';
}
</style>
