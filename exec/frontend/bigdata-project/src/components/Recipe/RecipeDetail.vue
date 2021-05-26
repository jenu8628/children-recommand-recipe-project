<template>
  <div class="recipe-detail-container">
    <div class="card">
      <!-- 사진과 좋아요 표시 영역 -->
      <div class="card-box">
        <i class="fas fa-chevron-left" @click="locationBack"></i>
        <div
          class="header"
          :style="{
            backgroundImage: 'url(' + recipeInfo.recipe_thumbnail + ')',
          }"
        >
          <!-- <i class="far fa-heart"></i> -->
          <i @click="likechange" class="far fa-bookmark"></i>
        </div>
      </div>
      <!-- 제목,시간, 내용 영역  -->
      <div class="text">
        <h1 class="food">
          {{ recipeInfo.recipe_name }}
        </h1>
        <div class="stars">
          <img
            style="width: 8%; height: 5%"
            src="@/assets/img/star.png"
            class="recipe_rating_star"
            v-for="(full,fullIndex) in fullStar"
            :key="fullIndex"
          />
          <img
          style="width: 8%; height: 8%"
            src="@/assets/img/empty-star.png"
            class="recipe_rating_empty"
            v-for="(empty,emptyIndex) in emptyStar"
            :key="emptyIndex+lengthFullStar"
          />
        </div>
        <!-- 재료 -->
        <h3 style="text-align: center">재료</h3>
        <p class="info">{{ recipeInfo.recipe_ingredient }}</p>
      </div>
      <!-- 요리과정 -->
      <div class="process" v-for="(detail, idx) in recipeProcess" :key="idx">
        <div>
          <div class="index">{{ idx + 1 }}</div>
        </div>
        <div>
          <!--img style max-width:300px -->
          <img
            :src="detail.processSrc"
            style="width: 300px; max-height: 260px; border-radius: 10px;"
            alt=""
          />
        </div>
        <div class="info">{{ detail.processDetail }}</div>
      </div>
      <div class="button-container">
        <div class="button-box">
          <div @click="openModal" class="carrot">완료</div>
          <div class="leaves-1"></div>
          <div class="leaves-2"></div>
          <div class="leaves-3"></div>
        </div>
      </div>
      <!-- <div class="complete-btn" @click="openModal">요리 완료</div> -->
    </div>
    <div style="display:none;">{{props_recipeId}}</div>
    <FoodRecordModal :propsrecipeId="props_recipeId" v-if="modal" @update="closeModal()" />
  </div>
</template>

<script>
import "@/assets/css/Recipe/RecipeDetail.css";
import FoodRecordModal from "./FoodRecordModal";
import http from "@/api/http-common.js";

export default {
  components: {
    FoodRecordModal : FoodRecordModal,
  },
  data() {
    return {
      like : false,
      modal: false,
      props_recipeId: null,
      recipeInfo: [],
      recipeProcess: [],
      recipeP: [],
      fullStar:[],
      lengthFullStar:0,
      emptyStar: [],
    };
  },
  mounted() {
    this.props_recipeId = localStorage.getItem('recipe_id')
    this.getRecipe();
    this.getRecipeProcess();
    this.getFavorite()
  },
  methods: {
    getFavorite() {
      http
      .get('/recipe/getAllScrapRecipe', {
        params:{
          scrap_parentId: localStorage.getItem('parent_id')
        }
      })
      .then((res)=>{
        const likeDisc = res.data.filter((data)=>{
          return data.recipe_id.includes(localStorage.getItem('recipe_id'))
        })
        if (likeDisc.length !== 0) {
          this.like = true
          const icon = document.querySelector(".fa-bookmark");
          icon.style.color = "#40aa57";
        }
      })
    },
    openModal() {
      setTimeout(() => {
        this.modal = true;
      }, 250);
    },
    closeModal() {
      setTimeout(() => {
        this.modal = false;
      }, 250);
    },
    likechange() {
      this.like = !this.like;
      const icon = document.querySelector(".fa-bookmark");
      if (this.like) {
        icon.style.color = "#40aa57 ";
        this.recipeScrap()
      } else {
        icon.style.color = "white";
        icon.style.boxShadow = "";
        this.recipeScrapCancel()
      }
    },
    getRecipe() {
      http.get("/recipe/getRecipe/" + this.props_recipeId).then((response) => {
        this.recipeInfo = response.data;
        for (let i = 0; i<Math.round(this.recipeInfo.recipe_rating);i++) {
          this.fullStar.push('0')
        }
        this.lengthFullStar = this.fullStar.length
        for (let i =0 ; i<5-Math.round(this.recipeInfo.recipe_rating);i++){
          this.emptyStar.push('0')
        }
      });
    },
    getRecipeProcess() {
      http
        .get("/recipe/getRecipeProcess/" + this.props_recipeId)
        .then((response) => {
          var list = response.data;
          this.recipeP = list;
          this.recipeProcess.push({
            processSrc: this.recipeP.recipeProcess_image1,
            processDetail: this.recipeP.recipeProcess_no1,
          });
          if (this.recipeP.recipeProcess_no2 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image2,
              processDetail: this.recipeP.recipeProcess_no2,
            });
          }
          if (this.recipeP.recipeProcess_no3 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image3,
              processDetail: this.recipeP.recipeProcess_no3,
            });
          }
          if (this.recipeP.recipeProcess_no4 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image4,
              processDetail: this.recipeP.recipeProcess_no4,
            });
          }
          if (this.recipeP.recipeProcess_no5 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image5,
              processDetail: this.recipeP.recipeProcess_no5,
            });
          }
          if (this.recipeP.recipeProcess_no6 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image6,
              processDetail: this.recipeP.recipeProcess_no6,
            });
          }
          if (this.recipeP.recipeProcess_no7 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image7,
              processDetail: this.recipeP.recipeProcess_no7,
            });
          }
          if (this.recipeP.recipeProcess_no8 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image8,
              processDetail: this.recipeP.recipeProcess_no8,
            });
          }
          if (this.recipeP.recipeProcess_no9 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image9,
              processDetail: this.recipeP.recipeProcess_no9,
            });
          }
          if (this.recipeP.recipeProcess_no10 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image10,
              processDetail: this.recipeP.recipeProcess_no10,
            });
          }
          if (this.recipeP.recipeProcess_no11 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image11,
              processDetail: this.recipeP.recipeProcess_no11,
            });
          }
          if (this.recipeP.recipeProcess_no12 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image12,
              processDetail: this.recipeP.recipeProcess_no12,
            });
          }
          if (this.recipeP.recipeProcess_no13 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image13,
              processDetail: this.recipeP.recipeProcess_no13,
            });
          }
          if (this.recipeP.recipeProcess_no14 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image14,
              processDetail: this.recipeP.recipeProcess_no14,
            });
          }
          if (this.recipeP.recipeProcess_no15 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image15,
              processDetail: this.recipeP.recipeProcess_no15,
            });
          }
          if (this.recipeP.recipeProcess_no16 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image16,
              processDetail: this.recipeP.recipeProcess_no16,
            });
          }
          if (this.recipeP.recipeProcess_no17 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image17,
              processDetail: this.recipeP.recipeProcess_no17,
            });
          }
          if (this.recipeP.recipeProcess_no18 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image18,
              processDetail: this.recipeP.recipeProcess_no18,
            });
          }
          if (this.recipeP.recipeProcess_no19 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image19,
              processDetail: this.recipeP.recipeProcess_no19,
            });
          }
          if (this.recipeP.recipeProcess_no20 != "") {
            this.recipeProcess.push({
              processSrc: this.recipeP.recipeProcess_image20,
              processDetail: this.recipeP.recipeProcess_no20,
            });
          }
        });
    },
    recipeScrap(){
      http.
      get('/recipe/scrapToRecipe',{
        params:{
          scrap_parentId:localStorage.getItem("parent_id"),
          scrap_recipeId:localStorage.getItem('recipe_id')
        }
      })
    },
    recipeScrapCancel(){
      http.
      get('/recipe/cancelScrapToRecipe',{
        params:{
          scrap_parentId:localStorage.getItem("parent_id"),
          scrap_recipeId:localStorage.getItem('recipe_id')
        }
      })
    },
    locationBack(){
      history.go(-1);
    }
  },
  created() {
    
  },
};
</script>

<style scoped>
</style>