<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>

      <!-- Create Knowledge Form -->
      <v-col cols="10">
        <v-form class="px-10" ref="knowledgeForm" v-model="knowledgeForm">
          <!-- PlantName -->
          <v-text-field v-model="plantName" :counter="50" :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 50) ||
              'plantName must be less than 50 characters',
          ]" label="PlantName" required></v-text-field>

          <!-- Movie -->
          <v-file-input v-model="trailer" label="Video" show-size prepend-icon="mdi-video" placeholder="Choose video"
            accept="video/mp4" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                !v ||
                v.size < 100000000 ||
                'Video size should be less than 1024 MB!',
            ]" @change="onChangeTrailer"></v-file-input>

          <!-- Trailer Preview -->
          <video v-if="trailerPreviewPath != null" class="mb-2" width="100%" :src="trailerPreviewPath" controls></video>

          <!-- About_detail -->
          <v-textarea v-model="about_detail" label="About_detail" rows="8" 
            @keydown.enter.prevent="handleEnterKey"></v-textarea>

          <!-- Category -->
          <v-select v-model="category" :items="knowledgeCategoryList" item-text="name" item-value="id"
            :rules="[(v) => !!v || 'Required']" label="Category" required>
          </v-select>

          <!-- Photo -->
          <v-file-input v-model="poster" label="Photo" show-size prepend-icon="mdi-camera"
            placeholder="Choose photo Image" accept="image/png, image/jpeg" :rules="[
              (v) => !!v || 'Required',
              (v) =>
                !v ||
                v.size < 20000000 ||
                'Image size should be less than 20 MB!',
            ]" @change="onChangePhoto"></v-file-input>

          <!-- Photo Preview -->
          <v-img v-if="photoPreviewPath != null" :src="photoPreviewPath" width="200" height="150" contain>
          </v-img>

          <!-- Create Btn -->
          <v-btn :disabled="!knowledgeForm" color="success" @click="createKnowledge()">
            <span v-if="!loading">Create Knowledge</span>
            <v-progress-circular v-else indeterminate color="primary"></v-progress-circular>
          </v-btn>

          <!-- Error Alert For Knowledge -->
          <v-alert class="mt-3" v-show="errorAlert" dense type="error">
            Create Knowledge Failed!
          </v-alert>
          <!-- Same Name Error Alert -->
          <v-alert class="mt-3" v-show="errorAlert2" dense type="error">
            Knowledge with same name exists!
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
  name: "admin_create_knowledge",
  components: { sidebar_admin },

  data() {
    return {
      knowledgeForm: false,

      plantName: "",
      about_detail: "",

      category: "",

      errorAlert: false,
      errorAlert2: false,

      loading: false,

      poster: null,
      photoPreviewPath: null,
      trailer: null,
      trailerPreviewPath: null,

      knowledgeCategoryList: [],
    };
  },

  async created() {
    await this.fetchKnowledgeCategories();
  },

  methods: {
    handleEnterKey(event) {
      if (event.key === "Enter" && !event.shiftKey) {
        this.about_detail += "\n";
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

    async createKnowledge() {
      // Form Validation
      if (this.$refs.knowledgeForm.validate()) {
        this.errorAlert = false;
        this.errorAlert2 = false;

        this.loading = true;

        // Step 1 -> Name Check

        let plantNameCheckOK = false;
        const respplantNameCheck = await utils.http.get(
          "/admin/knowledge/plantName/" + this.plantName
        );
        if (respplantNameCheck && respplantNameCheck.status === 200) {
          const data = await respplantNameCheck.json();
          // Undefined, Null, True
          if (data && data === true) {
            this.errorAlert2 = true;
          } else {
            this.errorAlert2 = false;
            plantNameCheckOK = true;
          }
        } else {
          this.errorAlert2 = true;
        }

        if (plantNameCheckOK) {
          let respPhotoData = null;
          let respTrailerData = null;

          // Step 2 -> Photo

          const respPhoto = await utils.http.postMedia(
            "/admin/file/create",
            this.poster,
            this.poster.type
          );
          if (respPhoto.status === 200) {
            respPhotoData = await respPhoto.text();
          } else {
            this.errorAlert = true;
          }

          // Step 2 -> Trailer

          const respTrailer = await utils.http.postMedia(
            "/admin/file/create",
            this.trailer,
            this.trailer.type
          );
          if (respTrailer.status === 200) {
            respTrailerData = await respTrailer.text();
          } else {
            this.errorAlert = true;
          }

          // Step 4 -> Create Knowledge

          if (respPhotoData) {
            // Create Knowledge API
            const respKnowledge = await utils.http.post(
              "/admin/knowledge/create",
              {
                plantName: this.plantName,
                about_detail: this.about_detail,
                category: { id: this.category },
                photo: respPhotoData,
                movie: respTrailerData,
              }
            );
            if (respKnowledge && respKnowledge.status === 200) {
              this.$router.push({ path: "/admin" });
            } else {
              this.errorAlert = true;
            }
          }
        }

        this.loading = false;
      }
    },

    onChangePhoto(photo) {
      this.photoPreviewPath = URL.createObjectURL(photo);
    },

    onChangeTrailer(trailer) {
      this.trailerPreviewPath = URL.createObjectURL(trailer);
    },
  },
};
</script>
