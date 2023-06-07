<template>
  <transition name="fade">
    <div v-if="isOpen">
      <div class="fixed inset-0 z-10 flex items-center justify-center">
        <div
          class="w-full max-w-md p-3 relative mx-auto my-auto rounded-xl shadow-lg bg-white"
        >
          <div>
            <div class="text-center p-3 flex-auto justify-center leading-6">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-16 h-16 items-center text-blue-500 mx-auto"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M8.625 9.75a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H8.25m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H12m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0h-.375m-13.5 3.01c0 1.6 1.123 2.994 2.707 3.227 1.087.16 2.185.283 3.293.369V21l4.184-4.183a1.14 1.14 0 01.778-.332 48.294 48.294 0 005.83-.498c1.585-.233 2.708-1.626 2.708-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0012 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018z"
                />
              </svg>
              <h2 class="text-2xl font-bold py-4">연차 반려 상세</h2>

              <div class="px-6 py-3 text-gray-600 ">
                <p>
                  {{props.data.annualType}}
                </p>
                <p>
                  {{props.data.startDate}} ~ {{ props.data.endDate }}
                </p>
              </div>
              <div class="px-6 py-3 ">
                <label class="label">
                  <span class="label-text text-gray-700">반려 사유</span>
                </label>
                <textarea
                  :value="props.data.rejectReason"
                  readonly
                  class="textarea textarea-bordered h-24 w-full text-gray-500"
                ></textarea>
              </div>
            </div>
            <div class="p-3 text-center space-x-4 md:block">
              <button
                class="mb-2 md:mb-0 bg-blue-500 border border-blue-500 px-5 py-2 text-sm shadow-sm font-medium tracking-wider text-white rounded-md hover:shadow-lg hover:bg-blue-600"
                @click="onToggle"
              >
                닫기
              </button>
            </div>
          </div>
        </div>
      </div>
      <div
        class="fixed inset-0 bg-black opacity-70 z-0"
        @click="onToggle"
      ></div>
    </div>
  </transition>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  isOpen: {
    type: Boolean,
    required: true,
  },
  onToggle: {
    type: Function,
    required: true,
  },
  data: {
    type: Object,
    required: true,
  },
});
</script>

<style scoped>
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 500ms ease-out;
}
</style>
F
