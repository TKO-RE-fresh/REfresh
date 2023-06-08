<template>
  <div>
    <form ref="joinForm" @submit="join">
      <div class="p-6 flex flex-col bg-white my-4 rounded-lg w-8/12 mx-auto">
        <div class="flex mt-7">
          <div class="w-1/2 text-center">
            <div class="my-5">
              <div class="my-3">
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >사원명</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.memberName"
                  type="text"
                  autocomplete="off"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50 hover:border-blue-300"
                  placeholder="사원명을 입력하세요."
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >아이디</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.memberId"
                  type="text"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50 hover:border-blue-300"
                  autocomplete="off"
                  placeholder="아이디를 입력하세요."
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >비밀번호</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.password"
                  type="password"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50 hover:border-blue-300"
                  autocomplete="off"
                  placeholder="비밀번호를 입력하세요."
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >부서명</label
                >
              </div>
              <div>
                <select
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 hover:border-blue-300 focus:outline-none focus:ring focus:border-blue-50"
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
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >연차 개수</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.annualCount"
                  type="number"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50 hover:border-blue-300"
                  autocomplete="off"
                  placeholder="연차 개수를 입력하세요."
                />
              </div>
            </div>
          </div>
          <div class="w-1/2 text-center">
            <div class="my-5">
              <div class="my-3">
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >입사일</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.createdDate"
                  type="date"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50 hover:border-blue-300"
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >전화번호</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.memberCellphone"
                  type="text"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50 hover:border-blue-300"
                  autocomplete="off"
                  placeholder="010-0000-0000"
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >이메일</label
                >
              </div>
              <div>
                <input
                  v-model="joinInput.memberEmail"
                  type="text"
                  autocomplete="off"
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50 hover:border-blue-300"
                  placeholder="이메일을 입력하세요."
                />
              </div>
            </div>
            <div class="my-5">
              <div class="my-3">
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >사용자 권한</label
                >
              </div>
              <div>
                <select
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 hover:border-blue-300 focus:outline-none focus:ring focus:border-blue-50"
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
                <label class="text-gray-700 font-bold mb-2 text-xl"
                  >사용자 상태</label
                >
              </div>
              <div>
                <select
                  class="w-2/3 shadow border rounded py-2 px-3 text-gray-700 hover:border-blue-300 focus:outline-none focus:ring focus:border-blue-50"
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
        <div class="flex justify-center mt-10">
          <div class="space-x-4">
            <button
              id="request-btn"
              class="text-white bg-[#003566] hover:bg-[#f6aa1c]  font-mice focus:ring-4 focus:outline-none focus:ring-[#1da1f2]/50 rounded-lg text-base px-3 py-1.5 text-center inline-flex items-center"
              type="submit"
            >
              등록
            </button>
            <button
              class="text-white bg-[#003566] hover:bg-[#f6aa1c] font-mice focus:ring-4 focus:outline-none focus:ring-gray-100 rounded-lg text-base px-3 py-1.5 text-center inline-flex items-center"
              type="reset"
            >
              취소
            </button>
          </div>
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
        .catch(() => {
          Swal.fire(
            "등록 실패",
            "사원 등록에 실패했습니다. 다시 시도해 주세요.",
            "error"
          );
        });
    }
    joinForm.value.reset();
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
</script>
