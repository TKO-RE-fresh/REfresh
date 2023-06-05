<template>
    <transition name="fade">
    <div v-if="isOpen">
      <form @submit="handleSubmit">
        <div>
          <div class="fixed inset-0 z-10 flex items-center justify-center">
            <div
              class="w-full max-w-2xl p-3 relative mx-auto rounded-xl shadow-lg bg-white"
            >
              <div>
                <div class="text-center p-3 flex-auto justify-center leading-6">
                  <h2 class="text-2xl font-bold py-4">사원 상세 정보</h2>
                  <div class="flex" name="firstContetns">
                      <div class="w-1/2 flex justify-center items-center">
                          <img class="h-24 w-24 rounded-full object-cover"
                          src="@/assets/images/user.svg"
                          alt="프로필 사진"/>
                      </div>
                      <div class="flex w-1/2 text-left">
                          <div class="w-1/3 text-sm" name="detailLabel">
                            <div class="m-4">
                              <label>이름</label>  
                            </div>
                            <div class="m-4">
                              <label>부서</label>  
                            </div>
                            <div class="m-4">
                              <label>전화번호</label>  
                            </div>
                            <div class="m-4">
                              <label>이메일</label>  
                            </div>  
                        </div>
                        <div class="w-2/3 text-sm" name="detailInfo">
                            <div class="m-2">
                              <input v-model="member.memberName" type="text" :readonly=!editMode 
                                :class="{'shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}">
                            </div>
                            <div class="m-2">
                              <select v-model="member.departmentName" :disabled=!editMode 
                                :class="{'disabled:opacity-100 shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'disabled:opacity-100 shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}">
                                <option v-for="(department, idx) in member.departmentNameList" :key="idx" :selected="department == member.departmentName">{{ department }}</option>
                              </select>
                              <!-- <input v-model="member.departmentName" type="text" :readonly=!editMode 
                                :class="{'shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}"> -->
                            </div>
                            <div class="m-2">
                              <input v-model="member.memberCellphone" type="text" :readonly=!editMode 
                                :class="{'shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}">
                            </div>
                            <div class="m-2">
                              <input v-model="member.memberEmail" type="text" :readonly=!editMode
                                :class="{'shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}">
                            </div>
                        </div>
                      </div>
                  </div>
                  <div class="flex" name="secondContetns">
                    <div class="flex w-1/2 text-left">
                      <div class="w-23 text-sm" name="detailLabel">
                        <div class="m-4">
                          <label>아이디</label>  
                        </div>
                        <div class="m-4">
                          <label>입사일자</label>  
                        </div>
                        <div class="m-4">
                          <label>퇴사일자</label>  
                        </div>
                        <div class="m-4">
                          <label>계정 상태</label>  
                        </div>  
                      </div>
                      <div class="text-sm" name="detailInfo">
                        <div class="m-2">
                          <input v-model="member.memberId" type="text" :readonly=!editMode
                            :class="{'shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}">
                        </div>
                        <div class="m-2">
                          <!-- <input v-model="member.createdDate" type="date" :readonly=!editMode
                            :class="{'shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}"/> -->
                          <input v-model="member.createdDate" type="text" :readonly=true
                            class="shadow border rounded py-1 text-gray-700 hover:bg-gray-100">
                        </div>
                        <div class="m-2">
                          <input v-model="member.retireDate" type="date" :readonly=!editMode
                            :class="{'shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}"/>
                        </div>
                        <div class="m-2">
                          <!-- <span :class="member.memberStatus == 'IN_USE' ? 'text-blue-500' : 'text-red-200'" class="py-1 mx-2">{{ member.memberStatus }}</span> -->
                          <select v-model="member.memberStatus" :disabled=!editMode 
                            :class="{'disabled:opacity-100 shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'disabled:opacity-100 shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}">
                            <option v-for="(status, idx) in member.memberStatusList" :key="idx" :selected="status == member.memberStatus">{{ status }}</option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div class="flex w-1/2 text-left">
                      <div class="w-23 text-sm" name="detailLabel">
                        <div class="m-4">
                          <label>잔여연차</label>  
                        </div>
                        <div class="m-4">
                          <label>생성자</label>  
                        </div>
                        <div class="m-4">
                          <label>수정자</label>  
                        </div>
                        <div class="m-4">
                          <label>수정 날짜</label>  
                        </div>  
                        <div class="m-4">
                          <label>사용자 권한</label>  
                        </div> 
                      </div>
                      <div class="text-sm" name="detailInfo">
                        <div class="m-2">
                          <input v-model="member.annualCount" type="text" :readonly=!editMode 
                            :class="{'shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}">
                        </div>
                        <div class="m-2">
                          <input v-model="member.createdBy" type="text" :readonly=true 
                            class="shadow border rounded py-1 text-gray-700 hover:bg-gray-100">
                        </div>
                        <div class="m-2">
                          <input v-model="member.modifiedBy" type="text" :readonly=true
                            class="shadow border rounded py-1 text-gray-700 hover:bg-gray-100">  
                        </div>
                        <div class="m-2">
                          <input v-model="member.modifiedDate" type="text" :readonly=true
                            class="shadow border rounded py-1 text-gray-700 hover:bg-gray-100">
                        </div>
                        <div class="m-2">
                          <select v-model="member.memberAuth" :disabled=!editMode 
                            :class="{'disabled:opacity-100 shadow border rounded py-1 text-gray-700 hover:bg-gray-100' : !editMode, 
                                    'disabled:opacity-100 shadow border rounded py-1 text-gray-700 outline-none ring border-blue-50': editMode}">
                            <option v-for="(role, idx) in member.roleTypeList" :key="idx" :selected="role == member.memberAuth">{{ role }}</option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
    
                <div class="p-3 mt-2 text-center space-x-4 md:block">
                  <button v-show="!editFormDisabled" :type="buttonType"
                    class="mb-2 md:mb-0 bg-white px-5 py-2 text-sm shadow-sm font-medium tracking-wider border text-gray-600 rounded-md hover:shadow-lg hover:bg-gray-100"
                    @click="editMember"
                  >
                    {{ btn }}
                  </button>
                  <button type="button"
                    class="mb-2 md:mb-0 bg-blue-500 border border-blue-500 px-5 py-2 text-sm shadow-sm font-medium tracking-wider text-white rounded-md hover:shadow-lg hover:bg-blue-600"
                    @click="onToggle"
                  >
                    닫기
                  </button>
                  <!-- <button type="submit">submit</button> -->
                </div>
              </div>
            </div>
          </div>
          <div
            class="fixed inset-0 bg-black opacity-70 z-0"
            @click="onToggle"
          ></div>
        </div>
      </form>
      </div>
    </transition>
  </template>
  
  <script setup>
  import { defineProps, watch, ref } from "vue";
  import axios from "axios";

  const member = ref({});
  const editMode = ref(false);
  const btn = ref('수정');
  const buttonType = ref('button');
  
  const props = defineProps({
    isOpen: {
      type: Boolean,
      required: true,
      default: false
    },
    onToggle: {
      type: Function,
      required: true,
    },
    memberId: {
      type : String,
      required: true,
    }
  });

  watch(() => props.isOpen, (newValue) => {
    console.log("props 변경 감지 : " + newValue);
    if(btn.value == '저장') {
      editMember();
    }
    
    // console.log(oldValue);
    if(newValue) {
      getMemberInfo();
    }
  })

  async function getMemberInfo() {
    try {
      const res = await axios.get('http://localhost:8090/admin/member/' + props.memberId);
      console.log("불러온 멤버 정보 : ");
      console.log(res.data);
      console.log(res.data.memberId);

      member.value = res.data;

      console.log(formatDate(member.value.createdDate));
      console.log("날짜객체 타입 : " + typeof member.value.modifiedDate);
      console.log(typeof res.data.memberStatus);

      member.value.createdDate = formatDate(member.value.createdDate);
      member.value.modifiedDate = formatDate(member.value.modifiedDate);
      member.value.retireDate = formatDate(member.value.retireDate);

    } catch (error) {
      console.error('Error get data: ', error);
    }
  }

  // 수정 버튼 클릭
  const editMember = () => {
    if(btn.value == '수정') { // 수정모드
      btn.value = '저장';
      editMode.value = true;
      buttonType.value = 'button';
    } else { // 읽기모드
      btn.value = '수정';
      editMode.value = false;
      buttonType.value = 'submit';
    }
    
    // editMode.value = !editMode.value;
  }

  // date 포맷(yyy-mm-dd) 변경 메서드
  const formatDate = (value) => {
    const dateParts = value.substr(0, 10).split('-');
    const formatted = `${dateParts[0]}-${dateParts[1]}-${dateParts[2]}`;
    return formatted;
  };

const handleSubmit = (event) => {
  event.preventDefault();
    const memberUpdateDto = {
      memberId: member.value.memberId,
      memberName: member.value.memberName,
      memberCellphone: member.value.memberCellphone,
      memberEmail: member.value.memberEmail,
      departmentName: member.value.departmentName,
      annualCount: member.value.annualCount,
      modifiedBy: "admin",
      modifiedDate: "2023-05-30T14:32:14.551757",
      retireDate: member.value.retireDate,
      memberAuth: member.value.memberAuth,
      memberStatus: member.value.memberStatus
    };

    axios.patch('http://localhost:8090/admin/member/' + props.memberId, memberUpdateDto)
    .then(response => {
      alert("사원 정보가 수정되었습니다.");
      console.log(response + " : 사원 정보 업데이트 성공");
    })
    .catch(error => {
      alert("사원 정보 수정에 실패했습니다.");
      console.log(error + " : 사원 정보 업데이트 실패");
    });
  };
  </script>
