<template>
  <div v-if="list.content && list.content.length > 0">
    <div v-for="(item, idx) in list.content" :key="idx">
      {{ item.memberInfo.name }} {{ item.memberInfo.email }}
      {{ item.annualType }} {{ item.startDate }} ~ {{ item.endDate }}
    </div>
  </div>
  <div v-else>
    <div v-if="list.content">해당 부서에 휴가자가 없습니다.</div>
  </div>
</template>
<script setup>
import { defineProps } from "vue";
defineProps({
  list: {
    type: Array,
    required: true,
  },
});
</script>