<template>
  <div id="home-container">
    <NavBar/>
  </div>
</template>

<script>
import NavBar from '../components/NavBar.vue'
export default {
  name: 'HelloWorld',
  components:{
    NavBar
  },
  data(){
    return {
      msg: ' '
    }
  },
  mounted() {
    
  }

}
</script>
<style>
  #home-container{
    background: linear-gradient(rgba(0,0,0,0.3), rgba(0,0,0,0.5)),url("../assets/background.jpg");
    height: 100vh;
    background-size: cover;
    -webkit-background-size:cover;
    background-position: 0;
    display: flex;
    flex-direction: column;
  }
</style>

