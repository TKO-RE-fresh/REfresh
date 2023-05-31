<template>
  <form>
    <div
      class="flex bg-base-80 border border-slate-300 rounded-lg shadow-md shadow-slate-300/40 my-10 mx-16"
    >
      <div class="py-3 flex items-center justify-center space-x-16 w-full">
        <div class="flex space-x-6">
          <!-- 부서 검색 -->
          <div class="flex items-center space-x-3">
            <label>부서명</label>

            <select class="select select-bordered">
              <option disabled="">부서 선택</option>
              <option>인사팀</option>
              <option>영업팀</option>
              <option>개발팀</option>
              <option>디자인팀</option>
              <option>서비스팀</option>
            </select>
          </div>
          <!-- 사원 검색 -->
          <div class="flex items-center space-x-3">
            <label>사원명</label>
            <input
              id="name"
              type="text"
              placeholder="사원명 입력"
              class="input input-bordered"
            />
          </div>
          <!-- 연차 상태 -->
          <div class="flex items-center space-x-3">
            <label>연차 상태</label>
            <select class="select select-bordered">
              <option disabled="">연차 상태 선택</option>
              <option>대기</option>
              <option>승인</option>
              <option>반려</option>
              <option>취소</option>
            </select>
          </div>

          <!-- 기간별 검색 -->
        </div>
        <button
          type="submit"
          class="bg-blue-500 px-5 py-3 rounded-xl text-white hover:bg-blue-700"
        >
          조회
        </button>
      </div>
    </div>
  </form>
</template>
<script setup>
// const deptOptions = ["인사팀", "영업팀", "개발팀", "디자인팀", "서비스팀"];
// const statusOptions = ["대기", "승인", "반려", "취소"];
</script>
