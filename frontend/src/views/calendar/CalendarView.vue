<template>
  <div>
    <main class="flex font-mice">
      <the-header></the-header>
      <div class="h-[calc(100%-4rem)] pt-24 w-screen pl-80 flex justify-evenly">
        <data-table>
          <!-- 헤더 구현 -->
          <template v-slot:header>
            <calendar-header
              :year="yearMonth.year"
              :month="yearMonth.month"
              :dept="departments"
              @deptEvent="fetchCalendarByDept"
              @yearMonthEvent="fetchCalendarByRefsData"
              @subCalendarEvent="toggleSubCalendar"
            ></calendar-header>
          </template>
          <template v-slot:body>
            <tr>
              <th
                v-for="(value, idx) in dayOfWeek"
                id="day-of-week"
                :key="idx"
                class="w-1/7 h-14 border-2 bg-[#DAD9FF] text-xl"
              >
                {{ value }}
              </th>
            </tr>
            <calendar-main :calendar="calendar"></calendar-main>
          </template>
        </data-table>
      </div>
      <calendar-sub-cal-modal
        v-if="subCalFlag"
        :yearList="subCalData.yearList"
        :monthList="subCalData.monthList"
        :curYear="yearMonth.year"
        :curMonth="yearMonth.month"
        @closeModal="closeSubModal"
        @yearMonthEvent="fetchCalendarByRefsData"
      ></calendar-sub-cal-modal>
    </main>
  </div>
  <the-sidebar></the-sidebar>
</template>
<script setup>
import { ref, reactive, watch, onMounted } from "vue";
import CalendarMain from "@/components/calendar/CalendarMain";
import TheSidebar from "@/components/sidebar/TheSidebar.vue";
import TheHeader from "@/components/header/TheHeader.vue";
import CalendarSubCalModal from "@/components/calendar/CalendarSubCalModal";
import CalendarHeader from "@/components/calendar/CalendarHeader";
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
const subCalFlag = ref(false);

onMounted(async () => {
  const params = makeParams();
  const calData = await mixins.methods.$api(`calendar`, "get", {
    params,
  });
  calendar.value = makeCalendarDom(calData);
  if (Store.state.auth !== "admin") {
    departments.value = [Store.state.deptName];
    return;
  }
  const deptData = await mixins.methods.$api(`calendar/department`, "get", {});
  departments.value = deptData.data.sort((a, b) => {
    if (a.name === Store.state.deptName) {
      return -1;
    } else if (b) {
      return 0;
    }
  });

  Store.commit("setDeptList", deptData.data);
});

// modalFlag를 통해 서브 캘린더를 렌더링
watch(subCalFlag, () => {
  if (subCalData.monthList.length === 0 && subCalData.yearList.length === 0) {
    makeSubCalendar();
  }
});

function closeSubModal() {
  subCalFlag.value = false;
}

// 서브 캘린더 생성
function makeSubCalendar() {
  const year = 1900;
  const month = 0;
  subCalData.yearList = Array.from({ length: 200 }, (_, i) => year + i);
  subCalData.monthList = Array.from({ length: 12 }, (_, i) => month + i + 1);
}

// 서브 캘린더 토글
function toggleSubCalendar(e) {
  curX.value = e.clientX;
  curY.value = e.clientY + 20;
  subCalFlag.value = !subCalFlag.value;
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

<style scoped></style>
