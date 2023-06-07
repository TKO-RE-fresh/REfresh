<template>
  <div v-if="totalPages > 0" class="pagination">
    <ul class="flex justify-center space-x-2 mt-10">
      <li>
        <a
          v-if="currentPagePos != 1"
          class="px-3 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-blue-200"
          @click="first"
          >&lt;&lt;</a
        >
      </li>
      <li>
        <a
          v-show="!prevButtonDisabled"
          class="px-3 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-blue-200"
          @click="prev"
          >&lt;</a
        >
      </li>
      <li v-for="number in pageList" :key="number">
        <a
          v-if="number == currentPage"
          class="px-3 py-2 bg-blue-300 text-gray-700 rounded-md"
          @click="selectPage(number)"
          >{{ number }}</a
        >
        <a
          v-if="number != currentPage"
          class="px-3 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-blue-300"
          @click="selectPage(number)"
          >{{ number }}</a
        >
      </li>
      <li>
        <a
          v-show="!nextButtonDisabled"
          class="px-3 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-blue-200"
          @click="next"
          >&gt;</a
        >
      </li>
      <li>
        <a
          v-if="
            currentPagePos !=
            Math.ceil(props.totalPages / props.displayPageCount)
          "
          class="px-3 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-blue-200"
          @click="last"
          >&gt;&gt;</a
        >
      </li>
    </ul>
  </div>
</template>

<script setup>
import { computed } from "@vue/reactivity";
import { defineProps, defineEmits } from "vue";

const emit = defineEmits(["selectPage"]);

const props = defineProps({
  currentPage: Number,
  totalPages: Number,
  displayPageCount: {
    type: Number,
    default: 2,
  },
});

//현재 페이지 위치
const currentPagePos = computed(() =>
  Math.ceil(props.currentPage / props.displayPageCount)
);
console.log(currentPagePos);

console.log(currentPagePos);

//마지막 페이지 번호
const lastPage = computed(() => {
  const last = currentPagePos.value * props.displayPageCount;
  if (last > props.totalPages) {
    return props.totalPages;
  }
  return last;
});

//첫번째 페이지 번호
const firstPage = computed(() => {
  if (lastPage.value == props.totalPages) {
    const multipleDisplayPage = lastPage.value % props.displayPageCount === 0;
    return multipleDisplayPage
      ? lastPage.value - props.displayPageCount + 1
      : lastPage.value - (lastPage.value % props.displayPageCount) + 1;
  }
  return lastPage.value - (props.displayPageCount - 1);
});

// 페이지 번호 리스트
const pageList = computed(() => {
  const list = [];
  for (let i = firstPage.value; i <= lastPage.value; i++) {
    list.push(i);
  }
  return list;
});

// 이전 버튼 비활성화
const prevButtonDisabled = computed(() => firstPage.value <= 1);

// 다음 버튼 비활성화
const nextButtonDisabled = computed(() => lastPage.value >= props.totalPages);

// 페이지 번호 클릭
const selectPage = (clickPageNum) => {
  if (clickPageNum === props.currentPage) {
    return false;
  }
  emit("selectPage", clickPageNum);
};

// 이전 버튼 클릭
const prev = () => {
  emit("selectPage", firstPage.value - props.displayPageCount);
};

//다음 버튼 클릭
const next = () => {
  emit("selectPage", lastPage.value + 1);
};

// 첫페이지로 이동
const first = () => {
  emit("selectPage", 1);
};

// 마지막페이지로 이동
const last = () => {
  emit("selectPage", props.totalPages);
};
</script>
