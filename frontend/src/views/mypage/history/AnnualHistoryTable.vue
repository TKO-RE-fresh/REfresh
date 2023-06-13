<template>
  <div class="px-14">
    <div class="border border-slate-200 rounded-xl overflow-hidden">
      <table class="w-full text-sm text-left text-gray-500">
        <thead class="text-md text-gray-800 uppercase bg-gray-50">
          <tr>
            <th scope="col" class="p-3">
              <div class="flex items-center">
                <input
                  id="checkbox-all-search"
                  type="checkbox"
                  class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500"
                />
                <label for="checkbox-all-search" class="sr-only"
                  >checkbox</label
                >
              </div>
            </th>
            <th
              v-for="(item, idx) in header"
              :key="idx"
              scope="col"
              class="px-3 py-3 font-mice-bold"
            >
              {{ item }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(row, idx) in Store.state.historyList.content"
            :key="idx"
            class="bg-white font-mice border-b hover:bg-blue-50"
          >
            <td class="w-4 p-3">
              <div class="flex items-center">
                <input
                  id="checkbox-table-search-1"
                  type="checkbox"
                  class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"
                />
                <label for="checkbox-table-search-1" class="sr-only"
                  >checkbox</label
                >
              </div>
            </td>
            <td class="px-3 py-3">{{ row.createdDate }}</td>
            <td class="px-3 py-3">{{ row.startDate }} ~ {{ row.endDate }}</td>
            <td class="px-3 py-3">{{ row.annualType }}</td>
            <th
              scope="row"
              class="flex items-center px-3 py-2 text-gray-800 whitespace-nowrap"
            >
              <div>
                <div class="text-base text-gray-700">
                  {{ row.acceptorName }}
                </div>
                <div class="text-gray-400 font-thin">
                  {{ row.acceptorEmail }}
                </div>
              </div>
            </th>
            <td class="px-3 py-3">
              <div
                :class="row.annualStatus == '대기' ? 'tooltip' : ''"
                data-tip="Click!"
                class="flex items-center"
              >
                <div
                  :class="
                    row.annualStatus == '승인'
                      ? 'bg-green-500'
                      : row.annualStatus == '대기'
                      ? 'bg-gray-200'
                      : 'bg-red-500'
                  "
                  class="h-2.5 w-2.5 rounded-full mr-2"
                ></div>
                {{ row.annualStatus }}
              </div>
            </td>
            <th>
              <div v-if="row.annualStatus != '반려'">
                <button
                  :disabled="isDisabled(row)"
                  class="bg-[#003566] hover:bg-[#f6aa1c] disabled:bg-gray-300 text-white font-mice font- py-1 px-2 ml-6 rounded"
                  @click="cancelHandeler(row.annualUid, row.startDate)"
                >
                  취소
                </button>
              </div>
              <div v-else>
                <button class="p-4 ml-3" @click="detailHandeler(row)">
                  <div>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke-width="1.5"
                      stroke="currentColor"
                      class="w-6 h-6 ml-1"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"
                      />
                    </svg>
                  </div>
                </button>
              </div>
            </th>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <RejectDetail
    :isOpen="isOpen"
    :onToggle="onToggle"
    :data="rejectData"
  ></RejectDetail>
</template>

<script setup>
import Swal from "sweetalert2/dist/sweetalert2.js";
import { ref, inject } from "vue";
import axios from "axios";
import RejectDetail from "./RejectDetailModal.vue";
import Store from "@/store/index.js";

const header = ["신청일", "기간", "연차 종류", "승인자", "상태", "취소/상세"];

const isDisabled = (data) => {
  const dateParts = data.startDate.split(" ");
  const year = parseInt(dateParts[0].replace("년", ""));
  const month = parseInt(dateParts[1].replace("월", "")) - 1;
  const day = parseInt(dateParts[2].replace("일", ""));
  const startDate = new Date(year, month, day);
  return Date.now() >= startDate || data.annualStatus == "취소";
};

const currentPage = inject("currentPage");
console.log(currentPage);

const cancelHandeler = (uid) => {
  Swal.fire({
    title: "취소",
    text: "연차신청을 취소하시겠습니까?",
    icon: "info",
    showCancelButton: true,
    cancelButtonText: "취소",
    confirmButtonColor: "#3b82f6",
    confirmButtonText: "확인",
  }).then(async (result) => {
    if (result.isConfirmed) {
      await axios
        .put("http://localhost:80/myPage/history", { uid })
        .then(() => {
          Store.dispatch("fetchLeaveInfo");
          Swal.fire("취소!", "연차 신청을 취소 하였습니다.", "success");
        })
        .catch(() => {
          Swal.fire(
            "실패",
            "연차 취소를 실패 하였습니다. 다시 시도해 주세요.",
            "error"
          );
        })
        .finally(() => {
          Store.dispatch("fetchHistoryList", currentPage.value);
        });
    }
  });
};

const isOpen = ref(false);
const rejectData = ref({});

const onToggle = () => {
  isOpen.value = !isOpen.value;
};

const detailHandeler = (data) => {
  onToggle();
  rejectData.value = data;
};
</script>
