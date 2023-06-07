<template>
  <div>
    <TheHeader></TheHeader>
    <main class="flex">
      <TheSidebar></TheSidebar>
      <AnnualHistoryArea></AnnualHistoryArea>
    </main>
  </div>
</template>

<script setup>
  import AnnualHistoryArea from './AnnualHistoryArea.vue';
  import TheHeader from '@/components/header/TheHeader.vue';
  import TheSidebar from '@/components/sidebar/TheSidebar.vue';
  

</script>
