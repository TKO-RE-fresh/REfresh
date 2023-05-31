<template>
  <div>
    <TheHeader></TheHeader>
    <main class="flex">
      <TheSidebar></TheSidebar>
      <LeaveRequestMainArea></LeaveRequestMainArea>
    </main>
  </div>
</template>

<script setup>
import LeaveRequestMainArea from "./LeaveRequestMainArea.vue";
import TheHeader from "@/components/header/TheHeader";
import TheSidebar from "@/components/sidebar/TheSidebar";
</script>
