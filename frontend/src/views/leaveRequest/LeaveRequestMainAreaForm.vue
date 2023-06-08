<template lang="ko">
    <div>
    <form
      @submit.prevent="submitForm"
        id="leave-request-form"
        method="post"
        action="/leaveRequest/confirmResult"
      >
        <div class="p-6 flex flex-col  bg-slate-200 my-4 rounded-lg shadow-sm
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
                    <div>남은 연차: <span class="text-blue-500">{{ restLeave }}</span>일</div>
                    <div>사용한 연차: <span class="text-red-500">{{ usedLeave }}</span>일</div>
                </div>
            </div>
                 

            <!-- 연차 종류 -->
            <div class="flex flex-col m-6">
              <label class="text-gray-700 font-bold mb-2 text-xl" > 연차 종류 </label>
              <div class="grid w-[24rem] grid-cols-3 space-x-2 rounded-xl bg-gray-100 p-2">
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
                    class="block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-blue-500 peer-checked:font-bold peer-checked:text-white font-semibold"
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
                    class="block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-blue-500 peer-checked:font-bold peer-checked:text-white font-semibold" 
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
                    class="block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-blue-500 peer-checked:font-bold peer-checked:text-white font-semibold"
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
                v-bind:min="today"
                placeholder="시작일"
                @change="validityCheck"
                class="rounded-lg pl-4 pr-2 py-2 shadow focus:outline-none focus:ring focus:ring-blue-500">
              </div>
              <div v-show="selectedLeaveType === '연차'" class="flex flex-col">
                <label class="font-bold mb-3">종료일</label>
                <input
                type="date"
                  id="endDate"
                  name="endDate"
                  v-model="endDate"
                  placeholder="종료일"
                  @change="validityCheck"
                  class="rounded-lg pl-4 pr-2 py-2 shadow focus:outline-none focus:ring focus:ring-blue-500">
              </div>
            </div>
            <div class="ml-6 mt-1 font-semibold text-red-600">{{ errorMessage}}</div>
          </div>

          

          <div class="flex justify-end">
            <div class="space-x-4">
              <button
                id="request-btn"
                class="text-white bg-blue-500 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-[#1da1f2]/50 font-semibold rounded-lg text-base px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-[#1da1f2]/55"
                type="submit"
              >
                신청
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
<script>
import { ref, computed, reactive, onMounted, watch } from "vue";
import axios from "axios";
import { useStore } from "vuex";

export default {
  setup() {
    const restLeave = ref(0);
    const usedLeave = ref(0);
    const startDate = ref(null);
    const endDate = ref(null);
    const errorMessage = ref("");

    const state = reactive({
      applicantName: "",
    });

    const store = useStore();
    const memberName = store.state.memberName;
    state.applicantName = memberName;

    const memberId = ref(store.state.memberId);
    // console.log("멤버 아이디: " + memberId.value);

    const selectedLeaveType = ref("");
    const annualType = ref("");

    const today = computed(() => {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, "0");
      const day = String(now.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    });

    console.log("오늘날짜 today.value: " + today.value);

    const tomorrow = computed(() => {
      const today = new Date();
      const now = new Date(today.setDate(today.getDate() + 1));
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, "0");
      const day = String(now.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    });

    console.log("내일: " + tomorrow.value);

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
    watch([selectedLeaveType, startDate, endDate, today], () => {
      errorMessage.value = "";

      const start = startDate.value;
      const end = endDate.value;
      // const today = new Date().toJSON().slice(0, 10);

      /* 유효성 검사 */
      /* 시작일을 선택 */
      if (start !== null) {
        /* 반차인 경우 */
        if (selectedLeaveType.value.includes("반차")) {
          // console.log("반차선택!!!!!");

          if (restLeave.value < 0.5) {
            errorMessage.value = "연차기간이 남은 연차일을 초과할 수 없습니다!";
            // console.log(errorMessage);
          }

          /* 오늘보다 이전날짜를 선택한 경우 */
          if (start <= today.value) {
            // console.log("휴가 시작일이 오늘 이전임 ㅡㅡ ");
            errorMessage.value = "휴가 시작일은 오늘 날짜 이후여야 합니다!";
            // console.log(errorMessage);
          } else {
            // console.log("휴가 시작일이 오늘보다 이후가 맞음");
          }
        } else {
          /* 연차인 경우 */
          // console.log("연차선택!!!!!!");

          /* 오늘보다 이전날짜를 선택한 경우 */
          if (start <= today.value) {
            // console.log("휴가 시작일이 오늘 이전임 ㅡㅡ ");
            errorMessage.value = "휴가 시작일은 오늘 날짜 이후여야 합니다!";
            // console.log(errorMessage);
          } else {
            // console.log("휴가 시작일이 오늘보다 이후가 맞음");
          }

          /* 종료일을 선택 */
          if (end !== null) {
            /* 종료일보다 시작일이 늦은 경우 */
            if (start > end) {
              errorMessage.value =
                "휴가 시작일은 휴가 종료일 이전이어야 합니다!";
              // console.log(errorMessage);
            }

            /* 남은 연차를 초과하는 경우 */
            const daysDiff = Math.ceil((end - start) / (1000 * 60 * 60 * 24));
            // console.log("날짜차이일: " + daysDiff);
            if (daysDiff > restLeave.value) {
              errorMessage.value = "휴가 일수가 잔여 연차보다 많습니다!";
              // console.log(errorMessage);
            }
          }
        }
      }
    });

    /* 잔여연차 & 사용연차 받아오기 */
    const fetchLeaveDates = async () => {
      try {
        const response = await axios.get("http://localhost:8090/leaveRequest");
        restLeave.value = response.data.restLeaveCount;
        usedLeave.value = response.data.usedLeaveCount;
      } catch (error) {
        console.error(error);
      }
    };

    /* 휴가 신청 폼 제출 */
    const submitForm = async () => {
      // if (!validityCheck()) {
      //   return;
      // }

      const period = {
        startDate: new Date(startDate.value).toISOString(),
        endDate: new Date(endDate.value).toISOString(),
      };

      // new Date(period.startDate).toISOString()

      /* 휴가 신청 로직  */
      let data = {
        memberId: memberId.value,
        annualType: annualType.value,
        period,
      };

      if (selectedLeaveType.value.includes("반차")) {
        // console.log("신청시 반차 선택!!!ㅋㅋㅋㅋㅋㅋㅋ");
        // console.log("피리어드의 스타트일: " + period.startDate);

        period.endDate = period.startDate;

        // console.log("피리어드의 종료일: " + period.endDate);

        // console.log(
        //   "기간: " +
        //     period +
        //     " 기간 쪼개서 확인: " +
        //     period.startDate +
        //     " " +
        //     period.endDate
        // );

        data = {
          memberId: memberId.value,
          annualType: annualType.value,
          period,
        };
      }

      console.log("시작일: " + new Date(period.startDate).toISOString());
      console.log("종료일: " + period.endDate);

      try {
        const response = await axios.post(
          "http://localhost:8090/leaveRequest",
          data
        );
        console.log("휴가 신청 post: " + response.data);
      } catch (error) {
        console.log("휴가 신청시 에러메시지: ", error);
      }
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
    };
  },
};
</script>
