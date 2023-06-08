<template>
  <div
    class="flex h-[calc(100vh-80px)] items-center justify-center p-5 bg-white w-full"
  >
    <div class="text-center">
      <div :class="errorImgInfo.subcss">
        <div :class="errorImgInfo.maincss">
          <svg
            class="w-16 h-16"
            :viewBox="errorImgInfo.viewBox"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              :d="errorImgInfo.path"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            ></path>
          </svg>
        </div>
      </div>
      <h1 class="mt-5 text-[36px] font-bold text-slate-800 lg:text-[50px]">
        {{ errorCode }}<br />{{ errorTitle }}
      </h1>
      <p class="text-slate-600 mt-5 lg:text-lg">
        {{ errorMessage }}
      </p>
      <button :class="buttonInfo.style" @click="handleButtonClick">
        {{ buttonInfo.message }}
      </button>
    </div>
  </div>
</template>
<script>
export default {
  name: "TheErrorView", // 컴포넌트명
  props: {
    errorImgInfo: {
      type: Object,
      required: true,
    },
    errorCode: {
      type: Number,
      required: true,
    },
    errorTitle: {
      type: String,
      required: true,
    },
    errorMessage: {
      type: String,
      required: true,
    },
    buttonInfo: {
      type: Object,
      required: true,
    },
  },
  methods: {
    handleButtonClick() {
      const path = this.buttonInfo.getPath(); // 호출된 컴포넌트에서 경로 반환
      this.$router.push(path);
    },
  },
};
</script>
