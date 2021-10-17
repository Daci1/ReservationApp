<template>
    <!-- <div>
        <input type="file" @change="onFileSelected" accept="image/png, image/jpeg" />
        <button @click="onUpload">get</button>
        <img id="ItemPreview" src="">
    </div> -->
    <BackButton/>
    <div class="wrapper">
        <br><br><br>
        <div class="menu-container">
            <div class="title">
                <br>
                <h4><span>fresh food for good health</span>our menu</h4>
            </div>
            <div class="menu">
                <div class="single-menu">
                    <img src="../assets/logo.png" alt="">
                    <div class="menu-content">
                        <h4>chicken fries<span>$30</span></h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Harum laudantium aliquid dolorum perferendis, facilis, suscipit sequi quidem consequuntur consequatur nisi tenetur doloribus quos! Exercitationem recusandae vero modi velit fugiat dolor.</p>
                    </div>
                </div>
                <div class="single-menu">
                    <img src="../assets/logo.png" alt="">
                    <div class="menu-content">
                        <h4>chicken fries<span>$30</span></h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Harum laudantium aliquid dolorum perferendis, facilis, suscipit sequi quidem consequuntur consequatur nisi tenetur doloribus quos! Exercitationem recusandae vero modi velit fugiat dolor.</p>
                    </div>
                </div>
                <div class="single-menu">
                    <img src="../assets/logo.png" alt="">
                    <div class="menu-content">
                        <h4>chicken fries<span>$30</span></h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Harum laudantium aliquid dolorum perferendis, facilis, suscipit sequi quidem consequuntur consequatur nisi tenetur doloribus quos! Exercitationem recusandae vero modi velit fugiat dolor.</p>
                    </div>
                </div>
                <div class="single-menu">
                    <img src="../assets/logo.png" alt="">
                    <div class="menu-content">
                        <h4>chicken fries<span>$30</span></h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Harum laudantium aliquid dolorum perferendis, facilis, suscipit sequi quidem consequuntur consequatur nisi tenetur doloribus quos! Exercitationem recusandae vero modi velit fugiat dolor.</p>
                    </div>
                </div>
                <div class="single-menu">
                    <img src="../assets/logo.png" alt="">
                    <div class="menu-content">
                        <h4>chicken fries<span>$30</span></h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Harum laudantium aliquid dolorum perferendis, facilis, suscipit sequi quidem consequuntur consequatur nisi tenetur doloribus quos! Exercitationem recusandae vero modi velit fugiat dolor.</p>
                    </div>
                </div>
                <div class="single-menu">
                    <img src="../assets/logo.png" alt="">
                    <div class="menu-content">
                        <h4>chicken fries<span>$30</span></h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Harum laudantium aliquid dolorum perferendis, facilis, suscipit sequi quidem consequuntur consequatur nisi tenetur doloribus quos! Exercitationem recusandae vero modi velit fugiat dolor.</p>
                    </div>
                </div>
                 <div class="single-menu">
                    <img src="../assets/logo.png" alt="">
                    <div class="menu-content">
                        <h4>chicken fries<span>$30</span></h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Harum laudantium aliquid dolorum perferendis, facilis, suscipit sequi quidem consequuntur consequatur nisi tenetur doloribus quos! Exercitationem recusandae vero modi velit fugiat dolor.</p>
                    </div>
                </div>
                 <div class="single-menu">
                    <img src="../assets/logo.png" alt="">
                    <div class="menu-content">
                        <h4>chicken fries<span>$30</span></h4>
                        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Harum laudantium aliquid dolorum perferendis, facilis, suscipit sequi quidem consequuntur consequatur nisi tenetur doloribus quos! Exercitationem recusandae vero modi velit fugiat dolor.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
import BackButton from '../components/BackButton.vue';
export default {
    
    data(){
        return{
            selectedFile: null,
        }
    },
    components:{
        BackButton,
    },
    setup() {
        
    },
    methods:{
        onFileSelected(event){
            this.selectedFile = event.target.files[0];
        },
        async onUpload(){
            const url = '/api/menu/getall';
            let response = await axios.get(url); 
            document.getElementById("ItemPreview").src = response.data[0].image;
            console.log(document.getElementById("ItemPreview").src);
        }
    }
}
</script>
<style scoped>
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
body{
    font-family: sans-serif;
}
.wrapper{
    background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.7)),url("../assets/menu.jpg");
    height: 100%;
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    -webkit-background-size:cover;
    
}
.menu-container{
    width: 70%;
    margin: auto;
    color: #ddd;
    background: rgba(8, 8, 8, 0.7);
    border-radius: 20px;
}
.title{
    text-align: center;
    margin-bottom: 60px;
}
.title h4{
    text-transform: capitalize;
    font-size: 36px;
    position: relative;
    display: inline-block;
    padding-bottom: 10px;
}
.title h4 span{
    display: block;
    font-size: 40px;
    font-style: italic;
    /* margin-bottom:-10px; */
}
.title h4:before{
    position: absolute;
    content:"";
    width:100px;
    height: 3px;
    background-color: #ff7720;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
}
.menu{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    /* background: rgba(8, 8, 8, 0.7);
    border-radius: 20px; */
    padding: 20px;

}
.single-menu{
    flex-basis: 580px;
    margin-bottom: 40px;
    padding-bottom: 40px;
    border-bottom: 1px solid #ddd;
    display: flex;
    flex-direction: row;
    align-items: center;
}
.single-menu:nth-child(5), .single-menu:nth-child(6){
    border-bottom: 0;
}
.single-menu img{
    max-width: 180px;
    margin-right: 30px;
    border-radius: 50%;
    border: 1px solid #ddd;
    padding: 3px;
    transition: .5s;
}
.single-menu:hover img{
    border-radius: 0;
}
.single-menu h4{
    text-transform: capitalize;
    font-size: 22px;
    border-bottom: 1px dashed #333;
    margin-bottom: 5px;
    padding: 5px;
}
.single-menu h4 span{
    float: right;
    color: #ff7720;
    font-style: italic;
}
</style>
