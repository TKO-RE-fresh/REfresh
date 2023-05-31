<template lang="ko">
    <div>
    <form
        id="leave-request-form"
        method="post"
        action="/leaveRequest/confirmResult"
      >
        <div class="p-6 flex flex-col  bg-slate-200 my-4 rounded-lg shadow-sm
        ">
          <div class="flex flex-col m-6">
            <label class="text-gray-700 font-bold mb-2 text-xl">신청인</label>
            <!-- <div class="mb-4"> -->
                <span>신청인명(하드)</span>
              <!-- <input
                id="name"
                class="shadow border rounded py-2 px-3 font-bold text-xl text-gray-700 focus:outline-none focus:ring focus:border-blue-50 text-center"
                type="text"
                placeholder="신청인"
                readonly
              /> -->
            <!-- </div> -->
          </div>

          <!-- 연차 확인 -->
          <div class="">
            <div class="flex flex-col m-6">
                <div class="font-bold mb-2">연차 현황</div>
                <div>
                    남은 연차:
                    <span class="text-blue-500"> 남은 연차 숫자 </span>
                    일 / 사용한 연차:
                    <span class="text-red-500"> 사용한 연차 숫자 </span>
                    일
                </div>
            </div>
                 

            <div class="flex flex-col m-6">
              <label class="text-gray-700 font-bold mb-2"> 연차 종류 </label>
              <div
                class="grid w-[20rem] grid-cols-3 space-x-2 rounded-xl bg-gray-100 p-2"
                x-data="app"
              >
                <div>
                  <input
                    id="full-day"
                    type="radio"
                    name="leaveType"
                    value="full-day"
                    class="peer hidden"
                    checked
                  />
                  <label
                    for="full-day"
                    class="block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-blue-500 peer-checked:font-bold peer-checked:text-white font-semibold"
                    >연차</label
                  >
                </div>

                <div>
                  <input
                    id="am-half-day"
                    type="radio"
                    name="leaveType"
                    value="am-half-day"
                    class="peer hidden"
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
                  />
                  <label
                    for="pm-half-day"
                    class="block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-blue-500 peer-checked:font-bold peer-checked:text-white font-semibold"
                    >오후 반차</label
                  >
                </div>
              </div>
            </div>

            <div class="flex flex-col m-6">
              <label class="text-gray-700 font-bold mb-2">
                시작일<span class="ml-36">종료일</span>
              </label>
              <div class="flex">
                <input
                  id="startDate"
                  class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
                  type="date"
                  placeholder="시작일을 선택하세요."
                  name="startDate"
                  value=""
                />
                <span class="text-2xl font-semibold mr-6"> </span>
                <input
                  id="endDate"
                  class="shadow border rounded py-2 px-3 text-gray-700 focus:outline-none focus:ring focus:border-blue-50"
                  type="date"
                  placeholder="종료일을 선택하세요."
                  name="endDate"
                  value=""
                />
              </div>
            </div>
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
<script setup></script>
