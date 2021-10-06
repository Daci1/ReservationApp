<template>
    <div id="container">
        <ul id="nav-bar">
            <li><a href="">Home</a></li>
            <li><a href="">Menu</a></li>
            <li><a href="">Contact</a></li>
            <li><a href="">Sign in</a></li>
        </ul>
    </div>
</template>

<script>
export default {
    setup() {
        
    },
}
</script>

<style>
    *{
        margin: 0;
        padding: 0;
    }

    #container{
        width: 100%;
        margin: 0 auto;
        /* background-color: rgba(52,33,35, 0.65); */
        border-radius: 0 0 10px 10px;
    }

    #nav-bar{
        float: right;
        list-style: none;
        margin: 40px 25px;
    }
    #nav-bar li{
        display: inline-block;
    }
    #nav-bar li a{
        color: rgb(251,224,160);
        text-decoration: none;
        margin: 20px 10px;
        padding: 10px 15px;
        font-family: 'Courier New';
        font-size: 26px;
        border-radius: 10px;
    }

    #nav-bar li a:hover{
        background:rgba(75, 26, 4, 0.52);
        color: rgb(238, 229, 207);
    }
</style>