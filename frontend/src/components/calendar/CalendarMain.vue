<template>
  <div class="mt-6"></div>
  <tr v-for="(it, idx) in calendar" :key="idx">
    <td
      v-for="(day, dayIdx) in it"
      :id="dayIdx"
      :key="dayIdx"
      class="border-2 w-48 h-36 relative"
      :class="{ 'cursor-pointer': day.hoName !== '' && day.sumCount !== 0 }"
      @click="showDayModal(day.hoName, day.sumCount, $event)"
    >
      <div
        :class="{ 'text-red-500': isHoliday(day) }"
        class="absolute max-w-full top-2 left-3"
      >
        {{ day.day }}
      </div>
      <div class="absolute text-sm md:text-base lg:text-lg top-2 left-10">
        {{ isSpecialHoliday(day) ? parseHoliday(day) : "" }}
      </div>
      <div class="absolute max-w-full">
        {{ day.sumCount === 0 ? "" : day.sumCount }}
      </div>
    </td>
  </tr>
  <calendar-small-modal
    v-if="showModal"
    :curX="curX"
    :curY="curY"
    :day="dayRef"
    @closeEvent="closeModal"
  ></calendar-small-modal>
</template>

<script setup>
import { defineProps, ref } from "vue";
import CalendarSmallModal from "./CalendarSmallModal.vue";
const dayRef = ref(0);
const showModal = ref(false);

const curX = ref("");
const curY = ref("");

function closeModal() {
  showModal.value = false;
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
function showDayModal(holidayName, sumCount, e) {
  if (holidayName === "" || sumCount === 0) {
    return;
  }
  curX.value = e.clientX;
  curY.value = e.clientY;
  dayRef.value = e.target.id;
  !showModal.value ? (showModal.value = true) : (showModal.value = true);
}
</script>

<style scoped>
</style>