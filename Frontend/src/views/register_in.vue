<template>
  <div>

    
    <center >

      <v-row>
      <v-col cols="6" class="leftcolor">
        <br><br><br><br><br><br>
        <p class="welcomesize">Welcome Back!</p>
        <p class="welcomecolor">To help you, please connected with <br>us and register with your personal details.</p>
      </v-col>
    
      <v-col cols="6" >
      <v-card  max-width="650" height="600">
      <!-- Register Form -->
      <v-form ref="registerForm" v-model="registerForm"  >
        <img alt="logo" :src="loginPath" >
        <p class="titlecolor">Create Account</p>
        <!-- Name -->
        <v-text-field v-model="name" :counter="20" :rules="[
          (v) => !!v || 'Required',
          (v) =>
            (v && v.length <= 20) || 'Name must be less than 20 characters',
        ]" label="Name" required></v-text-field>

        <!-- Email -->
        <v-text-field v-model="email" :rules="[
          (v) => !!v || 'Required',
          (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ]" label="E-mail" required></v-text-field>

        <!-- Password -->
        <v-text-field v-model="password" :counter="15" :rules="[
          (v) => !!v || 'Required',
          (v) =>
            (v && v.length <= 15) ||
            'Password must be less than 15 characters',
        ]" label="Password" required></v-text-field>

        <!-- Register Btn -->
        <v-btn :disabled="!registerForm" color="blue" class="mr-4" @click="register()">
          <span v-if="!loading">Register</span>
          <v-progress-circular v-else indeterminate color="primary"></v-progress-circular>
        </v-btn>

        <!-- Error Alert -->
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          Register Failed!
        </v-alert>
      </v-form>
    </v-card>
  </v-col>
</v-row>
    </center>
  
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "register_in",

  components: {},

  data() {
    return {
      registerForm: false,

      name: "",
      email: "",
      password: "",
      // name: "Mg Mg",
      // email: "test@gmail.com",
      // password: "1111",

      loginPath: require('@/assets/registers.jpg'),

      errorAlert: false,
      loading: false,
    };
  },

  async created() { },

  methods: {
    async register() {
      if (this.$refs.registerForm.validate()) {
        this.errorAlert = false;

        try {
          this.loading = true;

          // API Call
          const resp = await utils.http.post("/user/register", {
            name: this.name,
            gmail: this.email,
            password: this.password,
          });
          if (resp.status === 200) {
            const data = await resp.json();
            if (data) {
              // Store Login Info in Vuex
              this.$store.commit("setLoginUser", data);
              // If Admin -> Go to Admin
              // If User -> Go to Home
              if (data.role == "admin") {
                this.$router.push({ path: "/admin" });
              } else {
                this.$router.push({ path: "/" });
              }
            }
          } else {
            this.errorAlert = true;
          }
        } catch (error) {
          console.log(error);
        } finally {
          this.loading = false;
        }
      }
    },
  },
};
</script>

<style>
.size {
  width: 500px;
  height: 100px;
}

/* .bgimg {
  background-image: url("../assets/register.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: 100%;
} */


.welcomesize{
  font-size: 60px;
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  font-style: italic;
}

.welcomecolor{
  font-size: 30px;
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  font-style: italic;
  justify-content: left;
  
}


.titlecolor{
  font-size: 70px;
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  font-style: italic;
  color: green;
}

.leftcolor{
  background-color: lightgreen;
}

.rightcolor{
  background-color:azure;
}
</style>
