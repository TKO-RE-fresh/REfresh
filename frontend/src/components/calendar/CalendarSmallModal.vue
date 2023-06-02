<template>
  <Teleport to="body">
    <div
      :style="{ left: x, top: y }"
      class="fixed w-36 h-36 border-2 bg-slate-500"
    >
      <div>
        <div>header</div>
        <div>
          <a class="cursor-pointer" @click="closeEvent"> X </a>
        </div>
      </div>
      <div v-for="(item, idx) in annualList" :key="idx" class="flex">
        {{ item.memberName }} {{ item.annualType }} {{ item.startDate }} ~
        {{ item.endDate }}
      </div>
      <div>{{ curPage }} / {{ totalPage }}</div>
    </div>
  </Teleport>
</template>
<script setup>
import { defineProps, defineEmits, onMounted, reactive, watch, ref } from "vue";
import Store from "@/store/index.js";
import mixins from "@/utils/mixins";
const emit = defineEmits(["closeEvent"]);

const x = `${props.curX}px`;
const y = `${props.curY}px`;

function closeEvent() {
  emit("closeEvent");
}

const annualList = reactive([]);
const curPage = ref(0);
const totalPage = ref(0);

watch(props, async (oldValue, newValue) => {
  if (oldValue.day !== newValue.day) {
    await fetchDataForModal();
  }
});
onMounted(async () => {
  await fetchDataForModal();
});

async function fetchDataForModal() {
  const params = makeParams();
  const res = await mixins.methods.$api("calendar/annual", "get", { params });
  annualList.splice(0, annualList.length, ...res.data.content);
  curPage.value = res.data.curPage;
  totalPage.value = res.data.totalPage;
}

function makeParams() {
  const deptName = Store.state.deptName;
  const month = Store.state.calendarMonth;
  const year = Store.state.calendarYear;

  return {
    deptName,
    day: Number(props.day),
    year,
    month,
    page: 1,
    size: 5,
  };
}

const props = defineProps({
  day: {
    type: Number,
    required: true,
  },
  curX: {
    type: Number,
    required: true,
  },
  curY: {
    type: Number,
    required: true,
  },
});
</script>