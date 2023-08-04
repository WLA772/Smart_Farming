<template>
  <div>
    <v-row>
      <!-- Category Menu -->
      <v-col cols="2">
        <v-card class="mx-auto">
          <v-navigation-drawer permanent>
            <v-list dense nav>
              <v-list-item
                v-for="(cat, index) in productCategoryList"
                :key="index"
                link
                @click="onClickCategory(cat)"
              >
                <v-list-item-content>
                  <v-list-item-title>{{ cat.cate_name }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-navigation-drawer>
        </v-card>
      </v-col>

      <!-- Product List -->
      <v-col cols="10">
        <v-row>
          <v-col cols="3" v-for="(product, index) in productList" :key="index">
            <v-card @click="goToProductDetails(product)" height="230">
              <v-card-text>
                <v-img
                  :src="localDomain  + product.imagePath"
                  max-height="160"
                  cover
                ></v-img>
                <div class="text-h6">{{ product.prodName }}</div>
                <div class="text-body-1">{{ product.unit_price }} Kyats</div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "productView",

  components: {},

  data() {
    return {
      localDomain: utils.constant.localDomain,

      catList: [],
      productList: [],
      //imageList: [],

      productCategoryList: [],
    };
  },

  async created() {
    await this.fetchProductCategories();
    await this.fetchProducts();
    //await this.getImage();
  },

  methods: {
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
        // data is not undefined, not null
        if (data) {
          this.productCategoryList = data;
        }
      }
    },

    goToProductDetails(product) {
      this.$router.push({
        path: "/product_detail/" + product.prod_id,
      });
    },

    async onClickCategory(cat) {
      const resp = await utils.http.get("/product/product/categoryforproduct/" + cat.cate_id);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.productList = data;
        }
      }
    },

    
  },
};
</script>