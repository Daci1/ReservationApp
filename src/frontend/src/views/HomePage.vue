<template>
  <div id="home-container">
    <NavBar/>
    <div id="welcome-text">
      <h1>WELCOME</h1>
      <div id="lines">
        <div id="line"/>
        
        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <div id="line"/>
      </div>

      <div>
        <button @click="goToMenu">View Menu</button>
        <button @click="goToReservation">Make a Reservation</button>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '../components/NavBar.vue'
import {isUserLogged} from '../managers/userManager'
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
    
  },
  //TODO
  methods:{
    goToMenu(){
      this.$router.push('/menu')
    },
    goToReservation(){
      if(isUserLogged()){
        this.$router.push('/reservation')
      }else{
        this.$router.push('/signin');
      }
      
    }
  }

}
</script>
<style scoped>
  *{
    margin: 0;
    padding: 0;
  }
  #home-container{
    background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.7)),url("../assets/background.jpg");
    height: 100vh;
    width: 100%;
    background-size: cover;
    -webkit-background-size:cover;
    background-position: 0;
    display: flex;
    flex-direction: column;
  }
  #welcome-text{
    width: 35%;
    margin:20% auto;
    color: rgb(218, 176, 87);
    /* margin-left: 25%; */
    padding: 30px 0;
    text-align: center;
    box-sizing: border-box;
    border-radius: 25px;
    
  }
  #welcome-text button{
    text-decoration: none;
    color: inherit;
    padding: 20px 20px;
    text-align: center;
    margin: 20px 15px;
    border: 2px solid rgba(211, 69, 4, 0.7);
    font-weight: bold;
    border-radius: 25px;
    background: transparent;
    font-size: 1.1vw;
    cursor: pointer;
    background: rgba(31, 18, 7, 0.5);
  }

  #welcome-text h1{
    font-size: 4vw;
  }
  #line{
    width: 50%;
    border-top: 2px solid rgba(211, 69, 4, 0.7);
  }
  #lines{
    display: flex;
    flex-direction: row;
  }
</style>

