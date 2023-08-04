<template>
  <div class="my-5 container">
    <!-- image, Product Info -->
    <v-row class="ma-0">
      <!-- Image -->
      <v-col cols="3" class="ma-0 pa-0">
        <v-img
          class="ma-0"
          :src="localDomain  + product.imagePath"
          contain
        ></v-img>
      </v-col>

      <!-- product Info -->
      <v-col cols="7">
        <div class="text-h3">{{ product.prodName }}</div>
        <div class="text-caption ml-2 my-2">{{ product.category.cate_name }}</div>
        <div class="text-body-1 ml-2 my-2">{{ product.brandName }}</div>
        <div class="text-body-1 ml-2 my-2">{{ product.unit_price }} Kyats</div>
        <div class="text-body-1 ml-2 my-2">{{ product.status }} </div>
        <v-card class="text-body-1 ml-1 my-2 pa-2">{{ product.description }}</v-card>
        <div class="d-flex flex-row justify-content-between">
          <div>
            <div class="input-group-prepend">
              <span class="input-group-text">Quantity</span>
            </div>
            <input type="number" class="border" v-model="quantity" />
            
          </div>
          
          <div class="input-group col-6 p-2">
            <br><br><br>
            <v-btn
            color="success"
            class="mt-4 mr-4"
            @click="addToCart"
          >
            <span>Add To Cart
              <ion-icon name="cart-outline" v-pre></ion-icon>
            </span>
          </v-btn>
          </div>
        </div>
      </v-col>
    </v-row>

  </div>
</template>

<script>
import utils from "../utils/utils";
//import axios from "axios";
//  import swal from "sweetalert2";
//const axios = require("axios");
export default {
  name: "product_detail",

  data() {
    return {
      localDomain: utils.constant.localDomain,

      prodName : "",
      brandName : "",
      description : "",
      unit_price : 0,

      imagePath : null,
      loginUser: {},

      // Product ID from Path
      product_id: this.$route.params.id,
      product: {},
      
      //token : localStorage.getItem("token"),
      quantity: 1,
    };
  },

  async created() {
    // LoginUser from Vuex
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
     // (newVal, oldVal) => {
      //  this.loginUser = newVal;
      //},
      {
        deep: true,
      }
    );

    await this.fetchProducts();

  },

  props:["baseURL"],
  methods: {
    async fetchProducts() {
      const resp = await utils.http.get("/product/product/" + this.product_id);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.product = data;
        }
      }
    },
  

  // add to cart
  
  async addToCart(){
    console.log(this.quantity, this.product.prod_id)
    // console.log(sessionStorage.getItem("loginUser"))
   // const user = JSON.parse(sessionStorage.getItem("loginUser"));
    const respCartItem = await utils.http.post("/cart/add",
    { product : this.product,
      quantity : this.quantity,  
       },
);
    if (respCartItem && respCartItem.status === 200) {
      //this.$router.push({ path: "/cart" });
      swal({ 
          text : "Product added to the cart",
          icon : "success",
        });
    }
 },
    /* addToCart(){
      console.log(this.quantity, this.product.productId),
      axios.post(`${this.baseURL}/cart/add`,
      {productId : this.product.productId,
      quantity : this.quantity,
    })
      .then((res) => {
        if(res.status == 200){
        swal({ 
          text : "Product added to the cart",
          icon : "success",
        });
      }
      })
      .catch((err) => console.log("err", err));
    }, */
},
};
</script>

<style scoped>

#add-to-cart-button{
  background-color: rgb(50, 120, 75);
}
.container {
  margin-left: 10vw !important;
  margin-right: 10vw !important;
}

.border{
  border: 1px solid black;
}
</style>