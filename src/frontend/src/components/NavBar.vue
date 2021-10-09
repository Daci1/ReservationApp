<template>
    <div id="container">
        <ul id="nav-bar">
            <li><router-link class="router" to="/">Home</router-link></li>
            <li><router-link class="router" to="/about">Menu</router-link></li>
            <li><router-link class="router" to="/">Contact</router-link></li>
            <li><router-link class="router" to="/signin">Sign in</router-link></li>
        </ul>
    </div>
</template>

<script>
export default {
    setup() {
        
    },
}
</script>

<style scoped>
    #container{
        width: 100%;
        margin: 0 auto;
        border-radius: 0 0 10px 10px;
    }

    #nav-bar{
        float: right;
        list-style: none;
        margin: 40px 25px;
        display: flex;
        flex-direction: row;
    }
    #nav-bar li{
        display: inline-block;
    }
    #nav-bar li a,.router{
        color: rgb(251,224,160);
        text-decoration: none;
        margin: 20px 10px;
        padding: 10px 15px;
        font-family: 'Courier New';
        font-size: 26px;
        border-radius: 10px;
        cursor: pointer;
    }

    #nav-bar li a:hover,.router:hover{
        background:rgba(75, 26, 4, 0.52);
        color: rgb(238, 229, 207);
    }
</style>