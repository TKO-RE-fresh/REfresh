<template>
  <Teleport to="body">
    <transition name="fade">
      <div>
        <div class="fixed inset-0 z-10 flex items-center justify-center">
          <div
            class="w-full h-calModalH max-w-lg p-3 relative mx-auto my-auto rounded-xl shadow-lg bg-white"
          >
            <main class="relative h-full">
              <div class="flex justify-center space-x-10 my-4">
                <calendar-search-option
                  :list="selectList"
                  @selectedOption="handleData"
                ></calendar-search-option>
                <div v-show="select === 0">
                  <calendar-search-dept-select
                    :list="deptList"
                    @selectedDept="handleDept"
                    @clearContent="handleClear"
                  ></calendar-search-dept-select>
                </div>
                <div v-show="select === 1">
                  <calendar-search-member
                    @selectedMember="handleMember"
                  ></calendar-search-member>
                </div>
              </div>
              <hr class="border-1 w-full items-center" />
              <div>
                <calendar-search-main :list="annualList"></calendar-search-main>
              </div>
              <div
                class="absolute bottom-12 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
              >
                <paging-component
                  :currentPage="curPage"
                  :totalPages="totalPage"
                  @selectPage="handleChangePage"
                ></paging-component>
              </div>
              <div
                class="absolute bottom-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center md:block"
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
import { defineEmits, ref, watch } from "vue";
import CalendarSearchOption from "./search/CalendarSearchOption.vue";
import CalendarSearchDeptSelect from "./search/CalendarSearchDeptSelect.vue";
import CalendarSearchMember from "./search/CalendarSearchMember.vue";
import CalendarSearchMain from "./search/CalendarSearchMain.vue";
import Store from "@/store/index.js";
import PagingComponent from "@/components/pagination/pagingComponent.vue";
import mixins from "@/utils/mixins";

const emit = defineEmits(["closeSearchEvent", "selectedMember"]);
function closeEvent() {
  emit("closeSearchEvent");
}

const selectList = ["부서로 찾기", "이름으로 찾기"];
const dept = ref("");
const totalPage = ref(0);
const curPage = ref(1);
const select = ref(0);
const deptList = ref([]);
const annualList = ref([]);
const member = ref("");

function handleChangePage(number) {
  if (number) {
    curPage.value = number;
  }
  if (select.value === 0) {
    handleDept(dept.value);
  } else {
    handleMember(dept.value);
  }
}

watch(select, () => {
  annualList.value = [];
  curPage.value = 1;
});

function handleClear() {
  dept.value = "";
  annualList.value = [];
  curPage.value = 1;
}

Store.watch((newState) => {
  deptList.value = newState.deptList;
  curPage.value = 1;
});

function handleData(data) {
  const num = Number(data);
  if (select.value === num) {
    return;
  }
  select.value = num;
}

function makeParams(name) {
  return {
    name,
    page: curPage.value,
    size: 5,
  };
}

async function handleDept(deptName) {
  if (deptName !== dept.value) {
    dept.value = deptName;
  }
  const params = makeParams(deptName);
  const res = await mixins.methods.$api(`calendar/annual/department`, "get", {
    params,
  });
  annualList.value = res.data;
  totalPage.value = res.data.totalPage;
  curPage.value = res.data.curPage;
}

async function handleMember(memberName) {
  if (member.value === "" && memberName === "") {
    annualList.value = [];
    return;
  } else if (memberName !== "" && memberName !== member.value) {
    curPage.value = 1;
    member.value = memberName;
  }

  const params = makeParams(member.value);
  const res = await mixins.methods.$api(`calendar/annual/member`, "get", {
    params,
  });
  annualList.value = res.data;
  totalPage.value = res.data.totalPage;
  curPage.value = res.data.curPage;
}
</script>