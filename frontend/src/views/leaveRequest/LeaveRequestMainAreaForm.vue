<template lang="ko">
    <div>
    <form
      @submit.prevent="submitForm"
        id="leave-request-form"
        method="post"
        action="/leaveRequest/confirmResult"
      >
        <div class="p-6 flex flex-col  bg-slate-50 my-4 rounded-lg shadow-sm font-mice
        ">
          <div class="flex flex-col ml-6">
            <label class="text-gray-700 font-bold mb-2 text-xl">신청인</label>
              <span> {{ applicantName }}</span>
          </div>

          <!-- 연차 확인 -->
          <div class="">
            <div class="flex flex-col m-6">
                <div class="font-bold mb-2 text-xl">연차 현황</div>
                <div>
                    <div>남은 휴가: <span class="text-blue-500">{{ restLeave }}</span>일</div>
                    <div>사용 휴가: <span class="text-red-500">{{ usedLeave }}</span>일</div>
                </div>
            </div>
                 

            <!-- 연차 종류 -->
            <div class="flex flex-col m-6">
              <label class="text-gray-700 font-bold mb-2 text-xl" > 연차 종류 </label>
              <div class="grid w-[24rem] grid-cols-3 space-x-2 rounded-xl bg-gray-200 p-2">
                <div>
                  <input
                    id="am-half-day"
                    type="radio"
                    name="leaveType"
                    value="am-half-day"
                    class="peer hidden"
                    @change="handleLeaveType"
                  />
                  <label
                    for="am-half-day"
                    class="block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-[#f6aa1c] peer-checked:font-bold peer-checked:text-white font-semibold"
                    >오전 반차</label
                  >
                </div>

                <div>
                  <input
                    id="pm-half-day"
                    type="radio"
                    name="leaveType"
                    value="pm-half-day"
                    class="peer hidden"
                    @change="handleLeaveType"
                  />
                  <label
                    for="pm-half-day"
                    class="block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-[#f6aa1c] peer-checked:font-bold peer-checked:text-white font-semibold" 
                    >오후 반차</label
                  >
                </div>
                <div>
                  <input
                    id="full-day"
                    type="radio"
                    name="leaveType"
                    value="full-day"
                    class="peer hidden"
                    @change="handleLeaveType"
                  />
                  <label
                    for="full-day"
                    class="block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-[#f6aa1c] peer-checked:font-bold peer-checked:text-white font-semibold"
                    >연차</label>
                </div>
              </div>
            </div>
            

            <!-- 연차 시작일 & 종료일 선택 -->
            <div class="flex m-6 space-x-16">
              <div class="flex flex-col">
                <label class="font-bold mb-3">시작일</label>
                <input 
                type="date" 
                id="startDate"
                name="startDate"
                v-model="startDate"
                v-bind:min="tomorrow"
                placeholder="시작일"
                @change="validityCheck"
                class="rounded-lg pl-4 pr-2 py-2 shadow focus:outline-none focus:ring focus:ring-blue-500">
              </div>
              <div v-if="selectedLeaveType === '연차'" class="flex flex-col">
                <label class="font-bold mb-3">종료일</label>
                <input
                type="date"
                  id="endDate"
                  name="endDate"
                  v-bind:min="startDate"
                  v-bind:max="validateDate"
                  v-model="endDate"
                  placeholder="종료일"
                  @change="validityCheck"
                  class="rounded-lg pl-4 pr-2 py-2 shadow focus:outline-none focus:ring focus:ring-blue-500">
              </div>
            </div>
            <div class="ml-6 mt-1 font-semibold text-red-600">{{ errorMessage}}</div>
          </div>

          
          <!-- 버튼 영역 -->
          <div class="flex justify-end">
            <div class="space-x-4 font-mice mr-10">
              <button
                id="request-btn"
                class="text-white bg-[#003566] hover:bg-[#f6aa1c] focus:ring-4 focus:outline-none focus:ring-[#1da1f2]/50 font-semibold rounded-lg text-base px-5 py-2.5 text-center inline-flex items-center "
                type="submit"
                :disabled="errorMessageLength !== 0"
                @click="confirmAlert(applicantName, selectedLeaveType, startDate, endDate)"
              >
                신청
              </button>
              <button
                class="text-gray-900 bg-gray-300 hover:bg-[#f6aa1c] focus:ring-4 focus:outline-none focus:ring-gray-100 font-semibold rounded-lg text-base px-5 py-2.5 text-center inline-flex items-center"
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
<script>
import { ref, computed, reactive, onMounted, watch } from "vue";
import axios from "axios";
import { useStore } from "vuex";
import Swal from "sweetalert2/dist/sweetalert2";

export default {
  setup() {
    const store = useStore();

    const restLeave = ref(0);
    const usedLeave = ref(0);
    const startDate = ref(null);
    const endDate = ref(null);
    const daysDiff = ref(null);
    const errorMessage = ref("");
    const validateDate = ref(null);
    const errorMessageLength = ref(0);

    const state = reactive({
      applicantName: "",
    });

    const memberName = store.state.memberName;
    state.applicantName = memberName;

    const memberId = ref(store.state.memberId);

    const selectedLeaveType = ref("");
    const annualType = ref("");

    const today = computed(() => {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, "0");
      const day = String(now.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    });

    const tomorrow = computed(() => {
      const today = new Date();
      const now = new Date(today.setDate(today.getDate() + 1));
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, "0");
      const day = String(now.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    });

    const handleLeaveType = (e) => {
      const selectedValue = e.target.value; // let 쓰면 에러
      if (selectedValue === "full-day") {
        selectedLeaveType.value = "연차";
        annualType.value = "연차";
      } else if (selectedValue === "am-half-day") {
        selectedLeaveType.value = "오전 반차";
        annualType.value = "오전 반차";
      } else if (selectedValue === "pm-half-day") {
        selectedLeaveType.value = "오후 반차";
        annualType.value = "오후 반차";
      }
    };

    /* 시작 & 종료일 유효성 검사 */
    watch(
      [
        selectedLeaveType,
        startDate,
        endDate,
        today,
        daysDiff,
        restLeave,
        validateDate,
        errorMessageLength,
      ],
      () => {
        errorMessage.value = "";
        const start = startDate.value;
        const end = endDate.value;

        daysDiff.value = Math.ceil(
          (new Date(end).getTime() - new Date(start).getTime()) /
            (1000 * 60 * 60 * 24)
        );

        /* 유효성 검사 */
        /* 시작일을 선택 */
        if (start !== null) {
          /* 반차인 경우 */
          if (selectedLeaveType.value.includes("반차")) {
            endDate.value = startDate.value;
            console.log(
              `반차 - 시작일: ${startDate.value}, 종료일: ${endDate.value}`
            );
            if (restLeave.value < 0.5) {
              errorMessage.value =
                "연차기간이 남은 연차일을 초과할 수 없습니다!";
            }

            /* 오늘보다 이전날짜를 선택한 경우 */
            if (start <= today.value) {
              errorMessage.value = "휴가 시작일은 오늘 날짜 이후여야 합니다!";
            } else {
              console.log("반차 - 휴가 시작일이 오늘보다 이후가 맞음");
            }
          } else {
            /* 연차인 경우 */

            /* 오늘보다 이전날짜를 선택한 경우 */
            if (start <= today.value) {
              errorMessage.value = "휴가 시작일은 오늘 날짜 이후여야 합니다!";
            } else {
              console.log("연차 - 휴가 시작일이 오늘보다 이후가 맞음");
            }

            /* 종료일을 선택 */
            if (end !== null) {
              console.log("종료일 선택. + 잔여연차: " + restLeave.value);
              console.log("종료일 선택. 시작일 : " + start);
              console.log("종료일 선택. 종료일:  " + end);
              console.log("종료일 선택. + 날짜 차이일: " + daysDiff.value);

              /* 종료일보다 시작일이 늦은 경우 */
              if (start > end) {
                errorMessage.value =
                  "휴가 시작일은 휴가 종료일 이전이어야 합니다!";
              }

              /* 남은 연차를 초과하는 경우 */
              if (daysDiff.value > restLeave.value) {
                errorMessage.value =
                  "신청한 휴가 일수가 남은 휴가일수 보다 많습니다!";
                console.log("보유휴가수 > 잔여휴가수 에러: ", errorMessage);
              }
            }
          }
        } else {
          errorMessage.value = "시작일을 선택하세요!";
        }
        errorMessageLength.value = errorMessage.value.length;
      }
    );

    /* 잔여연차 & 사용연차 받아오기 */
    const fetchLeaveDates = async () => {
      try {
        const response = await axios.get("http://localhost:80/leaveRequest");
        restLeave.value = response.data.restLeaveCount;
        usedLeave.value = response.data.usedLeaveCount;
      } catch (error) {
        console.error(error);
      }
    };

    /* 휴가 신청 폼 제출 */
    const submitForm = async () => {
      const period = {
        startDate: new Date(startDate.value).toISOString(),
        endDate: new Date(endDate.value).toISOString(),
      };

      /* 휴가 신청 로직  */
      let data = {
        memberId: memberId.value,
        annualType: annualType.value,
        period,
      };

      // 반차인 경우
      if (selectedLeaveType.value.includes("반차")) {
        period.endDate = period.startDate;

        data = {
          memberId: memberId.value,
          annualType: annualType.value,
          period,
        };
      }
      console.log("삽입전 데이터 확인: " + data);

      try {
        const response = await axios.post(
          "http://localhost:80/leaveRequest",
          data
        );

        console.log("휴가 신청 post: " + response.data);
      } catch (error) {
        console.log("휴가 신청시 에러메시지: ", error);
      }
    };

    /* 연차신청내역 확인 모달  */
    const confirmAlert = (
      applicantName,
      selectedLeaveType,
      startDate,
      endDate
    ) => {
      Swal.fire({
        showCloseButton: true,
        title: "연차신청 내역 확인",
        text: "신청한 연차 내역을 확인하세요",
        icon: "success",
        confirmButtonColor: "#003566",
        confirmButtonText: "확인",
        html:
          "<div>이름: " +
          applicantName +
          "</div>" +
          "<div>" +
          "연차 종류: " +
          selectedLeaveType +
          "</div>" +
          "<div>" +
          "시작일: " +
          startDate +
          "</div>" +
          "<div>" +
          "종료일: " +
          endDate +
          "</div>",
      });
    };

    onMounted(fetchLeaveDates);

    return {
      applicantName: state.applicantName, // 변수를 반환하여 템플릿에서 사용할 수 있도록 함
      restLeave,
      usedLeave,
      startDate,
      endDate,
      handleLeaveType,
      errorMessage,
      submitForm,
      selectedLeaveType,
      today,
      tomorrow,
      confirmAlert,
      errorMessageLength,
    };
  },
};
</script>
