import axios from "axios";
import {reactive} from "vue"
import { getJWT, getLoggedUser } from "../managers/userManager"
let reservations;
let userReservations;
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

export async function getUserReservations(){
    if(!userReservations){
        userReservations = reactive([]);
    }
    let url = "/api/reservation/getuserreservations";
    let response = await axios.post(url, getLoggedUser().email, {
        headers: {
            authorization: getJWT()
        }
    });
    for(let reservation of response.data){
        let date = new Date(reservation.reservationBegin);
        let ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(date);
        let mo = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(date);
        let da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(date);
        let stringDate = `${ye}-${mo}-${da} ${String(date).split(" ")[4]}`;
        reservation.reservationBegin = stringDate;
        userReservations.push(reservation);
    }
    return userReservations;
}

export async function deleteReservation(reservation){
    let url = "/api/reservation/deletereservation";
    let response = await axios.post(url, {
        tableNumber: reservation.tableNumber,
        reservationBegin: reservation.reservationBegin
    },
    {
        headers: {
            authorization: getJWT()
        }
    });
    if(response.status && response.status == 200){
        return true;
    }

    return false;
}