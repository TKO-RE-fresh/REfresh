<template>
  <tr v-for="(it, idx) in calendar" :key="idx">
    <td
      v-for="(day, dayIdx) in it"
      :id="day.day"
      :key="dayIdx"
      class="border-2 w-48 h-28 relative"
      :class="{
        'cursor-pointer': isDayModal(day),
        'hover:bg-gray-100': isDayModal(day),
        'bg-slate-200': isToday(day),
      }"
      @click="showDayModal($event, day.hoName, day.sumCount)"
    >
      <div
        :id="day.day"
        :class="{ 'text-red-500': isHoliday(day) }"
        class="absolute max-w-full top-2 left-3"
      >
        {{ day.day }}
      </div>
      <div
        :id="day.day"
        class="absolute text-xs md:text-sm lg:text-lg top-2 left-10"
      >
        {{ isSpecialHoliday(day) ? parseHoliday(day) : "" }}
      </div>
      <div :id="day.day" class="absolute max-w-full right-2 bottom-2">
        <template v-if="day.sumCount !== 0">
          <div :id="day.day" v-html="personIcon"></div>
          {{ day.sumCount }}명
        </template>
      </div>
    </td>
  </tr>
  <calendar-main-modal
    v-if="showModal"
    :day="dayRef"
    @closeEvent="closeModal"
  ></calendar-main-modal>
</template>

<script setup>
import { defineProps, ref } from "vue";
import CalendarMainModal from "@/components/calendar/CalendarMainModal.vue";

const dayRef = ref(0);
const showModal = ref(false);

const personIcon = '<i class="cursor-default fa fa-user text-blue-400"></i>';
function closeModal() {
  showModal.value = false;
}

function isDayModal(day) {
  return day.hoName !== "" && day.sumCount !== 0;
}
function isToday(day) {
  return day.today && day.hoName !== "";
}
function isSpecialHoliday(day) {
  return (
    day.hoName !== "평일" && day.hoName !== "토요일" && day.hoName !== "일요일"
  );
}
function isHoliday(day) {
  return day.hoName !== "평일" && day.hoName !== "";
}

function parseHoliday(day) {
  return day.hoName.substring(1, day.hoName.length - 1);
}

defineProps({
  calendar: {
    type: Array,
    required: true,
  },
});
function showDayModal(e, holidayName, sumCount) {
  if (e.target.classList.contains("cursor-default")) {
    return;
  }
  if (holidayName === "" || sumCount === 0) {
    return;
  }
  dayRef.value = e.target.id;
  !showModal.value ? (showModal.value = true) : (showModal.value = true);
}
</script>

<style scoped>
</style>