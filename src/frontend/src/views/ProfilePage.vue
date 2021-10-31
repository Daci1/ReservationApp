<template>
    <div class="wrapper" id="wrapper">
        <NavBar/>
        <div class="profile-container">
            <h3 class="title">Profile</h3>
            <div class="profile-content">
                <div>
                    <h3>Email</h3>
                    <span>{{loggedUser.email}}</span>
                </div>
                <div>
                    <h3>Role</h3>
                    <span>{{loggedUser.role}}</span>
                </div>
                <div>
                    <h3>First Name</h3>
                    <span>{{loggedUser.firstName}}</span>
                </div>
                <div>
                    <h3>Last Name</h3>
                    <span>{{loggedUser.lastName}}</span>
                </div>
                <div>
                    <h3>Mobile Number</h3>
                    <span>{{loggedUser.mobileNo}}</span>
                </div>
                <div>
                    <h3>Date of birth</h3>
                    <span>{{loggedUser.dob}}</span>
                </div>
            </div>
            <button class="edit-button" @click="activateModal()">Edit</button>
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
import {getLoggedUser, selfUpdate} from "../managers/userManager"
export default {

    components: {
        NavBar,
    },
    data() {
        return {
            loggedUser: {},
            oldUserEmail: null,
            selectedUserToEdit: {
                email: null,
                firstName: null,
                lastName: null,
                mobileNo: null,
                role: null,
                dob: null,
            },
        }
    },
    setup() {
        
    },
    async created(){
        this.loggedUser = await getLoggedUser();
        let date = new Date(this.loggedUser.dob);
        let ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(date);
        let mo = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(date);
        let da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(date);
        let stringDate = `${ye}-${mo}-${da}`;
        this.loggedUser.dob = stringDate;
    },
    methods: {
        activateModal(){
            let modal = document.querySelector("#modal");
            let wrapper = document.querySelector("#wrapper");
            wrapper.classList.toggle("dark-background");
            modal.classList.toggle("closed-modal");

            this.selectedUserToEdit.email = this.loggedUser.email;
            this.selectedUserToEdit.firstName = this.loggedUser.firstName;
            this.selectedUserToEdit.lastName = this.loggedUser.lastName;
            this.selectedUserToEdit.role = this.loggedUser.role;
            this.selectedUserToEdit.mobileNo = this.loggedUser.mobileNo;
            this.selectedUserToEdit.dob = this.loggedUser.dob;
            
            this.oldUserEmail = this.loggedUser.email;
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
            if(await selfUpdate(this.selectedUserToEdit, this.oldUserEmail)){
                if(this.selectedUserToEdit.email !== this.oldUserEmail){
                    this.$router.push("/");
                }else{
                    this.loggedUser = await getLoggedUser();
                }
            }
            this.deactivateModal(e);
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
.profile-container{
    box-sizing: border-box;
    position: fixed;
    top: 20%;
    left: 50%;
    width: 50%;
    margin:auto;
    transform: translateX(-50%);
    border-radius: 25px;
    height: auto;
    border: 2px solid rgba(211, 69, 4, 0.7);
    background: rgba(71, 63, 56, 0.9);
    color: rgb(251,224,160);

}
.profile-container h3{
    font-size: 25px;
    text-align: center;
    padding: 20px;
}
.title{
    font-size: 45px !important;
}
.profile-content{
    height: auto;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-column: 3;
    grid-row: 2;
    text-align: center;
}
.profile-content div{
    background: rgba(189, 94, 17, 0.9);
    font-size: 25px;
    padding-bottom: 15px;
    border-radius: 25px;
    margin: 15px;
}
span{
    padding: 20px !important;
}
.edit-button{
    text-decoration: none;
    color: inherit;
    position: absolute;
    padding: 20px 50px;
    text-align: center;
    margin: 20px 0;
    right: 40%;
    width: 20%;
    border: 2px solid rgba(211, 69, 4, 0.7);
    font-weight: bold;
    border-radius: 25px;
    font-size: 1.1vw;
    cursor: pointer;
    background: rgba(235, 112, 11, 0.7);

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