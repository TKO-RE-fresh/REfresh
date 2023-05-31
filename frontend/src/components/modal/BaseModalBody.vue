<template lang="ko">
 <transition name="fade">
  <div v-if="isOpen">
        <div class="fixed inset-0 z-10 flex items-center justify-center">
          <div
            class="w-full max-w-lg p-3 relative mx-auto my-auto rounded-xl shadow-lg bg-white"
          >
            <div>
              <div class="text-center p-3 flex-auto justify-center leading-6">
               
                <svg xmlns="http://www.w3.org/2000/svg" class="w-16 h-16 flex items-center text-blue-500 mx-auto" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M12.92,16.12a.76.76,0,0,0-.09-.18,1.58,1.58,0,0,0-.12-.15l-.15-.12-.18-.09a.6.6,0,0,0-.19-.06,1,1,0,0,0-.9.27l-.12.15a.76.76,0,0,0-.09.18.64.64,0,0,0-.06.18,1.36,1.36,0,0,0,0,.2,1,1,0,0,0,.08.38,1.15,1.15,0,0,0,.21.33,1,1,0,0,0,1.09.22,1.46,1.46,0,0,0,.33-.22,1.46,1.46,0,0,0,.22-.33A1,1,0,0,0,13,16.5a1.36,1.36,0,0,0,0-.2A.64.64,0,0,0,12.92,16.12ZM12,10.5a1,1,0,0,0-1,1v2a1,1,0,0,0,2,0v-2A1,1,0,0,0,12,10.5Zm7-5H12.72l-.32-1a3,3,0,0,0-2.84-2H5a3,3,0,0,0-3,3v13a3,3,0,0,0,3,3H19a3,3,0,0,0,3-3V8.5A3,3,0,0,0,19,5.5Zm1,13a1,1,0,0,1-1,1H5a1,1,0,0,1-1-1V5.5a1,1,0,0,1,1-1H9.56a1,1,0,0,1,.95.68l.54,1.64A1,1,0,0,0,12,7.5h7a1,1,0,0,1,1,1Z"/>
                </svg>
             

                <h2 class="text-2xl font-bold py-4">확인 모달</h2>
                <p class="text-md text-gray-500 px-8">
                  확인이 완료되었습니까?
                </p>
              </div>
              <div class="p-3 mt-2 text-center space-x-4 md:block">
                <button
                  class="mb-2 md:mb-0 bg-white px-5 py-2 text-sm shadow-sm font-medium tracking-wider border text-gray-600 rounded-md hover:shadow-lg hover:bg-gray-100"
                >
                  확인
                </button>
                <button
                  class="mb-2 md:mb-0 bg-blue-500 border border-blue-500 px-5 py-2 text-sm shadow-sm font-medium tracking-wider text-white rounded-md hover:shadow-lg hover:bg-blue-600"
                  @click="onToggle"
                >
                  닫기
                </button>
              </div>
            </div>
          </div>
        </div>
        <div
          class="fixed inset-0 bg-black opacity-70 z-0"
          @click="onToggle"
        ></div>
      </div>
    </transition>
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

<style scoped>
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 500ms ease-out;
}
</style>
