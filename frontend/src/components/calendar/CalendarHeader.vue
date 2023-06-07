<template>
  <div class="flex space-x-36 mb-12 items-center">
    <div class="w-fit">
      <div class="h-fit p-3 whitespace-nowrap flex items-center">
        <svg
          class="w-12 h-12 text-gray-500 transition-all duration-300 transform rotate-180 hover:scale-110 cursor-pointer"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          xmlns="http://www.w3.org/2000/svg"
          @click="debouncePrevEvent"
        >
          <path
            d="M9 18l6-6-6-6"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        <div class="text-4xl">{{ dateRef.year }}년 {{ dateRef.month }}월</div>
        <svg
          class="w-12 h-12 text-gray-500 transition-all duration-300 transform rotate-180 hover:scale-110 cursor-pointer"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          xmlns="http://www.w3.org/2000/svg"
          @click="debounceNextEvent"
        >
          <path
            d="M15 18l-6-6 6-6"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        <div class="fit-content">
          <a
            class="m-auto cursor-pointer text-gray-700 hover:text-gray-900 transition-all duration-300"
            @click="subCalendar"
          >
            <svg
              class="fill-current w-12 h-12 ml-2 transform transition-all duration-300"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M10 13a1 1 0 0 1-.707-.293l-3-3a1 1 0 1 1 1.414-1.414L10 10.586l2.293-2.293a1 1 0 1 1 1.414 1.414l-3 3A1 1 0 0 1 10 13z"
              />
            </svg>
          </a>
        </div>
        <div>
          <button
            class="w-16 h-6 bg-blue-500 hover:bg-blue-700 text-white px-1 rounded"
            @click="resetYearMonth"
          >
            오늘
          </button>
        </div>
      </div>
    </div>
    <div v-if="auth === 'admin'">
      <select
        class="block appearance-none py-2 px-40 relative border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
        @change="handleDept"
      >
        <option v-for="(item, idx) in dept" :key="idx">
          <div class="absolute">
            {{ item.name }}
          </div>
        </option>
      </select>
    </div>
    <div v-else>
      <div class="text-4xl">{{ Store.state.deptName }}</div>
    </div>
    <div v-if="auth === 'admin'">
      <button
        class="w-24 h-6 bg-blue-500 hover:bg-blue-700 text-white px-1 rounded"
        @click="ocSearchModal"
      >
        사원 검색하기
      </button>
    </div>
    <calendar-search-modal
      v-if="searchModal"
      @closeSearchEvent="ocSearchModal"
    ></calendar-search-modal>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, reactive, watch, ref } from "vue";
import Store from "@/store/index.js";
import CalendarSearchModal from "@/components/calendar/CalendarSearchModal.vue";
const emit = defineEmits(["deptEvent", "yearMonthEvent", "subCalendarEvent"]);

let prevEventTimeOut;
let nextEventTimeOut;
const year = new Date().getFullYear();
const month = new Date().getMonth() + 1;
const auth = ref(Store.state.auth);
console.log(Store.state.auth);
const searchModal = ref(false);

const debouncePrevEvent = () => debounce(prevEvent, prevEventTimeOut);
const debounceNextEvent = () => debounce(nextEvent, nextEventTimeOut);

function ocSearchModal() {
  searchModal.value = !searchModal.value;
}
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

function subCalendar(e) {
  emit("subCalendarEvent", e);
}

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
  if (dateRef.year === year && dateRef.month === month) {
    return;
  }
  dateRef.year = year;
  dateRef.month = month;
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
  if (Store.state.auth !== "admin") {
    return;
  }
  const dept = e.target.value;
  emit("deptEvent", dept);
}
</script>

<style scoped>
</style>