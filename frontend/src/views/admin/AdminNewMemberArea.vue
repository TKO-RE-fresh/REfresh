<template>
  <div>
    <form id="new-member-form" method="post" action="">
      <div
        class="p-6 flex flex-col bg-gray-100 my-4 rounded-lg shadow-sm w-8/12 mx-auto"
      >
        <h3 class="normal-case text-3xl font-bold text-center">사원 생성</h3>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">사원명</label>
          <input
            type="text"
            autocomplete="off"
            class="shadow border rounded py-2 px-3 font-bold text-xl text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            placeholder="사원명을 입력하세요."
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">아이디</label>
          <input
            id="memberId"
            name="memberId"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            type="text"
            autocomplete="off"
            placeholder="아이디를 입력하세요."
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">비밀번호</label>
          <input
            th:field="*{memberPassword}"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            type="password"
            autocomplete="off"
            placeholder="비밀번호를 입력하세요."
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">부서명</label>
          <select
            th:field="*{departmentName}"
            class="shadow border rounded py-2 px-3 text-gray-700 hover:border-blue-300 focus:outline-none focus:ring focus:border-blue-50"
          >
            <option v-for="(deptName, idx) in department" :key="idx">
              {{ deptName }}
            </option>
          </select>
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">연차 개수</label>
          <input
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            type="number"
            autocomplete="off"
            placeholder="연차 개수를 입력하세요."
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">입사일</label>
          <input
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            type="date"
            value=""
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">전화번호</label>
          <input
            th:field="*{memberCellphone}"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            type="text"
            autocomplete="off"
            placeholder="010-xxxx-xxxx"
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">이메일</label>
          <input
            th:field="*{memberEmail}"
            autocomplete="off"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            type="text"
            placeholder="이메일을 입력하세요."
          />
        </div>
        <div class="flex m-2">
          <span class="m-2 font-bold">
            <input
              type="checkbox"
              th:field="*{memberAuth}"
              class="mr-1"
              value="admin"
              th:vlaue="admin"
            />
            <label>관리자 권한으로 생성</label>
          </span>
          <span class="m-2 font-bold">
            <input
              type="checkbox"
              th:field="*{memberAuth}"
              class="mr-1"
              value="admin"
              th:vlaue="admin"
            />
            <label>휴먼 계정으로 생성</label>
          </span>
        </div>
        <div class="flex justify-end">
          <div class="space-x-4">
            <button
              id="request-btn"
              class="text-white bg-blue-500 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-[#1da1f2]/50 font-semibold rounded-lg text-base px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-[#1da1f2]/55"
              type="submit"
            >
              등록
            </button>
            <button
              class="text-gray-900 bg-gray-300 hover:bg-white focus:ring-4 focus:outline-none focus:ring-gray-100 font-semibold rounded-lg text-base px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-gray-500"
              type="reset"
            >
              초기화
            </button>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue";
import mixins from "@/utils/mixins";

const department = ref([]);

onMounted(async () => {
  const res = await mixins.methods.$api(`/admin/member/new`, `get`, "");
  const deptList = [];
  console.log(res.data);
  for (let i = 0; i < res.data.length; i++) {
    deptList.push(res.data[i]);
  }
  department.value = deptList;
});
</script>
