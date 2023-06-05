<template>
  <th>
    <button class="border-2" @click="resetYearMonth">오늘</button>
  </th>
  <th>
    <select @change="handleDept">
      <option v-for="(item, idx) in dept" :key="idx">{{ item.name }}</option>
    </select>
  </th>
  <th>
    <a class="text-4xl cursor-pointer" @click="debouncePrevEvent"> &lt; </a>
  </th>
  <div class="w-36">
    <th class="overflow-visible whitespace-nowrap text-4xl">
      {{ dateRef.year }}년 {{ dateRef.month }}월
    </th>
  </div>

  <th>
    <a class="text-4xl cursor-pointer" @click="debounceNextEvent"> &gt; </a>
  </th>
  <div class="mb-12"></div>
</template>

<script setup>
import { defineProps, defineEmits, reactive, watch } from "vue";
import Store from "@/store/index.js";
const emit = defineEmits(["deptEvent", "yearMonthEvent"]);

let prevEventTimeOut;
let nextEventTimeOut;
const debouncePrevEvent = () => debounce(prevEvent, prevEventTimeOut);
const debounceNextEvent = () => debounce(nextEvent, nextEventTimeOut);
function debounce(fn, time) {
  if (time) {
    clearTimeout(time);
  }
  time = setTimeout(() => {
    fn();
  }, 500);
}

const dateRef = reactive({
  year: props.year,
  month: props.month,
});

Store.watch((newState) => {
  dateRef.year = newState.calendarYear;
  dateRef.month = newState.calendarMonth;
});

watch(dateRef, () => {
  emit("yearMonthEvent", { year: dateRef.year, month: dateRef.month });
});

function prevEvent() {
  if (Store.state.calendarMonth === 1) {
    dateRef.year = Store.state.calendarYear - 1;
    dateRef.month = 12;
    Store.commit("setCalendarDate", {
      year: dateRef.year,
      month: dateRef.month,
    });
  } else {
    dateRef.year = Store.state.calendarYear;
    dateRef.month = Store.state.calendarMonth - 1;
    Store.commit("setCalendarDate", {
      year: dateRef.year,
      month: dateRef.month,
    });
  }
}

function resetYearMonth() {
  dateRef.year = new Date().getFullYear();
  dateRef.month = new Date().getMonth() + 1;
  Store.commit("setCalendarDate", { year: dateRef.year, month: dateRef.month });
}

function nextEvent() {
  if (Store.state.calendarMonth === 12) {
    dateRef.year = Store.state.calendarYear + 1;
    dateRef.month = 1;
    Store.commit("setCalendarDate", {
      year: Store.state.calendarYear + 1,
      month: 1,
    });
  } else {
    dateRef.year = Store.state.calendarYear;
    dateRef.month = Store.state.calendarMonth + 1;
    Store.commit("setCalendarDate", {
      year: Store.state.calendarYear,
      month: Store.state.calendarMonth + 1,
    });
  }
}
const props = defineProps({
  dept: {
    type: Array,
    required: true,
  },
  year: {
    type: Number,
    required: true,
  },
  month: {
    type: Number,
    required: true,
  },
});
function handleDept(e) {
  const dept = e.target.value;
  emit("deptEvent", dept);
}
</script>

<style scoped>
</style>