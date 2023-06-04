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
                  class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
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
              class="px-3 py-3"
            >
              {{ item }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(row, idx) in Store.state.annualList.content"
            :key="idx"
            class="bg-white border-b hover:bg-blue-50"
          >
            <td class="w-4 p-3">
              <div class="flex items-center">
                <input
                  id="checkbox-table-search-1"
                  type="checkbox"
                  class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                />
                <label for="checkbox-table-search-1" class="sr-only"
                  >checkbox</label
                >
              </div>
            </td>
            <td class="px-3 py-3">{{ row.createdDate }}</td>
            <th
              scope="row"
              class="flex items-center px-3 py-2 text-gray-900 whitespace-nowrap dark:text-white"
            >
              <div class="pl-3">
                <div class="text-base font-semibold">{{ row.memberName }}</div>
                <div class="font-normal text-gray-500">
                  {{ row.email }}
                </div>
              </div>
            </th>
            <td class="px-3 py-3">{{ row.departmentName }}</td>
            <td class="px-3 py-3">{{ row.annualType }}</td>
            <td class="px-3 py-3">{{ row.startDate }} ~ {{ row.endDate }}</td>
            <td class="px-3 py-3" @click="handleModal(row.annualStatus, row.annualUid)" >
              <div :class="row.annualStatus == '대기' ? 'tooltip': '' " data-tip='Click!'  class="flex items-center">
                <div 
                  :class="row.annualStatus == '승인' ? 'bg-green-500' : row.annualStatus == '대기'? 'bg-gray-200': 'bg-red-500'
                  "
                  class="h-2.5 w-2.5 rounded-full mr-2"
                ></div>
                {{ row.annualStatus }}
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <AnnualStatusModal :isOpen="isOpen" :onToggle="onToggle" :uid = "uid"></AnnualStatusModal>
</template>
<script setup>
import {ref } from "vue";
import AnnualStatusModal from "./AnnualStatusModal.vue";
import Store from "@/store/index.js";

const header = ['신청일','사원명','부서명','연차 종류','기간','상태']
const isOpen = ref(false);
const uid = ref('');

const onToggle = () => {
  isOpen.value = !isOpen.value;
};

const handleModal=(data,id) =>{
  if(data == '대기'){
    uid.value = id;
    onToggle();
  }
}

</script>
