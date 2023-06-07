<template>
  <Teleport to="body">
    <transition name="fade">
      <div>
        <div class="fixed inset-0 z-10 flex items-center justify-center">
          <div
            class="w-full h-calModalH max-w-lg p-3 relative mx-auto my-auto rounded-xl shadow-lg bg-white"
          >
            <main class="relative h-full">
              <div class="py-3 text-3xl text-center">
                {{ year }}년 {{ month }}월 {{ props.day }}일 휴가자 목록
              </div>
              <hr class="border-1 w-full items-center" />

              <ul v-for="(val, idx) in annualList" :key="idx">
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
                        {{ val.memberName }}
                      </p>
                      <p
                        class="text-xs text-gray-500 truncate dark:text-gray-400"
                      >
                        {{}}
                      </p>
                    </div>
                    <div>
                      <span
                        :class="{
                          'bg-yellow-100':
                            val.annualType === '오전 반차' ||
                            val.annualType === '오후 반차',
                          'bg-green-100': val.annualType === '연차',
                          'text-green-800': val.annualType === '연차',
                          'text-yellow-800':
                            val.annualType === '오전 반차' ||
                            val.annualType === '오후 반차',
                        }"
                        class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium"
                      >
                        {{ val.annualType }}
                      </span>
                    </div>
                    <div
                      class="inline-flex items-center text-xs font-semibold text-gray-900 dark:text-white"
                    >
                      {{ val.startDate }} ~ {{ val.endDate }}
                    </div>
                  </div>
                </li>
              </ul>

              <div
                class="absolute bottom-12 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
              >
                <paging-component
                  :currentPage="curPage"
                  :totalPages="totalPage"
                ></paging-component>
              </div>
              <div
                class="absolute bottom-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
              >
                <button
                  class="mb-2 md:mb-0 bg-blue-500 border border-blue-500 px-5 py-2 text-sm shadow-sm font-medium tracking-wider text-white rounded-md hover:shadow-lg hover:bg-blue-600"
                  @click="closeEvent"
                >
                  닫기
                </button>
              </div>
            </main>
          </div>
        </div>
        <div class="fixed inset-0 bg-black opacity-70 z-0"></div>
      </div>
    </transition>
  </Teleport>
</template>
<script setup>
import { defineProps, defineEmits, reactive, ref, watch, onMounted } from "vue";
import PagingComponent from "@/components/pagination/pagingComponent.vue";
import Store from "@/store/index.js";
import mixins from "@/utils/mixins";

const emit = defineEmits(["closeEvent"]);
function closeEvent() {
  emit("closeEvent");
}
const year = ref(Store.state.calendarYear);
const month = ref(Store.state.calendarMonth);
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
  console.log(annualList);
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
  isOpen: {
    type: Boolean,
    required: true,
  },
});
</script>
<style scoped>
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 500ms ease-out;
}
</style>
