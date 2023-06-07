<template>
  <div v-if="list.content && list.content.length > 0">
    <ul v-for="(item, idx) in list.content" :key="idx">
      <li class="px-3 sm:pb-4">
        <div class="flex items-center pt-4 space-x-4">
          <div class="flex-shrink-0">
            <img
              class="w-8 h-8 rounded-full"
              src="@/assets/images/user.svg"
              alt="Neil image"
            />
          </div>
          <div class="flex-1">
            <p
              class="text-xs font-medium text-gray-900 truncate dark:text-white"
            >
              {{ item.memberInfo.name }}
            </p>
            <p class="text-xs text-gray-500 truncate dark:text-gray-400">
              {{ item.memberInfo.email }}
            </p>
          </div>
          <div>
            <span
              :class="{
                'bg-yellow-100':
                  item.annualType === '오전 반차' ||
                  item.annualType === '오후 반차',
                'bg-green-100': item.annualType === '연차',
                'text-green-800': item.annualType === '연차',
                'text-yellow-800':
                  item.annualType === '오전 반차' ||
                  item.annualType === '오후 반차',
              }"
              class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium"
            >
              {{ item.annualType }}
            </span>
          </div>
          <div
            class="inline-flex items-center text-xs font-semibold text-gray-900 dark:text-white"
          >
            {{ item.startDate }} ~ {{ item.endDate }}
          </div>
        </div>
      </li>
    </ul>
  </div>
  <div v-else>
    <div
      v-show="list.content"
      class="absolute text-2xl whitespace-nowrap top-1/3 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
    >
      검색 결과가 없습니다.
    </div>
  </div>
</template>
<script setup>
import { defineProps } from "vue";
defineProps({
  list: {
    type: Array,
    required: true,
  },
});
</script>