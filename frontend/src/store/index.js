import Vue from "vue";
import Vuex from "vuex";
import user from "./modules/user";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    user:user //:todo 생략 가능(ES6부터)
  },
});
