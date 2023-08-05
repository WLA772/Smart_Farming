<template>
  <div>
    <v-row>
     <v-col cols="6">
      <div class="col-2"></div>
      <!-- <h7 md-5 px-3>Cash Sales invoice</h7> -->
      <div class="col-7"><hr /></div>
      <div class="row">
              <div class="col-7 text-center">
                  <h4 class="pt-3">
                    Cash Sales invoice
                  </h4>
              </div>
              </div>
              <div class="col-7"><hr /></div>
  
              <div>
                
                <strong class="col-6 col-md-4 ">Name : </strong>{{ name }}
                
                <strong class="col-6 col-md-4">Order Date : </strong>{{ shoppingCart.orderCreateDate }}
              </div>
              <div>
                <strong class="col-md">Gmail : </strong>{{ gmail }}
              </div>
             <!--  <div>
                {{ shoppingCart.detail }}
              </div> -->
  
      <div class="col-2"></div>
      <v-row>
          <!-- Cart table -->
          <div class="col-2"></div>
          <v-col cols="6">
            <!-- <v-data-table
              :headers="headers"
              :items="shoppingCart"
            >
    
            </v-data-table> -->
  
            <table class="cart-table">
              <thead>
                <tr>
                  <th>No</th>
                  <th>Product Name</th>
                  <th>Price</th>
                  <th>Qty</th>
                  <th>Sub Total</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in shoppingCart.detail" :key="item.id">
                  <td class="align-right">{{ index + 1 }}</td>
                  <td class="align-left">{{ item.product.name }}</td>
                  <td class="align-right">{{ item.product.unit_price }}</td>
                  <td class="align-right">{{ item.quantity }}</td>
                  <td class="align-right">{{ item.product.unit_price * item.quantity }}</td>
                </tr>
                
              </tbody>
            </table> 
            <div class="col-7"><hr /></div>
            
            <div class="totalAmt text-align-center" >
              <strong class=" mb-2">Total Amount : </strong>{{ shoppingCart.totalAmount }}
            </div>
            <div class="col-5"></div>
            
          <h5>We will contact you in shortly for delivery process</h5>
            <!-- <div class="col-7"><hr /></div> -->
          </v-col>
          
          <!-- <div class="col-7"><hr /></div>
          <div class="col-5"></div>
          <div class="col-4"></div>
          <h5>We will contact you in shortly for delivery process</h5> -->
          <div class="col-7"><hr /></div>
          <div class="col-5"></div>
          <div class="col-4"></div>
          <div>
            <strong>Thanks for your shopping with us</strong>
          </div>
          <div class="col-4"></div>
        </v-row>
      </v-col><br>

      <v-col cols="6">
        <img alt="logo" :src="vouncherPath"  width="250" height="250" >
      </v-col>
  
      </v-row>
        <v-btn
                color="success"
                class="mt-4 mr-4"
                @click="clearAllItem"
              >
                <span>DONE
                </span>
              </v-btn>
    
  </div>
      
  </template>
  
  <script>
  import utils from '@/utils/utils';
  
  export default{
      name: "orderView",
      components: {},
      data(){
        return{
          localDomain: utils.constant.localDomain,
          vocher:[],
          name:'',
          gmail:'',
          productName:"",
          productPrice:"",
          productQty:"",
          productSubTotal:"",
          totalAmount:"",
          orderDate:"",
  
  
          vouncherPath: require('@/assets/thk.jpg'),
  
          shoppingCart:{},
          shoppingCart_id: this.$route.params.id,
        };
  },
  async created() {
  
    this.loginUser = this.$store.state.loginUser;
        this.$store.watch(
          () => {
            return this.$store.state.loginUser;
          },
        
          {
            deep: true,
          }
        );
  
      const user = JSON.parse(sessionStorage.getItem("loginUser"));
      this.name  =user.name;
      this.gmail = user.gmail;
  //this.name = JSON.parse(sessionStorage.getItem('userName'));
  //this.gmail = JSON.parse(sessionStorage.getItem('gmail'));
    //await this.fetchOrderVocher();
    await this.fetchCartVoc();
  },
  methods: {
      /* async fetchOrderVocher() {
    
        const user = JSON.parse(sessionStorage.getItem("loginUser"));
          const resp = await utils.http.get("/cart/getVocher" + user);
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              this.shoppingCart = data;
            }
          }
        }, */
  
        async fetchCartVoc() {
          
          const resp = await utils.http.get("/cart/getVoc");
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              this.shoppingCart = data;
              console.log("mm",this.shoppingCart.detail)
              //this.totalCost = data.totalCost;
            }
          }
        },
        async fetchProducts() {
          const resp = await utils.http.get("/product/product");
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              this.productList = data;
            }
          }
        },
  
        async fetchCart() {
          const resp = await utils.http.get("/cart/get");
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              this.cartItems = data;
              //this.totalCost = data.totalCost;
            }
          }
        },
  
        async clearAllItem() {
            const resp = await utils.http.del("/cart/clearAllItems");
            if(resp && resp.status === 200){
              console.log("clesr successfully");
              this.$router.push({ path: "/product" });
            }
          },
  },
  }   
  </script>
  
  <style scoped>
  .cart_table{
    border-collapse: collapse;
    width: 100%;
    background-color:rgb(3, 23, 16);
  }
  
  .cart_table th,
  .cart_table td{
    background-color: hsl(110, 68%, 15%);
    border: 1px hwb(252 1% 89%);
    padding: 12px;
    
    /* text-align: left; */
  }
   .align-left{
    text-align: left;
  }
  .align-right{
    text-align: right;
  }
  
  .cart-table th{
    background-color: hsl(110, 36%, 56%);
  }
  
  .totalAmt{
    text-align: left;
    margin-top: 10px;
    
  }
  </style>