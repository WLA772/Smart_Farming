<template>

  <div>
        <v-row>
          <!-- Cart table -->
          <v-col cols="12">
            <v-data-table
              :headers="headers"
              :items="cartItems"
              class="elevation-1"
            >
              <!-- Product Img -->
              <template v-slot:[`item.imagePath`]="{ item }">
                <v-img
                  :src="localDomain + '/product' + item.imagePath"
                  width="150"
                  height="150"
                  contain
                ></v-img>
              </template>
  
              
              <!-- Buttons -->
              <!-- <template v-slot:[`item.actions`]> -->
                <template v-slot:[`item.actions`]="{ item }">

             <v-btn
              fab
              
              color="red"
              x-small
              elevation="2">
                <v-icon @click="removeCartItem(item)" >
                delete</v-icon>
              </v-btn>
                <!-- Delete Product Btn -->
                
              </template>
    
            </v-data-table>
          </v-col>
        </v-row>
    
        <!-- Delete Dialog -->
        <v-dialog v-model="removeDialog" width="400">
          <v-card>
            <!-- Dialog Heading -->
            <v-toolbar color="primary" dark>
              <div>Remove This Product?</div>
              <v-spacer></v-spacer>
              <v-btn icon @click="removeDialog = false">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar>
    
            <!-- Delete Product Btn -->
            <!-- <v-card-actions class="justify-end">
              <v-btn color="red" dark @click="removeCartItem(item)">Remove</v-btn>
            </v-card-actions> -->
          </v-card>
        </v-dialog>
  
        <div class="input-group col-6 p-2">
                <v-btn
                color="success"
                class="mt-4 mr-4"
                @click="checkOut"
              >
                <span>Check Out
                </span>
              </v-btn>
              </div>
      </div>
  
  </template>
  
  <script>
  import utils from "../utils/utils";
  export default{
      name: "shoppingCart",
    
      components: {},
      data(){
        return{
          localDomain: utils.constant.localDomain,
  
          headers: [
            { text: "ID", value: "id", sortable: true },
            { text: "Image", value: "imagePath", sortable: true},
            { text: "Name", value: "product.prodName", sortable: true },
            { text: "Brand", value: "product.brandName", sortable: true },
            { text: "Price", value: "product.unit_price", sortable: true },
            { text: "Quantity", value: "quantity", sortable: true },
            { text: "Sub Total", value: "total", sortable: true },
            { text: "Actions", value: "actions", sortable: false},
          ],
          cartItems : [],
  
          //itemDetail:[],
          //totalCost : 0,
  
          shoppingCartItem_id: this.$route.params.id,
          //product_id: this.$route.params.id,
          removeDialog: false,
          toRemoveCartItem: {},
  
          productList: [],
          productCategoryList: [],
        };
      },
      //props: ["baseURL"],
  
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
        await this.fetchCart();
      },
      methods: {
          //fetch All Items In Cart
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
  
        async fetchProducts() {
          const resp = await utils.http.get("/product/product");
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              this.productList = data;
            }
          }
        },
  
        async fetchProductCategories() {
          const resp = await utils.http.get("/admin/categoryforproduct");
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              this.productCategoryList = data;
            }
          }
        },
  
        async removeCartItem(item) {
          console.log(item.id);
          const resp = await utils.http.del(
            "/cart/remove/" + item.id
          );
          if (resp && resp.status === 200) {
            this.removeDialog = false;
            // Refresh Cart
            await this.fetchCart();
          } else {
            this.errorAlert = true;
          }
        },
  
        // async fetchTotal() {
        //   const resp = await utils.http.get("/cart/getTotal");
        //   if (resp && resp.status === 200) {
        //    /*  const data = await resp.json();
        //     if (data) {
        //       this.totalCost = data;
        //     } */
        //     const result = resp.data;
        //     this.itemDetail = result.itemDetail;
        //     this.totalCost = result.totalCost;
        //   }
        // },
  
        checkOut(){
          this.$router.push({ path: "/orderView" });
        }
  
        
      }
  };
  
  </script>