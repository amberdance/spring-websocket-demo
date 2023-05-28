import Vue from "vue";
import App from "./App.vue";
import "./plugins/axios";
import "./plugins/element";
import "./styles.css";

Vue.config.productionTip = false;

new Vue({
  render: (h) => h(App),
}).$mount("#app");
