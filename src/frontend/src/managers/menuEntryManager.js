import axios from "axios";
import {reactive} from "vue"
import { getJWT, getLoggedUser } from "../managers/userManager"


let menuEntries;

export async function addNewMenuEntry(menuEntry){
    let url = "/api/menu/newMenuEntry";
    let response = axios.post(url, menuEntry,{
        headers: {
            authorization: getJWT(),
        }
    });
    if(response.status && response.status == 200){
        return true;
    }
    return false;
}

export async function getAllMenuEntires(){
    menuEntries = reactive([]);
    let url = "/api/menu/getall";
    let response = await axios.get(url);
    for(let menuEntry of response.data){
        menuEntries.push(menuEntry);
    }
    return menuEntries;
}