<template>
  <div class="px-14">
    <div class="border border-slate-200 rounded-xl overflow-hidden">
      <table class="w-full text-sm text-left text-gray-500">
        <thead class="text-md bg-gray-50 text-center text-gray-700">
          <tr class="h-11">
            <th>사원명</th>
            <th>아이디</th> 
            <th>부서명</th>
            <th>전화번호</th>
            <th>이메일</th>
            <th>입사일자</th>
            <th>퇴사일자</th>
            <th>사용상태</th>
            <th>상세정보</th>
          </tr>
        </thead>
        <tbody class="text-center">
            <tr v-for="(member, idx) in members" id="member" :key="idx" class="bg-white border-b hover:bg-blue-50">
                    <td>
                        <div class="flex items-center">
                            <div class="avatar">
                            <div class="mask mask-squircle w-8 h-8 m-2">
                                <img src="@/assets/images/user.svg"
                                    alt="프로필 사진" />
                            </div>
                            </div>
                            <div>
                            <div class="font-bold">{{ member.memberName }}</div>
                            </div>
                        </div>
                    </td>
                    <td>
                    {{ member.memberId }}
                    <br/>
                    </td>
                    <td>{{ member.departmentName }}</td>
                    <td>{{ member.memberCellphone }}</td>
                    <td>{{ member.memberEmail }}</td>
                    <td>{{ member.createdDate }}</td>
                    <td>{{ member.retireDate }}</td>
                    <td>{{ member.memberStatus }}</td>
                    <td>
                    <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" @click="handleModal(member.memberId)">조회</button>
                    </td>
                </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="text-right mr-20">
    계정 수 : {{ totalElements }}
  </div>
<div>
    <PagingView :currentPage="currentPage" :totalPages="totalPages" @selectPage="selectPage"/>
</div>
<AdminDetailMemberModal :isOpen="isOpen" :onToggle="onToggle" :memberId="memberId"></AdminDetailMemberModal>
</template>

<script setup>
import { ref, onMounted, watchEffect, watch, defineProps } from "vue";
import PagingView from "../../components/pagination/pagingComponent.vue";
import AdminDetailMemberModal from "./AdminDetailMemberModal.vue";
import mixins from "@/utils/mixins";

const members = ref([]);
const totalPages = ref(0);
const currentPage = ref(1);
const totalElements = ref(0);

const props = defineProps({
  memberName: String,
  departmentName: String,
  status: String,
});

onMounted(() => {
  selectPage(currentPage.value);
});

function selectPage(idx) {
  const params = {
    page: idx,
    memberName: props.memberName,
    departmentName: props.departmentName,
    status: props.status,
  };

  const arr = [];

  watchEffect(async () => {
      const res = await mixins.methods.$api(`/admin/member`, `get`, { params });    

      console.log(res);
      
      for (let i=0; i<res.data.content.length; i++) {
          (res.data.content[i].createdDate != null) ? res.data.content[i].createdDate = formatDate(res.data.content[i].createdDate) : res.data.content[i].createdDate =null;
          (res.data.content[i].retireDate != null) ? res.data.content[i].retireDate = formatDate(res.data.content[i].retireDate) : res.data.content[i].retireDate =null;
          arr.push(res.data.content[i]);
      }

      members.value = arr;
      totalPages.value = res.data.totalPages;
      currentPage.value = idx;
      totalElements.value = res.data.totalElements;
  });
}

const isOpen = ref(false);
const memberId = ref("");

const onToggle = () => {
  isOpen.value = !isOpen.value;
};

const handleModal = (id) => {
  memberId.value = id;
  console.log("넘길 아이디 : " + memberId.value);
  onToggle();
};

watch(
  () => isOpen.value,
  (newValue) => {
    console.log("창을 닫으면 현재 페이지 새로 고침");
    if (!newValue) {
      selectPage(currentPage.value);
    }
  }
);

watch(
  () => props.departmentName,
  (newValue) => {
    console.log(newValue);
    selectPage(1);
  }
);

watch(
  () => props.memberName,
  (newValue) => {
    console.log(newValue);
    selectPage(1);
  }
);

watch(
  () => props.status,
  (newValue) => {
    console.log(newValue);
    selectPage(1);
});

// date 포맷(yyy-mm-dd) 변경 메서드
const formatDate = (value) => {
const dateParts = value.substr(0, 10).split('-');
const formatted = `${dateParts[0]}-${dateParts[1]}-${dateParts[2]}`;
return formatted;
};
</script>
