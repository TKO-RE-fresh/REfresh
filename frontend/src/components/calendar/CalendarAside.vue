<template>
  <div class="w-96 flex justify-evenly">
    <CalendarAsideHeader
      :list="selectList"
      @selectedOption="handleData"
    ></CalendarAsideHeader>
    <CalendarAsideDeptSelect
      v-if="select === 0"
      :list="deptList"
      @selectedDept="handleDept"
      @clearContent="handleClear"
    ></CalendarAsideDeptSelect>
    <CalendarAsideMemberSearch
      v-show="select === 1"
      @selectedMember="handleMember"
    ></CalendarAsideMemberSearch>
  </div>
  <CalendarAsideMain :list="annualList"></CalendarAsideMain>
</template>
<script setup>
import { ref, watch } from "vue";
import CalendarAsideHeader from "./aside/CalendarAsideHeader.vue";
import CalendarAsideMain from "./aside/CalendarAsideMain.vue";
import CalendarAsideDeptSelect from "./aside/CalendarAsideDeptSelect.vue";
import CalendarAsideMemberSearch from "./aside/CalendarAsideMemberSearch.vue";
import Store from "@/store/index.js";
import mixins from "@/utils/mixins";

const selectList = ["부서로 찾기", "이름으로 찾기"];
const deptList = ref([]);
const annualList = ref([]);
const select = ref(0);
const dept = ref("");

watch(select, () => {
  annualList.value = [];
});

function handleClear() {
  annualList.value = [];
}

Store.watch((newState) => {
  deptList.value = newState.deptList;
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
    page: 1,
    size: 5,
  };
}

async function handleDept(deptName) {
  if (deptName === dept.value) {
    return;
  }
  dept.value = deptName;
  const params = makeParams(deptName);
  const res = await mixins.methods.$api(`calendar/annual/department`, "get", {
    params,
  });
  annualList.value = res.data;
}

async function handleMember(memberName) {
  if (memberName === "") {
    annualList.value = [];
    return;
  }
  const params = makeParams(memberName);
  const res = await mixins.methods.$api(`calendar/annual/member`, "get", {
    params,
  });
  annualList.value = res.data;
}
</script>