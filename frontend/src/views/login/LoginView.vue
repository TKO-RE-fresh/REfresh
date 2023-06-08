<template>
  <main class="relative bg-gradient-to-br from-sky-50 to-gray-200">
    <section
      class="relative container m-auto px-6 text-gray-500 md:px-12 xl:px-40 h-screen flex justify-center items-center"
    >
      <article class="m-auto md:w-8/12 lg:w-6/12 xl:w-5/12">
        <form
          class="bg-white p-8 rounded-lg shadow-lg h-full font-mice"
          @submit.prevent="onSubmit"
        >
          <h2 class="text-4xl font-mice font-bold mb-4 flex justify-center text-[#f6aa1c]">
            <span class="text-[#003566] font-mice-bold ">RE</span>fresh
          </h2>

          <div class="mb-12 h-16">
            <label class="block text-gray-700 font-bold mb-2" for="id"
              >아이디</label
            >
            <div class="mb-4">
              <input
                id="id"
                v-model="memberId"
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                type="text"
                placeholder="아이디 입력"
                required
                autocomplete="off"
                aria-required="true"
              />
              <div class="pt-2 text-rose-600">{{ alertIdMsg }}</div>
            </div>
          </div>
          <div class="mb-12 h-16">
            <label class="block text-gray-700 font-bold mb-2" for="password"
              >비밀번호</label
            >
            <input
              id="password"
              v-model="password"
              class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              type="password"
              required
              aria-required="true"
              placeholder="비밀번호 입력"
            />
            <div class="pt-2 text-rose-600">{{ alertPwMsg }}</div>
          </div>
          <div class="flex justify-between">
            <label class="block text-gray-500 font-bold my-4"
              ><input type="checkbox" class="leading-loose text-pink-600" />
              <span class="py-2 text-sm text-gray-600 leading-snug">
                로그인 정보 기억하기
              </span></label
            >
            <label class="block text-gray-500 font-bold my-4"
              ><a
                href="#"
                class="cursor-pointer tracking-tighter inline-block align-baseline font-bold text-sm text-[#003566] hover:text-blue-800"
                ><span>비밀번호 찾기</span></a
              ></label
            >
          </div>
          <button
            class="bg-[#003566] hover:bg-[#f6aa1c] text-white mt-3 text-lg font-semibold w-full rounded-lg px-4 py-3 block shadow-xl focus:outline-none focus:shadow-outline"
            type="submit"
          >
            로그인
          </button>
        </form>
      </article>
    </section>
  </main>
</template>
<script setup>
import { ref, watch } from "vue";
import mixins from "@/utils/mixins";
import Router from "@/router/index.js";

const memberId = ref("");
const password = ref("");
const alertIdMsg = ref("");
const alertPwMsg = ref("");

watch(memberId, () => {
  idCheck();
});

watch(password, () => {
  passwordCheck();
});

function idCheck() {
  if (alertIdMsg.value !== "") {
    alertIdMsg.value = "";
  }
}

function passwordCheck() {
  if (alertPwMsg.value !== "") {
    alertPwMsg.value = "";
  }
}

async function onSubmit() {
  const body = {
    memberId: memberId.value,
    password: password.value,
  };
  try {
    const res = await mixins.methods.$api("login", "post", { data: body });
    if (res && res.status === 200) {
      Router.push({ path: "/calendar" });
    }
  } catch (err) {
    if (err.response.status === 403) {
      if (err.response.data.message === "id") {
        alertIdMsg.value = "아이디를 확인해주세요.";
      } else {
        alertPwMsg.value = "비밀번호를 확인해주세요.";
      }
    } else {
      alertIdMsg.value = "아이디를 확인해주세요.";
      alertPwMsg.value = "비밀번호를 확인해주세요.";
    }
    console.log(err);
  }
}
</script>

<style scoped></style>
