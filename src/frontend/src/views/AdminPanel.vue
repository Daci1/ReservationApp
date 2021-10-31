<template>
    <NavBar/>
    <div class="wrapper">
        <div class="container">
            <div class="pagination-buttons-div">
                <button @click="prevPage">Previous</button> 
                <button @click="nextPage">Next</button>
                <button @click="swapTable">Swap tables</button>
            </div>
            <table>
            <thead>
                <tr>
                    <th v-for="header in Object.keys(currentTH)" :key="header" @click="sort(header)">{{currentTH[header]}}</th>
                    <th :colspan="this.currentTable === 'users' ? 2 : 1">Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="row in sortedRows" :key="row">
                    <td v-for="tdEntry in row" :key="tdEntry" >{{tdEntry}}</td>
                    <td><button class="delete-button" @click="deleteRow(row)">Delete</button></td>
                    <td v-if="this.currentTable === 'users'"><button class="delete-button" @click="console.log()">Edit</button></td>
                </tr>
            </tbody>
            </table>
        </div>
    </div>
</template>
<script>
import NavBar from "../components/NavBar.vue"
import {getAllUsers,} from "../managers/userManager"
import {getAllReservations, deleteReservation} from "../managers/reservationManager"
export default {
    components: {
        NavBar
    },
    data() {
        return {
            // userTH: ["Email", "First Name", "Last Name", "Role", "Mobile", "Date of birth"],
            userTH:{
                email: "Email",
                firstName: "First Name",
                lastName: "Last Name",
                role: "Role",
                mobile: "Mobile",
                dob: "Date of birth"
            },
            reservationTH: {
                tableNumber: "Table Number",
                reservationBegin: "Reservation Interval"
            },
            currentTable: "users",
            currentTableRows: [],
            pageSize: 7,
            currentPage: 1,
            currentSort: "email",
            currentSortDir: "desc",
            currentTH: {},
        }
    },
    setup() {
        
    },
    async created() {
        this.currentTableRows = await getAllUsers();
        this.sort(this.currentSort);
        this.currentTH = this.userTH;
    },
    computed: {
        sortedRows:function(){
            return this.currentTableRows.filter((row, index) => {
                let start = (this.currentPage-1)*this.pageSize;
                let end = this.currentPage*this.pageSize;
                if(index >= start && index < end) return true;
            });
        }
    },
    methods: {
        nextPage:function() {
            if((this.currentPage*this.pageSize) < this.currentTableRows.length) this.currentPage++;
        },
        prevPage:function() {
        if(this.currentPage > 1) this.currentPage--;
        },
        async sort(s){
            if(s === this.currentSort) {
            this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
            }
            this.currentSort = s;
            this.currentTableRows.sort((a,b) => {
                let modifier = 1;
                if(this.currentSortDir === 'desc') modifier = -1;
                if(a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
                if(a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
                return 0;
            });
        },
        async swapTable(){
            this.currentTableRows = [];
            this.currentPage = 1;
            this.currentTable = this.currentTable === "users" ? "reservations" : "users";
            this.currentTH = this.currentTH === this.userTH ? this.reservationTH : this.userTH;
            this.currentSort = this.currentSort === "email" ? "reservationBegin" : "email";
            this.currentSortDir = "desc";
            this.currentTableRows = this.currentTable === "users" ? await getAllUsers() : await getAllReservations();
            this.sort(this.currentSort);
            console.log(this.currentTableRows);
        },
        async deleteRow(row){
            if(this.currentTH === "users"){
                console.log();
            }else{
                if(deleteReservation(row)){
                    this.$router.go();
                    // this.swapTable();
                }
            }
        }
    }
}
</script>
<style scoped>
.wrapper{
    background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.7)),url("../assets/menu.jpg");
    min-height: 100vh;
    height: 100%;
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    -webkit-background-size:cover;
    margin: 0;
    font-family: sans-serif;
	font-weight: 100;
}

.container {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -130%);
    height: 200px;
    width: 60%;
}

table {
	width: 100%;
	border-collapse: collapse;
	overflow: hidden;
	box-shadow: 0 0 20px rgba(0,0,0,0.4);
    border-radius: 25px;
}

th,
td {
	padding: 15px;
	background-color: rgba(46, 37, 37, 0.9);
	color: rgb(251,224,160);
    border: 1px solid black;
}

th {
	text-align: center;
    background: rgb(251,224,160);
    color: rgb(75, 26, 4);
}

tr{
    text-align: center;
}
button{
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
    background: rgba(235, 112, 11, 0.7);
}
.delete-button{
    padding: 10px 10px;
    font-size: 15px;
    margin: 0;
}
.pagination-buttons-div{
    width: 60%;
    margin: 0 auto;
    color: rgb(251,224,160);
}

</style>