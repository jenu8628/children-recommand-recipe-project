<template>
  <div class="tab-container" @click="closeMenu">
      <!-- <svg class="bord">
        <path class="vector" d="M0 10 C10,0 20,0 30,0 H130 C160,0 160,30 187.5,30 C215,30 215,0 245,0 H345 C355,0 365,0 375,10 V80 H0z" />
      </svg> -->
      <div class="tab-box">

      <div class="icons">
        <div class="all-content">
          <div class="menu-btn-parent">
            <div class="parent-btn">
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
              <p v-if="parentContent" @click="move('MyPage','parent')">회원정보 수정</p>
            </div>
            <div class="parent-btn">
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
              <p v-if="parentContent" @click="move('MyPageFavorites','parent')">즐겨찾기</p>
            </div>
            <!-- <div class="parent-btn">
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
              <p v-if="parentContent" @click="move('MyPageRefrigerator','parent')">냉장고관리</p>
            </div> -->
            <div class="parent-btn">
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
              <p v-if="parentContent" @click="move('FoodRecord','parent')">식단기록</p>
            </div>
          </div>
          <div class="menu-btn-child">
            <div class="child-btn">
              <p v-if="childContent" @click="move('ChildChoice', 'child')">아이선택</p>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
            <div class="child-btn">
              <p v-if="childContent" @click="move('MyPageChildInfo','child')">아이정보 수정</p>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
            <div class="child-btn">
              <p v-if="childContent" @click="move('MyPageAllergy','child')">알러지 관리</p>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
            <div class="child-btn">
              <p v-if="childContent" @click="move('TodoList','child')">할일 관리</p>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
            <div class="child-btn">
              <p v-if="childContent" @click="move('ChatList','child')">채팅방</p>
              <div class="leaves-1"></div>
              <div class="leaves-2"></div>
              <div class="leaves-3"></div>
            </div>
          </div>
        </div>
        <div class="parent" @click="transParent">
          <i class="fas fa-user-cog"></i>
        </div>
        <div class="child" @click="transChild">
          <i class="fas fa-child"></i>
        </div>
        <!-- <img src="@/assets/img/MyPage/userBlack.png" class="parent" @click="transParent"> -->
        <!-- <img src="@/assets/img/MyPage/teddybear.png" class="child" @click="transChild"> -->
        <!-- <img src="@/assets/img/MyPage/about.png" class="about" @click="transAbout"> -->
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/MyPage/tab.css";
export default {
  data() {
    return {
      parentContent : false,
      childContent : false,
      aboutContent : false,
      parent : 0,
    };
  },
  mounted () {
  },
  methods: {
    closeMenu () {
      if (this.parentContent) {
        this.transParent()
      } else if (this.childContent) {
        this.transChild()
      } else if (this.aboutContent) {
        this.transAbout()
      }
    },
    transParent () {
      const allContent = document.querySelector('.all-content')
      const leave = document.querySelectorAll('.parent-btn')
      if (!this.childContent && !this.aboutContent) {
        if (!this.parentContent) {
          document.querySelector('.menu-btn-child').classList.add('active')
          setTimeout(() => {
            allContent.classList.add('active')
            document.querySelector('.tab-container').classList.add('active')
            document.querySelector('.icons').classList.add('active')
            for (let i = 0; i<leave.length; i++) {
              leave[i].classList.add('active')
            }
          }, 300);
          setTimeout(() => {
            this.parentContent = !this.parentContent
          }, 400);
        } else {
          this.parentContent = !this.parentContent
          allContent.classList.remove('active')
          document.querySelector('.tab-container').classList.remove('active')
          document.querySelector('.icons').classList.remove('active')
          for (let i = 0; i<leave.length; i++) {
              leave[i].classList.remove('active')
            }
          setTimeout(() => {
            document.querySelector('.menu-btn-child').classList.remove('active')
          }, 250);
        }
      }else {
        if (this.childContent) {
          this.transChild()
        } else {
          this.transAbout()
        }
      }
    },
    transChild () {
      const allContent = document.querySelector('.all-content')
      const leave = document.querySelectorAll('.child-btn')
      if (!this.parentContent && !this.aboutContent) { 
        if (!this.childContent) {
          document.querySelector('.menu-btn-parent').classList.add('active')
          setTimeout(() => {
            for (let i = 0; i<leave.length; i++) {
              leave[i].classList.add('active')
            }
          }, 250);
          setTimeout(() => {
            allContent.classList.add('active')
            document.querySelector('.tab-container').classList.add('active')
            document.querySelector('.icons').classList.add('active')
          }, 300);
          setTimeout(() => {
            this.childContent = !this.childContent
          }, 400);
        } else {
          this.childContent = !this.childContent
          allContent.classList.remove('active')
          document.querySelector('.tab-container').classList.remove('active')
          document.querySelector('.icons').classList.remove('active')
          for (let i = 0; i<leave.length; i++) {
              leave[i].classList.remove('active')
            }
          setTimeout(() => {
            document.querySelector('.menu-btn-parent').classList.remove('active')
          }, 250);
        }
      }else {
        if (this.parentContent) {
          this.transParent()
        } else {
          this.transAbout()
        }
      }
    },
    move (event, id) {
      if (id === 'parent') {
        this.transParent()
      } else if (id ==='child'){
        this.transChild()
      }
      setTimeout(() => {
        this.$emit('update', event)
      }, 500);
    },
  },
};
</script>

<style scoped>
.tab-container {
  position: fixed;
  bottom: 0;
  z-index: 80;
  height: 10vh;
  width:100vw;
  /* background: #40aa57; */
  border-radius: 20px 20px 0 0;
}
.tab-container.active{
  position: fixed;
  width: 100vw;
  z-index: 999;
  height: 100vh;
  bottom: 0;
  /* background: rgba(0, 0, 0, 0.7); */
  backdrop-filter: blur(2px);
}
.tab-container .tab-box{
  position: fixed;
  bottom: 0;
  height: 10vh;
  width:100%;
  border-radius: 20px 20px 0 0;
  background: #40aa57;
  box-shadow: 0px 0px 20px 3px gray;
}
.tab-container .icons {
  display: flex;
  justify-content: space-around;
  place-items: center;
  width: 100vw;
  /* background: #65a4f2; */
  position: absolute;
  bottom: -2px;
  height: 100%; 
  font-size: 1.7rem;
}
.tab-container .icons.active {
  height: 10vh; 
}
.tab-container .icons .closeParent {
  position: absolute;
  top: 3%;
  right: 5%;
  z-index: 5;
}
.all-content{
  position: absolute;
  visibility: hidden;
  top:-63px;
  width:70px;
  height:70px;
  border-radius: 50%;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1), bottom 0.25s cubic-bezier(0.5, 0, 0.25, 1);
}
.all-content.active{
  top: -290px;
  width: 200px;
  height: 250px;
  border-radius: 1.5rem;
  visibility: visible;
  display: flex;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1), bottom 0.25s cubic-bezier(0.1, 0.1, 0, 1);
  transition-delay: 0.015s;
}
.all-content .menu-btn-parent{
  width: 100%;
  text-align: center;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  top: 40%; /* 원래 top:20%인데 40%로 수정 */
  align-items: center;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.all-content .menu-btn-parent .parent-btn{
  width: 80%;
  opacity: 0;
  text-align: center;
  border-radius: 10px;
  background: #f3951b;
  color:white;
  margin-top: 10%;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
  border-radius: 60% 20px 20px 60%;
}
.all-content .menu-btn-parent .parent-btn.active{
  opacity: 1;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.all-content .menu-btn-parent .parent-btn .leaves-1,
.all-content .menu-btn-parent .parent-btn .leaves-2,
.all-content .menu-btn-parent .parent-btn .leaves-3{
  position: absolute;
  z-index: -1;
  width: 5px;
  height: 5px;
  border-right: 40px solid #40aa57;
  border-bottom: 10px solid transparent;
  border-radius: 50%;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
  right: -16%;
}
.all-content .menu-btn-parent .parent-btn .leaves-1{
  transform: rotate(-30deg);
  top:0%;
}
.all-content .menu-btn-parent .parent-btn .leaves-2{
  top:30%;
}
.all-content .menu-btn-parent .parent-btn .leaves-3{
  transform: rotate(30deg);
  top:60%;
}
.all-content .menu-btn-parent .parent-btn p{
  width: 100%;
  left:2%;
  font-size: 1.2rem;
  padding: 3.6% 0;
}

/* 아이메뉴 */
.all-content .menu-btn-child{
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.all-content .menu-btn-child .child-btn{
  width: 80%;
  opacity: 0;
  text-align: center;
  border-radius: 10px;
  background: #f3951b;
  color:white;
  margin-top: 10%;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
  border-radius: 60% 20px 20px 60%;
}
.all-content .menu-btn-child .child-btn.active{
  opacity: 1;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
}
.all-content .menu-btn-child .child-btn p{
  width: 100%;
  left:2%;
  font-size: 1.2rem;
  padding: 3.6% 0;
}
.all-content .menu-btn-child .child-btn .leaves-1,
.all-content .menu-btn-child .child-btn .leaves-2,
.all-content .menu-btn-child .child-btn .leaves-3{
  position: absolute;
  z-index: -1;
  width: 5px;
  height: 5px;
  border-right: 40px solid #40aa57;
  border-bottom: 10px solid transparent;
  border-radius: 50%;
  transition: 0.25s cubic-bezier(0.25, 0, 0, 1);
  right: -16%;
}
.all-content .menu-btn-child .child-btn .leaves-1{
  transform: rotate(-30deg);
  top:8%;
}
.all-content .menu-btn-child .child-btn .leaves-2{
  top:38%;
}
.all-content .menu-btn-child .child-btn .leaves-3{
  transform: rotate(30deg);
  top:68%;
  
}
.all-content .menu-btn-child.active{
  display: none;
}
.all-content .menu-btn-parent.active{
  display: none;
}
/* 부모구간 */
.tab-container .icons .parent {
  position: absolute;
  left:50px;
  z-index: 1;
  width:70px;
  height:70px;
  font-size: 2rem;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  opacity: 1;
  transition-duration: 0.10s;
}
.tab-container .icons .parent:active {
  color: #f3951b;
  transition-timing-function: linear;
  transform: translateY(5%);
}
/* 여긴 아이 구간 */
.tab-container .icons .child {
  position: absolute;
  right:50px;
  z-index: 1;
  width:70px;
  height:70px;
  font-size: 2.4rem;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 1;
  transition-duration: 0.20s;
}
.tab-container .icons .child:active {
  color: #f3951b;
  transition-timing-function: linear;
  transform: translateY(5%);
}
/* .tab-container .bord {
  width: 100vw;
  height: 10vh;
  border-radius: 20% 20% 0% 0%;
  position:absolute;
  bottom:-2px;
  opacity: 0.98;
}
.tab-container svg .vector {
  transition: all 0.25s cubic-bezier(0.25, 0, 0, 1);
  fill: #40aa57;
  
}
.tab-container svg .vector.open {
  d: path("M0 10 C10,0 20,0 30,0 H130 C160,0 160,0 187.5,0 C215,0 215,0 245,0 H345 C355,0 365,0 375,10 V80 H0z");
} */
</style>