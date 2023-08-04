<template>
  <div>
    <!-- Photo, Knowledge Info -->
    <!-- Photo -->
    <v-col>
      <center>
        <v-img
          width="655px"
          height="335px"
          :src="localDomain + knowledge.photo"
          contain
        ></v-img>
      </center>
    </v-col>
    <!-- Knowledge Info -->
    <v-col>
      <center>
        <div>{{ knowledge.plantName }}</div>
        <br>
        <div  class="justify">
          {{ knowledge.about_detail }}
        </div>
        <br />

        <!-- Movie -->
        <div class="mx-2 mt-5">
          <video
            class="mb-2"
            width="80%"
            height="50%"
            :src="localDomain + knowledge.movie"
            controls
          ></video>
        </div>
      </center>
      <br />
    </v-col>
    <center>
      <p class="textcolor">Is this post useful for you?</p>
      <v-btn color="primary" width="150px" @click="likeKnowledgeDetails()">
        <v-icon> thumb_up</v-icon>

        Yes &nbsp;&nbsp;&nbsp;
        <div>{{ knowledge.likeCount}}</div>
      </v-btn>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

      <v-btn color="primary" width="150px" @click="unlikeKnowledgeDetails()">
        <v-icon>thumb_down</v-icon>

        No &nbsp;&nbsp;
        <!-- <div>{{ knowledge.likeCount }}</div> -->
      </v-btn>
    </center>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "knowledge_details",

  data() {
    return {
      localDomain: utils.constant.localDomain,

      // Knowledge ID from Path
      knowledgeid: this.$route.params.id,

      knowledge: [],
      autoGetLike: [],
    };
  },

  async created() {
    await this.fetchKnowledge();
    await this.autogetLikeCount();
  },

  methods: {
    async fetchKnowledge() {
      const resp = await utils.http.get("/knowledge/" + this.knowledgeid);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.knowledge = data;
        }
      }
    },

    async likeKnowledgeDetails() {
      alert("Thank you for your feedback.");
      const user = JSON.parse(sessionStorage.getItem("loginUser"));
      const likeCount = await utils.http.post(
        "/like/" + this.knowledgeid + "/" + user.id
      );
      if (likeCount && likeCount.status === 200) {
        const data = await likeCount.json();
        // console.log(data);
        if (data) {
          this.knowledge = data;
        }
      }
    },

    async unlikeKnowledgeDetails() {
      alert("Thank you for your feedback.");
      const user = JSON.parse(sessionStorage.getItem("loginUser"));
      const unlikeCount = await utils.http.post(
        "/unlike/" + this.knowledgeid + "/" + user.id
      );
      if (unlikeCount && unlikeCount.status === 200) {
        const data = await unlikeCount.json();
        // console.log(data);
        if (data) {
          this.knowledge = data;
        }
      }
    },


    async autogetLikeCount(){
      const getLikeCount = await utils.http.get(
        "/knowledge/likeCount/" + this.knowledgeid
      )
      if (getLikeCount && getLikeCount.status === 200){
        const data = await getLikeCount.json();

        console.log(data);
        if(data){
          this.autoGetLike = data;
        }
      }
    }
  },
};
</script>

<style scoped>

btn {
  background-color: blue;
}

.textcolor{color:blue;}

.justify{
  font-family: Georgia, 'Times New Roman', Times, serif;
  text-align: left;
  text-decoration: dashed;
}
</style>
