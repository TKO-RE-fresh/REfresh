<template>
  <div>
    <section class="h-[calc(100%-4rem)] pt-14 w-screen ">
      <article class="sm:ml-64 sm:mt-8">
        <div class="mx-20">
          <h2 class="text-3xl font-semibold sr-only">관리자 페이지</h2>

          <!-- 관리자 페이지 헤더 -->
          <AdminMainHeader @selected-tab="handleId"></AdminMainHeader>
          
          <!--관리자 페이지 메인-->
          <component :is="flagTab == 0 ? AdminMemberArea : flagTab == 1 ? AdminNewMemberArea : AdminAnnualArea "></component>
        </div>
      </article>
    </section>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import AdminMainHeader from './AdminMainHeader.vue';
import AdminMemberArea from './AdminMemberArea.vue';
import AdminNewMemberArea from './AdminNewMemberArea.vue';
import AdminAnnualArea from './AdminAnnualArea.vue';

const flagTab = ref(0);
function handleId(id) {
    flagTab.value = id;
}
    
</script>