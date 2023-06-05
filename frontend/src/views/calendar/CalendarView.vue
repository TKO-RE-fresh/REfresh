<template>
  <div class="relative w-full h-full">
    <data-table class="absolute top-tableT left-tableL">
      <!-- 헤더 구현 -->
      <template v-slot:header>
        <calendar-header
          :year="yearMonth.year"
          :month="yearMonth.month"
          :dept="departments"
          @deptEvent="fetchCalendarByDept"
          @yearMonthEvent="fetchCalendarByRefsData"
        ></calendar-header>
        <a
          class="absolute left-subModalBtn top-10 inline-block cursor-pointer"
          @click="subCalendar"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 24 24"
          >
            <path d="M12 20l-8-8h5v-8h6v8h5z" />
          </svg>
        </a>
      </template>
      <template v-slot:body>
        <tr>
          <th
            v-for="(value, idx) in dayOfWeek"
            id="day-of-week"
            :key="idx"
            class="w-1/7"
          >
            {{ value }}
          </th>
        </tr>
        <calendar-main :calendar="calendar"></calendar-main>
      </template>
    </data-table>
    <calendar-modal
      v-if="modalFlag"
      :modalFlag="modalFlag"
      :yearList="subCalData.yearList"
      :monthList="subCalData.monthList"
      :curYear="yearMonth.year"
      :curMonth="yearMonth.month"
      :curX="curX"
      :curY="curY"
      @closeModal="closeSubCalendar"
      @yearMonthEvent="fetchCalendarByRefsData"
    ></calendar-modal>
    <div class="absolute top-tableT left-asideR">
      <calendar-aside></calendar-aside>
    </div>
  </div>
  <the-header></the-header>
  <the-sidebar></the-sidebar>
</template>
<script setup>
import { ref, reactive, watch, onMounted } from "vue";
import CalendarMain from "@/components/calendar/CalendarMain";
import TheSidebar from "@/components/sidebar/TheSidebar.vue";
import TheHeader from "@/components/header/TheHeader.vue";
import CalendarModal from "@/components/calendar/CalendarModal";
import CalendarHeader from "@/components/calendar/CalendarHeader";
import CalendarAside from "@/components/calendar/CalendarAside";
import DataTable from "@/components/calendar/data-table";
import Store from "@/store/index.js";
import mixins from "@/utils/mixins";

// 상수들
const dayOfWeek = ["일", "월", "화", "수", "목", "금", "토"];
const yearMonth = reactive({
  year: Store.state.calendarYear,
  month: Store.state.calendarMonth,
});
// 반응형 변수들
const calendar = ref([]);
const subCalData = reactive({
  yearList: [],
  monthList: [],
});
const curX = ref(0);
const curY = ref(0);
const departments = ref([]);
const selectedDept = ref("");
const modalFlag = ref(false);

onMounted(async () => {
  const params = makeParams();
  const calData = await mixins.methods.$api(`calendar`, "get", {
    params,
  });
  calendar.value = makeCalendarDom(calData);
  const deptData = await mixins.methods.$api(`calendar/department`, "get", {});
  departments.value = deptData.data;
  Store.commit("setDeptList", deptData.data);
});

// modalFlag를 통해 서브 캘린더를 렌더링
watch(modalFlag, () => {
  if (subCalData.monthList.length === 0 && subCalData.yearList.length === 0) {
    makeSubCalendar();
  }
});

function closeSubCalendar() {
  modalFlag.value = false;
}

// 서브 캘린더 생성
function makeSubCalendar() {
  const year = 1900;
  const month = 0;
  subCalData.yearList = Array.from({ length: 200 }, (_, i) => year + i);
  subCalData.monthList = Array.from({ length: 12 }, (_, i) => month + i + 1);
}
// 서브 캘린더 토글
function subCalendar(e) {
  curX.value = e.clientX;
  curY.value = e.clientY + 20;
  modalFlag.value = !modalFlag.value;
}

// param 생성
function makeParams(dept) {
  return {
    year: yearMonth.year,
    month: yearMonth.month,
    deptName: dept ? dept : Store.state.deptName,
  };
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
  const resCal = await mixins.methods.$api(`calendar`, "get", { params });
  return makeCalendarDom(resCal);
}

async function fetchCalendarByRefsData(emit) {
  updateCalendar(emit);
  calendar.value = await getCalendar(selectedDept.value);
}

async function fetchCalendarByDept(deptName) {
  selectedDept.value = deptName;
  calendar.value = await getCalendar(deptName);
  Store.commit("setDept", deptName);
}
</script>

<style scoped>
</style>