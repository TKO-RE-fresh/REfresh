<template>
  <select @change="handleSelect">
    <option value="">부서를 선택하세요</option>
    <option v-for="(item, idx) in list" :key="idx" :value="item.name">
      {{ item.name }}
    </option>
  </select>
</template>
<script setup>
import { defineProps, defineEmits } from "vue";

const emit = defineEmits(["selectedDept", "clearContent"]);
function handleSelect(e) {
  if (e.target.value === "") {
    emit("clearContent");
    return;
  }
  emit("selectedDept", e.target.value);
}
defineProps({
  list: {
    type: Array,
    required: true,
  },
});
</script>