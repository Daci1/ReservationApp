import axios from "axios";
import {reactive} from "vue"
import { getJWT } from "../managers/userManager"
let reservations;

export function getReservations(){
    
    reservations = reactive([]);
    for(let i = 0; i < 9; i++){
        let date = new Date(Date.now());
        date.setHours(8+2*i);
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
    // console.log(reservations);
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