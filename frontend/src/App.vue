<template>
  <div id="app">
  
    <a href="move.vue">뷰->메인</a>
    <div id="nav">
      <router-link to="/indexVue.vue">Home</router-link> |
      <router-link to="/about.vue">About</router-link> |
      <router-link to="/spring.vue">Spring</router-link> |
      <router-link to="/register.vue">Register</router-link> |
      <router-link to="/findid.vue">FindId</router-link> 
    </div>
    <router-view/>
    
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
