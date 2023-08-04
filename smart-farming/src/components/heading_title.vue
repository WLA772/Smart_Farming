<template>
  <center>
  <v-app-bar app color="#A7FFEB" >
     <div> <img alt="logo" :src="logoPath"  class="imgsize"></div> 
    <p class="titlecolor">SMART FARMING</p>
   
    <v-spacer></v-spacer>

    
    <!-- <router-link class="mx-2 navlink" to="/about_us">
      <v-icon color="primary">md_admin</v-icon>About Us
         </router-link> -->

         <!-- Home -->
    <router-link class="mx-2 navlink" to="/"
      ><v-icon color="primary">home</v-icon>&nbsp;home</router-link
    >
    &nbsp;&nbsp;&nbsp;



    <!-- Product -->
    <router-link class="mx-2 navlink" to="/knowledge"
      ><v-icon color="primary"></v-icon>&nbsp;Knowledge</router-link
    >
    &nbsp;&nbsp;&nbsp;



     <!-- Product -->
     
     
      <router-link class="mx-2 navlink" to="/product"
      >Product</router-link>
      &nbsp;&nbsp;


       <!-- cart -->
       <!-- <span v-if="isLogin">|</span> -->
      <router-link class="mx-2 navlink" to="/cart">
        <v-icon color="primary">shopping_cart</v-icon>
      </router-link>

      &nbsp;&nbsp;

    <!-- <span v-if="!isLogin">|</span> -->
    <router-link class="mx-2 navlink" to="/favourite_post"
      ><v-icon color="primary">favorite_border </v-icon>Post</router-link
    >
    &nbsp;

   

    <!-- Register -->
    
    <router-link v-if="!isLogin" class="mx-2 navlink" to="/register"
      ><v-icon color="primary">person_add</v-icon>&nbsp;Register</router-link
    >

    <!-- Login -->
    
    <router-link v-if="!isLogin" class="mx-2 navlink" to="/login"
      ><v-icon color="primary">person</v-icon>log in</router-link
    >
    &nbsp;&nbsp;&nbsp;

    <!-- Profile -->
    
    <router-link v-if="isLogin" class="mx-2 navlink" to="/profile"
      ><v-icon color="primary">account_circle</v-icon>&nbsp;profile</router-link
    >
    &nbsp;&nbsp;

    <!-- Login out -->
  
    <a v-if="isLogin" class="mx-2 navlink" @click="logout()"
      ><v-icon color="red">exit_to_app</v-icon>&nbsp;logout</a
    >
  </v-app-bar>
</center>
</template>

<script>
export default {
  name: "heading_title",

  data: () => ({
    loginUser: {},
    isLogin: false,
     logoPath: require('@/assets/homelogo.jpg'),
  }),

  created() {
    // Login User
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        console.log(this.loginUser,'wai')
        return this.$store.state.loginUser;
      },
      (newVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );

    // IsLogin
    this.isLogin = this.$store.state.isLogin;
    this.$store.watch(
      () => {
        return this.$store.state.isLogin;
      },
      (newVal) => {
        this.isLogin = newVal;
      },
      {
        deep: true,
      }
    );
  },

  methods: {
    logout() {
      this.$store.commit("logout");
      // If current path is not home page, Go to home page
      if (this.$route.path != "/") {
        this.$router.push({ path: "/" });
      }
    },
  },
};
</script>

<style scoped>

.imgsize{
  border-radius: 150px;
  height: 38px;
  width: 40px;
}

img{
  
  background-repeat: no-repeat;
  
  
}
.navlink {
  color: black;
  text-decoration: none;
}

.navlink:hover {
  cursor: pointer;
  text-decoration: none;
  background-color: rgb(160, 228, 160);
  color: aliceblue;
}

.navtitle {
  color: white !important;
  text-decoration: none;
  cursor: pointer;
}

.titlecolor {
  color: green;
  font-size: 35px;
}


app{
  border: 1px solid white;
   border-radius: 200px;
   width: 80%;
}



</style>
