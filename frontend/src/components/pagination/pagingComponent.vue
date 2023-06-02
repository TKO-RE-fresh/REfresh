<template>
<<<<<<< HEAD
  <div class="pagination">
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
          @click="prev">&lt;</a>
      </li>
      <li v-for="number in pageList" 
          :key="number">
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

//마지막 페이지 번호
const lastPage = computed(() => {
  const last = currentPagePos.value * props.displayPageCount;
  if (last > props.totalPages) {
    return props.totalPages;
  }
  return last;
=======
    <div class="pagination">
        <ul class="flex justify-center space-x-2 mt-10">
            <li>
                <a v-if="currentPagePos != 1" class="px-3 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-blue-200" @click="first">&lt;&lt;</a>
            </li>
            <li>
                <a v-show="!prevButtonDisabled" class="px-3 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-blue-200" @click="prev">&lt;</a>
            </li>
            <li v-for="number in pageList" :key="number">
                <a v-if="number == currentPage" class="px-3 py-2 bg-blue-300 text-gray-700 rounded-md" @click="selectPage(number)">{{ number }}</a>
                <a v-if="number != currentPage" class="px-3 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-blue-300" @click="selectPage(number)">{{ number }}</a>
            </li>
            <li>
                <a v-show="!nextButtonDisabled" class="px-3 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-blue-200" @click="next">&gt;</a>
            </li>
            <li>
                <a v-if="currentPagePos != Math.ceil(props.totalPages / props.displayPageCount)" class="px-3 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-blue-200" @click="last">&gt;&gt;</a>
            </li>
        </ul>
    </div>
</template>

<script setup>
import { computed } from '@vue/reactivity';
import { defineProps, defineEmits } from 'vue';

const emit = defineEmits(['selectPage']);

const props = defineProps({
    currentPage: Number,
    totalPages: Number,
    displayPageCount: {
        type: Number,
        default: 2,
    },
});

//현재 페이지 위치
const currentPagePos = computed(() => Math.ceil(props.currentPage / props.displayPageCount));

//마지막 페이지 번호
const lastPage = computed(() => {
    const last = currentPagePos.value * props.displayPageCount;
    if(last > props.totalPages) {
        return props.totalPages;
    }
    return last;
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
});

//첫번째 페이지 번호
const firstPage = computed(() => {
<<<<<<< HEAD
  if (lastPage.value == props.totalPages) {
    const multipleDisplayPage = lastPage.value % props.displayPageCount === 0;
    return multipleDisplayPage
      ? lastPage.value - props.displayPageCount + 1
      : lastPage.value - (lastPage.value % props.displayPageCount) + 1;
  }
  return lastPage.value - (props.displayPageCount - 1);
=======
    if(lastPage.value == props.totalPages) {
        const multipleDisplayPage = lastPage.value % props.displayPageCount === 0;
        return multipleDisplayPage ? lastPage.value - props.displayPageCount + 1 : lastPage.value - (lastPage.value % props.displayPageCount) + 1;
    }
    return lastPage.value - (props.displayPageCount - 1);
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
});

// 페이지 번호 리스트
const pageList = computed(() => {
<<<<<<< HEAD
  const list = [];
  for (let i = firstPage.value; i <= lastPage.value; i++) {
    list.push(i);
  }
  return list;
=======
    const list = [];
    for(let i=firstPage.value; i<= lastPage.value; i++) {
        list.push(i);
    }
    return list;
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
});

// 이전 버튼 비활성화
const prevButtonDisabled = computed(() => firstPage.value <= 1);

// 다음 버튼 비활성화
const nextButtonDisabled = computed(() => lastPage.value >= props.totalPages);

// 페이지 번호 클릭
<<<<<<< HEAD
const selectPage = (clickPageNum) => {
  if (clickPageNum === props.currentPage) {
    return false;
  }
  emit("selectPage", clickPageNum);
=======
const selectPage = clickPageNum => {
    if(clickPageNum === props.currentPage) {
        return false;
    }
    emit('selectPage', clickPageNum);
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
};

// 이전 버튼 클릭
const prev = () => {
<<<<<<< HEAD
  emit("selectPage", firstPage.value - props.displayPageCount);
=======
    emit('selectPage', firstPage.value - props.displayPageCount);
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
};

//다음 버튼 클릭
const next = () => {
<<<<<<< HEAD
  emit("selectPage", lastPage.value + 1);
=======
    emit('selectPage', lastPage.value + 1);
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
};

// 첫페이지로 이동
const first = () => {
<<<<<<< HEAD
  emit("selectPage", 1);
=======
    emit('selectPage', 1);
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
};

// 마지막페이지로 이동
const last = () => {
<<<<<<< HEAD
  emit("selectPage", props.totalPages);
};
</script>
=======
    emit('selectPage', props.totalPages);
};
</script>
>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
