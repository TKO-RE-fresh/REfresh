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
      <div></div>
    </template>
    <template v-slot:row>
      
    </template>
  </data-table>
</template>
<script setup>
import { onMounted } from 'vue';
import DataTable from "@/components/calendar/data-table.vue";
import mixins from '@/mixins';


const year = 2021;
const month = 10;

onMounted(() => {
  mixins.methods.$api('calendar', 'get', {year, month})
    .then(res => {
      console.log(res);
    })
    .catch(err => {
      console.log(err);
    })
})


</script>

<style scoped>

</style>