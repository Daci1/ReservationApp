import {reactive} from "vue"
let items;

export function getReservations(){
    
    items = reactive([]);
    for(let i = 0; i < 9; i++){
        let date = new Date(Date.now());
        date.setHours(8+2*i);
        date.setMinutes(0);
        date.setSeconds(0);
        let hour = date.getHours();
        let interval = hour < 10 ? "0" + String(hour) + ":00" : String(hour) + ":00";
        interval = interval + "-";
        interval += (hour+2)%24 < 10 ? "0" + String((hour+2)%24) + ":00" : String((hour+2)%24) + ":00"
        items.push({
            reservationInterval: interval,
            reservationDate: date,
            busyState: i%2 == 0 ? false : true
        });
    }

    return items;
}