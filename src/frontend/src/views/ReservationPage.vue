<template>
    <NavBar/>
    <div class="wrapper" id="wrapper">
        <br><br><br><br><br>
        <div class="menu-container">
            <div class="title">
                <br>
                <h4><span>fresh food for good health</span>make a reservation!</h4>
            </div>
            <div class="menu">
                <div v-for="index in 6" :key="index" class="table" @click="makeAReservation(index)">
                    <div>
                        <br><span class="tableName">Table {{index}}</span>
                    </div>
                    <img src="../assets/table.png" alt="">
                </div>
            </div>
        </div>
    </div>
    <div class="modal closed-modal" id="modal">
        <div>
            <input id="inputDate" :min="this.minDate" name="reservationDate" type="date" v-model="dateToReserve">
            <button class="load-button" @click="load">Load</button>
        </div>
        
        <div class="grid-container">
            <div v-for="reservation in tableReservations" :key="reservation.interval">
                <button class="grid-item" v-bind:class="{ busyReservation: reservation.busyState==true }" @click="syncInterval(reservation.reservationInterval)">
                    {{reservation.reservationInterval}}
                </button> 
            </div>
        </div>
        <button class="confirm-button" @click="confirm">Confirm</button>
        <button class="close-button" @click="deactivateModal">Close</button>
        <!-- <button></button> -->
    </div>
</template>
<script>
// import axios from 'axios';
import NavBar from '../components/NavBar.vue';
import {getReservations, getReservationByTableAndDay, addUserReservation
} from '../managers/reservationManager.js'
import {getJWT} from '../managers/userManager.js'
export default {
    
    data(){
        return{
            selectedFile: null,
            tableToReserve: 0,
            tableReservations: null,
            dateToReserve: null,
            interval: null,
        }
    },
    components:{
        NavBar,
    },
    date() {
        return {
            minDate: null,
        }
    },
    setup() {
        
    },
    created(){
        this.minDate = new Date().toISOString().split('T')[0];
        this.dateToReserve = this.minDate;
        this.tableReservations = getReservations();
        
    },
    mounted(){
        document.getElementById('inputDate').value = this.minDate;
    },
    methods:{
        async makeAReservation(index){
            this.tableToReserve=index;
            await this.activateModal();
        },
        async activateModal(){
            let modal = document.querySelector("#modal");
            let wrapper = document.querySelector("#wrapper");
            await getReservationByTableAndDay(this.tableToReserve, this.dateToReserve);
            wrapper.classList.toggle("dark-background");
            modal.classList.toggle("closed-modal");
        },
        deactivateModal(){
            let modal = document.querySelector("#modal");
            let wrapper = document.querySelector("#wrapper");
            wrapper.classList.toggle("dark-background");
            modal.classList.toggle("closed-modal");
        },
        async load(){
            await getReservationByTableAndDay(this.tableToReserve, this.dateToReserve);
        },
        syncInterval(interval){
            this.interval = interval;
        },
        confirm(){
            addUserReservation(this.tableToReserve, this.dateToReserve, this.interval);
            this.deactivateModal();
        }
    }
}
</script>
<style scoped>
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
body{
    font-family: sans-serif;
}
.wrapper{
    background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.7)),url("../assets/menu.jpg");
    height: 100%;
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    -webkit-background-size:cover;
    
}
.menu-container{
    width: 70%;
    margin: auto;
    color: #ddd;
    background: rgba(8, 8, 8, 0.7);
    border-radius: 20px;
}
.title{
    text-align: center;
    margin-bottom: 60px;
}
.title h4{
    text-transform: capitalize;
    font-size: 36px;
    position: relative;
    display: inline-block;
    padding-bottom: 10px;
}
.title h4 span{
    display: block;
    font-size: 40px;
    font-style: italic;
}
.title h4:before{
    position: absolute;
    content:"";
    width:200px;
    height: 3px;
    background-color: #ff7720;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
}
.menu{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    padding: 20px;

}
.table{
    flex-basis: 580px;
    margin-bottom: 40px;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: rgba(231, 208, 208, 0.3);
    border-radius: 25px;
    cursor:pointer;
    text-align: center;
}
.table:nth-child(5), .table:nth-child(6){
    border-bottom: 0;
}
img{
    max-width: 300px;
    margin: 0 auto;
}
.table h4{
    text-transform: capitalize;
    font-size: 22px;
    margin: auto;
    margin-bottom: 5px;
    padding: 5px;
}
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 50%;
  max-width: 100%;
  height: 600px;
  max-height: 100%;
  z-index: 1000;
  background: gray;
  padding: 20px;
  border-radius: 25px;
}
.closed-modal{
    display: none !important;
}
.close-button{
   position: absolute;
    right:    0;
    bottom:   0; 
    padding: 10px;
    margin: 30px 60px !important;   
}
.confirm-button{
    position: absolute;
    left:    0;
    bottom:   0; 
    padding: 10px;
    margin: 30px 60px !important;   
}
.dark-background{
    background-color: black;
    pointer-events: none
}
.grid-container {
  display: grid;
  grid-template-columns: auto auto auto;
  /* background-color: #ff7720; */
  padding: 10px;
}
.grid-item {
  background-color: rgba(255, 255, 255, 0.8);
  border: 1px solid rgba(0, 0, 0, 0.8);
  padding: 20px;
  font-size: 30px;
  text-align: center;
  cursor: pointer;
  margin: 10px 5px;
}
.grid-container button,.close-button,.confirm-button{
    text-decoration: none;
    color: inherit;
    padding: 20px 50px;
    text-align: center;
    margin: 20px 15px;
    border: 2px solid rgba(211, 69, 4, 0.7);
    font-weight: bold;
    border-radius: 25px;
    font-size: 1.1vw;
    cursor: pointer;
    background: rgba(235, 112, 11, 0.5);
}
.grid-container button:focus{
    border: 2px solid rgba(44, 24, 15, 0.7);
    background: rgba(248, 115, 7, 0.897);
}
.busyReservation{
    pointer-events: none;
    cursor: none;
    color: rgb(255, 0, 0) !important;
    background: rgba(53, 34, 19, 0.5) !important;
}
input{
    outline: 0;
    border: 1px solid rgba(211, 69, 4, 0.7);
    padding: 15px 15px;
	width: 50%;
    margin-left: 25%;
    background-color: rgba(251,224,160);
    font-size: 18px;
}
.load-button{
    text-decoration: none;
    color: inherit;
    padding: 10px 30px;
    text-align: center;
    margin: 20px 15px;
    border: 2px solid rgba(211, 69, 4, 0.7);
    font-weight: bold;
    border-radius: 25px;
    font-size: 1.1vw;
    cursor: pointer;
    background: rgba(235, 112, 11, 0.5);
}
.tableName{
    font-size: 30px;
    color: rgb(251,224,160);
}
</style>
