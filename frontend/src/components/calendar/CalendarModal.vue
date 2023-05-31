<template>
  <Teleport to="body">
    <div class="fixed z-50 left-1/2 bottom-1/2 w-36 h-36 border-2">   
      <div class="relative h-full w-full">
        <a class="cursor-pointer" @click="closeModal">X</a>
        <div class="absolute flex flex-row h-full w-full bg-slate-300">
          <div class="flex flex-col overflow-y-auto w-full">
            <a v-for="val in yearList" :id="val" :key="val" 
            class="cursor-pointer pl-2 py-1" :class="reputeYear(val)" @click="handleYear" >
              {{ val }}
            </a>
          </div>
          <div class="flex flex-col overflow-y-auto w-full">
            <a v-for="val in monthList" :id="val" :key="val" 
            class="cursor-pointer pl-2 py-1" :class="reputeMonth(val)" @click="handleMonth">
              {{ val }}
            </a>
          </div>
        </div>
      </div>
    </div>
  </Teleport>
</template>
<script setup>
import { defineProps, defineEmits, computed, watch, reactive } from 'vue';
import Store from '@/store/index.js';
const emit = defineEmits(['closeModal', 'yearMonthEvent']);

const selected = reactive({
  year: props.curYear,
  month: props.curMonth
});

const reputeYear = computed(() => {
  return (val) => {
    return {
      'bg-amber-500': selected.year == val,
    };
  }
});

const reputeMonth = computed(() => {
  return (val) => {
    return {
      'bg-amber-500': selected.month == val,
    };
  }
});

watch(selected, () => {
  Store.commit('setCalendarDate', { year: selected.year, month: selected.month });
  emit('yearMonthEvent', selected);
});
function handleYear(e) {
  const id = Number(e.target.id);
  if(selected.year === id) {
    return;
  }
  selected.year = id;
}

function handleMonth(e) {
  const id = Number(e.target.id);
  if(selected.month === id) {
    return;
  }
  selected.month = id;
}

function closeModal() {
  emit('closeModal');
}

const props = defineProps({
  yearList: {
    type: Array,
    required: true
  },
  modalFlag: {
    type: Boolean,
    required: true
  },
  monthList: {
    type: Array,
    required: true
  },
  curMonth: {
    type: Number,
    required: true
  },
  curYear: {
    type: Number,
    required: true
  }
})

</script>