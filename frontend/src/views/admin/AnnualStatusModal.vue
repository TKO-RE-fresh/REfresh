<template>
  <transition name="fade">
    <div v-if="isOpen">
      <div class="fixed inset-0 z-10 flex items-center justify-center">
        <div
          class="w-full max-w-lg p-3 relative mx-auto my-auto rounded-xl shadow-lg bg-white"
        >
          <div>
            <div class="text-center p-3 flex-auto justify-center leading-6">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="w-16 h-16 flex items-center text-blue-500 mx-auto"
                viewBox="0 0 24 24"
                fill="currentColor"
              >
                <path
                  d="M12.92,16.12a.76.76,0,0,0-.09-.18,1.58,1.58,0,0,0-.12-.15l-.15-.12-.18-.09a.6.6,0,0,0-.19-.06,1,1,0,0,0-.9.27l-.12.15a.76.76,0,0,0-.09.18.64.64,0,0,0-.06.18,1.36,1.36,0,0,0,0,.2,1,1,0,0,0,.08.38,1.15,1.15,0,0,0,.21.33,1,1,0,0,0,1.09.22,1.46,1.46,0,0,0,.33-.22,1.46,1.46,0,0,0,.22-.33A1,1,0,0,0,13,16.5a1.36,1.36,0,0,0,0-.2A.64.64,0,0,0,12.92,16.12ZM12,10.5a1,1,0,0,0-1,1v2a1,1,0,0,0,2,0v-2A1,1,0,0,0,12,10.5Zm7-5H12.72l-.32-1a3,3,0,0,0-2.84-2H5a3,3,0,0,0-3,3v13a3,3,0,0,0,3,3H19a3,3,0,0,0,3-3V8.5A3,3,0,0,0,19,5.5Zm1,13a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V5.5a1,1,0,0,1,1-1H9.56a1,1,0,0,1,.95.68l.54,1.64A1,1,0,0,0,12,7.5h7a1,1,0,0,1,1,1Z"
                />
              </svg>
              <h2 class="text-2xl font-bold py-4">연차 승인 여부</h2>
              <div class="p-3 items-center">
                <label class="item-center">
                  <input
                    v-model="acceptYn"
                    type="radio"
                    name="radio-10"
                    value="y"
                    class="radio checked:bg-green-500 pt-2"
                    checked
                  />
                  <span class="mx-3">승인</span>
                </label>
              </div>

              <div class="p-3">
                <label>
                  <input
                    v-model="acceptYn"
                    type="radio"
                    name="radio-10"
                    value="n"
                    class="radio checked:bg-red-500"
                  />
                  <span class="mx-3">반려</span>
                </label>
              </div>

              <div v-if="acceptYn == 'n'" class="form-control mx-10">
                <label class="label">
                  <span class="label-text text-gray-700">반려 사유</span>
                </label>
                <textarea
                  v-model="rejectReason"
                  class="textarea textarea-bordered h-24"
                  placeholder="반려 사유 입력"
                ></textarea>
              </div>
            </div>

            <div class="p-3 mt-2 text-center space-x-4 md:block font-mice">
              <button
                class="mb-2 md:mb-0 bg-[#003566] px-5 py-2 text-sm shadow-sm font-medium tracking-wider border text-white rounded-md hover:shadow-lg hover:bg-[#f6aa1c]"
                @click="accetpHandler"
              >
                확인
              </button>
              <button
                class="mb-2 md:mb-0 bg-gray-300 px-5 py-2 text-sm shadow-sm font-medium tracking-wider text-white rounded-md hover:shadow-lg hover:bg-[#f6aa1c]"
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
import Swal from "sweetalert2/dist/sweetalert2.js";
import { defineProps, ref, inject } from "vue";
import axios from "axios";
import Store from "@/store/index.js";

// import mixins from '@/utils/mixins';

const props = defineProps({
  isOpen: {
    type: Boolean,
    required: true,
  },
  onToggle: {
    type: Function,
    required: true,
  },
  uid: {
    type: String,
    required: true,
  },
});
const acceptYn = ref("y");
const rejectReason = ref("");
const currentPage = inject("currentPage");

function makeBody() {
  return {
    uid: props.uid,
    status: acceptYn.value == "y" ? "AGREE" : "REJECT",
    rejectReason: rejectReason.value,
  };
}

const accetpHandler = async () => {
  const body = makeBody();
  await axios
    .put("http://localhost:80/admin/annual", body)
    .then(() => {
      Swal.fire({
        toast: true,
        icon: "success",
        title: "연차 신청 처리 성공",
        timer: 1500,
        timerProgressBar: true,
        showConfirmButton: false,
      });
    })
    .catch(() => {
      Swal.fire({
        toast: true,
        icon: "error",
        title: "연차 신청 처리 실패",
        timer: 1500,
        timerProgressBar: true,
        showConfirmButton: false,
      });
    });
  await Store.dispatch("fetchAnnualList", currentPage.value);
  props.onToggle();
};
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
