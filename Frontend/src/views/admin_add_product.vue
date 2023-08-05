<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>

      <!-- Create Product Form -->
      <v-col cols="10">
        <v-form class="px-10" ref="productForm" v-model="productForm">
          <!-- Product Name -->
          <v-text-field
            v-model="prodName"
            :counter="50"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 50) ||
                'Name must be less than 50 characters',
            ]"
            label="Name"
            required
          ></v-text-field>

          <!-- Brand Name -->
          <v-text-field
            v-model="brandName"
            :counter="50"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 50) ||
                'Brand Name must be less than 50 characters',
            ]"
            label="Brand"
            required
          ></v-text-field>

          <!-- Description -->
          <v-text-field
            v-model="description"
            :counter="1000"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 1000) ||
                'Description must be less than 1000 characters',
            ]"
            label="Description"
            required
          ></v-text-field>

          <!-- Product Status -->
          <v-text-field
            v-model="status"
            :counter="50"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 50) ||
                'Status must be less than 50 characters',
            ]"
            label="Status"
            required
          ></v-text-field>

          <!-- Price -->
          <v-text-field
            v-model="unit_price"
            type="number"
            suffix="MMK"
            max="999999"
            min="1"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v > 0 && v <= 999999) ||
                'Buget must be between 0 and 999999 MMK',
            ]"
            label="Price"
            required
          ></v-text-field>

          <!-- Category -->
          <v-select
            v-model="category"
            :items="productCategoryList"
            item-text="cate_name"
            item-value="cate_id"
            :rules="[(v) => !!v || 'Required']"
            label="Category"
            required
          >
          </v-select>

          <!-- Image -->
          <v-file-input
            v-model="image"
            label="Image"
            show-size
            prepend-icon="mdi-camera"
            placeholder="Choose Poster Image"
            accept="image/png, image/jpeg"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                !v ||
                v.size < 10000000 ||
                'Image size should be less than 10 MB!',
            ]"
            @change="onChangeImage"
          ></v-file-input>

          <!-- Image Preview -->
          <v-img
            v-if="imagePreviewPath != null"
            :src="imagePreviewPath"
            width="200"
            height="150"
            contain
          >
          </v-img>

          <!-- Create Btn -->
          <v-btn
            :disabled="!productForm"
            color="success"
            class="mt-4 mr-4"
            @click="addProduct()"
          >
            <span v-if="!loading">Add Product</span>
            <v-progress-circular
              v-else
              indeterminate
              color="primary"
            ></v-progress-circular>
          </v-btn>

          <!-- Error Alert For Product -->
          <v-alert class="mt-3" v-show="errorAlert" dense type="error">
            Adding Product Failed!
          </v-alert>
          <!-- Same Title Error Alert -->
          <v-alert class="mt-3" v-show="errorAlert2" dense type="error">
            Product with same name exists!
          </v-alert>
        </v-form>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import sidebar_admin from "../components/sidebar_admin.vue";
import utils from "../utils/utils";

export default {
  name: "admin_add_product",
  components: { sidebar_admin },

  data() {
    return {
      productForm: false,

      prodName : "",
      brandName : "",
      description : "",
      status : "",
      unit_price : 0,
      
      category: 1,

      errorAlert: false,
      errorAlert2: false,

      loading: false,

      image : null,
      imagePreviewPath: null,

      productCategoryList: [],
    };
  },

  async created() {
    await this.fetchProductCategories();
  },

  methods: {
    async fetchProductCategories() {
      const resp = await utils.http.get("/admin/categoryforproduct");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.productCategoryList = data;
        }
      }
    },

    async addProduct() {
      // Form Validation
      if (this.$refs.productForm.validate()) {
        this.errorAlert = false;
        this.errorAlert2 = false;

        this.loading = true;

        // Step 1 -> Name Check

        let nameCheckOK = false;
        const respNameCheck = await utils.http.get(
          "/admin/product/name/" + this.prodName
        );
        if (respNameCheck && respNameCheck.status === 200) {
          const data = await respNameCheck.json();
          // Undefined, Null, True
          if (data && data === true) {
            this.errorAlert2 = true;
          } else {
            this.errorAlert2 = false;
            nameCheckOK = true;
          }
        } else {
          this.errorAlert2 = true;
        }

        if (nameCheckOK) {
          let respImageData = null;

          // Step 2 -> Image

          const respImage = await utils.http.postMedia(
            "/admin/file/create",
            this.image,
            this.image.type
          );
          if (respImage.status === 200) {
            respImageData = await respImage.text();
          } else {
            this.errorAlert = true;
          }

          // Step 4 -> Create Product

          if (respImageData) {
            // Create Product API
            const respProduct = await utils.http.post("/admin/product/add", {
              prodName : this.prodName,
              brandName : this.brandName,
              description : this.description,
              unit_price : this.unit_price,
              status : this.status,
              category: { cate_id: this.category },
              imagePath: respImageData,
            });
            if (respProduct && respProduct.status === 200) {
              this.$router.push({ path: "/admin_byproduct" });
            } else {
              this.errorAlert = true;
            }
          }
        }

        this.loading = false;
      }
    },

    onChangeImage(image) {
      this.imagePreviewPath = URL.createObjectURL(image);
    },
  },
};
</script>