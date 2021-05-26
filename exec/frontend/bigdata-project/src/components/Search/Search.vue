<template>
  <div class="search-container">
    <div class="search-bar">
      <div class="search-input-container">
        <input
          type="text"
          id="input-search"
          required="required"
          v-model="search"
          placeholder="재료명을 입력하세요."
        />
        <div class="bar"></div>
      </div>
      <div class="search-png"></div>
    </div>
    <div class="empty" v-if="state">
      <div class="title">오늘은 뭐 먹을래?</div>
      <hr />
      <div class="content">
        #부대찌개 #제육볶음 #닭볶음탕 #카레라이스 #베이컨볶음밥 #잔치국수
        #전복죽 #스팸구이 #꼬막무침
      </div>
    </div>
    <div class="search-result-container start">
      <div class="search-box" v-for="(pic, index) in loadedItems" :key="index">
        <div class="result-card" @click="getRecipeDetail(pic)">
          <div class="result-thumbnail">
            <div
              class="result-recipe_thumbnail"
              :style="'background-image : url(' + pic.recipe_thumbnail + ')'"
            ></div>
          </div>
          <div class="result-header">
            <h2>{{ pic.recipe_name }}</h2>
          </div>
          <div class="result-colorband"></div>
          <!-- <div class="result-desc-content">Recipe By Foodsafetykorea</div> -->
          <div class="result-desc-content">
            <span class="result_recipe_kcal"
              >열량 : {{ pic.recipe_kcal }}(kcal)</span
            >
            <br />
            탄수화물 : {{ pic.recipe_carbohydrate }}(g) <br />
            단백질 : {{ pic.recipe_protein }}(g) <br />
            지방 : {{ pic.recipe_fat }}(g) <br />
            나트륨 : {{ pic.recipe_natrium }}(mg)
          </div>
          <div class="result-actions">
            <div class="result-recipe-rating">
              <img
                src="@/assets/img/star.png"
                class="recipe_rating_star"
                v-for="rating in pic.recipe_rating"
                :key="rating"
              />
              <img
                src="@/assets/img/empty-star.png"
                class="recipe_rating_empty"
                v-for="empty in 5 - pic.recipe_rating"
                :key="empty"
              />
            </div>
            <div class="result-recipe-difficulty">
              <img
                src="@/assets/img/Search/easy.png"
                alt="쉬움"
                v-if="pic.recipe_difficulty == 1"
              />
              <img
                src="@/assets/img/Search/middle.png"
                alt="중간"
                v-if="pic.recipe_difficulty == 0 || pic.recipe_difficulty == 2"
              />
              <img
                src="@/assets/img/Search/hard.png"
                alt="어려움"
                v-if="pic.recipe_difficulty == 3"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="modal" class="modal-window">
      <div>
        <div><p class="modal_content">검색어를 입력해주세요!</p></div>
      </div>
    </div>
    <div class="search-scroll-btn search-scroll" @click="moveToTop">
      <i class="fa fa-angle-up" aria-hidden="true"></i>
      <!-- <i>TOP</i> -->
    </div>
  </div>
</template>

<script>
import http from "@/api/http-common";
import "@/assets/css/Search/Search.css";

export default {
  data() {
    return {
      search: "",
      loadedItems: [],
      state: true,
      modal: false,
    };
  },
  created() {
    this.foodCheck();
  },
  mounted() {
    this.searchbar_animation();
  },
  methods: {
    orderbydifficulty() {
      this.loadedItems.sort(function(a, b) {
        return a.recipe_difficulty > b.recipe_difficulty
          ? -1
          : a.recipe_difficulty < b.recipe_difficulty
          ? 1
          : 0;
      });
    },
    getRecipeDetail(pic) {
      localStorage.setItem("recipe_id", pic.recipe_id);
      this.$router.push({
        name: "RecipeDetail",
        params: {
          props_recipeId: pic.recipe_id,
        },
      });
    },
    SearchByRecipeIngredient() {
      if (this.search == "") {
        this.modal = true;

        setTimeout(() => {
          this.modal = false;
        }, 800);

        return;
      }

      document.querySelector(".search-result-container").classList.add("start");
      setTimeout(() => {
        document
          .querySelector(".search-result-container")
          .classList.remove("start");
      }, 500);
      http
        .get(
          `/recipe/searchByRecipeIngredient?recipe_ingredient=${this.search}`
        )
        .then((res) => {
          this.search = "";
          this.loadedItems = [];
          for (var i = 0; i < res.data.length; i++) {
            this.loadedItems.push(res.data[res.data.length - 1 - i]);
          }

          for (let i = 0; i < this.loadedItems.length; i++) {
            if (this.loadedItems[i].recipe_name.length > 7) {
              this.loadedItems[i].recipe_name =
                this.loadedItems[i].recipe_name.slice(0, 6) + "..";
            }
          }

          this.foodCheck();
        });
    },
    foodCheck() {
      if (this.loadedItems.length < 1) {
        this.state = true;
      } else {
        this.state = false;
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
    searchbar_animation() {
      const search_container = document.querySelector(".search-container");
      const search_input_container = document.querySelector(
        ".search-input-container"
      );
      const input_search = document.querySelector("#input-search");
      const search_png = document.querySelector(".search-png");

      search_container;
      search_input_container;
      input_search;
      search_png;

      input_search.addEventListener("focus", () => {
        setTimeout(() => {
          search_png.classList.add("search-input-active");
        }, 180);
      });

      input_search.addEventListener("keyup", (e) => {
        if (e.keyCode == 13) {
          this.SearchByRecipeIngredient();
        }
      });

      search_container.addEventListener("click", (e) => {
        if (e.target.classList.contains("search-input-container")) {
          return;
        } else if (e.target.id == "input-search") {
          return;
        } else {
          search_png.classList.remove("search-input-active");
        }
      });

      search_png.addEventListener("click", () => {
        this.SearchByRecipeIngredient();

        setTimeout(() => {
          search_png.classList.remove("search-input-active");
        }, 100);
      });

      document.body.addEventListener("click", (e) => {
        if (e.target.classList.contains("search-input-container")) {
          return;
        } else if (e.target.id == "input-search") {
          return;
        } else {
          search_png.classList.remove("search-input-active");
        }
      });
    },
  },
};
</script>
