<template>
<BackButton/>
<div class="background">
<div class="container" id="container">
	<div class="form-container sign-up-container">
		<form @submit="registerAccount" method="post">
			<!-- <br> -->
			<h1>Create Account</h1>
            <input required="required" type="text" placeholder="First Name" v-model="registerUser.firstName"/>
			<input required="required" type="text" placeholder="Last Name" v-model="registerUser.lastName"/>
            <input required="required" type="date" v-model="registerUser.dob"/>
			<input required="required" type="email" placeholder="Email" v-model="registerUser.email"/>
			<input required="required" type="number" placeholder="Mobile" v-model="registerUser.mobileNo"/>
			<input required="required" type="password" placeholder="Password" v-model="registerUser.password"/>
			<span>{{invalidRegisterCredentials}}</span>
			<button>Sign Up</button>
			<!-- <br> -->
		</form>
	</div>
	<div class="form-container sign-in-container">
		<form action="#"  @submit="logIn" method="post">
			<h1>Sign in</h1>
			<input type="email" placeholder="Email" v-model="loggingUser.email"/>
			<input type="password" placeholder="Password" v-model="loggingUser.password"/>
			<a href="#">Forgot your password?</a>
			<button>Sign In</button>
			<span>{{invalidCredentials}}</span>
		</form>
	</div>
	<div class="overlay-container">
		<div class="overlay">
			<div class="overlay-panel overlay-left">
				<h1>Welcome Back!</h1>
				<p>To keep connected with us please login with your personal info</p>
				<button class="ghost" id="signIn" @click="disableSignUp">Sign In</button>
			</div>
			<div class="overlay-panel overlay-right">
				<h1>Hello, Friend!</h1>
				<p>Enter your personal details and start journey with us</p>
				<button class="ghost" id="signUp" @click="toggleSignUp">Sign Up</button>
			</div>
		</div>
	</div>
</div>
</div>
</template>

<script>
import BackButton from '../components/BackButton.vue';
import { logUserIn, registerUser } from '../managers/userManager.js'
export default {
	components:{
		BackButton,
	},
    setup() {
        
    },
    data(){
        return {
            registerUser:{
                firstName: null,
				lastName: null,
                dob: null,
                email: null,
				mobileNo: null,
                password: null
            },
			loggingUser:{
				email: null,
				password: null
			},
			invalidCredentials:"",
			invalidRegisterCredentials:"",

        }
    },
    methods:{
        toggleSignUp(){
            const container = document.getElementById('container');
            container.classList.add("right-panel-active");
			this.invalidRegisterCredentials = "";
        },
        disableSignUp(){
            const container = document.getElementById('container');
            container.classList.remove("right-panel-active");
			this.invalidCredentials = "";
        },
        async registerAccount(e){
			e.preventDefault();
			try{
				await registerUser(this.registerUser);
				this.$router.go();
			}catch(err){
				//add invalid credentials
				if(String(err).includes("302")){
					this.invalidRegisterCredentials = "Already used email!";
				}
			}
            
			
            // console.log(this.registerUser);
        },
		async logIn(e){
			try{
				e.preventDefault();
				await logUserIn(this.loggingUser.email, this.loggingUser.password);
				this.invalidCredentials = "";
				this.$router.push("/");
			}catch(exception){
				this.invalidCredentials = "Wrong email or password!";
			}
			
		}
    }
}
</script>

<style scoped>

* {
	box-sizing: border-box;
    
}
.background{
    background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.7)),url("../assets/background.jpg");
    height: 100vh;
    width: 100%;
    background-size: cover;
    -webkit-background-size:cover;
    background-position: 0;
}
.container{
    
    width: 50%;
    margin: auto;
	height: 60%;
    transform: scale(1.1, 1.1) translateY(40%);
    -ms-transform: scale(1.1, 1.1) translateY(40%);
}
h1 {
	font-weight: bold;
	margin: 10px 0;
}

h2 {
	text-align: center;
}

p {
	font-size: 15px;
	font-weight: 100;
	line-height: 20px;
	letter-spacing: 0.5px;
	margin: 20px 0 30px;
}

span {
	color: rgba(211, 4, 4, 0.7);
	font-size: 14px;
	text-decoration: none;
	margin: 15px 0;
}

a{
	color: rgba(211, 69, 4, 0.7);
	font-size: 14px;
	text-decoration: none;
	margin: 15px 0;
}
button {
	border-radius: 20px;
	border: 1px solid rgba(211, 69, 4, 1);
	background-color: rgba(211, 69, 4, 0.9);
	color: rgba(251,224,160);
	font-size: 14px;
	font-weight: bold;
	padding: 12px 45px;
	margin-bottom: 10px;
	letter-spacing: 1px;
	text-transform: uppercase;
	transition: transform 80ms ease-in;
}

button:active {
	transform: scale(0.95);
}

button:focus {
	outline: none;
}

button.ghost {
	background-color: transparent;
	border-color: rgba(251,224,160);
    color: rgba(251,224,160);
}

form {
	background-color: rgba(251,224,160);
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
	padding: 0 50px;
	height: 100%;
	text-align: center;
    color: black;
    }
    ::placeholder{
        color:black;
    }

input {
	background-color: rgba(251,224,160);
    border: 1px solid rgba(211, 69, 4, 0.7);
	padding: 15px 15px;
	margin: 8px 0;
	width: 100%;
    outline: 0;
}
.container {
	background-color: transparent;
	border-radius: 10px;
    box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
	position: relative;
	overflow: hidden;
	width: 768px;
	max-width: 100%;
	min-height: 480px;
}

.form-container {
	position: absolute;
	top: 0;
	height: 100%;
	transition: all 0.6s ease-in-out;
}

.sign-in-container {
	left: 0;
	width: 50%;
	z-index: 2;
}

.form-container {
	position: absolute;
	top: 0;
	height: 100%;
	transition: all 0.6s ease-in-out;
}

.sign-in-container {
	left: 0;
	width: 50%;
	z-index: 2;
}

.container.right-panel-active .sign-in-container {
	transform: translateX(100%);
}

.sign-up-container {
	left: 0;
	width: 50%;
	opacity: 0;
	z-index: 1;
}

.container.right-panel-active .sign-up-container {
	transform: translateX(100%);
	opacity: 1;
	z-index: 5;
	animation: show 0.6s;
}

@keyframes show {
	0%, 49.99% {
		opacity: 0;
		z-index: 1;
	}
	
	50%, 100% {
		opacity: 1;
		z-index: 5;
	}
}

.overlay-container {
	position: absolute;
	top: 0;
	left: 50%;
	width: 50%;
	height: 100%;
	overflow: hidden;
	transition: transform 0.6s ease-in-out;
	z-index: 100;
}

.container.right-panel-active .overlay-container{
	transform: translateX(-100%);
}

.overlay {
	background: rgba(211, 69, 4, 0.7);
	background: rgba(211, 69, 4, 0.7);
	background: rgba(211, 69, 4, 0.7);
	background-repeat: no-repeat;
	background-size: cover;
	background-position: 0 0;
	color: rgba(251,224,160);
	position: relative;
	left: -100%;
	height: 100%;
	width: 200%;
    transform: translateX(0);
	transition: transform 0.6s ease-in-out;
}

.container.right-panel-active .overlay {
    transform: translateX(50%);
}

.overlay-panel {
	position: absolute;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
	padding: 0 40px;
	text-align: center;
	top: 0;
	height: 100%;
	width: 50%;
	transform: translateX(0);
	transition: transform 0.6s ease-in-out;
}

.overlay-left {
	transform: translateX(-20%);
}

.container.right-panel-active .overlay-left {
	transform: translateX(0);
}

.overlay-right {
	right: 0;
	transform: translateX(0);
}

.container.right-panel-active .overlay-right {
	transform: translateX(20%);
}

.social-container {
	margin: 20px 0;
}

.social-container a {
	border: 1px solid #DDDDDD;
	border-radius: 50%;
	display: inline-flex;
	justify-content: center;
	align-items: center;
	margin: 0 5px;
	height: 40px;
	width: 40px;
}
/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* Firefox */
input[type=number] {
  -moz-appearance: textfield;
}
</style>
