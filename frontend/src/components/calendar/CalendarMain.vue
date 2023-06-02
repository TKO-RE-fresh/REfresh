<template>
  <tr v-for="(it, idx) in calendar" :key="idx">
    <td
      v-for="(day, dayIdx) in it"
      id="calendar"
      :key="dayIdx"
      class="border-2 px-8 py-12 w-1/7"
    >
      <a
        :id="dayIdx"
        :class="{ 'cursor-pointer': day.hoName !== '' && day.sumCount !== 0 }"
        @click="showDayModal(day.hoName, day.sumCount, $event)"
      >
        {{ day.day }}
      </a>
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