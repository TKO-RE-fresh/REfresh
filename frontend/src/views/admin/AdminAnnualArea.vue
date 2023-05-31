<template>
  <div class="bg-base-80 my-10 px-10 ">
    <div class="py-3 px-10 border border-slate-300 rounded-lg shadow-md shadow-slate-300/40 flex justify-center items-center">
      <div class="">
        <input id="name" type="text" placeholder="사원명 입력" class="input input-bordered px-10"  />
      </div>
      <div class="border border">
        <select class="select select-bordered px-32" >
          <option disabled selected>부서 선택</option>
          <option v-for="(item,idx) in deptOptions" :key="idx">{{item}}</option>
        </select>
      </div>
      <div class="">
        <select class="select select-bordered px-20">
          <option disabled selected>연차 상태 선택</option>
          <option v-for="(item,idx) in statusOptions" :key="idx">{{item}}</option>
        </select>
      </div>
    </div>
  </div>
</template>
<script setup>
  const deptOptions = ['인사팀','영업팀','개발팀','디자인팀','서비스팀'];
  const statusOptions = ['대기','승인','반려','취소'];
</script>     