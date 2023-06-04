<template>
  <router-view />
</template>
<script setup>
import { onMounted, onBeforeUnmount } from "vue";
import Store from "./store";
onMounted(() => {
  window.addEventListener("beforeunload", leave);
});
onBeforeUnmount(() => {
  window.removeEventListener("beforeunload", leave);
});
function leave() {
  Store.dispatch("logout");
}
</script>

<style></style>
