import { createWebHistory, createRouter } from "vue-router";
import About from "@/views/About.vue"
import HomePage from "@/views/HomePage.vue"
import SignIn from "@/views/SignIn.vue"
import RestaurantMenu from "@/views/RestaurantMenu.vue"

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
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
  });

export default router;