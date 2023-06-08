<template>
  <form @submit="onFormSubmit($event)">
    <div
      class="flex bg-base-80 border border-slate-300 rounded-lg shadow-md shadow-slate-300/40 my-10 mx-16"
    >
      <div class="py-2 flex items-center justify-between space-x-16 w-full mx-10">
        <div class="flex space-x-6">
          <!-- 부서 검색 -->
          <div class="flex items-center space-x-3">
            <label>부서명</label>

            <select class="select select-bordered" 
                    @change="setDepartment($event)">
              <option v-for="(item,idx) in deptOptions" 
                      :key="idx"
                      :value="item.value">{{ item.option }}</option>
            </select>
          </div>
          <!-- 사원 검색 -->
          <div class="flex items-center space-x-3">
            <label>사원명</label>
            <input
              id="name"
              v-model="searchInput.memberName"
              type="text"
              placeholder="사원명 입력"
              class="input input-bordered"
            />
          </div>
          <!-- 연차 상태 -->
          <div class="flex items-center space-x-3">
            <label>연차 상태</label>
            <select class="select select-bordered"
                    @change="setStatus($event)">
              <option v-for="(item,idx) in statusOptions" 
                      :key="idx"
                      :value="item.value">{{item.option}}</option>
            </select>
          </div>

          <!-- 기간별 검색 -->
        </div>
        <button
          type="submit"
          class="bg-blue-500 px-5 py-3 rounded-xl text-white hover:bg-blue-700"
        >
          조회
        </button>
      </div>
    </div>
  </form>
  <AdminAnnualTable></AdminAnnualTable>
  <div>
        <PagingView :currentPage="currentPage" :totalPages="totalPages" @selectPage="selectPage"/>
  </div>
</template>
<script setup>
import { ref,reactive,onMounted ,provide } from 'vue';
import PagingView from '../../components/pagination/pagingComponent.vue';
import AdminAnnualTable from './AdminAnnualTable';
import Store from "@/store/index.js";


//import mixins from '@/utils/mixins';
// s

const deptOptions = [
  {option : "부서 선택", value : ""},
  {option : "인사팀", value : "인사팀"},
  {option : "영업팀", value : "영업팀"},
  {option : "개발팀", value : "개발팀"},
  {option : "디자인팀", value : "디자인팀"},
  {option : "서비스팀", value : "서비스팀"},
];
const statusOptions = [
  {option : "상태 선택" , value : ""},
  {option : "대기" , value : "WAITING" },
  {option : "승인" , value : "AGREE" },
  {option : "반려" , value : "REJECT" },
  {option : "취소" , value : "CANCEL" },
];
const searchInput = reactive({
  memberName: '',
  departmentName: '',
  status: ''
});

function setDepartment(e){
  searchInput.departmentName = e.target.value;
}

function setStatus(e){
  searchInput.status = e.target.value;
}

const currentPage = ref(1);
const totalPages = ref(0);

provide('currentPage',currentPage);

async function onFormSubmit(e) { 
  e.preventDefault();
  Store.commit("setManageSearch", searchInput);
  await Store.dispatch('fetchAnnualList', 1);
  totalPages.value = Store.state.annualList.totalPages;
}

onMounted(async () => {
  await Store.dispatch('fetchAnnualList', 1);
  totalPages.value=Store.state.annualList.totalPages;
});

async function selectPage(idx) {
  currentPage.value=idx;
  await Store.dispatch('fetchAnnualList', currentPage.value);
  totalPages.value = Store.state.annualList.totalPages;
}



</script>
