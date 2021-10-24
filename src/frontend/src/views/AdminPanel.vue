<template>
    <NavBar/>
    <div class="wrapper">
        <div class="container">
            <div class="pagination-buttons-div">
                <button @click="prevPage">Previous</button> 
                <button @click="nextPage">Next</button>
            </div>
            <table>
            <thead>
                <tr>
                    <th @click="sort('tableNumber')">Table Number</th>
                    <th @click="sort('reservationBegin')">Reservation Interval</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="reservation in sortedReservations" :key="reservation.reservationBegin">
                    <td>{{reservation.tableNumber}}</td>
                    <td>{{reservation.reservationBegin}}</td>
                    <td><button class="delete-button" @click="deleteR(reservation)">Delete</button></td>
                </tr>
            </tbody>
            </table>
        </div>
    </div>
</template>
<script>
import NavBar from "../components/NavBar.vue"
export default {
    components: {
        NavBar
    },
    setup() {
        
    },
}
</script>
<style scoped>

</style>