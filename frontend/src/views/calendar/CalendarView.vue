<template>
  <data-table>
    <!-- 헤더 구현 -->
    <template v-slot:header>
      <button>오늘</button>
      <select>{{ }}</select>
      <div><a></a></div>
      <div><h1>{{year}}년 {{month}}</h1></div>
      <div><a></a></div>
      <div><a></a></div>
    </template>
    <!-- 필터 구현 -->
    <template v-slot:filter>
      <tr>
        <td v-for="(value, idx) in dayOfWeek" id="day-of-week" :key="idx" >
        {{ value }}
        </td>
      </tr>
    </template>
    <template v-slot:body>
      <tr v-for="(it, idx) in calendar" :key="idx">
        <td v-for="(day, dayIdx) in it" id="calendar" :key="dayIdx" >
          {{ day.day }}
        </td>
      </tr>      
    </template>
  </data-table>
</template>
<script setup>
import {  ref, onMounted } from 'vue';
// import Router from '@/router/index.js';
import DataTable from "@/components/calendar/data-table.vue";
import mixins from '@/utils/mixins';
const year = ref(2023);
const month = ref(5);
const dayOfWeek = ref(['일', '월', '화', '수', '목', '금', '토']);
const calendar = ref([]);
onMounted(async () => {
  const params = {
    year: year.value,
    month: month.value,
    deptName: '개발팀'
  }
  const res = await mixins.methods.$api(`calendar`, 'get', { params });
  
  const arr = [];
  for (let i = 0; i < res.data.length; i += 7) {
    arr.push(res.data.slice(i, i + 7));
  }
  calendar.value = arr;
})


</script>

<style scoped>

</style>