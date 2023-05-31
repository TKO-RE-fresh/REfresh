<template>
  <div>
    <button @click="resetYearMonth">오늘</button>
  </div>
  <div>
    <select @change="handleDept">
      <option v-for="(item, idx) in dept" :key="idx">{{ item.name }}</option>
    </select>
  </div>
  <div>
    <a @click="prevEvent">
      &lt;
    </a>
  </div>
  <div>
    {{ dateRef.year }}년 {{ dateRef.month }}월
  </div>
  <div>
    <a @click="nextEvent">
      &gt;
    </a>
  </div>  
</template>

<script setup>
import { defineProps, defineEmits, reactive, watch} from "vue";
import Store from '@/store/index.js';
const emit = defineEmits(['deptEvent', 'yearMonthEvent']);

const dateRef = reactive({
  year: props.year,
  month: props.month
})
Store.watch(
  (newState) => {
    dateRef.year = newState.calendarYear;
    dateRef.month = newState.calendarMonth;
  }
)


watch(dateRef, () => {
  emit('yearMonthEvent', { year: dateRef.year, month: dateRef.month });
})



function prevEvent() {
  if (Store.state.calendarMonth === 1) {
    dateRef.year = Store.state.calendarYear - 1;
    dateRef.month = 12;
    Store.commit('setCalendarDate', { year: dateRef.year, month: dateRef.month });
  } else {
    dateRef.year = Store.state.calendarYear;
    dateRef.month = Store.state.calendarMonth - 1;
    Store.commit('setCalendarDate', { year: dateRef.year, month: dateRef.month });
  }
}

function resetYearMonth() {
  dateRef.year = new Date().getFullYear();
  dateRef.month = new Date().getMonth() + 1;
  Store.commit('setCalendarDate', { year: dateRef.year, month: dateRef.month });
}


function nextEvent() {
  if (Store.state.calendarMonth === 12) {
    dateRef.year = Store.state.calendarYear + 1;
    dateRef.month = 1;
    Store.commit('setCalendarDate', { year: Store.state.calendarYear + 1, month: 1 });
  } else {
    dateRef.year = Store.state.calendarYear;
    dateRef.month = Store.state.calendarMonth + 1;
    Store.commit('setCalendarDate', { year: Store.state.calendarYear, month: Store.state.calendarMonth + 1 });
  }
}
const props = defineProps({
  dept: {
    type: Array,
    required: true
  },
  year: {
    type: Number,
    required: true
  },
  month: {
    type: Number,
    required: true
  }
})
function handleDept(e) {
  const dept = e.target.value;
  emit('deptEvent', dept);
}
</script>

<style scoped>

</style>