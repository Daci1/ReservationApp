<template>
    <NavBar/>
    <div class="wrapper">
        <div class="container">
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
                    <td><button>Delete</button></td>
                </tr>
            </tbody>
            </table>
            <p>
                <button @click="prevPage">Previous</button> 
                <button @click="nextPage">Next</button>
            </p>
        </div>
    </div>
</template>
<script>
import NavBar from "../components/NavBar.vue"
import {getUserReservations} from "../managers/reservationManager"
export default {
    components: {
        NavBar,
    },
    setup() {
        
    },
    data() {
        return {
            userReservations: null,
            sortedUserReservations: null,
            currentSort: 'reservationBegin',
            currentSortDir: 'desc',
            pageSize:10,
            currentPage: 1
        }
    },
    async created() {
        this.userReservations = await getUserReservations();
        this.sort("reservationBegin");
    },
    computed: {
        sortedReservations: function(){
            return this.userReservations.filter((row, index) => {
                let start = (this.currentPage-1)*this.pageSize;
                let end = this.currentPage*this.pageSize;
                if(index >= start && index < end) return true;
            });
        }
    },
    methods: {
        async sort(s){
            if(s === this.currentSort) {
            this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
            }
            this.currentSort = s;
            this.userReservations.slice().sort((a,b) => {
                let modifier = 1;
                if(this.currentSortDir === 'desc') modifier = -1;
                if(a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
                if(a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
                return 0;
            });
            console.log(this.sortedUserReservations);
        },
        nextPage:function() {
            if((this.currentPage*this.pageSize) < this.userReservations.length) this.currentPage++;
        },
        prevPage:function() {
        if(this.currentPage > 1) this.currentPage--;
        }
    }
}
</script>
<style scoped>
.wrapper{
    background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.7)),url("../assets/menu.jpg");
    min-height: 100vh;
    height: 100%;
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    -webkit-background-size:cover;
    margin: 0;
    font-family: sans-serif;
	font-weight: 100;
}

.container {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
    height: 200px;
}

table {
	width: 800px;
	border-collapse: collapse;
	overflow: hidden;
	box-shadow: 0 0 20px rgba(0,0,0,0.3);
    border-radius: 25px;
}

th,
td {
	padding: 15px;
	background-color: rgba(46, 37, 37, 0.6);
	color: rgb(251,224,160);
    border: 1px solid black;
}

th {
	text-align: center;
    background: rgb(251,224,160);
    color: rgba(75, 26, 4   );
}

tr{
    text-align: center;
}

</style>