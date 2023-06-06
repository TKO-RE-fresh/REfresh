<template>
  <div>
    <input type="text" class="border-2" @input="onChnageThrottled" />
  </div>
</template>
<script setup>
import { defineEmits } from "vue";

const emit = defineEmits(["selectedMember"]);

function handleInput(e) {
  console.log(e.target.value);
  emit("selectedMember", e.target.value);
}

const onChnageThrottled = throttle(handleInput, 500);

function throttle(fn, wait) {
  let throttled = false;

  function executeFn(...args) {
    fn.apply(this, args);
    throttled = false;
  }

  return function (...args) {
    if (!throttled) {
      throttled = true;
      setTimeout(executeFn.bind(this, ...args), wait);
    }
  };
}
</script>