<template>
  <div class="navbar px-10">
    <div class="flex-1 ml-3">
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
          d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z"
        />
      </svg>
      <h3 class="normal-case text-3xl ml-3 font-mice-bold text-gray-600">
        내 연차 조회
      </h3>
    </div>
  </div>

  <form @submit="onFormSubmit($event)">
    <div
      class="flex bg-base-80 border border-slate-300 rounded-lg shadow-md shadow-slate-300/40 my-10 mx-14"
    >
      <div
        class="py-2 flex items-center justify-between space-x-16 w-full mx-6"
      >
        <div class="flex space-x-6 font-mice text-gray-600">
          <!-- 종류 검색 -->
          <div class="flex items-center space-x-3">
            <label>연차 종류</label>
            <select
              class="select select-bordered font-semibold pr-16"
              @change="setType($event)"
            >
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
          <div class="flex items-center space-x-3">
            <label>연차 상태</label>
            <select class="select select-bordered font-semibold pr-14" 
                    @change="setStatus($event)">
              <option
                v-for="(item, idx) in statusOptions"
                :key="idx"
                :value="item.value"
                class="font-mice"
              >
                {{ item.option }}
              </option>
            </select>
          </div>
          <!--연도 검색 -->
          <div class="flex items-center space-x-3 ">
            <label>연도</label>
            <select class="select select-bordered font-semibold pr-14" @change="setYear($event)">
              <option :value="0" class="font-mice">연도 선택</option>
              <option v-for="n in year" :key="n" :value="n" class="font-mice">
                {{ n }}년
              </option>
            </select>
          </div>
        </div>
        <button
          type="submit"
          class="bg-[#003566] hover:bg-[#f6aa1c] px-5 py-2 font-mice rounded-md text-white"
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
  await Store.dispatch("fetchHistoryList", 1);
}

onMounted(async () => {
  const current = new Date().getFullYear();
  const res = await mixins.methods.$api("/myPage/history/year", "get", {});
  for (let i = current; i >= res.data; i--) {
    year.value.push(i);
  }
  await Store.dispatch("fetchHistoryList", 1);
});
</script>

<style></style>
