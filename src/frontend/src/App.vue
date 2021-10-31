<template>
  <router-view/>
</template>

<script>
import {getJWT, signUserOut} from "./managers/userManager"
export default {
  name: 'App',
  created() {
    if(!getJWT() && localStorage.getItem("user")){
      signUserOut();
      this.$router.push("/");
    }
  }
}
</script>
<style>
  *{
        margin: 0;
        padding: 0;
    }
</style>
