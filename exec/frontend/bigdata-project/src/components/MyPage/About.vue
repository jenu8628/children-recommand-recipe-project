<template>
  <div class="About-container">
    <div class="member-box">
      <div v-for="(member,index) in members" :key="index">
        <div>{{member.name}}</div>
      </div>
    </div>
    <MyPageTab @update="movepath" />
  </div>
</template>

<script>
import MyPageTab from "./MyPageTab";

export default {
    components: {
    "MyPageTab": MyPageTab,
  },
  data() {
    return {
      members : [
        {
          name: "Jeong Su Been"
        },
        {
          name: "Oh Hyun Jeong"
        },
        {
          name: "Bong You Jeong"
        },
        {
          name: "Lee Hyun Woo"
        },
        {
          name: "Kim Byeong Soo"
        },
        {
          name: "Sung Jeong Wook"
        },
      ]
    }
  },
  methods: {
    movepath (event){
      if (this.$route.name === event) {
        window.location.reload()
      } else {
        this.$router.push({name:event})
      }
    }
  },
}
</script>

<style scoped>
.About-container {
  width: 100vw;
  height: 100vh;
  font-family: "BMDOHYEON", sans-serif;
  display: flex;
  align-items: center;
  justify-content: center;
}
.About-container .member-box{
  width: 100vw;
  height: 70vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
}

@font-face {
  font-family: "BMDOHYEON";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
</style>