<template>
    <form @submit="handleSubmit">
    <div
      class="flex bg-base-80 border border-slate-300 rounded-lg shadow-md shadow-slate-300/40 my-10 mx-16"
    >
      <div class="py-2 flex items-center justify-between space-x-16 w-full mx-6 text-gray-600">
        <div class="flex space-x-6 font-mice">
          <!-- 부서 검색 -->
          <div class="flex items-center space-x-3">
            <label>부서명</label>
            <select class="select select-bordered font-semibold"
                    @change="setDepartment($event)">
              <option value="" selected>전체</option>
              <option v-for="(department, idx) in searchFormData.departmentNameList"
                      :key="idx" :value="department">{{ department }}</option>
            </select>
          </div>
          <!-- 사원 검색 -->
          <div class="flex items-center space-x-3">
            <label>사원명</label>
            <input
              id="name"
              v-model="selectedMemberName"
              type="text"
              placeholder="사원명 입력"
              class="input input-bordered"
            />
          </div>
          <!-- 사용자 상태 -->
          <div class="flex items-center space-x-3">
            <label>사용자 상태</label>
            <select class="select select-bordered font-semibold"
                    @change="setStatus($event)">
              <option value="" selected>전체</option>
              <option v-for="(status, idx) in searchFormData.memberStatusList" 
                      :key="idx" :value="status">{{ status }}</option>
            </select>
          </div>
        </div>
        <button
          type="submit"
          class="bg-[#003566] hover:bg-[#f6aa1c] px-3 py-1.5  rounded-md text-white font-mice"
        >
          조회
        </button>
      </div>
    </div>
  </form>
  <AdminMemberTable :memberName="memberName" :departmentName="departmentName" :status="status" ></AdminMemberTable>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import AdminMemberTable from './AdminMemberTable.vue';
import mixins from '@/utils/mixins';

const searchFormData = reactive({
    departmentNameList: '',
    memberStatusList: ''
});

const selectedDepartmentName = ref();
const selectedMemberName = ref();
const selectedStatus = ref();
const memberName = ref();
const departmentName = ref();
const status = ref();

onMounted(async () => {
    const res = await mixins.methods.$api('admin/member/searchForm', 'get', '');

    searchFormData.departmentNameList = res.data.departmentNameList;
    searchFormData.memberStatusList = res.data.memberStatusList;
});

const handleSubmit = (event) => {
    event.preventDefault();

    memberName.value = selectedMemberName.value;
    departmentName.value = selectedDepartmentName.value;
    status.value = selectedStatus.value;
}; 


function setDepartment(e) {
  selectedDepartmentName.value = e.target.value;
}

function setStatus(e) {
  selectedStatus.value = e.target.value;
}

</script>
