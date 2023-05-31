<template>
  <tr v-for="(it, idx) in calendar" :key="idx">
        <td v-for="(day, dayIdx) in it" id="calendar" :key="dayIdx" >
          {{ day.day }}
        </td>
  </tr>
</template>

<script setup>
import { defineProps } from "vue";
defineProps({
  calendar: {
    type: Array,
    required: true
  }
})
</script>

<style scoped>

</style>