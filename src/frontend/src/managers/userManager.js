import {reactive} from "vue"
import axios from "axios"

let users;
let JWT = "";

export async function logUserIn(mail, password){
    try{
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
        console.log(userResponse.data);
        localStorage.setItem("user", JSON.stringify(userResponse.data));
    }catch(e){
        //
    }
}

function updateJWT(){
    let expDate = new Date(Date.now() + 1000*3);
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    document.cookie = "JWT=" + JWT + "; expires=" + expDate + "; path=/;"
}