import axios from "axios";

export default {
  namespaced: true,
  state: {
    // users: []
    users:{
      username: '',
      password:'',
    },


    username:'',
  },
  mutations: {
    SET_USERS(state, users) {
      state.users = users;
    },

    FIND_ID(state, username){
      console.log("gd: " + username);
      state.username = username;
      console.log("sss : " + state.username );
    }
  },
  actions: {
    getId({ commit }, value) {
      //   axios.get("https://jsonplaceholder.typicode.com/users").then((res) => {
      //     commit("SET_USERS", res.data);
      //   });

        console.log("kwc : " + value);
        axios.post('findId.vue', {username: value})
        .then((resp) => {
            console.log(resp);
            commit("FIND_ID", resp.data);
        });

    //   axios({
    //     url: "join.vue",
    //     method: "post",
    //     data: {
    //       user: value
    //     }
    //   }).then(function(res) {
    //     console.log("성공 : " + res);
    //   });


    },
    getUsers({ commit }, value) {
      //   axios.get("https://jsonplaceholder.typicode.com/users").then((res) => {
      //     commit("SET_USERS", res.data);
      //   });

        console.log("gd : " + value);
        axios.post('join.vue', value)
        .then((resp) => {
            console.log(resp);
        });
  }
}};
