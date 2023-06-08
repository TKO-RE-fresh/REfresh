<template>
  <div>
    <section class="h-[calc(100%-4rem)] pt-14 w-screen">
      <article class="sm:ml-64 sm:mt-8">
        <div class="mx-20">
          <h2 class="text-3xl font-semibold sr-only">내 연차 조회</h2>

          <!-- 연차 내역 페이지 헤더 -->
          <SearchView></SearchView>

          <!--연차 내역 페이지 메인-->
          <HistoryTable></HistoryTable>

          <!--페이징-->
          <PagingView
            :currentPage="currentPage"
            :totalPages="totalPages"
            @selectPage="selectPage"
          />
        </div>
      </article>
    </section>
  </div>
</template>
<script setup>
import {ref, provide} from 'vue';
import PagingView from "../../../components/pagination/pagingComponent.vue";
import SearchView from "./AnnualHistorySearchArea.vue";
import HistoryTable from "./AnnualHistoryTable.vue";
import Store from "@/store/index.js";

const currentPage = ref(1);
provide('currentPage',currentPage);


const totalPages = Store.state.historyList.totalPages;

async function selectPage(idx) {
  currentPage.value=idx;
  await Store.dispatch('fetchHistoryList', currentPage.value);
}

</script>
