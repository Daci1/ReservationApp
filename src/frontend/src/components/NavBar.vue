<template>
    <div id="container">
        <ul id="nav-bar">
            <li class="admin"><router-link v-if="loggedUser && loggedUser.role=='admin'" class="router" to="/adminPanel">Admin Panel</router-link></li>
            <li><router-link class="router" to="/">Home</router-link></li>
            <li><router-link v-if="this.$route.path=='/reservation'" to="/ownReservations">Own Reservation</router-link></li>
            <li><router-link v-if="this.$route.path !== '/reservation' && this.$route.path!=='/'" to="/reservation">Make a Reservation</router-link></li>
            <li><router-link v-if="this.$route.path!=='/'" class="router" to="/menu">Menu</router-link></li>
            <li><router-link v-if="!loggedUser" class="router" to="/signin">Sign in</router-link></li>
            <li><router-link v-if="loggedUser" class="router" @click="signOut" to="/">Sign Out</router-link></li>
            <li><router-link v-if="loggedUser" class="router"  to="/profile">Profile</router-link></li>
        </ul>
    </div>
</template>

<script>
import { getLoggedUser, signUserOut } from "../managers/userManager"
export default {
    data (){
        return {
            loggedUser: null
        }
    },
    created() {
        this.loggedUser = getLoggedUser();
    },
    methods: {
        signOut(){
            signUserOut();
            if(this.$route.path === "/"){
                this.$router.go();
            }else{
                this.$router.push("/");
            }
        }
    }
}
</script>

<style scoped>
    #container{
        width: 100%;
        margin: 0 auto;
        border-radius: 0 0 10px 10px;
    }

    #nav-bar{
        float: right;
        list-style: none;
        margin: 40px 25px;
        display: flex;
        flex-direction: row;
    }
    #nav-bar li{
        display: inline-block;
    }
    #nav-bar li a,.router{
        color: rgb(251,224,160);
        text-decoration: none;
        margin: 20px 10px;
        padding: 10px 15px;
        font-family: 'Courier New';
        font-size: 26px;
        border-radius: 10px;
        cursor: pointer;
    }

    #nav-bar li a:hover,.router:hover{
        background:rgba(75, 26, 4, 0.52);
        color: rgb(238, 229, 207);
    }
</style>