<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>

      <!-- Product table -->
      <v-col cols="10">
        <v-data-table
          :headers="headers"
          :items="productList"
          :items-per-page="5"
          class="elevation-1"
        >
          <!-- Product Img -->
          <template v-slot:[`item.imagePath`]="{ item }">
            <v-img
              :src="localDomain  + item.imagePath"
              width="150"
              height="150"
              contain
            ></v-img>
          </template>

          <!-- Buttons -->
          <template v-slot:[`item.actions`]="{ item }">
            <!-- Update Product -->
            <v-btn
              class="mr-2"
              color="primary"
              fab
              dark
              x-small
              elevation="2"
              @click="onClickUpdateProduct(item)"
            >
              <v-icon>edit</v-icon>
            </v-btn>
            <!-- Delete Product Btn -->
            <v-btn
              color="red"
              fab
              
              x-small
              elevation="2"
              @click="
                toDeleteProduct = item;
                deleteDialog = true;
              "
            >
              <v-icon>delete</v-icon>
            </v-btn>
          </template>
        </v-data-table>
      </v-col>
    </v-row>

    <!-- Delete Dialog -->
    <v-dialog v-model="deleteDialog" width="400">
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="primary" dark>
          <div>Delete This Product?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="deleteDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- Delete Product Info -->
        <v-card-text class="pa-4">
          <v-row dense>
            <v-col cols="3" class="font-weight-bold">ID</v-col>
            <v-col cols="7">{{ toDeleteProduct.prod_id }}</v-col>
            <v-col cols="3" class="font-weight-bold">Name</v-col>
            <v-col cols="7">{{ toDeleteProduct.prodName }}</v-col>
          </v-row>
        </v-card-text>

        <!-- Delete Product Btn -->
        <v-card-actions class="justify-end">
          <v-btn color="red" dark @click="deleteProduct()">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Update Dialog -->
    <v-dialog v-model="updateDialog" width="500">
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="primary" dark>
          <div>Update This Product?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="updateDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- Update Form -->
        <v-card-text class="pa-4">
          <v-form ref="productForm" v-model="productForm">
            <!-- Update Product Name -->
            <v-text-field
              v-model="toUpdateProduct.prodName"
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

            <!-- Update Product Brand -->
            <v-text-field
              v-model="toUpdateProduct.brandName"
              :counter="50"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 50) ||
                  'Brand must be less than 50 characters',
              ]"
              label="Brand"
              required
            ></v-text-field>

            <!-- Update Product Description -->
            <v-text-field
              v-model="toUpdateProduct.description"
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

            <!-- Update Product Price -->
            <v-text-field
              v-model="toUpdateProduct.unit_price"
              type="number"
              suffix="MMK"
              max="999999"
              min="1"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v > 0 && v <= 999999) ||
                  'Price must be between 0 and 999999 MMK',
              ]"
              label="Price"
              required
            ></v-text-field>

            <!-- Update Product Category -->
            <v-select
              v-model="toUpdateProduct.category"
              :items="productCategoryList"
              item-text="cate_name"
              item-value="cate_id"
              :rules="[(v) => !!v || 'Required']"
              label="Category"
              required
            >
            </v-select>

            <!-- Update Product Status -->
            <v-text-field
              v-model="toUpdateProduct.status"
              :counter="50"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 20) ||
                  'Status must be less than 20 characters',
              ]"
              label="Status"
              required
            ></v-text-field>

            <!-- Update Product Image -->
            <v-file-input
              v-model="toUpdateProduct.image"
              label="Image"
              show-size
              prepend-icon="mdi-camera"
              placeholder="Choose Poster Image"
              accept="image/png, image/jpeg"
              :rules="[
                (v) =>
                  !v ||
                  v.size < 10000000 ||
                  'Image size should be less than 10 MB!',
              ]"
              @change="onChangeImage"
            ></v-file-input>

            <!-- Image Preview -->
            <!-- Image is not selected (null) -->
            <v-img
              v-if="imagePreviewPath == null"
              :src="localDomain + toUpdateProduct.imagePath"
              width="200"
              height="150"
              contain
            >
            </v-img>
            <!-- Image is selected (not null) -->
            <v-img
              v-if="imagePreviewPath != null"
              :src="imagePreviewPath"
              width="200"
              height="150"
              contain
            >
            </v-img>

            <!-- Update Btn -->
            <v-btn
              :disabled="!productForm"
              color="success"
              class="mr-4"
              @click="updateProduct"
            >
              <span v-if="!loading">Update</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
            </v-btn>

            <!-- Error Alert -->
            <v-alert class="mt-3" v-show="errorAlert" dense type="error">
              Update Product Failed!
            </v-alert>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import utils from "../utils/utils";
import sidebar_admin from "../components/sidebar_admin.vue";

export default {
  name: "adminView",

  components: { sidebar_admin },

  data() {
    return {
      localDomain: utils.constant.localDomain,

      headers: [
        // { text: "ID", value: "id", sortable: true },
        { text: "Image", value: "imagePath", sortable: false },
        { text: "Name", value: "prodName", sortable: true },
        { text: "Brand", value: "brandName", sortable: true },
        { text: "Price", value: "unit_price", sortable: true },
        { text: "Status", value: "status", sortable: true },
        // { text: "Description", value: "description", width: 200, sortable: false },
        { text: "Category", value: "category.cate_name", sortable: true },
        { text: "Actions", value: "actions", sortable: false},
      ],
      productList: [],

      deleteDialog: false,
      toDeleteProduct: {},

      updateDialog: false,
      productForm: false,
      toUpdateProduct: {
        prodName: "",
        brandName: "",
        description: "",
        status: "",
        unit_price: 0,
        category: 1,
        imagePath: "",
        image: null,
      },
      imagePreviewPath: null,

      errorAlert: false,
      loading: false,

      productCategoryList: [],
    };
  },

  async created() {
    await this.fetchProductCategories();
    await this.fetchProducts();
  },

  methods: {
    async fetchProducts() {
      const resp = await utils.http.get("/admin/product");
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

    async deleteProduct() {
      const resp = await utils.http.del(
        "/admin/product/delete/" + this.toDeleteProduct.prod_id
      );
      if (resp && resp.status === 200) {
        this.deleteDialog = false;
        // Refresh Products
        await this.fetchProducts();
      } else {
        this.errorAlert = true;
      }
    },

    onClickUpdateProduct(item) {
      this.updateDialog = true;
      // Copy Object
      this.toUpdateProduct = Object.assign({}, item);
      this.toUpdateProduct.iamge = null;
      this.imagePreviewPath = null;
    },

    async updateProduct() {
      // Form Validation
      if (this.$refs.productForm.validate()) {
        this.errorAlert = false;

        this.loading = true;

        let imagePath = this.toUpdateProduct.imagePath;
        
        // Step 1 -> Update Poster

        // Null -> image is not selected
        if (this.toUpdateProduct.image != null) {
          // Update Image API
          const respImage = await utils.http.putMedia(
            "/admin/file/update",
            this.toUpdateProduct.image,
            this.toUpdateProduct.image.type,
            this.toUpdateProduct.imagePath
          );
          if (respImage && respImage.status === 200) {
            imagePath = await respImage.text();
          } else {
            console.debug("Image Update Failed");
          }
        }

        // Step 3 -> Update Product

        const respProduct = await utils.http.put(
          "/admin/product/update/" + this.toUpdateProduct.prod_id,
          {
            prodName: this.toUpdateProduct.prodName,
            brandName: this.toUpdateProduct.brandName,
            description: this.toUpdateProduct.description,
            unit_price: this.toUpdateProduct.unit_price,
            status: this.toUpdateProduct.status,
            category: {
              cate_id: this.toUpdateProduct.category,
            },
            imagePath: imagePath,
          }
        );
        if (respProduct && respProduct.status === 200) {
          this.toUpdateProduct.image = null;
          this.updateDialog = false;
          // Refresh Products
          await this.fetchProducts();
        } else {
          this.errorAlert = true;
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