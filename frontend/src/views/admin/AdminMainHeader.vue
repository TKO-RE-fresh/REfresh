<template>
  <div>
    <div class="navbar bg-slate-200 rounded-lg shadow-sm px-6">
      <div class="flex-1">
        <h3 class="normal-case text-3xl font-bold">관리자 페이지</h3>
        <!-- <a class="btn btn-ghost normal-case text-xl">연차 신청</a> -->
      </div>
      <div class="flex-none">
        <div class="tabs">
          <span id="0" :class="{'tab-active' : 0 === value}" class="tab tab-bordered text-lg"  @click="tabHandler($event)">사원 조회</span> 
          <span id="1" :class="{'tab-active' : 1 === value}"  class="tab tab-bordered text-lg"   @click="tabHandler($event)" >사원 생성</span> 
          <span id="2" :class="{'tab-active' : 2 === value}" class="tab tab-bordered text-lg"  @click="tabHandler($event)">연차</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref, defineEmits } from 'vue';
  const value = ref(0);
  const emit = defineEmits(['selectedTab']);
  // const selectedTab = ref('');
  const tabHandler = (e) => {
    const id = e.target.id;
    value.value = Number(id);
    emit('selectedTab', value.value);
  } 

</script>