<template>
  <select @change="handleChange">
    <option v-for="(item, idx) in list" :key="idx" :value="idx">
      {{ item }}
    </option>
  </select>
</template>
<script setup>
import { defineProps, defineEmits } from "vue";
const emit = defineEmits(["selectedOption"]);

function handleChange(e) {
  emit("selectedOption", e.target.value);
}

defineProps({
  list: {
    type: Array,
    required: true,
  },
});
</script>