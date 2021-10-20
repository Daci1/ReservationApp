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
    let expDate = new Date(Date.now() + 1000*3).toUTCString();
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    document.cookie = "JWT=" + JWT + "; expires=" + expDate + "; path=/;";
}

export function getLoggedUser(){
    return JSON.parse(localStorage.getItem("user"));
}

export function signUserOut(){
    localStorage.removeItem("user");
}