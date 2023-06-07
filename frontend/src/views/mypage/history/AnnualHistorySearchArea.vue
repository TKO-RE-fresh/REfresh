<template>
  <div class="navbar px-10">
    <div class="flex-1">
      <h3 class="normal-case text-3xl font-bold">내 연차 조회</h3>
    </div>
  </div>

  <form @submit="onFormSubmit($event)">
    <div
      class="flex bg-base-80 border border-slate-300 rounded-lg shadow-md shadow-slate-300/40 my-10 mx-16"
    >
      <div class="py-2 flex items-center justify-between space-x-16 w-full mx-10">
        <div class="flex space-x-6">
          <!-- 종류 검색 -->
          <div class="flex items-center space-x-3">
            <label>연차 종류</label>
            <select class="select select-bordered" 
                    @change="setType($event)">
              <option v-for="(item,idx) in typeOptions" 
                      :key="idx"
                      :value="item.value">{{ item.option }}</option>
            </select>
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
          <!--연도 검색 -->
          <div class="flex items-center space-x-3">
            <label>연도</label>
            <select class="select select-bordered" 
                    @change="setYear($event)">
              <option :value="0">연도 선택</option>
              <option v-for=" n in year" 
                      :key="n"
                      :value="n">{{n}}년</option>
            </select>
          </div>
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
</template>

<script setup>
import { onMounted, ref, reactive} from 'vue';
import Store from "@/store/index.js";
import mixins from "@/utils/mixins";

const statusOptions = [
  {option : "상태 선택" , value : ""},
  {option : "대기" , value : "WAITING" },
  {option : "승인" , value : "AGREE" },
  {option : "반려" , value : "REJECT" },
  {option : "취소" , value : "CANCEL" },
];

const typeOptions = [
    {option : "종류 선택" , value : ""},
    {option : "연차" , value : "ANNUAL_LEAVE"},
    {option : "오전 반차" , value : "MORNING_HALF" },
    {option : "오후 반차" , value : "AFTERNOON_HALF"},
]

const year = ref([]);

const searchInput = reactive({
  status: '',
  type: '',
  year: 0
});

function setStatus(e){
  searchInput.status = e.target.value;
}

function setType(e){
  searchInput.type = e.target.value;
}

function setYear(e){
  searchInput.year = e.target.value;
}

const currentPage = ref(1);
const totalPages = ref(0);


function onFormSubmit(e) {
  e.preventDefault();
  Store.commit("setHistorySearch",searchInput);
  console.log(Store.state.historySearch);
  Store.commit("setHistoryList",1);
}

onMounted(async() => {
  const current = new Date().getFullYear();
  const res = await mixins.methods.$api('/myPage/history/year','get',{});
  for(let i = current ; i >= res.data ; i--){
    year.value.push(i);
  }
  Store.commit("setHistoryList", currentPage.value);
  totalPages.value = Store.state.historyList.totalPages;
});

</script>