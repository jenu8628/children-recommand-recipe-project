<template>
  <div id="app">
    <div class="nav-header">
      <!-- <div v-if="record" class="food-record">
        <i @click="foodRecord" class="fas fa-calendar-alt" style="font-size:2.5em;"></i>
      </div> -->
      <div v-if="loginCheck">
        <div v-if="loginTypeCheck" class="nav" id="nav">
          <div class="nav-item nav-count-1" @click="navigation">
            <i class="fas fa-home"  @click="moveLink('MainPage')"></i>
          </div>
          <div class="nav-item nav-count-2" @click="navigation">
            <i class="fas fa-search"  @click="moveLink('Search')"></i>
          </div>
          <div class="nav-item nav-count-3" @click="navigation">
            <i class="fas fa-user"  @click="moveLink('MyPage')"></i>
          </div>
          <div class="nav-item nav-count-4" @click="navigation">
            <i class="fas fa-sign-out-alt"  @click="logout" ></i>
          </div>
          <div class="mask" id="mask" @click="navigation">
            <i v-if="active" class="fas fa-times"></i>
            <i v-else class="fas fa-bars fa-lg" ></i>
          </div>
        </div>
        <div v-else class="nav2" id="nav2">
          <div class="nav-item nav-count-1" @click="navigation">
            <i class="fas fa-home"  @click="moveLink('MainPage')"></i>
          </div>
          <div class="nav-item nav-count-2" @click="navigation">
            <i class="fas fa-comment-dots"  @click="moveLink('Chat')"></i>
            
            </div>
          <div class="nav-item nav-count-3" @click="navigation">
            <i class="fas fa-check-double"  @click="moveLink('TodoList')"></i>
          </div>
          <div class="nav-item nav-count-4" @click="navigation">
            <i class="fas fa-sign-out-alt"  @click="logout" ></i>
          </div>
          <div class="mask2" id="mask2" @click="navigation">
            <i v-if="active" class="far fa-times" ></i>
            <i v-else class="fas fa-bars"></i>
          </div>
        </div>
      </div>

      <div v-else class="nav" id="nav">
        <div class="mask" id="mask" >
          <i class="fas fa-sign-in-alt" @click="loginPush"></i>
          <!-- <img src="@/assets/img/icon/login.png" width="30" height="30" @click="loginPush"> -->
          
        </div>
      </div>
    </div>
    <router-view :key="$route.fullPath"/>
  </div>
</template>

<script>

export default {

  name: 'App',
  data() {
    return {
      active: false,
      loginCheck : false,
      cnt : 0,
      loginTypeCheck:null,
    }
  },
  components: {
  },
  computed:{
    // record () {
    //   if (this.$route.name === "FoodRecord" || this.$route.name === "FoodRecordDetail" ){
    //     return true
    //   } else {
    //     return false
    //   }
    // },
  },
  mounted() {
    const token = localStorage.getItem('jwt')
    const childToken = localStorage.getItem('child_jwt') 
    const loginTypeof = localStorage.getItem('loginType')  
    if (token || childToken) {
      this.loginCheck = true
      if (loginTypeof === '1') {
        this.loginTypeCheck = true
      } else if  (loginTypeof === '2') {
        this.loginTypeCheck = false
      }
    }
  },
  methods: {
    navigation () {
      this.active = !this.active
      const nav = document.querySelector(".nav")
      const mask = document.querySelector('.mask')
      const navHeader = document.querySelector('.nav-header')

      const nav2 = document.querySelector(".nav2")
      const mask2 = document.querySelector('.mask2')

      if (this.loginTypeCheck){
        nav.classList.toggle('active')
        mask.classList.toggle('active')
      } else {
        nav2.classList.toggle('active')
        mask2.classList.toggle('active')
      }
      if (this.cnt === 1) {
        setTimeout(() => {
          navHeader.classList.toggle('active')
          
        }, 100);
      } else {
        navHeader.classList.toggle('active')
      }
      this.cnt = (this.cnt + 1 ) % 2
    },
    setup() {
      
    },
    moveLink (event) {
      if (this.$route.name === event) {
        window.location.reload()
      } else {
        this.$router.push({name:event})
      }
    },
    logout () {
      this.loginCheck = false
      localStorage.clear()
      this.$router.push({path:'/login'})
      location.reload()
    },
    loginPush () {
      if (this.$route.name === "Login") {
        window.location.reload();
      } else {
        this.$router.push({ name: "Login" });
      }
    },
    // foodRecord () {
    //   if (this.$route.name === "FoodRecord") {
    //     window.location.reload();
    //   } else {
    //     this.$router.push({ name: "FoodRecord" });
    //   }
    // }
  },
}
</script>

<style>
@font-face {
    font-family: 'WandohopeB';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/WandohopeB.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'BinggraeSamanco-Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10@1.0/BinggraeSamanco-Bold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@import url(//fonts.googleapis.com/earlyaccess/jejumyeongjo.css);

.jejumyeongjo * { 
 font-family: 'Jeju Myeongjo', serif;
}
@font-face {
    font-family: 'Independence_hall';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2102-01@1.0/Independence_hall.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@import url('https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap');

/* * { font-family: "BinggraeSamanco-Bold" !important; } */
 
 * { font-family: 'Independence_hall', serif;
}

@font-face {
  font-family: "Jal_Onuel";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10-21@1.0/Jal_Onuel.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

* {
  font-family: "Jal_Onuel", serif;
}
#app {
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  /* font-family: 'WandohopeB'; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
.nav-header{
  position: fixed;
  overflow: hidden;
  width: 20vw;
  right: 0;
  height: 63px;
  top: 0;
  z-index: 999;
}
.nav-header.active{
  height: 50%;
  width: 100vw;
}
.nav-header .nav,
.nav-header .nav2 {
  right: 15px;
  z-index: 20;
  width: 45px;
  top: 15px;
  height: 45px;
  display: block;
  position: absolute;
  line-height: 45px;
  border-radius: 50%;
  box-shadow: 0 0 5px 0 rgba(0, 0, 0, .75)
}
.nav-header .nav .mask,
.nav-header .nav2 .mask2 {
  z-index: 21;
  color: #fff;
  width: inherit;
  height: inherit;
  cursor: pointer;
  font-size: 1.2em;
  text-align: center;
  border-radius: 50%;
  position: absolute;
  background: #40aa57;
  transition: all .1s ease-in-out 0s;
  padding-top: 0.2em;
}
.nav-header .nav.active .mask,
.nav-header .nav2.active .mask2 {
  background: #40aa57;
  /* transform: rotate(-135deg) */
}
.nav-header .nav:after,
.nav-header .nav2:after {
  top: 0;
  left: 0;
  content: '';
  width: inherit;
  height: inherit;
  border-radius: 50%;
  position: absolute;
  background: #40aa57;
  transition: all .1s ease-in-out 0s
}
.nav-header .nav.active:after,
.nav-header .nav2.active:after {
  top: -150px;
  left: -150px;
  width: 350px;
  height: 350px;
  
}
.nav-header .nav .nav-item, 
.nav-header .nav2 .nav-item {
  top: 0;
  left: 0;
  z-index: 19;
  width: 45px;
  height: 45px;
  color: #fff;
  font-size: 24px;
  transform: none;
  line-height: 45px;
  border-radius: 50%;
  position: absolute;
  text-align: center;
  transition: all .3s cubic-bezier(.68, 1.55, .265, 1)
}
.nav.active .nav-count-1, 
.nav2.active .nav-count-1 {
  transform: translate(-130px, 0px)
}
.nav.active .nav-count-2, 
.nav2.active .nav-count-2 {
  transform: translate(-110px, 65px)
}
.nav.active .nav-count-3,
.nav2.active .nav-count-3 {
  transform: translate(-70px, 120px)
}
.nav.active .nav-count-4,
.nav2.active .nav-count-4 {
  transform: translate(0px, 140px)
}
.fas.fa-calendar-alt{
  position: absolute;
  left: 20px;
  top: 20px;
  color: #40aa57;
}

/* .nav-header .food-record{
  height: 63px;
  display: flex;
  justify-content: center;
  background: white;
}
.nav-header .food-record .food-today{
  font-family: "BMDOHYEON", sans-serif;
  position: absolute;
  top: 25px;
  font-size: 1.4rem;
} */

@font-face {
  font-family: "BMDOHYEON";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
</style>
