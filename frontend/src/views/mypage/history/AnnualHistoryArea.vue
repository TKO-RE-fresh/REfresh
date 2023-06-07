<template>
      <div>
    <section class="h-[calc(100%-4rem)] pt-14 w-screen ">
      <article class="sm:ml-64 sm:mt-8">
        <div class="mx-20">
          <h2 class="text-3xl font-semibold sr-only">내 연차 조회</h2>

          <!-- 연차 내역 페이지 헤더 -->
          <SearchView></SearchView>
          
          <!--연차 내역 페이지 메인-->
          <HistoryTable></HistoryTable>
          
        </div>
      </article>
    </section>
  </div>
</template>
<script setup>
    import SearchView from './AnnualHistorySearchArea.vue'
    import HistoryTable from './AnnualHistoryTable.vue'
</script>