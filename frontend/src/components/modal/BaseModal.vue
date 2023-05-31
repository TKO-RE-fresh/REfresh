<template>
  <BaseModalOpenButton
    :isOpen="isOpen"
    :onToggle="onToggle"
  ></BaseModalOpenButton>
  <BaseModalBody :isOpen="isOpen" :onToggle="onToggle"></BaseModalBody>
</template>

<script>
import { ref } from "vue";
import BaseModalOpenButton from "./BaseModalOpenButton.vue";
import BaseModalBody from "./BaseModalBody.vue";

export default {
  components: { BaseModalOpenButton, BaseModalBody },
  setup() {
    const isOpen = ref(false);
    const onToggle = () => {
      isOpen.value = !isOpen.value;
    };

    return {
      isOpen,
      onToggle,
    };
  },
};
</script>
