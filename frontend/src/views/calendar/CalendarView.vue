<template>
    <data-table class="absolute top-1/2 left-1/2">
      <!-- 헤더 구현 -->
      <template v-slot:header >
        <calendar-header :year="yearMonth.year" :month="yearMonth.month" :dept="departments"
        @deptEvent="fetchCalendarByDept" @yearMonthEvent="fetchCalendarByRefsData" ></calendar-header>
      </template>
      <!-- 필터 구현 -->
    <template v-slot:filter>
      <a class="cursor-pointer relative" @click="subCalendar">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
          <path d="M12 20l-8-8h5v-8h6v8h5z"/>
        </svg>
      </a>
      <tr>
        <td v-for="(value, idx) in dayOfWeek" id="day-of-week" :key="idx" >
          {{ value }}
        </td>
      </tr>
    </template>
    <template v-slot:body>
      <calendar-main :calendar="calendar"></calendar-main>  
    </template>
  </data-table>
  <calendar-modal v-if="modalFlag" 
  :modalFlag="modalFlag" 
  :yearList="subCalData.yearList" 
  :monthList="subCalData.monthList"
  :curYear="yearMonth.year"
  :curMonth="yearMonth.month"
  @closeModal="subCalendar"
  @yearMonthEvent="fetchCalendarByRefsData"
  ></calendar-modal>
  <the-header></the-header>
  <the-sidebar></the-sidebar>
</template>
<script setup>
import { ref,onMounted, reactive, watch } from 'vue';
import CalendarMain from '@/components/calendar/CalendarMain';
import TheSidebar from '@/components/sidebar/TheSidebar.vue';
import TheHeader from '@/components/header/TheHeader.vue';
import CalendarModal from '@/components/calendar/CalendarModal';
import CalendarHeader from '@/components/calendar/CalendarHeader';
import DataTable from "@/components/calendar/data-table";
import Store  from '@/store/index.js';
import mixins from '@/utils/mixins';

// 상수들
const dayOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
const yearMonth = reactive({
  year: Store.state.calendarYear,
  month: Store.state.calendarMonth,
});

// 반응형 변수들
const calendar = ref([]);
const subCalData = reactive({
  yearList: [],
  monthList: []
})
const departments = ref([]);
const selectedDept = ref('');
const modalFlag = ref(false);

// 렌더링 사이클
onMounted(async () => {
  const params = makeParams();
  const resCal = await mixins.methods.$api(`calendar`, 'get', { params });
  const resDept = await mixins.methods.$api(`calendar/department`, 'get', {});
  const cal = makeCalendarDom(resCal);
  calendar.value = cal;
  departments.value = resDept.data;
});

// modalFlag를 통해 서브 캘린더를 렌더링
watch(modalFlag, () => {
  if (subCalData.monthList.length === 0
    && subCalData.yearList.length === 0) {
    makeSubCalendar();
  }
});




// 서브 캘린더 생성
function makeSubCalendar() {
  const year = 1900;
  const month = 0;
  subCalData.yearList = Array.from({ length: 200 }, (_, i) => year + i);
  subCalData.monthList = Array.from({ length: 12 }, (_, i) => month + i + 1);
}
// 서브 캘린더 토글
function subCalendar() {
  modalFlag.value = !modalFlag.value;
}

// param 생성
function makeParams(arg) {
  return {
    year: yearMonth.year,
    month: yearMonth.month,
    deptName: arg ? arg : '개발팀'
  }
}

// calendar dom 생성
function makeCalendarDom(cal) {
  const arr = [];
  for (let i = 0; i < cal.data.length; i += 7) {
    arr.push(cal.data.slice(i, i + 7));
  }
  return arr;
}
function updateCalendar(emit) {
  yearMonth.year = emit.year;
  yearMonth.month = emit.month;
}


// data fetching 영역
async function getCalendar(deptName) {
  const params = makeParams(deptName);
  const resCal = await mixins.methods.$api(`calendar`, 'get', { params })
  return makeCalendarDom(resCal);
}

async function fetchCalendarByRefsData(emit) {
  updateCalendar(emit);
  calendar.value = await getCalendar(selectedDept.value);
}

async function fetchCalendarByDept(deptName) {
  selectedDept.value = deptName;  
  calendar.value = await getCalendar(deptName);
}


</script>

<style scoped>
 
</style>