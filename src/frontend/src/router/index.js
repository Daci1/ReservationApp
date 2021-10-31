import { createWebHistory, createRouter } from "vue-router";
import About from "@/views/About.vue"
import HomePage from "@/views/HomePage.vue"
import SignIn from "@/views/SignIn.vue"
import RestaurantMenu from "@/views/RestaurantMenu.vue"
import ReservationPage from "@/views/ReservationPage.vue"
import OwnReservations from "@/views/OwnReservations.vue"
import AdminPanel from "@/views/AdminPanel.vue"
import ProfilePage from "@/views/ProfilePage.vue"

const routes = [
    {
        path: '/',
        name: 'HomePage',
        component: HomePage
    },
    {
        path: "/about",
        name: "About",
        component: About
    },
    {
        path: "/signin",
        name: "SignIn",
        component: SignIn
    },
    {
        path: "/menu",
        name: "Menu",
        component: RestaurantMenu
    },
    {
        path: "/reservation",
        name: "ReservationPage",
        component: ReservationPage
    },
    {
        path: "/ownReservations",
        name: "OwnReservations",
        component: OwnReservations
    },
    {
        path: "/adminPanel",
        name: "AdminPanel",
        component: AdminPanel
    },
    {
        path: "/profile",
        name: "ProfilePage",
        component: ProfilePage
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
  });

export default router;