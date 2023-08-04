<template>
  <div>
    <center>
      <!-- Profile Card -->
      <v-card max-width="800">
        <v-card-text>
          <v-row>
            <v-col cols="6">
              <br />
              <div class="titlewel">Welcome</div>
              <br />
              <p class="userfont">
                {{ loginUser.name }}
              </p>
              <br />
              <div class="gmailfont">
                <div v-if="loginUser.role == 'admin'">Admin</div>
                <div>{{ loginUser.gmail }}</div>
                <br />
                <div>Joining Date: {{ loginUser.startJoinDate }}</div>
              </div>
            </v-col>

            <v-col cols="6" class="userprofile">
              <img alt="logo" :src="loginPath" />
            </v-col>
          </v-row>
        </v-card-text>

        <!-- Change Pwd Btn -->
        <v-btn @click="changePwd()">
          <div class="colorpwd">Change Password</div>
        </v-btn>
      </v-card>
    </center>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "profile_in",
  data() {
    return {
      loginUser: {},

      loginPath: require("@/assets/profilephoto.jpg"),
    };
  },

  created() {
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );
  },

  methods: {
    changePwd() {
      this.$router.push({ path: "/changePwd" });
    },

    async openRecordDialog() {
      const resp = await utils.http.get("/record?userID=" + this.loginUser.id);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.recordList = data;
        }
      }
      this.recordDialog = true;
    },
  },
};
</script>

<style>
.titlewel {
  font-size: 50px;
  font-family: Georgia, "Times New Roman", Times, serif;
  font-style: italic;
}

.userfont {
  font-size: 35px;
  font-family: Georgia, "Times New Roman", Times, serif;
  font-style: italic;
}

.gmailfont {
  font-size: 20px;
  font-family: Georgia, "Times New Roman", Times, serif;
  font-style: normal;
}

.colorpwd {
  font-size: 20px;
  font-family: Georgia, "Times New Roman", Times, serif;
  font-style: italic;
  color: darkturquoise;
  background-repeat: no-repeat;
}
</style>
