<template>
  <div>
    <form ref="joinForm" @submit="join">
      <div class="flex flex-col bg-white my-4 rounded-lg w-8/12 mx-auto">
        <div class="flex mt-2">
          <div class="w-1/2 text-center">
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  required
                  >사원명</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.memberName"
                  type="text"
                  autocomplete="off"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  placeholder="사원명을 입력하세요."
                  required
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  >아이디</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.memberId"
                  type="text"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  autocomplete="off"
                  placeholder="아이디를 입력하세요."
                  required
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  >비밀번호</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.password"
                  type="password"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  autocomplete="off"
                  placeholder="비밀번호를 입력하세요."
                  required
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  >부서명</label
                >
              </div>
              <div>
                <select
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  @change="setDepartment($event)"
                >
                  <option
                    v-for="(deptName, idx) in joinFormData.departmentList"
                    :key="idx"
                    :value="deptName"
                    :selected="idx == 0"
                  >
                    {{ deptName }}
                  </option>
                </select>
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  >연차 개수</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.annualCount"
                  type="number"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  autocomplete="off"
                  placeholder="연차 개수를 입력하세요."
                  required
                />
              </div>
            </div>
          </div>
          <div class="w-1/2 text-center">
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  >입사일</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.createdDate"
                  type="date"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  required
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  >전화번호</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.memberCellphone"
                  type="text"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  autocomplete="off"
                  placeholder="010-0000-0000"
                  required
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  >이메일</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.memberEmail"
                  type="text"
                  autocomplete="off"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  placeholder="이메일을 입력하세요."
                  required
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  >사용자 권한</label
                >
              </div>
              <div>
                <select
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  @change="setMemberAuth($event)"
                >
                  <option
                    v-for="(auth, idx) in joinFormData.memberAuthList"
                    :key="idx"
                    :value="auth"
                    :selected="idx == 0"
                  >
                    {{ auth }}
                  </option>
                </select>
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label
                  class="text-gray-700 font-bold mb-2 text-xl border-b-2 border-[#f6aa1c]"
                  >사용자 상태</label
                >
              </div>
              <div>
                <select
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 border-[#003566] focus:outline-none focus:ring-1 focus:ring-[#003566]"
                  @change="setMemberStatus($event)"
                >
                  <option
                    v-for="(status, idx) in joinFormData.memberStatusList"
                    :key="idx"
                    :value="status"
                    :selected="idx == 0"
                  >
                    {{ status }}
                  </option>
                </select>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="flex justify-center mt-10">
        <div class="space-x-4">
          <button
            id="request-btn"
            class="text-white bg-[#003566] hover:bg-[#f6aa1c] focus:ring-4 focus:outline-none focus:ring-[#1da1f2]/50 font-semibold rounded-lg text-base px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-[#1da1f2]/55"
            type="submit"
          >
            등록
          </button>
          <button
            class="text-gray-900 bg-gray-300 hover:bg-[#f6aa1c] focus:ring-4 focus:outline-none focus:ring-gray-100 font-semibold rounded-lg text-base px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-gray-500"
            type="button"
            @click="resetForm"
          >
            취소
          </button>
        </div>
      </div>
    </form>
  </div>
</template>
<script setup>
import { onMounted, reactive, toRaw, ref } from "vue";
import axios from "axios";
import Swal from "sweetalert2/dist/sweetalert2.js";
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
});

const joinForm = ref(null);

onMounted(async () => {
  const res = await mixins.methods.$api("admin/member/searchForm", "get", "");

  joinFormData.departmentList = res.data.departmentNameList;
  joinFormData.memberStatusList = res.data.memberStatusList;
  joinFormData.memberAuthList = res.data.memberAuthList;

  joinInput.departmentName = res.data.departmentNameList[0];
  joinInput.memberStatus = res.data.memberStatusList[0];
  joinInput.memberAuth = res.data.memberAuthList[0];
});

const join = (event) => {
  event.preventDefault();

  console.log(joinInput);

  Swal.fire({
    title: "사원 등록",
    text: "사원을 등록하시겠습니까?",
    icon: "info",
    showCancelButton: true,
    cancelButtonText: "취소",
    confirmButtonColor: "#3b82f6",
    confirmButtonText: "확인",
  }).then(async (result) => {
    if (result.isConfirmed) {
      await axios
        .post("http://localhost:8090/admin/member", toRaw(joinInput))
        .then(() => {
          Swal.fire("등록 완료", "사원이 등록되었습니다.", "success");
        })
        .catch((error) => {
          console.log(error);
          Swal.fire("등록 실패", "사원 등록에 실패했습니다. 다시 시도해 주세요.",'error'); 
        })
        .finally( () =>{
          resetForm();
        })
    } 
  });
};

function setDepartment(e) {
  joinInput.departmentName = e.target.value;
}

function setMemberAuth(e) {
  joinInput.memberAuth = e.target.value;
}

function setMemberStatus(e) {
  joinInput.memberStatus = e.target.value;
}

const resetForm = () => {
  joinInput.memberId = '';
  joinInput.password = '';
  joinInput.memberName = '';
  joinInput.memberCellphone = '';
  joinInput.memberEmail = '';
  joinInput.annualCount = '';
  joinInput.createdDate = '';
  joinInput.departmentName = joinFormData.departmentList[0];
  joinInput.memberStatus = joinFormData.memberStatusList[0];
  joinInput.memberAuth = joinFormData.memberAuthList[0];

  joinForm.value.reset();
};
</script>
