<template>
    <NavBar/>
    <div class="wrapper" id="wrapper">
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
                    <td v-if="this.currentTable === 'users'"><button class="delete-button" @click="activateModal(row)">Edit</button></td>
                </tr>
            </tbody>
            </table>
        </div>
                <div class="modal closed-modal" id="modal">
                        <h3>Add new Menu Entry</h3>
                        <form action="/action_page.php" @submit="confirm">
                            <label for="email">Email</label>
                            <input required type="text" id="email" name="email" :placeholder="selectedUserToEdit.email" v-model="selectedUserToEdit.email">

                            <label for="firstName">First Name</label>
                            <input required type="text" id="firstName" name="firstName" placeholder="User First Name.." v-model="selectedUserToEdit.firstName">

                            <label for="lastName">Last Name</label>
                            <input required type="text" id="lastName" name="lastName" placeholder="User Last Name.." v-model="selectedUserToEdit.lastName">

                            <label for="mobileNo">Mobile Number</label>
                            <input required type="text" id="mobileNo" name="mobileNo" placeholder="User Mobile Number.." v-model="selectedUserToEdit.mobileNo">

                            <label for="role">Role</label>
                            <input required type="text" id="role" name="role" placeholder="User Role.." v-model="selectedUserToEdit.role">

                            <label for="dob">Date of Birth</label>
                            <input required type="date" id="dob" name="dob" placeholder="User Date of Birth.." v-model="selectedUserToEdit.dob">

                        <button class="confirm-button">Confirm</button>
                        <button class="close-button" @click="deactivateModal">Close</button>
                        </form>
                </div>
    </div>
</template>
<script>
import NavBar from "../components/NavBar.vue"
import {getAllUsers, updateUser, deleteUser, getLoggedUser, signUserOut} from "../managers/userManager"
import {getAllReservations, deleteReservation} from "../managers/reservationManager"
export default {
    components: {
        NavBar
    },
    data() {
        return {
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
            selectedUserToEdit: {
                email: null,
                firstName: null,
                lastName: null,
                mobileNo: null,
                role: null,
                dob: null,
            },
            oldUserEmail: null,
            rowToDelete: null,
            loggedUser: null,
        }
    },
    setup() {
        
    },
    async created() {
        this.loggedUser = await getLoggedUser();
        if(this.loggedUser.role.toUpperCase() !== "ADMIN"){
            let refreshUser = JSON.parse(localStorage.getItem("user"));
            refreshUser.role = "user";
            localStorage.setItem("user", JSON.stringify(refreshUser));
            signUserOut();
            this.$router.push("/");
        }

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
            this.rowToDelete = row;
            if(this.currentTable === "users"){
                let confirmation = confirm(`Are you sure you want to delete the user ${this.rowToDelete.email}?`);
                if(confirmation && await deleteUser(this.rowToDelete.email)){
                    this.$router.go();
                }
            }else{
                let confirmation = confirm(`Are you sure you want to delete the reservation?`);
                if(confirmation && await deleteReservation(this.rowToDelete)){
                    this.$router.go();
                }
            }
        },
        activateModal(user){
            let modal = document.querySelector("#modal");
            let wrapper = document.querySelector("#wrapper");
            wrapper.classList.toggle("dark-background");
            modal.classList.toggle("closed-modal");

            this.selectedUserToEdit.email = user.email;
            this.selectedUserToEdit.firstName = user.firstName;
            this.selectedUserToEdit.lastName = user.lastName;
            this.selectedUserToEdit.role = user.role;
            this.selectedUserToEdit.mobileNo = user.mobile;
            this.selectedUserToEdit.dob = user.dob;
            
            this.oldUserEmail = user.email;
        },
        deactivateModal(e){
            e.preventDefault();
            let modal = document.querySelector("#modal");
            let wrapper = document.querySelector("#wrapper");
            wrapper.classList.toggle("dark-background");
            modal.classList.toggle("closed-modal");
        },
        async confirm(e){
            e.preventDefault();
            console.log(this.selectedUserToEdit);
            if(await updateUser(this.selectedUserToEdit, this.oldUserEmail)){
                if(this.loggedUser.email === this.oldUserEmail && this.oldUserEmail !== this.selectedUserToEdit.email){
                    console.log(this.loggedUser.email, this.oldUserEmail, this.selectedUserToEdit.email);
                    this.$router.push("/");
                }else{
                    if(this.loggedUser.email === this.oldUserEmail && this.selectedUserToEdit.email === this.oldUserEmail){
                        this.loggedUser = await getLoggedUser();
                    }
                    if(this.loggedUser.role.toUpperCase() === "ADMIN"){
                        this.currentTableRows = await getAllUsers();
                    }
                    
                    this.$router.go();
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
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 50%;
  max-width: 100%;
  height: 650px;
  max-height: 100%;
  z-index: 1000;
  background: gray;
  padding: 20px;
  border-radius: 25px;
  color: rgb(251,224,160);
}
.closed-modal{
    display: none !important;
}
.modal h3{
    text-align: center;
    font-size: 30px;
}
input, select, textarea{
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  outline: 0;
  background: rgb(251,224,160);
}
.close-button,.confirm-button{
    text-decoration: none;
    color: inherit;
    padding: 20px 60px;
    text-align: center;
    margin: 20px 150px;
    border: 2px solid rgba(211, 69, 4, 0.7);
    font-weight: bold;
    border-radius: 25px;
    font-size: 1.1vw;
    cursor: pointer;
    background: rgba(235, 112, 11, 0.5);
}
.close-button{
   position: absolute;
    right:    0;
    /* bottom:   0;   */
}
</style>