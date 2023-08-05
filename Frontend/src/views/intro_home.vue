<template>
  <div class="bg">
    <br /><br />
    <center>
      <div>
        <p>
          Farming is not just a job,<br />
          &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
          &nbsp;&nbsp; &nbsp;&nbsp; it's a way to help our &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; world.
        </p>
        <br /><br /><br /><br />

        <v-row>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-col cols="3">
            <img alt="logo" :src="homethird" class="homethree" />
          </v-col>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-col cols="3">
            <img alt="logo" :src="homesecond" class="hometwo" />
          </v-col>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-col cols="3">
            <img alt="logo" :src="homefirst" class="homeone" />
          </v-col>
        </v-row><br><br>

        <v-row>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-col cols="3">
            <img alt="logo" :src="homefour" class="homethree" />
          </v-col>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-col cols="3">
            <img alt="logo" :src="homefive" class="hometwo" />
          </v-col>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <v-col cols="3">
            <img alt="logo" :src="homesix" class="homeone" />
          </v-col>
        </v-row>

      </div>
    </center>
  </div>
</template>

<script>
export default {
  data() {
    return {
      homefirst: require("@/assets/homefirst.jpg"),
      homesecond: require("@/assets/homesecond.jpg"),
      homethird: require("@/assets/homethird.jpg"),
      homefour: require("@/assets/homecult.jpg"),
      homefive: require("@/assets/homefertilizer.jpg"),
      homesix: require("@/assets/homepesticide.jpg"),
    };
  },
};
</script>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
}

.bg {
  background-image: url("../assets/home bg.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: 100%;
}

.homeone {
  border-radius: 200px;
  width: 270px;
  height: 170px;
}

.hometwo {
  border-radius: 200px;
  width: 270px;
  height: 170px;
}

.homethree {
  border-radius: 200px;
  width: 270px;
  height: 170px;
}

p {
  width: 60%;
  font-size: 50px;
  font-style: italic;
  font-family: "Times New Roman", Times, serif;
  font-weight: 200;
  text-align: center;
  color: aliceblue;
}
</style>
