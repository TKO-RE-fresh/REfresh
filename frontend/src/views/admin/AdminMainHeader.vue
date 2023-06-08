<template>
  <div>
    <div class="navbar px-10">
      <div class="flex-1 ml-3">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          strokeWidth="{1.5}"
          stroke="currentColor"
          class="w-10 h-10 text-gray-600"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            d="M2.25 7.125C2.25 6.504 2.754 6 3.375 6h6c.621 0 1.125.504 1.125 1.125v3.75c0 .621-.504 1.125-1.125 1.125h-6a1.125 1.125 0 01-1.125-1.125v-3.75zM14.25 8.625c0-.621.504-1.125 1.125-1.125h5.25c.621 0 1.125.504 1.125 1.125v8.25c0 .621-.504 1.125-1.125 1.125h-5.25a1.125 1.125 0 01-1.125-1.125v-8.25zM3.75 16.125c0-.621.504-1.125 1.125-1.125h5.25c.621 0 1.125.504 1.125 1.125v2.25c0 .621-.504 1.125-1.125 1.125h-5.25a1.125 1.125 0 01-1.125-1.125v-2.25z"
          />
        </svg>
        <h3 class="normal-case text-3xl ml-3 font-mice-bold text-gray-600">
          관리자 페이지
        </h3>

        <!-- <a class="btn btn-ghost normal-case text-xl">연차 신청</a> -->
      </div>
      <div class="flex-none">
        <div class="tabs font-mice font-semibold">
          <span
            id="0"
            :class="{ 'tab-active text-blue-500 bg-blue-100': 0 === value }"
            class="tab tab-lg tab-lifted"
            @click="tabHandler($event)"
            >사원 조회</span
          >
          <span
            id="1"
            :class="{ 'tab-active text-blue-500 bg-blue-100': 1 === value }"
            class="tab tab-lg tab-lifted"
            @click="tabHandler($event)"
            >사원 생성</span
          >
          <span
            id="2"
            :class="{ 'tab-active text-blue-500 bg-blue-100': 2 === value }"
            class="tab tab-lg tab-lifted"
            @click="tabHandler($event)"
            >연차</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from "vue";
const value = ref(0);
const emit = defineEmits(["selectedTab"]);

const tabHandler = (e) => {
  const id = e.target.id;
  value.value = Number(id);
  emit("selectedTab", value.value);
};
</script>
