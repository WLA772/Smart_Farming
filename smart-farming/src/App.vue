<template>
  <!-- SPA - Single Page Application -->
  <v-app>
    <v-main >
       <headingview></headingview> 
      <!-- <img alt="home photo" src="./assets/home bg.jpg"> -->
      <router-view class="mx-3 my-2" style="height: 100%" />
    
      <!-- <footing></footing> -->
    </v-main>
  </v-app>
</template>

<script>
 
 import headingview from "./components/heading_title.vue";
import footing from "./components/footing_part.vue";

export default {
  name: "App",

  components: {
     headingview,
    footing,
    
  },

  data: () => ({}),
};
</script>

<style>

.headingcolor{
    color: #A7FFEB;
  }

  
 
</style>
