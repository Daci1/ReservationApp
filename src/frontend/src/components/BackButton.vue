<template>
    <button id="backbutton" @click="goBack"> &lt;&lt; Go Back</button> 
</template>
<script>
export default {
    setup() {
        
    },
    methods:{
        goBack(){
            console.log(this.$router);
        this.$router.go(-1);
        },
    }
}
</script>
<style scoped>
#backbutton{
    position: absolute;
    margin: 30px 15px;
    padding: 15px 15px;
    text-transform: none;
    background-color: rgba(211, 69, 4, 0.7);
    border: 1px solid rgba(211, 69, 4, 1);
    color: rgba(251,224,160);
    border-radius: 20px;
    font-size: 14px;
	font-weight: bold;
	letter-spacing: 1px;
	text-transform: uppercase;
	transition: transform 80ms ease-in;
}
</style>