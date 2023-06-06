<template>
  <Teleport to="body">
    <transition name="fade">
      <div>
        <div class="fixed inset-0 z-10 flex items-center justify-center">
          <div
            class="w-full max-w-lg p-3 relative mx-auto my-auto rounded-xl shadow-lg bg-white"
          >
            <div>
              <main>
                <div v-for="(val, idx) in annualList" :key="idx">
                  {{ val }}
                </div>
                <div>
                  <paging-component
                    :currentPage="curPage"
                    :totalPages="totalPage"
                  ></paging-component>
                </div>
              </main>
              <div class="p-3 mt-2 text-center space-x-4 md:block">
                <button
                  class="mb-2 md:mb-0 bg-blue-500 border border-blue-500 px-5 py-2 text-sm shadow-sm font-medium tracking-wider text-white rounded-md hover:shadow-lg hover:bg-blue-600"
                  @click="closeEvent"
                >
                  닫기
                </button>
              </div>
            </div>
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
  console.log(res);
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
