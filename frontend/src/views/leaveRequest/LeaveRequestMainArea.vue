<template lang="ko">
  <div>
  <section class="h-[calc(100%-4rem)] pt-14 w-screen ">
    <article class="sm:ml-64 sm:mt-10">
    <div class="mx-20">
      <h2 class="text-3xl font-semibold sr-only">연차 신청 페이지</h2>
      <!-- 연차 신청 페이지 헤더 -->
      <LeaveRequestMainAreaHeader></LeaveRequestMainAreaHeader>
            
      <!-- 연차 신청 폼 -->
      <LeaveRequestMainAreaForm></LeaveRequestMainAreaForm>
    </div>
    </article>
  </section>
</div>
</template>
<script>
import LeaveRequestMainAreaHeader from "./LeaveRequestMainAreaHeader.vue";
import LeaveRequestMainAreaForm from "./LeaveRequestMainAreaForm.vue";
export default {
  components: {
    LeaveRequestMainAreaHeader,
    LeaveRequestMainAreaForm,
  },
};
</script>
