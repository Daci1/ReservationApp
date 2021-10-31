import {reactive} from "vue"
import axios from "axios"
// import router from '../router/index.js'

let users;
let loggedUser;
let JWT = "";

export async function logUserIn(mail, password){
    let url = '/api/user/authenticate'
    let response = await axios.post(url, {
        'username': mail,
        'password': password
    });
    JWT = response.data;
    updateJWT();
    let userUrl = '/api/user/getUser';
    let userResponse = await axios.post(userUrl, 
    {
        'username': mail,
        'password': password
    }, 
    {
        headers: {
            authorization: response.data
        }
    });
    let data = userResponse.data;
    
    
    loggedUser = data;
    localStorage.setItem("user", JSON.stringify(data));

    
}

function updateJWT(){
    let expDate = new Date(Date.now() + 1000*60*60*3).toUTCString();
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    document.cookie = "JWT=" + JWT + "; expires=" + expDate + "; path=/;";
}

export function getJWT(){
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${"JWT"}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

export function getLoggedUser(){
    return JSON.parse(localStorage.getItem("user"));
}

export function signUserOut(){
    localStorage.removeItem("user");
}

export async function registerUser(user){
    let url = "/api/user/register";
    let response = await axios.post(url, user);
    
}

export function isUserLogged(){
    return getLoggedUser() != null;
}

export async function getAllUsers(){
    let url = "/api/user/getall"
    let response = await axios.post(url, null, {
        headers: {
            authorization: getJWT()
        }
    });
    users = reactive([]);
    for(let user of response.data){
        let date = new Date(user.dob);
        let ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(date);
        let mo = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(date);
        let da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(date);
        let stringDate = `${ye}-${mo}-${da}`;
        user.dob = stringDate;
        users.push({
            email: user.email,
            firstName: user.firstName,
            lastName: user.lastName,
            role: user.role,
            mobile: user.mobileNo,
            dob: user.dob
        });
    }

    return users;
}

export async function updateUser(user, oldUserEmail){
    let url = '/api/user/editUser?oldUserEmail=' + oldUserEmail;
    let response = await axios.post(url, user, {
        headers: {
            authorization: getJWT(),
        }
    });
    if(response.status && response.status == 200){
        return true;
    }
    return false;
}

export async function deleteUser(userEmail){
    let url = "/api/user/deleteUser";
    let response = await axios.post(url, userEmail,{
        headers:{
            authorization: getJWT(),
        }
    });
    if(response.status && response.status == 200){
        return true;
    }
    return false;
}