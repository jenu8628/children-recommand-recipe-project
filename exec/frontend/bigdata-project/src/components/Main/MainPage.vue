<template>
  <div>
    <div v-if="!loginCheck" class="MainPage-container">
      <div class="main-top main-not-login">
        <div class="main_logo">
          오구 오구<br />
          우리 아이
        </div>
        <div class="main-recipe-btn">
          <p @click="moveToTop">레시피 검색하러 가기</p>
          <div class="leaves-1"></div>
          <div class="leaves-2"></div>
          <div class="leaves-3"></div>
        </div>
      </div>
      <Search />
    </div>
    <div v-else class="MainPage-container">
      <div class="main-top">
        <div class="main_logo">
          오구 오구<br />
          우리 아이
        </div>
        <div class="main-charts-container">
          <h2>
            <span>우리 아이 </span>
            <span id="child_name">{{ child.child_name }}</span>
            <span>의</span><br /><br />
            <span class="title">하루 권장 영양소 채우기</span>
          </h2>
          <!-- <canvas id="bar-chart-grouped" width="800" height="600"></canvas> -->
          <div class="pie-chart-container">
            <vue-easy-pie-chart
              class="chart-row"
              :percent="allKcal"
              :line-width="8"
              :size="150"
              :animate="500"
              :bar-color="'#f3951b'"
            >
              <div class="chart-row-wrapper">
                <p class="chart-name">칼로리</p>
                <p class="chart-value">{{ allKcal }} %</p>
              </div>
            </vue-easy-pie-chart>
            <vue-easy-pie-chart
              class="chart-row"
              :percent="allCarbo"
              :line-width="8"
              :size="150"
              :animate="500"
              :bar-color="'#40aa57'"
            >
              <p class="chart-name">탄수화물</p>
              <p class="chart-value">{{ allCarbo }} %</p>
            </vue-easy-pie-chart>
            <vue-easy-pie-chart
              class="chart-row"
              :percent="allProtein"
              :line-width="8"
              :size="150"
              :animate="500"
              :bar-color="'#40aa57'"
              ><p class="chart-name">단백질</p>
              <p class="chart-value">{{ allProtein }} %</p></vue-easy-pie-chart
            >
            <vue-easy-pie-chart
              class="chart-row"
              :percent="allFat"
              :line-width="8"
              :size="150"
              :animate="500"
              :bar-color="'#40aa57'"
              ><p class="chart-name">지방</p>
              <p class="chart-value">{{ allFat }} %</p></vue-easy-pie-chart
            >
          </div>
        </div>
        <div v-if="loginTypeCheck">
          <div class="main-chat-btn">
            <i class="fas fa-comment-dots" @click="moveLink('ChatList')"></i>
          </div>
        </div>
      </div>
      <TodayTip v-if="tip" @update="closeModal" />
      <div class="comment-region" id="example-comment">
        <NutritionRecommend v-if="swipe" id="nutrition" />
        <MaterialRecommend v-else id="material" />
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/Main/MainPage.css";
import NutritionRecommend from "./NutritionRecommend";
import MaterialRecommend from "./MaterialRecommend";
import TodayTip from "./TodayTip";
import http from "@/api/http-common";
import VueEasyPieChart from "vue-easy-pie-chart";
import "vue-easy-pie-chart/dist/vue-easy-pie-chart.css";
import Search from "../Search/Search";

export default {
  components: {
    MaterialRecommend: MaterialRecommend,
    NutritionRecommend: NutritionRecommend,
    TodayTip: TodayTip,
    VueEasyPieChart,
    Search,
  },
  data() {
    return {
      child: [],
      swipe: false,
      tip: false,
      allKcal: 0,
      allCarbo: 0,
      allProtein: 0,
      allFat: 0,
      standardKcal: 0,
      standardCarbo: 0,
      standardProtein: 0,
      standardFat: 0,
      // 스크롤 이벤트 관련 변수
      prevScrollHeight: 0, // 현재 스크롤 위치(yOffset)보다 이전에 위치한 스크롤 섹션들의 높이값의 합
      currentScene: 0, // 현재 활성화된(눈 앞에 보고있는) 씬($index)
      enterNewScene: false, //새로운 scene이 시작된 순간 true (에러방지)
      loginCheck: false,
      loginTypeCheck: null,
    };
  },
  methods: {
    getMyChild() {
      var child_id = localStorage.getItem("child_id");
      http
        .get("/child/getMyChild?child_id=" + child_id)
        .then((response) => {
          this.child = response.data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    getTodayNutritionalInfo() {
      var child_id = localStorage.getItem("child_id");
      var today = new Date();
      var month = today.getMonth() + 1;
      var day = today.getDate();
      if (month < 10) month = "0" + month;
      if (day < 10) day = "0" + day;
      today = today.getFullYear() + "-" + month + "-" + day;
      var formData = new FormData();
      formData.append("child_id", child_id);
      formData.append("today", today);

      http
        .get("/nutrition/getTodayNutritionalInfo", {
          params: { child_id: child_id, today: today },
        })
        .then((response) => {
          this.allKcal = response.data.kcal;
          this.allCarbo = response.data.carbohydrate;
          this.allProtein = response.data.protein;
          this.allFat = response.data.fat;

          http
            .get("/nutrition/getNutritionalStandard", {
              params: { child_id: child_id },
            })
            .then((response) => {
              this.standardKcal = response.data.kcal;
              this.standardCarbo = response.data.carbohydrate;
              this.standardProtein = response.data.protein;
              this.standardFat = response.data.fat;

              this.allKcal = ((this.allKcal / this.standardKcal) * 100).toFixed(
                1
              );
              this.allCarbo = (
                (this.allCarbo / this.standardCarbo) *
                100
              ).toFixed(1);
              this.allProtein = (
                (this.allProtein / this.standardProtein) *
                100
              ).toFixed(1);
              this.allFat = ((this.allFat / this.standardFat) * 100).toFixed(1);

              if (this.allKcal > 100) this.allKcal = 100.0;
              if (this.allCarbo > 100) this.allCarbo = 100.0;
              if (this.allProtein > 100) this.allProtein = 100.0;
              if (this.allFat > 100) this.allFat = 100.0;
            });
        });
    },
    moveLink(event) {
      if (this.$route.name === event) {
        window.location.reload();
      } else {
        this.$router.push({ name: event });
      }
    },
    moveToTop() {
      window.scrollTo({
        top:
          document.body.scrollHeight -
          document.querySelector(".search-container").scrollHeight +
          140,
        left: 0,
        behavior: "smooth",
      });
    },
    // 모달 닫기
    closeModal() {
      this.tip = false;
    },
    goToTop() {
      window.scrollTo({
        top: document.querySelector(".MainPage-container").scrollTop,
        left: 0,
        behavior: "smooth",
      });
    },
  },
  created() {},
  mounted() {
    this.goToTop();

    const token = localStorage.getItem("jwt");
    const childToken = localStorage.getItem("child_jwt");
    const loginTypeof = localStorage.getItem("loginType");
    if (token || childToken) {
      this.loginCheck = true;
      if (loginTypeof === "1") {
        this.loginTypeCheck = true;
      } else if (loginTypeof === "2") {
        this.loginTypeCheck = false;
      }
    }

    this.getMyChild();
    // this.createChart("myChart", this.charData);
    // this.createBarChart();
    // this.scroll();
    // this.playAnimation();

    this.getTodayNutritionalInfo();
    // window.addEventListener("scroll", (e) => {
    //   e.stopPropagation();
    //   e.preventDefault();
    //   e.stopImmediatePropagation();
    //   // this.scroll();
    // });

    // document
    //   .querySelector("#example-comment")
    //   .load(window.location.href + "#example-comment");
  },
  destroyed() {},
};
</script>

<style>
.comment-region {
  /* cursor: move; */
  overflow: hidden;
  transition: background-color 0.5s;
  /* Uncomment below for some weird kinetic events */
  /*transition: all 0.5s;*/
}
</style>
