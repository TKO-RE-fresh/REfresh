<template>
  <Teleport to="body">
    <div class="fixed top-subModalH left-subModalX w-64 h-48">
      <div class="h-full w-full">
        <div class="flex border-2">
          <div class="whitespace-nowrap p-2">직접 선택</div>
          <div class="w-full text-right p-2">
            <i class="fa-solid fa-xmark cursor-pointer" @click="closeModal"></i>
          </div>
        </div>

        <div class="flex flex-row h-full w-full bg-slate-100">
          <div
            ref="scrollYearContainer"
            class="flex flex-col overflow-y-auto bg-slate-100 w-full"
          >
            <a
              v-for="val in yearList"
              :id="val"
              :key="val"
              class="cursor-pointer pl-2 py-1 hover:bg-amber-100"
              :class="[reputeYear(val), { 'sticky-class': isSticky(val) }]"
              @click="handleYear"
            >
              {{ val }}
            </a>
          </div>
          <div
            ref="scrollMonthContainer"
            class="flex flex-col overflow-y-auto w-full"
            @scroll="handleScroll"
          >
            <a
              v-for="val in monthList"
              :id="val"
              :key="val"
              class="cursor-pointer pl-2 py-1 hover:bg-amber-100"
              :class="[reputeMonth(val), { 'sticky-class': isSticky(val) }]"
              @click="handleMonth"
            >
              {{ val }}
            </a>
          </div>
        </div>
      </div>
    </div>
  </Teleport>
</template>
<script setup>
import {
  defineProps,
  defineEmits,
  computed,
  watch,
  reactive,
  ref,
  onMounted,
} from "vue";
import Store from "@/store/index.js";
const emit = defineEmits(["closeModal", "yearMonthEvent"]);
const scrollYearContainer = ref(null);
const scrollMonthContainer = ref(null);
const selected = reactive({
  year: props.curYear,
  month: props.curMonth,
});

onMounted(() => {
  const dom = document.querySelectorAll(".sticky-class");
  dom.forEach((el) => el.scrollIntoView());
});

function isSticky(val) {
  if (selected.year === val || selected.month === val) {
    return true;
  }
}

const reputeYear = computed(() => {
  return (val) => {
    return {
      "bg-amber-200": selected.year == val,
    };
  };
});

const reputeMonth = computed(() => {
  return (val) => {
    return {
      "bg-amber-200": selected.month == val,
    };
  };
});

watch(selected, () => {
  Store.commit("setCalendarDate", {
    year: selected.year,
    month: selected.month,
  });
  emit("yearMonthEvent", selected);
});

function handleYear(e) {
  const id = Number(e.target.id);
  if (selected.year === id) {
    return;
  }
  selected.year = id;
}

function handleMonth(e) {
  const id = Number(e.target.id);
  if (selected.month === id) {
    return;
  }
  selected.month = id;
}

function closeModal() {
  emit("closeModal");
}

const props = defineProps({
  yearList: {
    type: Array,
    required: true,
  },
  monthList: {
    type: Array,
    required: true,
  },
  curMonth: {
    type: Number,
    required: true,
  },
  curYear: {
    type: Number,
    required: true,
  },
});
</script>
<style scoped>
</style>