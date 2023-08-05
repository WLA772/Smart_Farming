<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>

      <!-- Knowledge table -->
      <v-col cols="10">
        <v-data-table
          :headers="headers"
          :items="knowledgeList"
          :items-per-page="5"
          class="elevation-1"
        >
          <!-- Photo Img -->
          <template v-slot:[`item.photo`]="{ item }">
            <v-img
              :src="localDomain + item.photo"
              width="150"
              height="150"
              contain
            ></v-img>
          </template>

          <!-- Plant Name -->
          <template>
            <div>{item.plantName}</div>
          </template>

          <!-- About Detail -->
          <template>
            <div>{item.about_detail}</div>
          </template>

          <!-- Category -->
          <template>
          <div>{item.category.name}</div>
        </template>

        <template>
           <div> {item.movie}</div>
         </template>
            

          <!-- Buttons -->
          <template v-slot:[`item.actions`]="{ item }">
            <!-- Update Knowledge -->
            <v-btn
              class="mr-2"
              color="primary"
              fab
              dark
              x-small
              elevation="2"
              @click="onClickUpdateKnowledge(item)"
            >
              <v-icon>edit</v-icon>
            </v-btn>

            <!-- Delete Knowledge Btn -->
            <v-btn
              fab
              
              color="red"
              x-small
              elevation="2"
              @click="
                toDeleteKnowledge = item;
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
          <div>Delete This Knowledge?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="deleteDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- Delete Knowledge Info -->
        <v-card-text class="pa-4">
          <v-row dense>
            <v-col cols="3" class="font-weight-bold">ID</v-col>
            <v-col cols="7">{{ toDeleteKnowledge.knowledge_id }}</v-col>
            <v-col cols="3" class="font-weight-bold">plantName</v-col>
            <v-col cols="7">{{ toDeleteKnowledge.plantName }}</v-col>
          </v-row>
        </v-card-text>

        <!-- Delete Knowledge Btn -->
        <v-card-actions class="justify-end">
          <v-btn color="red" dark @click="deleteKnowledge()">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Update Dialog -->
    <v-dialog v-model="updateDialog" width="500">
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="primary" dark>
          <div>Update This Knowledge?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="updateDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- Update Form -->
        <v-card-text class="pa-4">
          <v-form ref="knowledgeForm" v-model="knowledgeForm">
            <!-- Update Knowledge name -->
            <v-text-field
              v-model="toUpdateKnowledge.plantName"
              :counter="50"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 50) ||
                  'plantName must be less than 50 characters',
              ]"
              label="plantName"
              required
            ></v-text-field>

            <!-- Update Knowledge About Detail -->
            <v-textarea
              v-model="toUpdateKnowledge.about_detail"
              label="About Detail"
              required
            ></v-textarea>

            <!-- Update Knowledge Category -->
            <v-select
              v-model="toUpdateKnowledge.category"
              :items="knowledgeCategoryList"
              item-text="name"
              item-value="id"
              :rules="[(v) => !!v || 'Required']"
              label="Category"
              required
            >
            </v-select>

            <!-- Update Knowledge Photo -->
            <v-file-input
              v-model="toUpdateKnowledge.poster"
              label="Photo"
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
              @change="onChangePhoto"
            ></v-file-input>

            <!-- Poster Preview -->
            <!-- Poster is not selected (null) -->
            <v-img
              v-if="photoPreviewPath == null"
              :src="localDomain + toUpdateKnowledge.photo"
              width="200"
              height="150"
              contain
            >
            </v-img>
            <!-- Poster is selected (not null) -->
            <v-img
              v-if="photoPreviewPath != null"
              :src="photoPreviewPath"
              width="200"
              height="150"
              contain
            >
            </v-img>






               <!-- Update Movie Trailer -->
               <v-file-input
              v-model="toUpdateKnowledge.trailer"
              label="Trailer"
              show-size
              prepend-icon="mdi-video"
              placeholder="Choose Movie"
              accept="video/mp4"
              :rules="[
                (v) =>
                  !v ||
                  v.size < 100000000 ||
                  'Trailer size should be less than 1024 MB!',
              ]"
              @change="onChangeTrailer"
            ></v-file-input>

            <!-- Trailer Preview -->
            <!-- Trailer is not selected (null) -->
            <video
              v-if="trailerPreviewPath == null"
              class="mb-2"
              width="100%"
              :src="localDomain + toUpdateKnowledge.movie"
              controls
            ></video>
            <!-- Trailer is selected (not null) -->
            <video
              v-if="trailerPreviewPath != null"
              class="mb-2"
              width="100%"
              :src="trailerPreviewPath"
              controls
            ></video>





            <!-- Update Btn -->
            <v-btn
              :disabled="!knowledgeForm"
              color="green"
              class="mr-4"
              @click="updateKnowledge"
            >
              <span v-if="!loading" >Update</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
            </v-btn>

            <!-- Error Alert -->
            <v-alert class="mt-3" v-show="errorAlert" dense type="error">
              Update Knowledge Failed!
            </v-alert>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import sidebar_admin from "../components/sidebar_admin.vue";
import utils from "../utils/utils";

export default {
  name: "admin_by",

  components: { sidebar_admin },

  data() {
    return {
      localDomain: utils.constant.localDomain,

      
      headers: [
        // { text: "No", key: "No", value: "item" },
        { text: "Photo", key: "photo", value: "photo" },
        { text: "Plant Name", key: "plantName", value: "plantName" },
        // {text: "Movie" , key: "movie" , value: "movie"},
        // { text: "About Detail", key: "about_detail", value: "about_detail" },
        { text: "Category", key: "category.name", value: "category.name" },
        { text: "CreatedAt", key: "createdAt", value: "createdAt" },
        { text: "UpdatedAt", key: "updatedAt", value: "updatedAt" },
        { text: "UserLikeCount", key: "usercount" , value: "likeCount"},
        // {text: "User",  value:"users"},
        // {text :  "FavPost",  key:"fav" ,   value: "favpost"},
        //  { text: "LikeUnlike Reaction" , key:"likeUnlikeReaction" , value: "likeUnlikeCount"},
        //  { text: "UserName" , key: "user.id" , value: "user.id"},
        { text: "Actions", key: "actions", value: "actions" },
      ],
      knowledgeList: [],

      

      deleteDialog: false,
      toDeleteKnowledge: {},

      updateDialog: false,
      knowledgeForm: false,
      toUpdateKnowledge: {
        knowledge_id: "",
        plantName: "",
        about_detail: "",
        category: 1,
        photo: "",
       
        poster: null,
        
        movie: "",
        trailer: null,
      },
      photoPreviewPath: null,

      errorAlert: false,
      loading: false,

      knowledgeCategoryList: [],
    };
  },

  async created() {
    await this.fetchKnowledgeCategories();
    await this.fetchKnowledges();
  },

  methods: {
    async fetchKnowledges() {
      const resp = await utils.http.get("/knowledge");
      if (resp && resp.status === 200) {
        const data = await resp.json();

        if (data) {
          this.knowledgeList = data;
        }
      }
    },

    async fetchKnowledgeCategories() {
      const resp = await utils.http.get("/admin/category");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.knowledgeCategoryList = data;
        }
      }
    },

    async deleteKnowledge() {
      const resp = await utils.http.del(
        "/admin/knowledge/delete/" + this.toDeleteKnowledge.knowledge_id
      );
      if (resp && resp.status === 200) {
        this.deleteDialog = false;
        // Refresh Knowledges
        await this.fetchKnowledges();
      } else {
        this.errorAlert = true;
      }
    },

    onClickUpdateKnowledge(item) {
      this.updateDialog = true;
      // Copy Object
      this.toUpdateKnowledge = Object.assign({}, item);
      // this.toUpdateKnowledge.poster = null;
      // this.toUpdateMovie.trailer = null;
      // this.photoPreviewPath = null;
      // this.trailerPreviewPath = null;
    },

    async updateKnowledge() {
      // Form Validation
      if (this.$refs.knowledgeForm.validate()) {
        this.errorAlert = false;

        this.loading = true;

        let photo = this.toUpdateKnowledge.photo;
        let movie = this.toUpdateKnowledge.movie;

        // Step 1 -> Update Photo

        // Null -> Photo is not selected
        if (this.toUpdateKnowledge.poster != null) {
          // Update Photo API
          const respPhoto = await utils.http.putMedia(
            "/admin/file/update",
            this.toUpdateKnowledge.poster,
            this.toUpdateKnowledge.poster.type,
            this.toUpdateKnowledge.photo
          );
          if (respPhoto && respPhoto.status === 200) {
            photo = await respPhoto.text();
          } else {
            console.debug("Photo Update Failed");
          }
        }






          // Step 2 -> Update Trailer

          if (this.toUpdateKnowledge.trailer != null) {
          const respTrailer = await utils.http.putMedia(
            "/admin/file/update",
            this.toUpdateKnowledge.trailer,
            this.toUpdateKnowledge.trailer.type,
            this.toUpdateKnowledge.movie
          );
          if (respTrailer && respTrailer.status === 200) {
            photo = await respTrailer.text();
          } else {
            console.debug("Trailer Update Failed");
          }
        }







        // Step 3 -> Update Knowledge

        const respKnowledge = await utils.http.put(
          "/admin/knowledge/update/" + this.toUpdateKnowledge.knowledge_id,
          {
            plantName: this.toUpdateKnowledge.plantName,
            about_detail: this.toUpdateKnowledge.about_detail,
            category: {
              id: this.toUpdateKnowledge.category,
            },
            photo: photo,
            movie: movie,
          }
        );
        if (respKnowledge && respKnowledge.status === 200) {
          this.toUpdateKnowledge.poster = null;
          this.updateDialog = false;
          // Refresh Knowledges
          await this.fetchKnowledges();
        } else {
          this.errorAlert = true;
        }

        this.loading = false;
      }
    },

    onChangePhoto(poster) {
      this.photoPreviewPath = URL.createObjectURL(poster);
    },

    onChangeTrailer(trailer) {
      this.trailerPreviewPath = URL.createObjectURL(trailer);
    },
  },
};
</script>

<style></style>
