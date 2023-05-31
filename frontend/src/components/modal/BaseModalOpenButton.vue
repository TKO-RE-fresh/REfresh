<!-- BaseModalOpenButton.vue -->
<template>
  <button
    class="bg-blue-500 border border-blue-500 px-5 py-2 text-sm shadow-sm font-medium tracking-wider text-white rounded-lg hover:shadow-lg hover:bg-blue-600"
    @click="onToggle"
  >
    클릭시 모달 오픈
  </button>
</template>

<script>
export default {
  props: {
    isOpen: {
      type: Boolean,
      required: true,
    },
    onToggle: {
      type: Function,
      required: true,
    },
  },
};
</script>
