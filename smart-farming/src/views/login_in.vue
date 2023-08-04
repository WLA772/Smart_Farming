<template>
    <v-row>

      <v-col cols="6" class="firstimg">
      <img alt="logo" :src="loginPath"  width="90%" height="75%" >
      </v-col>
      
      <v-col cols="6" class="box" >


         <center> <h2> <img alt="logo" :src="balloon"  width="4.3%" height="2%" >WELCOME TO OUR LAND</h2>

          <br>
          <img alt="logo" :src="logoPath"  width="120" height="120" >
        

          <br><br><br><br>
        <!-- Login Form -->
        <v-form ref="loginForm" v-model="loginForm" class="size">
          <!-- Email -->
          <v-text-field
            v-model="email"
            :rules="[
              (v) => !!v || 'Required',
              (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
            ]"
            label="E-mail"
            required
          ></v-text-field>
  
          <!-- Password -->
          <v-text-field
            v-model="password"
            :counter="15"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 15) ||
                'Password must be less than 15 characters',
            ]"
            :type="passwordShow ? 'text' : 'password'"
            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="passwordShow = !passwordShow"
            label="Password"
            required
          ></v-text-field>
  
          <!-- Login Btn -->
          <v-btn
            :disabled="!loginForm"
            color="blue"
            class="mr-4"
            @click="login()"
          >
            <span v-if="!loading">Login</span>
            <v-progress-circular
              v-else
              indeterminate
              color="primary"
            ></v-progress-circular>
          </v-btn>
  
          <!-- Error Msg -->
          <v-alert class="mt-3" v-show="errorAlert" dense type="error">
            Login Failed! <br />
            Email or Password is wrong!
          </v-alert>
        </v-form>
    
      </center>
      </v-col>
    </v-row>
    
  </template>
  
  <script>
  import utils from "../utils/utils";
  
  export default {
    name: "login_in",
  
    components: {},
  
    data() {
      return {
        loginForm: false,
        email: "",
        password: "",
        passwordShow: false,
        // email: "admin@gmail.com",
        // password: "1111",
        errorAlert: false,
        loading: false,

         logoPath: require('@/assets/famer.png'),
         loginPath: require('@/assets/log.png'),
         balloon: require('@/assets/ba.jpg'),
      };
    },
  
    async created() {},
  
    methods: {
      async login() {
        if (this.$refs.loginForm.validate()) {
          this.errorAlert = false;
  
          try {
            this.loading = true;
  
            // API Call
            const resp = await utils.http.post("/user/login", {
              gmail: this.email,
              password: this.password,
            });
            if (resp && resp.status === 200) {
              const data = await resp.json();
              console.log(data)
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
.imge{
  align-items: center;
}

.size{
  width: 300px;
  border-radius: 50%;
  
}
.display{
  display: flex;
  justify-content: flex-end;
}

/* .firstimg{
  position: right;
  height: 100%;
  left: 0;
  bottom: 0;
  z-index: -1;
} */




/* .box{
 width: 100vw;
 height: 100vh;
 display: grid;
 grid-template-columns: repeat(2,1fr);
 grid-gap: 7rem;
 padding: 0 2rem;
} */



</style>