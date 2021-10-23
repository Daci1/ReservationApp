import axios from "axios";
import {reactive} from "vue"
import { getJWT, getLoggedUser } from "../managers/userManager"
let reservations;
let userReservations = [];
export function getReservations(){
    
    reservations = reactive([]);
    for(let i = 0; i < 9; i++){
        let date = new Date(Date.now());
        date.setHours(6+2*i);
        date.setMinutes(0);
        date.setSeconds(0);
        let hour = date.getHours();
        let interval = getTwoHourInterval(hour);
        reservations.push({
            reservationInterval: interval,
            reservationDate: date,
            busyState: false
        });
    }
    return reservations;
}

export async function getReservationByTableAndDay(tableNumber, day){
    if(!reservations){
        reservations = getReservations();
    }
    initializeReservations();
    let url = "/api/reservation/findreservationbyday";
    let response = await axios.post(url, {
        day: day,
        tableName: tableNumber
    },
    {
        headers: {
            authorization: getJWT()
        }
    });
    // let reservation = [];
    // console.log(reservations);
    // console.log(response.data);
    for(let reservation of response.data){
        let date = new Date(reservation.reservationBegin);
        let interval = getTwoHourInterval(date.getHours());
        for(let reserv of reservations){
            if(!reserv.reservationInterval.localeCompare(interval)){
                reserv.busyState = true;
            }
        }
    }
}

function getTwoHourInterval(hour){
    let interval = hour < 10 ? "0" + String(hour) + ":00" : String(hour) + ":00";
    interval = interval + "-";
    interval += (hour+2)%24 < 10 ? "0" + String((hour+2)%24) + ":00" : String((hour+2)%24) + ":00"
    return interval;
}

function initializeReservations(){
    for(let reservation of reservations){
        reservation.busyState = false;
    }
}

async function loadUserReservations(){
    let email = JSON.parse(localStorage.getItem("user")).email;
    console.log(email);
    let url = "/api/reservation/getuserreservations";
    let response = await axios.post(url, email);
    userReservations = [];

}

export async function addUserReservation(tableName, date, interval){
    let reservationBegin = date + " " + interval.substring(0,2) + ":00:00";
    let url = "/api/reservation/addreservation";
    let response = await axios.post(url, {
        tableNumber: tableName,
        email: getLoggedUser().email,
        reservationBegin: reservationBegin
    },
    {
        headers: {
            authorization: getJWT()
        }
    });
    // console.log(reservationBegin);
    console.log(response);
}