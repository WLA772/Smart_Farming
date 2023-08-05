<template>
  <v-container>
    <h1>Welcome from your favourites!</h1>
    <v-row>
      <v-col
        cols="3"
        v-for="favknowledge in knowledgeList"
        :key="favknowledge.id"
      >
        <v-card @click="ShowKnowledgeDetails(favknowledge)" height="230">
          <v-card-text>
            <v-img
              :src="localDomain + favknowledge.photo"
              max-height="160"
              cover
            ></v-img>
            <div>{{ favknowledge.plantName }}</div>
            <div>{{ favknowledge.createdAt }}</div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import utils from "../utils/utils";
export default {
  data()  {
    return {

      localDomain: utils.constant.localDomain,

    knowledgeList: [],
   
  };
},

  async created() {
    await this.fetchFavouiteKnowledges();
  },

  methods: {
    async fetchFavouiteKnowledges() {
      // const favPost = await utils.http.post("/user/save", {
      //   favpost_id : this.favpost_id,
      //   users :{favpost_id : this.users}
      // });
      // if(favPost && favPost.status === 200)
      const user = JSON.parse(sessionStorage.getItem("loginUser"));

      // console.log(user)

      const favposts = await utils.http.get("/user/knowledges/"  + user.id);

      //  console.log(favposts)
      if (favposts && favposts.status === 200) {
        const data = await favposts.json();
      // console.log(data)
        if (data) {
          this.knowledgeList = data;
          
        }
      }
    },

    async ShowKnowledgeDetails(favknowledge) {
      this.$router.push({
        path: "/knowledge_details/" + favknowledge.knowledge_id,
      });
    },
  },
};
</script>
