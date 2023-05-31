<template>
  <table>
    <thead>
      <tr class="heading">
        <slot name="header"></slot> <!-- 이부분을 치환 -->
      </tr>
     
      <tr class="filter">
        <slot name="filter"></slot> <!-- 이부분을 치환 -->
      </tr>
    </thead>
    <tbody>
      <slot name="body">
      </slot> <!-- 이부분을 치환 -->
      <!-- <tr v-if="list.length === 0" class="empty">
        <td colspan="100">데이터가 없습니다.</td> 
      </tr>  -->
    </tbody>
  </table>
</template>
<script setup>
</script>
