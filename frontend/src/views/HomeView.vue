<template>
  <div class="home">
    <SideBar></SideBar>
    <main>
      <div>메인페이지</div>
    </main>
  </div>
</template>

<script>
// @ is an alias to /src
import SideBar from '../components/sidebar/TheSidebar.vue'

export default {
  name: "HomeView",
  components: {
    SideBar,
  },
};
</script>
