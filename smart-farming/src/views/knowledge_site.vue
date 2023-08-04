<template>
  <div>
    <v-row>
      <!-- Category Menu -->
      <v-col cols="2">
        <v-card>
          <div
            class="categorystyle"
            v-for="(cat, index) in knowledgeCategoryList"
            :key="index"
            link
            @click="onClickCategory(cat)"
          >
            <!-- <v-list> -->
            <v-list-item-content>
              <v-list-item-title>{{ cat.name }}</v-list-item-title>
            </v-list-item-content>

            <!-- <v-list-item-icon>
              <v-icon color="primary">{{ cat.icon }}</v-icon>
            </v-list-item-icon> -->

            <!-- </v-list> -->
          </div>
        </v-card>
      </v-col>

      <v-col cols="10">
        <div v-if="show">
          <v-select
            v-model="selectedPlant"
            :items="plants"
            label="Select"
            hint="Pick your favorite plants"
            persistent-hint
          ></v-select>
        </div>

        <!-- <v-col cols="10" > 
              <div v-if="show">      
                <tag_for_plant></tag_for_plant>
             </div> -->

        <br /><br />

        <!-- Knowledge List -->

        <v-row>
          <v-col
            cols="3"
            v-for="(knowledge, index) in knowledgeList"
            :key="index"
          >
            <v-card @click="goToKnowledgeDetails(knowledge)" height="230">
              <v-card-text>
                <v-img
                  :src="localDomain + knowledge.photo"
                  max-height="160"
                  cover
                ></v-img>
                <div class="text-h6">{{ knowledge.plantName }}</div>
                
                <div>{{ knowledge.createdAt }}</div>
              </v-card-text>
            </v-card>
            <v-card-actions
              ><v-btn
                @click="addToFavourite(knowledge)"
                class="btnposition"
                color="primary"
                :disable:="isDisable"
              >
                save
              </v-btn>
            </v-card-actions>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import utils from "../utils/utils";
export default {
  name: "home_site",

  components: {},

  data() {
    return {
      localDomain: utils.constant.localDomain,

      catList: [],
      knowledgeList: [],
      isDisable: false,

      knowledgeCategoryList: [
        {
          name: "",
          icon: "person",
        },

        {
          name: "",
          icon: "person",
        },

        {
          name: "",
          icon: "person",
        },

        {
          name: "",
          icon: "person",
        },
      ],

      show: false,

      selectedPlant: "",
      plants: [
        "Peanut",
        "Gourd",
        "Bitter Gourd",
        "Carrot",
        "Corn Plant",
        "Sunflower",
        "Banana",
        "Lettuce",
        "Betel",
        "Big Bean",
        "Eggplant",
        "Chickpea",
        "Green Bean",
        "Apple",
        "Orange",
        "Onion",
        "Tomato",
        "Potato",
        "Berry",
        "Sugar Cane",
        "Coffee",
        "Wheat",
        "Chili",
        "Grape",
        "Watermelon",
        "Peach",
        "Cucumber",
        "Pear",
        "Strawberry",
        "Durian",
        "Rose",
        "Lemon",
        "Sesame",
      ],
    };
  },

  async created() {
    await this.fetchKnowledgeCategories();
    await this.fetchKnowledges();
  },

  watch: {
    selectedPlant() {
      this.knowledgeList = this.knowledgeList.filter(
        (knowledge) => knowledge.plantName == this.selectedPlant
      );
    },
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

    goToKnowledgeDetails(knowledge) {
      this.$router.push({
        path: "/knowledge_details/" + knowledge.knowledge_id,
      });
    },

    async onClickCategory(cat) {
      this.show = true;

      const resp = await utils.http.get("/knowledge/category/" + cat.id);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.knowledgeList = data;
        }
      }
    },

    async addToFavourite(knowledge) {
      this.isDisable = !this.isDisable;
      // console.log(sessionStorage.getItem("loginUser"))
      const user = JSON.parse(sessionStorage.getItem("loginUser"));

      const resp = await utils.http.post(
        "/user/knowledge/" + user.id + "/" + knowledge.knowledge_id
      );
      if (resp && resp.stauts === 200) {
        const data = await resp.json();

        if (data) {
          this.knowledgeList = data;
        }
      }

      // this.$store.commit("ADDTO_FAVOURITE", knowledge)
    },

    toggleVisibility() {
      this.isvisible = !this.isvisible;
    },
  },
};
</script>

<style>
.btnposition {
  left: 175px;
}

/* .borderstyle {
  border-style: groove;
} */

.categorystyle {
  :hover {
    background-color: rgb(160, 228, 160);
    color: aliceblue;
  }

  border-style: groove;
}

/* background-color: rgb(160, 228, 160); */
/* .home {
  background-image: url("../assets/home bg.jpg");
  background-size: cover;
  background-position: center;
  background-attachment: 100%;
} */

/* .borderStyle {
  border-style: outset;
} */
</style>
