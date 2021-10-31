<template>
    <BackButton/>
    <div class="wrapper" id="wrapper">
        <br><br><br>
        <div class="menu-container">
            <div class="title">
                <br>
                <h4><span>fresh food for good health</span>our menu</h4>
            </div>
            <div class="menu">
                <div class="single-menu" v-for="menuEntry of menuEntries" :key="menuEntry.productName">
                    <div class="menu-content">
                        <h4>{{menuEntry.category}}-{{menuEntry.productName}}<span>${{menuEntry.price}}</span></h4>
                        <p>{{menuEntry.description}}</p>
                        <span class="gramaj">{{menuEntry.quantity}}g</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal closed-modal" id="modal">
                        <h3>Add new Menu Entry</h3>
                        <form action="/action_page.php" @submit="confirm">
                            <label for="pname">Product Name</label>
                            <input required type="text" id="pname" name="pname" placeholder="Product name.." v-model="newProduct.productName">

                            <label for="price">Product price</label>
                            <input required type="text" id="price" name="price" placeholder="Product price.." v-model="newProduct.price">

                            <label for="quantity">Product quantity</label>
                            <input required type="text" id="quantity" name="quantity" placeholder="Product quantity.." v-model="newProduct.quantity">

                            <label for="category">Product category</label>
                            <input required type="text" id="category" name="category" placeholder="Product category.." v-model="newProduct.category">

                            <label for="description">Product description</label>
                            <textarea required placeholder="Product description.." rows="7" style="resize:none;" v-model="newProduct.description"/>

                        <button class="confirm-button">Confirm</button>
                        <button class="close-button" @click="deactivateModal">Close</button>
                        </form>
        </div>
        <button class="add-button" @click="activateModal()">+</button>
    </div>
</template>
<script>
import axios from 'axios';
import BackButton from '../components/BackButton.vue';
import {addNewMenuEntry, getAllMenuEntires} from '../managers/menuEntryManager'
export default {
    
    data(){
        return{
            newProduct:{
                productName: null,
                price: null,
                description: null,
                category: null,
                quantity: null
            },
            menuEntries: [],
        }
    },
    components:{
        BackButton,
    },
    setup() {
        
    },
    async created(){
        this.menuEntries = await getAllMenuEntires();
        console.log(this.menuEntries);
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
        },
        async activateModal(){
            let modal = document.querySelector("#modal");
            let wrapper = document.querySelector("#wrapper");
            wrapper.classList.toggle("dark-background");
            modal.classList.toggle("closed-modal");
        },
        deactivateModal(){
            let modal = document.querySelector("#modal");
            let wrapper = document.querySelector("#wrapper");
            wrapper.classList.toggle("dark-background");
            modal.classList.toggle("closed-modal");
        },
        confirm(e){
            e.preventDefault();
            if(addNewMenuEntry(this.newProduct)){
                this.$router.go();
            }
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
    min-height: 100vh;
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
    font-size: 35px;
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
    font-size: 35px;
    border-bottom: 1px dashed #333;
    margin-bottom: 5px;
    padding: 5px;
}
.single-menu h4 span{
    float: right;
    color: #ff7720;
    font-style: italic;
}

.gramaj{
    /* position: absolute; */
    float: right;
    font-size: 35px;
    color: #ff7720;
    font-style: italic;
    margin-top: 20px;
}

.menu-content p{
    font-size: 35px;
}

.add-button{
    position: fixed;
    bottom: 0;
    right: 0;
    padding: 15px 30px;
    border: none;
    margin: 20px 20px;
    border-radius: 50%;
    font-size: 50px;
    color: rgb(251,224,160);
    background: rgb(230,141,7);
    background: radial-gradient(circle, rgba(131, 83, 10,1) 0%, rgb(238, 25, 6) 100%);
    cursor: pointer;
    transition: 0.5s;
}

.add-button:hover{
    margin: 30px 30px;
    transform: scale(1.2, 1.2);
}
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 50%;
  max-width: 100%;
  height: 650px;
  max-height: 100%;
  z-index: 1000;
  background: gray;
  padding: 20px;
  border-radius: 25px;
  color: rgb(251,224,160);
}
.closed-modal{
    display: none !important;
}
.modal h3{
    text-align: center;
    font-size: 30px;
}
input, select, textarea{
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  outline: 0;
  background: rgb(251,224,160);
}

.close-button,.confirm-button{
    text-decoration: none;
    color: inherit;
    padding: 20px 60px;
    text-align: center;
    margin: 20px 150px;
    border: 2px solid rgba(211, 69, 4, 0.7);
    font-weight: bold;
    border-radius: 25px;
    font-size: 1.1vw;
    cursor: pointer;
    background: rgba(235, 112, 11, 0.5);
}
.close-button{
   position: absolute;
    right:    0;
    /* bottom:   0;   */
}
</style>
