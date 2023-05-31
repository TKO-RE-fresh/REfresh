<template>
  <div>
    <TheHeader></TheHeader>
    <main class="flex">
      <TheSidebar></TheSidebar>
      <AdminMainArea></AdminMainArea>
    </main>
  </div>
</template>

<script setup>
  import AdminMainArea from './AdminMainArea.vue';
  import TheHeader from '@/components/header/TheHeader.vue';
  import TheSidebar from '@/components/sidebar/TheSidebar.vue';

  

</script>
