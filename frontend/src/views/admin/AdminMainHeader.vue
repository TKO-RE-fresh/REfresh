<template>
  <div>
    <div class="navbar px-10">
      <div class="flex-1">
        <h3 class="normal-case text-3xl font-bold">관리자 페이지</h3>
        <!-- <a class="btn btn-ghost normal-case text-xl">연차 신청</a> -->
      </div>
      <div class="flex-none">
        <div class="tabs ">
          <span id="0" :class="{'tab-active text-blue-500 bg-blue-100' : 0 === value}" class="tab tab-lg tab-lifted "  @click="tabHandler($event)">사원 조회</span> 
          <span id="1" :class="{'tab-active text-blue-500 bg-blue-100' : 1 === value}"  class="tab tab-lg tab-lifted "   @click="tabHandler($event)" >사원 생성</span> 
          <span id="2" :class="{'tab-active text-blue-500 bg-blue-100' : 2 === value}" class="tab tab-lg tab-lifted "  @click="tabHandler($event)">연차</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { ref, defineEmits } from 'vue';
  const value = ref(0);
  const emit = defineEmits(['selectedTab']);

  const tabHandler = (e) => {
    const id = e.target.id;
    value.value = Number(id);
    emit('selectedTab', value.value);
  } 

</script>