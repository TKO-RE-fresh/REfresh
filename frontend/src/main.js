import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store/index";
import mixins from "./utils/mixins";
import "./style.css";

createApp(App).use(store).mixin(mixins).use(router).mount("#app");
