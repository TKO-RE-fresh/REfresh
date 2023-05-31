<template>
  <div class="bg-base-80 border border-slate-300 rounded-lg shadow-md shadow-slate-300/40 my-10">
    <div class="py-3 flex justify-center items-center max-w-500">
      <div class="px-5">
        <input id="name" type="text" placeholder="사원명 입력" class="input input-bordered "  />
      </div>
      <div class="px-10">
        <select class="select select-bordered" >
          <option disabled selected>부서 선택</option>
          <option v-for="(item,idx) in deptOptions" :key="idx">{{item}}</option>
        </select>
      </div>
      <div class="px-10">
        <select class="select select-bordered">
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