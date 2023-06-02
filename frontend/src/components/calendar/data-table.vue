<template>
  <table>
    <thead>
      <tr class="heading">
        <slot name="header"></slot>
        <!-- 이부분을 치환 -->
      </tr>
      <tr class="filter">
        <slot name="filter"></slot>
        <!-- 이부분을 치환 -->
      </tr>
    </thead>
    <tbody>
      <slot name="body"> </slot>
    </tbody>
  </table>
</template>
<script setup>
</script>
