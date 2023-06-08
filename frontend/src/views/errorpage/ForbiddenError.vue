<template>
  <TheErrorView
    :errorImgInfo="errorImginfo"
    :errorCode="errorCode"
    :errorTitle="errorTitle"
    :errorMessage="errorMessage"
    :buttonInfo="buttonInfo"
  />
</template>
<script>
import TheErrorView from "../../components/error/TheErrorView";

export default {
  name: "ForbiddenError", // 컴포넌트명
  components: { TheErrorView }, // 외부 컴포넌트 사용시 import후 배열로 등록
  data() {
    return {
      errorImginfo: {
        viewBox: "0 0 28 28",
        subcss: "inline-flex rounded-full bg-red-100 p-4",
        maincss: "rounded-full stroke-red-600 bg-red-200 p-4",
        path: "M14.0002 9.33337V14M14.0002 18.6667H14.0118M25.6668 14C25.6668 20.4434 20.4435 25.6667 14.0002 25.6667C7.55684 25.6667 2.3335 20.4434 2.3335 14C2.3335 7.55672 7.55684 2.33337 14.0002 2.33337C20.4435 2.33337 25.6668 7.55672 25.6668 14Z",
      },
      errorCode: 403,
      errorTitle: "권한 없음",
      errorMessage: "접근 권한이 없으므로 해당 페이지로 접근할 수 없습니다",
      buttonInfo: {
        style:
          "mt-8 px-4 inline py-3 font-semibold leading-5 shadow text-white transition-colors duration-150 border border-transparent focus:outline-none focus:shadow-outline-red bg-red-400 active:bg-red-600 hover:bg-red-700",
        message: "로그인 화면으로",
        getPath() {
          return "/";
        },
      },
    };
  },
};
</script>
