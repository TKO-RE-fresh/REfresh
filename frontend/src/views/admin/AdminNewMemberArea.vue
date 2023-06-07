<template>
  <div>
    <form @submit="join">
      <div
        class="p-6 flex flex-col bg-gray-100 my-4 rounded-lg shadow-sm w-8/12 mx-auto"
      >
        <h3 class="normal-case text-3xl font-bold text-center">사원 생성</h3>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">사원명</label>
          <input
            v-model="joinInput.memberName"
            type="text"
            autocomplete="off"
            class="shadow border rounded py-2 px-3 font-bold text-xl text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            placeholder="사원명을 입력하세요."
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">아이디</label>
          <input
            v-model="joinInput.memberId"
            type="text"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            autocomplete="off"
            placeholder="아이디를 입력하세요."
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">비밀번호</label>
          <input
            v-model="joinInput.password"
            type="password"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            autocomplete="off"
            placeholder="비밀번호를 입력하세요."
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">부서명</label>
          <select
            v-model="joinInput.departmentName"
            class="shadow border rounded py-2 px-3 text-gray-700 hover:border-blue-300 focus:outline-none focus:ring focus:border-blue-50"
          >
            <option
              v-for="(deptName, idx) in joinFormData.departmentList"
              :key="idx"
              :selected="idx == 0"
            >
              {{ deptName }}
            </option>
          </select>
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">연차 개수</label>
          <input
            v-model="joinInput.annualCount"
            type="number"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            autocomplete="off"
            placeholder="연차 개수를 입력하세요."
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">입사일</label>
          <input
            v-model="joinInput.createdDate"
            type="date"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">전화번호</label>
          <input
            v-model="joinInput.memberCellphone"
            type="text"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            autocomplete="off"
            placeholder="010-0000-0000"
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl">이메일</label>
          <input
            v-model="joinInput.memberEmail"
            type="text"
            autocomplete="off"
            class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
            placeholder="이메일을 입력하세요."
          />
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl"
            >사용자 권한</label
          >
          <select
            v-model="joinInput.memberAuth"
            class="shadow border rounded py-2 px-3 text-gray-700 hover:border-blue-300 focus:outline-none focus:ring focus:border-blue-50"
          >
            <option
              v-for="(auth, idx) in joinFormData.memberAuthList"
              :key="idx"
              :selected="idx == 0"
            >
              {{ auth }}
            </option>
          </select>
        </div>
        <div class="flex flex-col m-2">
          <label class="text-gray-700 font-bold mb-2 text-xl"
            >사용자 상태</label
          >
          <select
            v-model="joinInput.memberStatus"
            class="shadow border rounded py-2 px-3 text-gray-700 hover:border-blue-300 focus:outline-none focus:ring focus:border-blue-50"
          >
            <option
              v-for="(status, idx) in joinFormData.memberStatusList"
              :key="idx"
              :selected="idx == 0"
            >
              {{ status }}
            </option>
          </select>
        </div>
        <div class="flex justify-center">
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
import { onMounted, reactive, toRaw } from "vue";
import axios from "axios";
import mixins from "@/utils/mixins";

const joinFormData = reactive({
  departmentNameList: "",
  memberStatusList: "",
  memberAuthList: "",
});

const joinInput = reactive({
  memberId: "",
  password: "",
  memberAuth: "",
  memberName: "",
  memberCellphone: "",
  memberEmail: "",
  memberStatus: "",
  annualCount: "",
  departmentName: "",
  createdDate: "",
  modifiedBy: "jaeseok", // 로그인한 관리자 아이디
  createdBy: "jaeseok", // 로그인한 관리자 아이디
});

onMounted(async () => {
  const res = await mixins.methods.$api("admin/member/searchForm", "get", "");

  joinFormData.departmentList = res.data.departmentNameList;
  joinFormData.memberStatusList = res.data.memberStatusList;
  joinFormData.memberAuthList = res.data.memberAuthList;
});

const join = (event) => {
  event.preventDefault();

  // console.log(joinInput);

  axios
    .post("http://localhost:8090/admin/member", toRaw(joinInput))
    .then((response) => {
      alert("사원이 생성되었습니다.");
      console.log(response + " : 사원 추가 성공");
    })
    .catch((error) => {
      alert("사원 생성에 실패하였습니다.");
      console.log(error + " : 사원 추가 실패");
    });
};
</script>
